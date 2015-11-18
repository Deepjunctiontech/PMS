package in.junctiontech.project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import in.junctiontech.project.employee.EmployeeLocation;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.net.ConnectivityManager.TYPE_WIFI;

/**
 * Created by Junction Software on 29-Sep-15.
 */
public class MyService extends Service implements LocationListener {

    private LocationManager locationManager;
    private PMSDatabase pmsDatabase;
    private MediaPlayer mediaPlayer;
    private SendEmployeeData sendEmployeeData;
    private EmployeeLocation employeeLocation;
    private Handler handler;
    private Runnable runnable;
    private String stringStatus;
    private AlarmManager alarmManager;
    private Context context;
    private PendingIntent pendingIntent;
    static boolean isRunning, isDeviceAdmin;
    private DevicePolicyManager deviceManger;
    private ComponentName componentName;
//    private byte check = 0;
//public static boolean version = Build.VERSION.SDK_INT > 22; for MARSHMALLOW

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = true;
        context = getApplicationContext();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        deviceManger = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context, PMSAdmin.class);
        mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        employeeLocation = new EmployeeLocation();
        pmsDatabase = PMSDatabase.getInstance(context);
        sendEmployeeData = SendEmployeeData.getInstance(context);
        handler = new Handler(Looper.myLooper());
        alarmManager = ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));
        pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, MyBroadCast.class).setAction("com.MyService"),
                PendingIntent.FLAG_UPDATE_CURRENT);
        //       Toast.makeText(context, "onCreate", Toast.LENGTH_LONG).show();

    }

    // TODO MANUALLY WE SET HIGH VERSION LOLLIPOP LOW VERSION GINGERBREAD
    // TODO SET STATIC FIELD TO CHECK IF SERVICE IS ON/OFF

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        //       check = 0;
        //       Toast.makeText(context, "onStartCommand", Toast.LENGTH_LONG).show();
        handler.post(runnable = new Runnable() {
            public void run() {
               /* try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/     // because handler uses concept of message queue, GUI me delay na ho i.e. not recommended

                registerAlarm();
                employeeLocation.setEmployeeLocationDate(Utility.getDate());
                employeeLocation.setEmployeeLocationTime(Utility.getTime());
                pmsDatabase.setEmployeeData(employeeLocation);

                if (Utility.isLocationEnabled(context)) {

                    if (Utility.gps_network_enable) {
                        registerProvider(GPS_PROVIDER);

                        handler.postDelayed(runnable = new Runnable() {
                            public void run() {
                                /*try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }*/  // because handler uses concept of message queue, GUI me delay na ho i.e. not recommended
                                checkAndRegister();
                            }
                        }, PMSOtherConstant.GPS_DURATION);

                    } else if (Utility.gps_enable) {
                        registerProvider(GPS_PROVIDER);
                        handler.postDelayed(runnable = new Runnable() {
                            public void run() {
                                stringStatus = "FAIL";
                                onLocationChanged(null);
                            }
                        }, PMSOtherConstant.GPS_DURATION);

                    } else if (Utility.network_enable) {
                        checkAndRegister();
                    }

                } else {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    stringStatus = "SERVICE OFF";
                    mediaPlayer.start();
                    onLocationChanged(null);
                }


            }

            private void registerAlarm() {
                int wakeup = AlarmManager.ELAPSED_REALTIME_WAKEUP;
                long duration = SystemClock.elapsedRealtime() + PMSOtherConstant.REPEAT_DURATION;
                byte version = (byte) Build.VERSION.SDK_INT;
                if (version > 18) {
                    Log.d("VERSION", version + " setExact()");
                    alarmManager.setExact(wakeup, duration, pendingIntent);
                } else {
                    Log.d("VERSION", version + " set()");
                    alarmManager.set(wakeup, duration, pendingIntent);
                }
            }

            private void registerProvider(final String provider) {
                //locationManager.getAllProviders().contains(provider);
                locationManager.removeUpdates(MyService.this);
                locationManager.requestLocationUpdates(provider, 0, 0, MyService.this);
                /*      locationManager.requestSingleUpdate(provider, MyService.this, null); requestSingleUpdate() comes in api 9
                stringStatus=provider;*/
                Log.d("PROVIDER", provider);

            }

            private void checkAndRegister() {
                if (Utility.isFlightModeEnable(context) && Utility.isNetEnable(context, TYPE_WIFI)) {
                    registerProvider(NETWORK_PROVIDER);

                    /*handler.postDelayed(runnable = new Runnable() {
                            public void run() {
                                stringBuilder.append(" FAIL TO GET LOCATION");
                                onLocationChanged(null);
                            }
                        }, PMSOtherConstant.GPS_DURATION);*/


                } else if (Utility.isFlightModeEnable(context)) {
                    stringStatus = "FLIGHT MODE";
                    onLocationChanged(null);
                } else {
                    registerProvider(NETWORK_PROVIDER);
                /*handler.postDelayed(runnable = new Runnable() {
                            public void run() {
                                stringBuilder.append(" FAIL TO GET LOCATION");
                                onLocationChanged(null);
                            }
                        }, PMSOtherConstant.GPS_DURATION);*/
                }
            }

        });

        return START_STICKY;
    }

    @Override
    public void onLocationChanged(final Location location) {
        locationManager.removeUpdates(MyService.this);
        handler.removeCallbacks(runnable);

        handler.post(new Runnable() {
            public void run() {
               /* try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                // because handler uses concept of message queue, GUI me delay na ho i.e. not recommended
                double latitude = 0.0, longitude = 0.0;
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    stringStatus = location.getProvider();
                }
                isDeviceAdmin = deviceManger.isAdminActive(componentName);
                employeeLocation.setEmployeeLocationLatitude(latitude);
                employeeLocation.setEmployeeLocationLongitude(longitude);
                employeeLocation.setEmployeeLocationBatteryLevel(Utility.getBatteryLevel(MyService.this));
                employeeLocation.setEmployeeLocationProviderName(stringStatus + "," + isDeviceAdmin);

                pmsDatabase.setEmployeeData(employeeLocation);


                if ((Utility.isNetEnable(context, -1)))
                    sendEmployeeData.send();

               /* if(check==2)
                    uninitializeObjects();
                check=1;*/
            }
        });


    }


