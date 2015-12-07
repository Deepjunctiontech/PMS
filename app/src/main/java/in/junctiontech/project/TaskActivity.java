package in.junctiontech.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import in.junctiontech.project.employeeproject.Task;

public class TaskActivity extends AppCompatActivity {

    private CustomAdapter customAdapter;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        referenceInitialization();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void referenceInitialization() {
        ListView lv = (ListView) this.findViewById(R.id.project_task_listView);
        PMSDatabase pmsDatabase = PMSDatabase.getInstance(TaskActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<Task> task_list = pmsDatabase.getProjectTaskList(getIntent().getStringExtra("PROJECT_ID"));
        if (task_list != null) {
            customAdapter = new CustomAdapter(TaskActivity.this, task_list);
            lv.setAdapter(customAdapter);

        } else
            Utility.showToast(TaskActivity.this, "No Task Found");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Task task = customAdapter.getItem(position);
                Intent intent = new Intent(TaskActivity.this, DetailActivity.class);
                intent.putExtra("PROJECT_ID", task.getProject_id());
                intent.putExtra("TASK_ID", task.getTask_id());
                startActivity(intent);*/
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                // Toast.makeText(ProjectActivity.this,position+"",Toast.LENGTH_LONG).show();
                task = customAdapter.getItem(position);
                return false;
            }
        });
        registerForContextMenu(lv);


    }

    private class CustomAdapter extends ArrayAdapter<Task> { // TODO see <String> and think
        private final Context context;
        private List<Task> task_list;

        public CustomAdapter(Context context, List<Task> task_list) {
            super(context, R.layout.project_task_list_design, task_list);
            this.context = context;
            this.task_list = task_list;
        }

        @Override
        public Task getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // super.getView(position, convertView, parent);

            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater myLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = myLayoutInflater.inflate(R.layout.project_task_list_design, null);
                holder = new ViewHolder();
                holder.projectId = (TextView) convertView.findViewById(R.id.project_task_projectId);
                holder.taskId = (TextView) convertView.findViewById(R.id.project_task_id);
                holder.description = (TextView) convertView.findViewById(R.id.project_task_description);
                holder.status = (TextView) convertView.findViewById(R.id.project_task_status);
                holder.startDate = (TextView) convertView.findViewById(R.id.project_task_startDate);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            Task task = task_list.get(position);
            holder.projectId.setText(task.getProject_id());
            holder.taskId.setText(task.getTask_id());
            holder.description.setText(task.getDescription());
            holder.status.setText(task.getStatus());
            holder.startDate.setText(task.getStart_date());


            return convertView;
        }

        public class ViewHolder {
            private TextView description, projectId, taskId, status, startDate;
            // private things are access easily in method of same class
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_task_long, menu);
        menu.setHeaderTitle("Select Option for Task");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        String s = "";
        Class cls = null;
        switch (item.getItemId()) {

            case R.id.expense_task_long:
                s = "Expense";
                cls = ExpenseActivity.class;

                break;
            case R.id.receipt_task_long:
                s = "Receipt";
                cls = ReceiptActivity.class;
                break;
            case R.id.image_task_long:
                s = "Image";
                cls = ImageSelectionActivity.class;

                break;
        }
        if (cls != null) {
            Intent intent=new Intent(this, cls);
            intent.putExtra("PROJECT_ID", task.getProject_id());
            intent.putExtra("TASK_ID", task.getTask_id());
            startActivity(intent);
        }
        Utility.showToast(this, s);
        return super.onContextItemSelected(item);


    }
}
