package in.junctiontech.project;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Junction Software on 05-Nov-15.
 */

public class PMSAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivityForResult(new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                        .putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this, PMSAdmin.class))
                        .putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                                "Please Activate Device Admin For Better Performance Of Application."),
                111);

// TODO DEVICE ADMIN SETTING PUT IN SETTING PAGE FOR PERFECTLY WORK
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 111:
                if (resultCode == Activity.RESULT_OK)
                    Log.i("DeviceAdminSample", "Admin enabled!");
                else
                    Log.i("DeviceAdminSample", "Admin fails!");
        }
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