// TODO broadcasting in ondestroy and check if its work when app is forcefully stop by user not service stop.
    // Check here if oll resources are free means onLoacationChanged run completed and successfully free oll resources


    @Override
    public void onDestroy() {
        super.onDestroy();

        //      Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
        alarmManager = null;
        mediaPlayer.release();
        pendingIntent = null;
      /*  if (check == 1)
            uninitializeObjects();
        check=2;*/
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
        /*Toast.makeText(this, "onStatusChanged\n" + provider + "\n" + status + "\n" + extras.toString(),
                Toast.LENGTH_LONG).show();*/

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
        /*Toast.makeText(this, "onProviderEnabled\n" + provider,
                Toast.LENGTH_LONG).show();*/

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
      /*  Toast.makeText(this, "onProviderDisabled\n" + provider,
                Toast.LENGTH_LONG).show();*/

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


}

  /*  private void uninitializeObjects() {
        Toast.makeText(this, "Delete Objects\n"+check, Toast.LENGTH_SHORT).show();
        locationManager=null;
        pmsDatabase=null;
        sendEmployeeData=null;
        employeeLocation=null;
        handler=null;
        runnable=null;
        stringBuilder=null;
        context=null;
        simpleDataFormat=null;

    }*/

 /*
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
           *//* int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
            int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
            int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
            boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
            int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
            String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);*//*

        }
    };*/

/*if((Utility.isNetEnable(context,TYPE_MOBILE)||Utility.isNetEnable(context,TYPE_WIFI)))
                {
                  //  if(pmsDatabase.getEmployeeData(employee).getEmployeeLocationList().size()>0)
                    Toast.makeText(context,"SEND",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(context,"DATA OFF",Toast.LENGTH_LONG).show();*/