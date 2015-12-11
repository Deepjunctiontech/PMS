package in.junctiontech.project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout user_text, pass_text, organization_text;
    private EditText username, password, organizationname;
    private SharedPreferences sp;
    private LinearLayout rl;

    /**
     * Created by Junction Software on 17-Oct-15.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = this.getSharedPreferences("Login", this.MODE_PRIVATE);
        check();
        setContentView(R.layout.activity_sign_up);
        if (!MyService.isDeviceAdmin)
            startActivity(new Intent(this, PMSAdminActivity.class));  // TODO ITS SHOWS ONLY WHEN USER NOT LOGIN PUT IN SETTING
        user_text = (TextInputLayout) this.findViewById(R.id.user_text);
        pass_text = (TextInputLayout) this.findViewById(R.id.pass_text);
        organization_text = (TextInputLayout) this.findViewById(R.id.organization_text);

        username = (EditText) this.findViewById(R.id.user_edit);
        password = (EditText) this.findViewById(R.id.pass_edit);
        organizationname = (EditText) this.findViewById(R.id.organization_edit);
        rl = (LinearLayout) this.findViewById(R.id.rl);
    }

    public void submit(View v) {
        if (v.getId() == R.id.btn_login) {
           submit();
           //  startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
           // finish();
        } else
            startActivity(new Intent(this, RegisterActivity.class));
    }

    private void submit() {
        Utility.hideKeyboard(this);

        boolean b1 = isEmptyEmail();
        boolean b2 = isEmptyPassword();
        boolean b3 = isEmptyOrganization();

        user_text.setError(null);
        pass_text.setError(null);
        organization_text.setError(null);

        if (b3 && b1 && b2) {
            Snackbar.make(rl, "One Or More Field Are Blank", Snackbar.LENGTH_LONG).setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sp = getSharedPreferences("Login", MODE_PRIVATE);
                    /*String get_user = sp.getString("user_name", "Couldn't loaded Username...");
                    String get_pass = sp.getString("user_pass", "Couldn't loaded Password...");

                    Toast.makeText(LoginScreen.this, get_user + "\n" + get_pass, Toast.LENGTH_LONG).show();*/
                }
            }).show();

        } else {
            if(b3 || b2 || b1) {
                if (b3) {
                    organization_text.setError("Organization Name cannot be blank");

                }
                if (b1) {
                    user_text.setError("User Name cannot be blank");
                }
                if (b2) {
                    pass_text.setError("Password cannot be blank");
                }
            }
            else {
                final ProgressDialog pDialog = new ProgressDialog(this);
                pDialog.setMessage("Connecting...");
                pDialog.setCancelable(false);
                pDialog.show();

                //   startActivity(new Intent(LoginScreen.this, MainScreen.class));
                //  finish();

                RequestQueue queue = Volley.newRequestQueue(this);
                StringRequest strReq = new StringRequest(Request.Method.POST,

                        "http://junctiondev.cloudapp.net/appmanager/login/login_function", new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "Login Response: " + response.toString());
                        Utility.showToast(SignUpActivity.this, response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String check = jsonObject.getString("result");

                            if (check.equalsIgnoreCase("success")) {
                                //  JSONObject js = new JSONObject(jsonObject.getString("data"));
                                //
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("user_id", jsonObject.getString("user_id"));
                                editor.putString("user_name", username.getText().toString());
                                editor.putString("user_pass", password.getText().toString());
                                editor.putString("user_organization", organizationname.getText().toString());
                                //   editor.putString("userID", userID);

                                editor.commit();
                                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
                                editor = sharedPrefs.edit();
                                editor.putString("organization_name", organizationname.getText().toString());
                                editor.commit();

                                startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Utility.showToast(SignUpActivity.this, "INVALID USER ID PASSWORD");
                                password.setText("");
                            }
                            //         Toast.makeText(Appointment.this,js.getString("userID"),Toast.LENGTH_LONG).show();

                            //   Toast.makeText(Appointment.this,response,Toast.LENGTH_LONG).show();


//                            Toast.makeText(Appointment.this,obj.length()+"",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            pDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "Registration Error: " + error.getMessage());
                        String err = error.getMessage();
                        if (error instanceof NoConnectionError) {
                            err = "No Internet Access\nCheck Your Internet Connection.";
                        }
                        // TODO dispaly appropriate message ex 404- page not found..
                        Snackbar.make(rl, err + "\n" + error.toString(), Snackbar.LENGTH_LONG).setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();

                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        pDialog.dismiss();

                    }

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("db_name", organizationname.getText().toString());
                        param.put("username", username.getText().toString());
                        param.put("password", password.getText().toString());
                        param.put("device", "androide");
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("json", new JSONObject(param).toString());
                        return params;

                    /*params.put("json",
                            new GsonBuilder().create().toJson(new Employee("junction_erp", username.getText().toString(), password.getText().toString())));
                */
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        headers.put("abc", "value");
                        return headers;
                    }
                };
                strReq.setRetryPolicy(new DefaultRetryPolicy(3000, 2, 2));
                queue.add(strReq);
            }
        }

    }

    private boolean isEmptyOrganization() {
        return organizationname.getText() == null || organizationname.getText().toString() == null
                || organizationname.getText().toString().equals("") || organizationname.getText().toString().isEmpty();

    }


    private boolean isEmptyEmail() {
        return username.getText() == null || username.getText().toString() == null
                || username.getText().toString().equals("") || username.getText().toString().isEmpty();
    }

    private boolean isEmptyPassword() {
        return password.getText() == null || password.getText().toString() == null
                || password.getText().toString().equals("") || password.getText().toString().isEmpty();
    }


    private void check() {
        if (!sp.getString("user_name", "Not Found").equals("Not Found") &&
                !sp.getString("user_pass", "Not Found").equals("Not Found") &&
                !sp.getString("user_organization", "Not Found").equals("Not Found")) {
            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
            finish();
        }
    }




   /* public void onResume() {
        super.onResume();
       *//* Intent refresh = new Intent(this, SignUpActivity.class);
        startActivity(refresh);//Start the same Activity
        finish();*//*
    }

    public void onStop() {
        super.onStop();
        Log.i("TAG", "SignUp Activity Kill");
        finish();
    }*/

}
