package in.junctiontech.pms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {
    /**
     * Created by Junction Software on 17-Oct-15.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void submit(View v) {
        startActivity(new Intent(this, HomeActivity.class));
    }


}
