package in.junctiontech.project;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import in.junctiontech.project.employee.Employee;

import static in.junctiontech.project.PMSOtherConstant.URL_REGISTER;

/**
 * Created by Junction Software on 15-Nov-15.
 */

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout text_name, text_mobile_no, text_organization_name, text_password;
    private EditText edit_name, edit_mobile_no, edit_organization_name, edit_password,edit_employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        referenceInitialization();
    }

    private void referenceInitialization() {
        text_name = (TextInputLayout) this.findViewById(R.id.register_name_text);
        text_mobile_no = (TextInputLayout) this.findViewById(R.id.register_mobile_no_text);
        text_organization_name = (TextInputLayout) this.findViewById(R.id.register_organization_name_text);
        text_password = (TextInputLayout) this.findViewById(R.id.register_password_text);

        edit_name = (EditText) this.findViewById(R.id.register_name);
        edit_mobile_no = (EditText) this.findViewById(R.id.register_mobile_no);
        edit_organization_name = (EditText) this.findViewById(R.id.register_organization_name);
        edit_password = (EditText) this.findViewById(R.id.register_password);
        edit_employee = (EditText) this.findViewById(R.id.register_employee_edit);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void submit(View v) {

        final String name = edit_name.getText().toString();
        final String mobile_no = edit_mobile_no.getText().toString();
        final String password = edit_password.getText().toString();
        final String organization_name = edit_organization_name.getText().toString();
        final String employeeId = edit_employee.getText().toString();
        boolean checkOrganization=isEmptyOrganization();


        if(checkOrganization)
        {
            edit_organization_name.requestFocus();
            edit_organization_name.setError("Organization Name cannot be blank");
        }
        else if (name.length() == 0) {
            edit_name.requestFocus();
            edit_name.setError("name cannot be blank");
        } else if (mobile_no.length() == 0 || mobile_no.length() < 10) {
            edit_mobile_no.requestFocus();
            edit_mobile_no.setError("contain atleast 10 number");
        } else if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edit_password.requestFocus();
            edit_password.setError("between 4 to 10 characters");
        } else {
            final ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage("Connecting...");
            pDialog.setCancelable(false);
            pDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.dismiss();
                            //TODO check success from server
                            if (response.equalsIgnoreCase("true")) {
                                Log.d("onResponse()", "Success");
                                Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            // Display the first 500 characters of the response string.
                                 Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("onErrorResponse()", "Error");
                    pDialog.dismiss();
                    String err = error.getMessage();
                    if (error instanceof NoConnectionError) {
                        err = "No Internet Access\nCheck Your Internet Connection.";
                    }

                    Toast.makeText(RegisterActivity.this,"Error:"+ err, Toast.LENGTH_LONG).show();

                }


            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> param = new LinkedHashMap<>();
                   param.put("registration_info",
                           new GsonBuilder().create().toJson(new Employee(RegisterActivity.this,name, organization_name,
                                   mobile_no, password,employeeId)));

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
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);

        }


    }

    private boolean isEmptyOrganization() {
        return edit_organization_name.getText() == null
                || edit_organization_name.getText().toString().equals("") || edit_organization_name.getText().toString().isEmpty()
                || edit_organization_name.getText().toString() == null; // TODO PUT THIS CONDITION AT LAST IN OLL ACTIVITY

    }


}
