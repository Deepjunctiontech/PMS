package in.junctiontech.project;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.junctiontech.project.image.ImageSelection;

import static in.junctiontech.project.PMSOtherConstant.IMAGE_DIRECTORY_NAME_MEDIUM;
import static in.junctiontech.project.PMSOtherConstant.IMAGE_DIRECTORY_NAME_ORIGINAL;
import static in.junctiontech.project.PMSOtherConstant.IMAGE_DIRECTORY_NAME_THUMBNAIL;

public class ImageSelectionActivity extends AppCompatActivity {

    static boolean isActive = false;
    private static final int CONSTANT_IMAGE = 1000;
    private GridView gv;

    private static final String IMAGE_DIRECTORY_NAME = "PMS-ORIGINAL";
    private static String path;

    private int itemBackground;
    int click;
    private ArrayList<File> list;
    private ArrayList<String> status;
    private ArrayList total[];
    private Uri fileUri;
    private PMSDatabase db;
    //  private myGridAdapter obj;
    private Spinner image_spinner_projectId, image_spinner_taskId;
    private ArrayAdapter<String> obj1;
    private String[] spinnerArray = {};
    private TextView textGallery;
    private static Context c;
    private String[] list_of_projects, list_of_tasks;
    private Object fileFromFolder;
    private ImageView imageViewTesting;
    private PMSDatabase pmsDatabase;
    private ImageSelection imageSelection;
    private ImageView fab_image_button;
    private ArrayAdapter adapterProjectTask;
    private ImageGridAdapter imageGridAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_seletion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        referenceInitialization();
        eventRegistration();

    }

    private void referenceInitialization() {
        fab_image_button = (ImageView) findViewById(R.id.fab_image_button);
        imageSelection = new ImageSelection();
        pmsDatabase = PMSDatabase.getInstance(this);
        gv = (GridView) findViewById(R.id.new_open_gridView);
        image_spinner_taskId = (Spinner) findViewById(R.id.image_spinner_taskId);
        image_spinner_projectId = (Spinner) findViewById(R.id.image_spinner_projectId);




        String selectedProjectId = this.getIntent().getStringExtra("PROJECT_ID");
        List<String> projectListIDS = pmsDatabase.getProjectIDS();
        projectListIDS = (projectListIDS != null) ? projectListIDS : new ArrayList<String>(1);
        projectListIDS.add(0, "null");
            fab_image_button.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapterProject = new ArrayAdapter(this, R.layout.myspinner_layout, projectListIDS);
            adapterProject.setDropDownViewResource(R.layout.myspinner_dropdown_item);

            image_spinner_projectId.setAdapter(adapterProject);
            image_spinner_projectId.setSelection(adapterProject.getPosition(selectedProjectId));
        //updateGridView();

    }

    private void populateDataForTaskList(String selectedProjectId) {
        List<String> taskListIDS = pmsDatabase.getTaskIDSFromProjectID(selectedProjectId);
        taskListIDS = (taskListIDS != null) ? taskListIDS : new ArrayList<String>(1);
        taskListIDS.add(0, "null");
        if (adapterProjectTask == null) {
            Log.d("ADAPTER", "CREATED");
            adapterProjectTask = new ArrayAdapter(this, R.layout.myspinner_layout, taskListIDS);
            adapterProjectTask.setDropDownViewResource(R.layout.myspinner_dropdown_item);
            image_spinner_taskId.setAdapter(adapterProjectTask);
            String selectedTaskId = this.getIntent().getStringExtra("TASK_ID");
            image_spinner_taskId.setSelection(adapterProjectTask.getPosition(selectedTaskId));
            // for null no issue, when user directly come to this page TASK_ID not get, no problm
        } else {
            Log.d("ADAPTER", "MODIFIED");
            adapterProjectTask.clear();
            adapterProjectTask.addAll(taskListIDS);
            adapterProjectTask.notifyDataSetChanged();
        }


    }


    private void eventRegistration() {

        image_spinner_projectId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    imageSelection.setProject_id(parent.getItemAtPosition(position) + "");
                        populateDataForTaskList(imageSelection.getProject_id());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

            image_spinner_taskId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null)
                        imageSelection.setTask_id(parent.getItemAtPosition(position) + "");
                    //    Utility.showToast(ImageSelectionActivity.this, list_of_tasks[position]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(ImageSelectionActivity.this, ViewImage.class).putExtra("image", imageGridAdapter.getItem(position).getImage_location()));

            }
        });
    }


    public static Context getContext() {
        return c;
    }


    @Override
    public boolean onSupportNavigateUp() {
        // startActivity(new Intent(this, STEP2.class));
        finish();
        return true;
    }


    public void fabClick(View v) {
        if(!"null".equalsIgnoreCase(imageSelection.getProject_id()))
        captureImage();
            else
            Utility.showToast(this,"Please select project id");


    }

    public void fabClickSend(View v) {
        List list = pmsDatabase.getImageData();
        if (list != null) {
            //  Utility.showToast(this, "Send:" + list.size());
            SendEmployeeProjectImages sendEmployeeProjectImages = SendEmployeeProjectImages.getInstance(this);
            sendEmployeeProjectImages.sendImageData(list);
        }
        else
        Utility.showToast(this,"No Image In Database");
    }

    @Override
    public void onResume() {
        super.onResume();
        this.c = this;
        updateGridView();

    }


    private void captureImage() {
        if (!getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            Utility.showToast(this, "Sorry! Your device doesn't support camera");
            return;

        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        fileUri = getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        // startActivityForResult(intent, CONSTANT_IMAGE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            ;//  TODO CHANGE DIRECTORY NAME TO THUMBNAIL
            imageSelection.setImage_location(fileUri.getPath().replace(IMAGE_DIRECTORY_NAME, "PMS-THUMBNAIL"));
            imageSelection.setStatus("pending");
            startActivityForResult(intent, CONSTANT_IMAGE);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // if the result is capturing Image
        if (requestCode == CONSTANT_IMAGE) {
            if (resultCode == RESULT_OK) {
                pmsDatabase.setImageData(imageSelection);


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);

                compressImage(IMAGE_DIRECTORY_NAME_THUMBNAIL, bitmap, 50);
                options.inSampleSize = 4;
                bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                compressImage(IMAGE_DIRECTORY_NAME_MEDIUM, bitmap, 75);
                //   imageViewTesting.setImageBitmap(bitmap);

                updateGridView();


            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Utility.showToast(ImageSelectionActivity.this, "User cancelled image capture");

            } else {
                // failed to capture image
                Utility.showToast(ImageSelectionActivity.this, "Sorry! Failed to capture image");
            }


        }
    }

    private void compressImage(String folder, Bitmap bitmap, int number) {

        // External sdcard location
        File mediaStorageDir = getFileFromFolder(folder);
        if (mediaStorageDir == null)
            return;

        File abc = new File(mediaStorageDir + path);

        FileOutputStream out;
        try {
            out = new FileOutputStream(abc);
            bitmap.compress(Bitmap.CompressFormat.JPEG, number, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /*
     * returning image / video
     */
    private File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = getFileFromFolder(IMAGE_DIRECTORY_NAME_ORIGINAL);
        if (mediaStorageDir == null)
            return null;

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile;

        path = File.separator +
                "USER_ID=" +
                getSharedPreferences("Login", MODE_PRIVATE).getString("user_id", "Not Found")
                + "," + "PROJECT_ID=" + imageSelection.getProject_id() + "," + "TASK_ID=" + imageSelection.getTask_id() + "," + timeStamp + ".jpg";
        Utility.showToast(this,path);
        mediaFile = new File(mediaStorageDir.getPath() + path);

        return mediaFile;
    }

    public File getFileFromFolder(final String folder) {

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folder);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(folder, "Oops! Failed create "
                        + folder + " directory");
                return null;
            }
        }
        return mediaStorageDir;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
        // outState.putInt("CLICK", click);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
        //   click = savedInstanceState.getInt("CLICK");

    }

    private void updateGridView() {
        List list = pmsDatabase.getImageData();
        if (list != null) {
            imageGridAdapter = new ImageGridAdapter(ImageSelectionActivity.this, list);
            gv.setAdapter(imageGridAdapter);
        }

    }

    class ImageGridAdapter extends BaseAdapter {

        private final Context context;
        private List<ImageSelection> imageSelections;

        public ImageGridAdapter(Context context, List imageSelections) {
            // TODO this String array iterate get view according to size
            this.context = context;
            this.imageSelections = imageSelections;

        }

        @Override
        public int getCount() {
            return imageSelections.size();
        }

        @Override
        public ImageSelection getItem(int position) {
            return imageSelections.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.d("vishal", position + "");
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.gridview_image_design, parent, false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView);

            ImageSelection imageSelection = imageSelections.get(position);


            TextView tv = (TextView) convertView.findViewById(R.id.statusImage);
            tv.setText(imageSelection.getStatus());
            iv.setImageURI(Uri.parse(imageSelection.getImage_location()));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setLayoutParams(new android.widget.FrameLayout.LayoutParams(220, 220));


            return convertView;
        }


    }
}


// NOT WORKING
               /* Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageViewTesting.setImageBitmap(imageBitmap);
                compressImage("PMS-AUTOMATIC-THUMBNAIL", imageBitmap, 100);*/
