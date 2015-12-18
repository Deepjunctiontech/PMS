package in.junctiontech.project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import in.junctiontech.project.image.ImageSelection;

/**
 * Created by JUNCTION SOFTWARE on 24-Nov-15.
 */
public class SendEmployeeProjectImages {
    private static SendEmployeeProjectImages ourInstance;
    private final Context context;

    public static SendEmployeeProjectImages getInstance(Context context) {
        if(ourInstance==null)
            ourInstance = new SendEmployeeProjectImages(context);
        return ourInstance;
    }

    private SendEmployeeProjectImages(Context context) {
        this.context=context;

    }

    public void sendImageData(List<ImageSelection> imageSelectionList) {
        boolean check=false;
        for (int i = 0; imageSelectionList.size()>i; i++) {

            ImageSelection imageSelection=imageSelectionList.get(i);
            if("pending".equalsIgnoreCase(imageSelection.getStatus())) {
                check=true;
                new UploadFileToServer(
                        imageSelection.getProject_id(),
                        imageSelection.getTask_id(),
                        imageSelection.getImage_location()

                ).execute();
            }
            if(!check)
                Utility.showToast(context,"Already Send");
        }

    }

    private class UploadFileToServer extends AsyncTask<Void, Void, String> {


        private final String image_location, projectId, taskId;

        public UploadFileToServer(String projectId, String taskId,  String image_location) {
            this.image_location = image_location;
            this.projectId = projectId;
            this.taskId = taskId;
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://junctiondev.cloudapp.net/zeroerp/remoteapi/project_image_update");

            File sourceFile = new File(image_location);
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                //   publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                //          SharedPreferences sp =c.getSharedPreferences("Login", c.MODE_PRIVATE);
                // Adding file data to http body
                //          entity.addPart("userID", new StringBody(sp.getString("userID", "Not Found")));

                entity.addPart("image_name", new FileBody(sourceFile));
                entity.addPart("project_id", new StringBody(projectId));
                entity.addPart("task_id", new StringBody(taskId));
                SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(context);
                entity.addPart("database_name",  new StringBody(sharedPrefs.getString("organization_name", "NULL")));

                // Extra parameters if you want to pass to server

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

         /*   if(responseString.contains("success")) {
                SQLiteDatabase db = DBHandler.super.getReadableDatabase();
                ContentValues cv= new ContentValues();
                cv.put("status", "success");
                long i= db.update("ImageSelection", cv, "id=? AND room_id=? AND type=?", new String[]{room_id, id, type});
                Toast.makeText(c,i+"",Toast.LENGTH_LONG).show();
                db.close();
            }*/
            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            //Log.e(TAG, "Response from server: " + result);
            super.onPostExecute(result);
            // showing the server response in an alert dialog
            //     Toast.makeText(c,result,Toast.LENGTH_LONG).show();
            Utility.showToast(context, result);
        //   PMSDatabase.getInstance(context).updateImageStatus(image_location);  // TODO  GET KEY FROM SERVER AND UPDATE IN DATABASE STATUS
            try {
                JSONObject jsonObject = new JSONObject(result);
                if("success".equalsIgnoreCase(jsonObject.getString("status")))
                {
//                   PMSDatabase.getInstance(context).updateImageStatus(jsonObject.getString("image_location"));
                    PMSDatabase.getInstance(context).updateImageStatus(image_location);
                    Utility.showToast(context, "success");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            showAlert(result);
        }


    }

    /**
     * Method to show alert dialog
     */
    private void showAlert(String message) {


        Context current;


        current = ImageSelectionActivity.getContext();

       /* if (((Activity) current).isFinishing()) {
            if (((Activity) c).isFinishing()) {
                //Toast
                current = MainScreen.getContext();
                if (((Activity) current).isFinishing())
                    return;
                else
                    ((MainScreen) current).onResume();
            } else
                current = c;
        } else
           */
        /*if(ImageSelectionActivity.isActive)*/
        ((ImageSelectionActivity)current).onResume();


        AlertDialog.Builder builder = new AlertDialog.Builder(current);
        builder.setMessage(message).setTitle("Response from Servers")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
    class AndroidMultiPartEntity extends MultipartEntity

    {

        private final ProgressListener listener;

        public AndroidMultiPartEntity(final ProgressListener listener) {
            super();
            this.listener = listener;
        }

        public AndroidMultiPartEntity(final HttpMultipartMode mode,
                                      final ProgressListener listener) {
            super(mode);
            this.listener = listener;
        }

        public AndroidMultiPartEntity(HttpMultipartMode mode, final String boundary,
                                      final Charset charset, final ProgressListener listener) {
            super(mode, boundary, charset);
            this.listener = listener;
        }

        @Override
        public void writeTo(final OutputStream outstream) throws IOException {
            super.writeTo(new CountingOutputStream(outstream, this.listener));
        }

        public static interface ProgressListener {
            void transferred(long num);
        }

        public static class CountingOutputStream extends FilterOutputStream {

            private final ProgressListener listener;
            private long transferred;

            public CountingOutputStream(final OutputStream out,
                                        final ProgressListener listener) {
                super(out);
                this.listener = listener;
                this.transferred = 0;
            }

            public void write(byte[] b, int off, int len) throws IOException {
                out.write(b, off, len);
                this.transferred += len;
                this.listener.transferred(this.transferred);
            }

            public void write(int b) throws IOException {
                out.write(b);
                this.transferred++;
                this.listener.transferred(this.transferred);
            }
        }

    }

