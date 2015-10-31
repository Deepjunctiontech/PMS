package in.junctiontech.pms;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class ExpenseActivity extends AppCompatActivity {

    private Spinner expenses_spinner_project,expenses_spinner_task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        LinearLayout ll= (LinearLayout) findViewById(R.id.expenses1);
        LinearLayout l2= (LinearLayout) findViewById(R.id.expenses2);
        LinearLayout l3= (LinearLayout) findViewById(R.id.expenses3);
        LinearLayout l4= (LinearLayout) findViewById(R.id.expenses4);

        TranslateAnimation trans1 = new TranslateAnimation(-1000,1, -100,1);
        TranslateAnimation trans2 = new TranslateAnimation(1000,1, 100,1);
        TranslateAnimation trans3 = new TranslateAnimation(100,1, 1000,1);

        trans1.setDuration(1100);
        trans2.setDuration(1100);
        trans3.setDuration(1100);


        ll.setAnimation(trans1);
        l2.setAnimation(trans2);
        l3.setAnimation(trans1);
        l4.setAnimation(trans3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        expenses_spinner_project= (Spinner) findViewById(R.id.expenses_spinner_project);
        expenses_spinner_task= (Spinner) findViewById(R.id.expenses_spinner_task);
     //   expenses_spinner_project.setAdapter(new ArrayAdapter<String>(this, R.layout.myspinner_layout, new String[]{"hello", "hi", "tata", "bye"}));
        //expenses_spinner_project.onSaveInstanceState()

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.list_of_projects, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_item);
        expenses_spinner_project.setAdapter(adapter);


        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.list_of_tasks, R.layout.myspinner_layout);
        adapter1.setDropDownViewResource(R.layout.myspinner_dropdown_item);
        expenses_spinner_task.setAdapter(adapter1);


    }
/*
    public void onResume()
    {
        super.onResume();
        if(p!=null) {
            expenses_spinner_project.onRestoreInstanceState(p);
            Toast.makeText(this, "resume\n" + p.toString(), Toast.LENGTH_LONG).show();
        }


    }

    public void onPause()
    {
        super.onPause();
        p= expenses_spinner_project.onSaveInstanceState();

        Toast.makeText(this,"pause\n"+p.toString(),Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("spinner_value", p);
       // Toast.makeText(this,p.toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Save\n"+p.toString(),Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        p = savedInstanceState.getParcelable("spinner_value");
      //  Toast.makeText(this,p.toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(this,"restore\n"+p.toString(),Toast.LENGTH_LONG).show();


    }*/

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Class selected=null;
        if (id == R.id.action_settings) {
            selected=SettingActivity.class;

        }else if(id==R.id.action_aboutus) {
            selected=AboutUsActivity.class;
        }
        else if(id==R.id.action_contactus) {
            selected=ContactUsActivity.class;
        }
        else if(id==R.id.action_help){
            selected=HelpActivity.class;
        }
        if(selected!=null)
            startActivity(new Intent(this,selected));

        return super.onOptionsItemSelected(item);
    }
}
