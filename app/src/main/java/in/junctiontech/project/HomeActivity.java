package in.junctiontech.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import in.junctiontech.project.employeeproject.DashBoard;

import static in.junctiontech.project.PMSOtherConstant.IMAGE_DIRECTORY_NAME_MEDIUM;
import static in.junctiontech.project.PMSOtherConstant.IMAGE_DIRECTORY_NAME_ORIGINAL;
import static in.junctiontech.project.PMSOtherConstant.IMAGE_DIRECTORY_NAME_THUMBNAIL;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar tb;
    private NavigationView navi;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private static boolean checked;
    private PMSDatabase pmsDatabase;
    //  private ListView lv_for_dashbaord;
    private CustomAdapter customAdapter;
    private AlphaAnimation alphaDown, alphaUp;
    private Button project, expense, receipt;
    private ImageButton task;

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
        pmsDatabase = PMSDatabase.getInstance(this);
        alphaDown = new AlphaAnimation(1.0f, 0.3f);
        alphaUp = new AlphaAnimation(0.2f, 1.0f);
        alphaDown.setDuration(200);
        alphaUp.setDuration(1000);


        project = (Button) findViewById(R.id.projects_dashboard);
        task = (ImageButton) findViewById(R.id.tasks_dashboard);
        expense = (Button) findViewById(R.id.expenses_dashboard);
        receipt = (Button) findViewById(R.id.receipts_dashboard);

        //   lv_for_dashbaord = (ListView) findViewById(R.id.lv_for_dashboard);
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
                startActivity(new Intent(this, ProjectActivity.class));
                /*if(menu != null){
                    MenuItem item_up = menu.findItem(R.id.action_up);
                    item_up.setVisible(isChecked);
                }*/
                break;
            case R.id.expense:
                startActivity(new Intent(this, ExpenseActivity.class));
                break;
            case R.id.receipt:
                startActivity(new Intent(this, ReceiptActivity.class));
                break;
            case R.id.logout:
               /* getSharedPreferences("Login", MODE_PRIVATE).edit().clear().commit();
                finish();
                startActivity(new Intent(this, SignUpActivity.class));*/
                alert();
                break;
            case R.id.image:
                startActivity(new Intent(this, ImageSelectionActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;

        }

        return false;
    }

    public void onResume() {
        super.onResume();
        // dl.openDrawer(GravityCompat.START);
        navi.getMenu().getItem(0).setChecked(true);
        updateTable("projects");
    }

    private void alert() {
        navi.getMenu().getItem(0).setChecked(true);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        // builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to logout\nAll data will be flush...?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pmsDatabase.flushAllData();
                // Toast.makeText(this,"Database Delete",Toast.LENGTH_LONG).show();
                deleteImage(IMAGE_DIRECTORY_NAME_ORIGINAL);
                deleteImage(IMAGE_DIRECTORY_NAME_THUMBNAIL);
                deleteImage(IMAGE_DIRECTORY_NAME_MEDIUM);

                getSharedPreferences("Login", MODE_PRIVATE)
                        .edit().clear().commit();
                startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
                finish();

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void deleteImage(String folder) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                folder);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return;
            }
        }


        File[] f = mediaStorageDir.listFiles();

        for (int i = 0; i < f.length; i++) {
            f[i].delete();
        }
        mediaStorageDir.delete();
        //Toast.makeText(this,mediaStorageDir.delete()+"",Toast.LENGTH_LONG).show();


    }

    public void updateTable(String status) {
    /*    List<DashBoard> dashBoards;
        if("projects".equalsIgnoreCase(status))
            dashBoards= pmsDatabase.getDashBoardProjectData();
        else
            dashBoards= pmsDatabase.getDashBoardTaskData();

        if (dashBoards != null) {
            if (customAdapter == null) {
                Log.d("ADAPTER", "CREATED");
                customAdapter = new CustomAdapter(this, dashBoards);
                lv_for_dashbaord.setAdapter(customAdapter);
            } else {
                Log.d("ADAPTER", "MODIFIED");
                customAdapter.clear();
                customAdapter.addAll(dashBoards);
                customAdapter.notifyDataSetChanged();
            }


        } else {
            lv_for_dashbaord.setAdapter(new ArrayAdapter<>
                    (this, android.R.layout.simple_list_item_1, new String[]{"Currently Not Available"}));

        }*/
    }


    private View v = null;


    public void onClick(View view) {
      /*  alphaDown.cancel();
        alphaDown.reset();

      */
        if (v != null) {
            v.clearAnimation();
        }
        v = view;


        switch (view.getId()) {
            case R.id.projects_dashboard:
                project.startAnimation(alphaUp);
                startActivity(new Intent(this, DashboardActivity.class).putExtra("ID","Project"));
                break;
            case R.id.tasks_dashboard:
                task.startAnimation(alphaUp);
                startActivity(new Intent(this, DashboardActivity.class).putExtra("ID", "Task"));
                break;
            case R.id.expenses_dashboard:
                expense.startAnimation(alphaUp);
                startActivity(new Intent(this, EditActivity.class).putExtra("ID", "Expense"));
                break;
            case R.id.receipts_dashboard:
                receipt.startAnimation(alphaUp);
                startActivity(new Intent(this, EditActivity.class).putExtra("ID", "Receipt"));
                break;
        }


    }

    class CustomAdapter extends ArrayAdapter<DashBoard> {
        private List<DashBoard> dashBoards;
        private Context context;

        public CustomAdapter(Context context, List<DashBoard> dashBoards) {
            super(context, R.layout.for_dashboard, dashBoards);
            this.context = context;
            this.dashBoards = dashBoards;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public DashBoard getItem(int position) {
            return super.getItem(position);
        }


        public void hideView(int pos) {
            dashBoards.remove(pos);
            notifyDataSetChanged();
        }

        public void showView(int pos) {
            dashBoards.add(pos, dashBoards.get(pos));
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater myLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = myLayoutInflater.inflate(R.layout.for_dashboard, null);
                viewHolder = new ViewHolder();
                viewHolder.pid = (TextView) convertView.findViewById(R.id.pid_dashboard);
                viewHolder.tid = (TextView) convertView.findViewById(R.id.tid_dashboard);
                viewHolder.expense = (TextView) convertView.findViewById(R.id.expense_dashboard);
                viewHolder.receipt = (TextView) convertView.findViewById(R.id.receipt_dashboard);
                viewHolder.image = (TextView) convertView.findViewById(R.id.image_dashboard);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            DashBoard dashBoard = dashBoards.get(position);
            viewHolder.pid.setText(dashBoard.getPid());
            viewHolder.tid.setText(dashBoard.getTid());
            viewHolder.expense.setText(dashBoard.getExpense());
            viewHolder.receipt.setText(dashBoard.getReceipt());
            viewHolder.image.setText(dashBoard.getImage());


            return convertView;
        }

        public class ViewHolder {
            TextView pid, tid, expense, receipt, image;
        }


    }

    public void setListView(View v) {
      /* if( v.getId()==R.id.dashboard_projects)
       {
           updateTable("projects");
       }
        else if(v.getId()==R.id.dashboard_tasks)
       {
           updateTable("tasks");
       }*/
    }


}
