package in.junctiontech.project;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import in.junctiontech.project.employee.EmployeeOtherDetails;
import in.junctiontech.project.employeeproject.Expense;
import in.junctiontech.project.employeeproject.Project;
import in.junctiontech.project.employeeproject.Receipt;

import static in.junctiontech.project.PMSOtherConstant.URL_UPDATE;

/**
 * Created by Junction Software on 02-Dec-15.
 */
public class SendProjectData {

    public static void SendData(final Context context, final String data) {
        RequestQueue queue = Volley.newRequestQueue(context);
        // String Request For Sending Data initialize here because we restrict multiple object creation of String Request

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://junctiondev.cloudapp.net/zeroerp/remoteapi/project_update",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //TODO check success from server
                        if (response.equalsIgnoreCase("true")) {
                            Log.d("onResponse()", "Success");

                        }

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            try {

                                JSONArray jsonArray = jsonObject.getJSONArray("expense_list");

                                int countRecord = jsonArray.length();
                                if (countRecord > 0) {
                                    List<Expense> expenses = new ArrayList<>(countRecord);

                                    for (int i = 0; i < countRecord; i++) {
                                        JSONObject n = jsonArray.getJSONObject(i);
                                        if ("success".equalsIgnoreCase(n.getString("status"))) {
                                            expenses.add(new Expense(n.getString("key"), n.getString("status")));
                                        }

                                    }

                                    PMSDatabase.getInstance(context).updateExpenseStatus(expenses);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                JSONArray jsonArray = jsonObject.getJSONArray("receipt_list");

                           int countRecord = jsonArray.length();
                            if (countRecord > 0) {
                                List<Receipt> receipts = new ArrayList<>(countRecord);

                                for (int i = 0; i < countRecord; i++) {
                                    JSONObject n = jsonArray.getJSONObject(i);
                                    if ("success".equalsIgnoreCase(n.getString("status"))) {
                                        receipts.add(new Receipt(n.getString("key" ), n.getString("status")));
                                    }

                                }

                                PMSDatabase.getInstance(context).updateReceiptStatus(receipts);

                            }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Utility.showToast(context,response);
                        Log.d("onResponse()", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onErrorResponse()", "Error");
                String err = error.getMessage();
                if (error instanceof NoConnectionError) {
                    err = "No Internet Access\nCheck Your Internet Connection.";
                }
                Utility.showToast(context,err);

            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new LinkedHashMap<>();
                param.put("projectData", data);
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("abc", "value");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 2, 2));
        queue.add(stringRequest);
    }


}
