package in.junctiontech.project;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ViewImage extends AppCompatActivity {

    private ImageView imag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
       // getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        String f=getIntent().getStringExtra("image");
     //   Toast.makeText(this,f,Toast.LENGTH_LONG).show();
     //   f= f.replace("thumbnail","DB");
     //   Toast.makeText(this,f,Toast.LENGTH_LONG).show();
        imag=(ImageView)findViewById(R.id.imageView_gallery);
        imag.setImageURI(Uri.parse(f));   //OUT OF MEMORY EXCEPTION IF REPLACE

    }

    public void onPause()
    {
        Log.d("debug","onPause");
        super.onPause();
        finish();
    }
}
