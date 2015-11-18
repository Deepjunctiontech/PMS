package in.junctiontech.project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import in.junctiontech.project.employee.EmployeeLocation;

/**
 * Created by Junction Software  on 29-Sep-15.
 */
public class MyBroadCast extends BroadcastReceiver {

    private static boolean firstConnect = true;

    @Override
    public void onReceive(final Context context, final Intent arg1) {
        //arg1.getPackage()
        final String action=arg1.getAction();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (action.equals("com.MyService")) {
                    context.startService(new Intent(context, MyService.class));
                    Log.v("STATUS", "SERVICE");

                } else if (action.equals("com.MyBroadCast")) {
                    if (!MyService.isRunning)
                        context.startService(new Intent(context, MyService.class));
                    Log.v("STATUS", "SPLASH_SCREEN");

                } else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    if (!MyService.isRunning)
                    {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        context.startService(new Intent(context, MyService.class));
                        Log.v("STATUS", "CONNECTIVITY CHANGE");
                    }
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
                } else if (action.equals("android.intent.action.BOOT_COMPLETED")||action.equals("android.intent.action.QUICKBOOT_POWERON")) {
                    saveData(context,"SWITCHED ON");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.v("STATUS", "BOOT_COMPLETED");
                    Log.v("CHECK", action);
                    context.startService(new Intent(context, MyService.class));

                } else if (action.equals("android.intent.action.AIRPLANE_MODE")) {
                    Log.v("STATUS", arg1.getBooleanExtra("state", false) + " FLIGHT MODE");
                    //Toast.makeText(context, " FLIGHT MODE "+(aeroplaneMode = arg1.getBooleanExtra("state", false)) , Toast.LENGTH_LONG).show();
                } else if (action.equals("android.intent.action.ACTION_SHUTDOWN")||action.equals("android.intent.action.QUICKBOOT_POWEROFF")) {
                    Log.v("STATUS", "PHONE_SWITCHED_OFF");
                    Log.v("CHECK", action);
                    saveData(context,"SWITCHED OFF");
                }



            }
        }).start();

    }

    private void saveData(final Context context,final String status)
    {
        PMSDatabase pmsDatabase = PMSDatabase.getInstance(context);
        pmsDatabase.setEmployeeData(new EmployeeLocation(
                Utility.getDate(),
                Utility.getTime(),
                0.0,
                0.0,
                Utility.getBatteryLevel(context),
                status));
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