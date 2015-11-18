package in.junctiontech.project;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Junction Software on 02-Nov-15.
 */
public class PMSAdmin extends DeviceAdminReceiver {


    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
      //  showToast(context, "Sample Device Admin: Enabled");
        //    showToast(context,"MESSAGE ADMIN DEVICE ADMINISTRATOR ENABLE");

    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "This is an optional message to warn the user about disabling.";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
     //   showToast(context, "Sample Device Admin: Disabled");
        //   showToast(context,"MESSAGE ADMIN DEVICE ADMINISTRATOR DISABLE");
       /* SmsManager.getDefault().sendTextMessage(SENDING_NUMBER,
                null, "DEVICE AMDIN DISABLE", null, null);*/
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
     //   showToast(context, "Sample Device Admin: pw changed");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
    //    showToast(context, "Sample Device Admin: pw failed");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
      //  showToast(context, "Sample Device Admin: pw succeeded");
    }
}
