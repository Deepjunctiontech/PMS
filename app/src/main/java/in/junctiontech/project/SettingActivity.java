package in.junctiontech.project;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Junction Software on 29-Oct-15.
 */
public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }

    public void onPause() {
        super.onPause();
        finish();
    }
}
