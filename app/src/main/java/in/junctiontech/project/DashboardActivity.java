package in.junctiontech.project;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import in.junctiontech.project.employeeproject.DashBoard;

public class DashboardActivity extends AppCompatActivity {

    private PMSDatabase pmsDatabase;
    private ListView lv_for_dashbaord;
    private CustomAdapter customAdapter;
    private String getName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        lv_for_dashbaord = (ListView) findViewById(R.id.lv_for_dashboard);
        pmsDatabase = PMSDatabase.getInstance(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getName = this.getIntent().getStringExtra("ID");
        getSupportActionBar().setTitle("Dashboard - " + getName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onResume() {
        super.onResume();
        updateTable();

    }

    public void updateTable() {
        List<DashBoard> dashBoards;
        dashBoards = pmsDatabase.getDashBoardProjectData();
        if (dashBoards != null) {
            List<DashBoard> dashBoardTask=pmsDatabase.getDashBoardTaskData();
            if(dashBoardTask!=null)
                dashBoards.addAll(dashBoardTask);
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

}