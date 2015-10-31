package in.junctiontech.pms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Junction Software  on 29-Sep-15.
 */
public class MyBroadCast extends BroadcastReceiver {

    static boolean aeroplaneMode;
    private static boolean firstConnect = true;

    @Override
    public void onReceive(Context context, Intent arg1) {
        //arg1.getPackage()
        String action = arg1.getAction();


        if (action.equals("com.MyService")) {
            context.startService(new Intent(context, MyService.class));
            Log.v("STATUS", "SERVICE");

        } else if (action.equals("com.MyBroadCast")) {
            if (!MyService.isRunning)
                context.startService(new Intent(context, MyService.class));
            Log.v("STATUS", "SPLASH_SCREEN");

        } else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (!MyService.isRunning)
                context.startService(new Intent(context, MyService.class));
            else {
                if (Utility.isNetEnable(context, -1)) {
                    Log.v("STATUS", "INTERNET AVAILABLE");
                    if (firstConnect) {
                        SendEmployeeData.getInstance(context).send();
                        firstConnect = false;
                    }
                } else {
                    Log.v("STATUS", "INTERNET NOT AVAILABLE");
                    firstConnect = true;
                }
            }
        } else if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, MyService.class));
            Log.v("STATUS", "BOOT_COMPLETED");
        } else if (action.equals("android.intent.action.AIRPLANE_MODE")) {
            Log.v("STATUS", arg1.getBooleanExtra("state", false) + " FLIGHT MODE");
            //        Toast.makeText(context, " FLIGHT MODE "+(aeroplaneMode = arg1.getBooleanExtra("state", false)) , Toast.LENGTH_LONG).show();
        }

    }
}

/* synchronized private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) // for airplane mode it will be null
            {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                   //     networkName= info[i].getType();

                        if (!isConnected) {
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
                            Toast.makeText(context, "Internet availablle via Broadcast receiver", Toast.LENGTH_SHORT).show();
                            isConnected = true;
   //                         db=new DataBaseHandler(context,"junction",null,1);
   //                         String data=db.searchAndSend();
   //                         if(!data.equalsIgnoreCase("blank"))
   //                             new SendEmployeeData(db,context).send(data); // TODO CALL SERVICE, CHECK BOOLEAN WHICH IS A STATIC FIELD,
                                 //TODO CHECK SERVICE IF ITS NOT RUNNING RUN IT, OTHERWISE LEAVE IT

                        }
                        return true;
                    }
                }
            }
        }
        Log.v(LOG_TAG, "You are not connected to Internet!");
        Toast.makeText(context, "Internet NOT availablle via Broadcast receiver", Toast.LENGTH_SHORT).show();
        isConnected = false;
        return false;




    }*/