package in.junctiontech.pms;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.junctiontech.pms.employee.Employee;

import static in.junctiontech.pms.PMSOtherConstant.*;


/**
 * Created by Junction Software on 30-Sep-15.
 */
public class SendEmployeeData {

    private static SendEmployeeData sendEmployeeData;
    private final StringRequest stringRequest;
    private final Gson gson;
    private final Context context;
    private final Employee employee;
    private PMSDatabase pmsDatabase;
    private final RequestQueue queue;
    private String data;


    private SendEmployeeData(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        gson=new GsonBuilder().create();
        employee =new Employee(context);
        pmsDatabase=PMSDatabase.getInstance(context);
        // String Request For Sending Data initialize here because we restrict multiple object creation of String Request

        stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //TODO check success from server
                        if (response.equalsIgnoreCase("true")) {
                            Log.d("onResponse()", "Success");
                           Toast.makeText(SendEmployeeData.this.context, "Entry Deleted",
                                    Toast.LENGTH_SHORT).show();
                            pmsDatabase.deleteEmployeeData();
                        }

                        // Display the first 500 characters of the response string.
                  //      Toast.makeText(SendEmployeeData.this.context, response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onErrorResponse()", "Error");
                String err = error.getMessage();
                if (error instanceof NoConnectionError) {
                    err = "No Internet Access\nCheck Your Internet Connection.";
                }

              // Toast.makeText(SendEmployeeData.this.context, err, Toast.LENGTH_LONG).show();

            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new LinkedHashMap<>();
                param.put("employeeData",data);
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("abc", "value");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 2, 2));
    }

    public static SendEmployeeData getInstance(final Context context) {

        if (sendEmployeeData == null) {
            sendEmployeeData = new SendEmployeeData(context.getApplicationContext());

        }
        return sendEmployeeData;
    }

    public void send() {

       if( pmsDatabase.getNumberOfEmployeeRecord()>0){
         data=  gson.toJson(pmsDatabase.getEmployeeData(employee));
           queue.add(stringRequest);
       }

    }

}
