package in.junctiontech.pms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Junction Software on 28-Oct-15.
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onPause() {
        super.onPause();
        finish();
    }
}

