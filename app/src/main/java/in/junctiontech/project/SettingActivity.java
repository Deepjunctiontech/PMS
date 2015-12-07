package in.junctiontech.project;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Junction Software on 29-Oct-15.
 */
public class SettingActivity extends AppCompatActivity {

   private static Context c;
   private static ComponentName componentName;
    private static DevicePolicyManager deviceManger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        c = this;
         componentName = new ComponentName(c, PMSAdmin.class);
      deviceManger = (DevicePolicyManager) c.getSystemService(Context.DEVICE_POLICY_SERVICE);
        // Display the fragment as the main content
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }



    public void onPause() {
        super.onPause();
        // finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static class SettingsFragment extends PreferenceFragment /*implements Preference.OnPreferenceChangeListener*/  {

        CheckBoxPreference checkBox;
        private boolean mAdminActive=true;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
            checkBox = (CheckBoxPreference) getPreferenceManager()
                    .findPreference("device_admin");
            //checkBox.setOnPreferenceChangeListener(this );


        }
           /* @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (super.onPreferenceChange(preference, newValue)) {
                    return true;
                }
                boolean value = (Boolean) newValue;
                if (preference == checkBox) {
                    if (value != isActiveAdmin()) {
                        if (value) {
                            // Launch the activity to have the user enable our admin.
                            startActivityForResult(new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)  // TODO BECAUSE MULTIPLE DA SHOWS
                                            .putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(c, PMSAdmin.class))
                                            .putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                                                    "Please Activate Device Admin For Better Performance Of Application."),
                                    111);
                            // return false - don't update checkbox until we're really active
                            return false;
                        } else {
                            deviceManger.removeActiveAdmin(componentName);
                            //enableDeviceCapabilitiesArea(false);
                            mAdminActive = false;
                        }
                    }
                }
                return true;

            }
        });*/

        private boolean isActiveAdmin() {
            return deviceManger.isAdminActive(componentName);
        }

       /* @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


            Utility.showToast(c, key);

            if (key.equals("device_admin")) {

                checkBox.setChecked(false);

                startActivityForResult(new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)  // TODO BECAUSE MULTIPLE DA SHOWS
                                .putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(c, PMSAdmin.class))
                                .putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                                        "Please Activate Device Admin For Better Performance Of Application."),
                        111);

                boolean isDeviceAdmin = deviceManger.isAdminActive(componentName);
                if (!isDeviceAdmin)
                    startActivity(new Intent(c, PMSAdminActivity.class));
              *//*      checkBox.setShouldDisableView(false);
                checkBox.setEnabled(false);*//*

            }

        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            switch (requestCode) {
                case 111:
                    if (resultCode == Activity.RESULT_OK) {
                        Log.i("DeviceAdminSample", "Admin enabled!");
                        checkBox.setChecked(true);
                        checkBox.setEnabled(false);
                    }
                    else {
                        Log.i("DeviceAdminSample", "Admin fails!");
                        checkBox.setChecked(false);
                    }
            }
            super.onActivityResult(requestCode, resultCode, data);
            //finish();
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);


        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
*/

    }


}
