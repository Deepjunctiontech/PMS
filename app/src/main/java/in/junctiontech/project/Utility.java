package in.junctiontech.project;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Junction Software on 17-Oct-15.
 */

// class visibility is default because we dont want this class access from outside the package

class Utility {

    static boolean gps_enable, network_enable, gps_network_enable;
    private static SimpleDateFormat simpleDataFormat = new SimpleDateFormat("yyyy-MM-dd");


    @SuppressWarnings("deprecation")
    public static boolean isLocationEnabled(final Context context) {
        int locationMode = 0;
        String locationProviders;
        boolean enable_disable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            enable_disable = locationMode != Settings.Secure.LOCATION_MODE_OFF;
            if (enable_disable) {
                gps_network_enable = locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;
                gps_enable = locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY;
                network_enable = locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING;
            }

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            enable_disable = !TextUtils.isEmpty(locationProviders);
            if (enable_disable) {
                gps_enable = locationProviders.contains("gps");
                network_enable = locationProviders.contains("network");
                gps_network_enable = gps_enable && network_enable;
            }
        }
        return enable_disable;


    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)

    public static boolean isFlightModeEnable(final Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;

        }
    }

    @SuppressWarnings("deprecation")
    public static boolean isNetEnable(final Context context, final int i) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        if (i == -1)
            networkInfo = connManager.getActiveNetworkInfo();
        else
            networkInfo = connManager.getNetworkInfo(i);
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static void longInfo(final String str) {
        if (str.length() > 4000) {
            Log.i("JSON_Format", str.substring(0, 4000));
            longInfo(str.substring(4000));
        } else
            Log.i("JSON_Format", str);
    }

    public static String getDate() {
        Log.i("DATE", "Utility Date");
        return simpleDataFormat.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        Log.i("TIME", "Utility Time");
        return (calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
    }

    public static byte getBatteryLevel(Context context) {

        byte batteryLevel = -1;
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (intent != null)
            batteryLevel = (byte) intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        Log.i("BATTERY_LEVEL", batteryLevel + "");
        return batteryLevel;
    }

    public static void showToast(Context context, String data) {
        Toast.makeText(context, data, Toast.LENGTH_LONG).show();
    }
    public static void hideKeyboard(Context context) {

        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

            View view = ((Activity) context).getCurrentFocus();
            if (view != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
