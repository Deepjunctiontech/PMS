package in.junctiontech.project;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import static in.junctiontech.project.PMSOtherConstant.SPLASH_SCREEN_DURATION;

/**
 * Created by Junction Software on 17-Oct-15.
 */
public class SplashScreenActivity extends Activity implements Runnable{

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        thread = new Thread(this);
        thread.start();

    }

    private void startAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alhpa);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.rel);
        l.clearAnimation();
        l.startAnimation(anim);

    }


    @Override
    protected void onStart() {
        super.onStart();
        startAnimations();
    }


    @Override
    public void run() { // TODO check imei for dual sim, insert sim card in first slot then check second- IMEI


        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        registerBroadCast();
        /* TODO check permission in lollipop
        if (MyService.version
                && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }*/
        SharedPreferences sharedPreferences = this.getSharedPreferences("employee_data", MODE_PRIVATE);
        String imei = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        // String imei="12345678";
        if (!sharedPreferences.getString("IMEI", "notfound").equalsIgnoreCase(imei)) {
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putString("IMEI", imei);
            e.commit();
            /*SmsManager.getDefault().sendTextMessage(SENDING_NUMBER,
                    null, "IMEI NUMBER=" + imei, null, null);*/
            Log.v("IMEI", imei);
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if ((sharedPreferences.getBoolean("splash_status", true))) {
            try {
                Thread.sleep(SPLASH_SCREEN_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        startActivity(new Intent(this, SignUpActivity.class));
        finish();

    }

    public void registerBroadCast() {

        AlarmManager alarmManager = ((AlarmManager) getSystemService(Context.ALARM_SERVICE));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, MyBroadCast.class).setAction("com.MyBroadCast"),
                PendingIntent.FLAG_UPDATE_CURRENT);
        int wakeup = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        long duration = SystemClock.elapsedRealtime() + 10 * 1000;
        byte version = (byte) Build.VERSION.SDK_INT;
        if (version > 18) {
            Log.d("VERSION", version + " setExact()");
            alarmManager.setExact(wakeup, duration, pendingIntent);
        } else {
            Log.d("VERSION", version + " set()");
            alarmManager.set(wakeup, duration, pendingIntent);
        }


    }

    public void onDestroy() {
        super.onDestroy();
        thread = null;
    }


}
