package in.junctiontech.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends Activity {

    /**
     * Created by Junction Software on 17-Oct-15.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (!MyService.isDeviceAdmin)
            startActivity(new Intent(this, PMSAdminActivity.class));
    }

    public void submit(View v) {
        if (v.getId() == R.id.btn_login)
            startActivity(new Intent(this, HomeActivity.class));
        else
            startActivity(new Intent(this, RegisterActivity.class));
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
