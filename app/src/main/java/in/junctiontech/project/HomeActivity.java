package in.junctiontech.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar tb;
    private NavigationView navi;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tb = (Toolbar) this.findViewById(R.id.app_bar);
        navi = (NavigationView) this.findViewById(R.id.navi);
        dl = (DrawerLayout) this.findViewById(R.id.dl);
        this.setSupportActionBar(tb);
        abdt = new ActionBarDrawerToggle(this, dl, tb, R.string.open, R.string.close);
        navi.setNavigationItemSelectedListener(this);
        dl.setDrawerListener(abdt);
        abdt.syncState();
        dl.openDrawer(GravityCompat.START);
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

        Class selected = null;
        if (id == R.id.action_settings) {
            selected = SettingActivity.class;

        } else if (id == R.id.action_aboutus) {
            selected = AboutUsActivity.class;
        } else if (id == R.id.action_contactus) {
            selected = ContactUsActivity.class;
        } else if (id == R.id.action_help) {
            selected = HelpActivity.class;
        }
        if (selected != null)
            startActivity(new Intent(this, selected));

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        //  menuItem.setChecked(true);
        int id = menuItem.getItemId();
        dl.closeDrawer(GravityCompat.START);

        switch (id) {
            case R.id.project:
                //startActivity(new Intent(this, Appointment.class));
                break;
            case R.id.expense:
                startActivity(new Intent(this, ExpenseActivity.class));
                break;
            case R.id.receipt:
                startActivity(new Intent(this, ReceiptActivity.class));
                break;
            case R.id.logout:
                // startActivity(new Intent(this, Appointment.class));
                break;


        }

        return false;
    }

    public void onResume() {
        super.onResume();
    }
}
