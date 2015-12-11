package in.junctiontech.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import in.junctiontech.project.employeeproject.Project;
import in.junctiontech.project.employeeproject.Task;

public class ProjectActivity extends AppCompatActivity {

    private ListView lv;
    private PMSDatabase pmsDatabase;
    private CustomAdapter customAdapter;
    private String currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        referenceInitialization();
        checkServer();
    }

    private void referenceInitialization() {
        lv = (ListView) this.findViewById(R.id.project_listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String projectId = customAdapter.getItem(position).getProject_id();
                startActivity(new Intent(ProjectActivity.this, TaskActivity.class).putExtra("PROJECT_ID", projectId));
            }
        });
        pmsDatabase = PMSDatabase.getInstance(ProjectActivity.this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fillDataInAdapter();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
                // Toast.makeText(ProjectActivity.this,position+"",Toast.LENGTH_LONG).show();
                currentId = customAdapter.getItem(position).getProject_id();
                return false;
            }
        });
        registerForContextMenu(lv);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void checkServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://junctiondev.cloudapp.net/zeroerp/remoteapi/project",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //TODO check success from server
                        if (response.equalsIgnoreCase("true")) {
                            Log.d("onResponse()", "Success");
                            Toast.makeText(ProjectActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        // Display the first 500 characters of the response string.
                        //      Toast.makeText(ProjectActivity.this, response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray obj = jsonObject.getJSONArray("project_of_list");
                            int countRecord = obj.length();
                            if (countRecord > 0) {
                                List<Project> project_list = new ArrayList<>(obj.length());

                                for (int i = 0; i < obj.length(); i++) {
                                    JSONObject n = obj.getJSONObject(i);

                                    Project project = new Project(n.getString("project_id"),
                                            n.getString("status"),
                                            n.getString("start_date"),
                                            n.getString("project_description"));
                                    project.setTask_list(getProjectTaskList(n.getJSONArray("list_of_task")));
                                    project_list.add(project);


                                }
                                pmsDatabase.setProjectList(project_list);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ProjectActivity.this, "Project List-Invalid Response From Server", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        } finally {
                            fillDataInAdapter();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onErrorResponse()", "Error");
                String err = error.getMessage();
                if (error instanceof NoConnectionError) {
                    err = "No Internet Access\nCheck Your Internet Connection.";
                }
                Utility.showToast(ProjectActivity.this, "Error:" + err);
            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new LinkedHashMap<>();
              /*  param.put("registration_info",
                        new GsonBuilder().create().toJson(new Employee(ProjectActivity.this,name, organization_name, mobile_no, password))) ;
                   TODO this will help at server when project list fetch every time according to id, password, and organization.
            */
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("abc", "value");
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, 2, 2));
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void fillDataInAdapter() {
        List<Project> project_list = pmsDatabase.getProjectList();
        if (project_list != null) {
            customAdapter = new CustomAdapter(ProjectActivity.this, project_list);
            lv.setAdapter(customAdapter);

        } else
            Utility.showToast(ProjectActivity.this, "Database Blank");
    }

    private List<Task> getProjectTaskList(JSONArray jsonArray) {
        List<Task> task_list = null;  // TODO PUT  new ArrayList<>(0);
        try {

            int countRecord = jsonArray.length();
            if (countRecord > 0) {
                task_list = new ArrayList<>(countRecord);

                for (int i = 0; i < countRecord; i++) {
                    JSONObject n = jsonArray.getJSONObject(i);
                    Task task = new Task(
                            n.getString("project_id"),
                            n.getString("task_id"),
                            n.getString("status"),
                            n.getString("task_description"));
                    task_list.add(task);
                }
            }
        } catch (JSONException e) {
            Utility.showToast(ProjectActivity.this, "Task List-Invalid Response From Server");
            e.printStackTrace();

        }
        return task_list;
    }

    class CustomAdapter extends ArrayAdapter<Project> {
        private final Context context;
        private List<Project> project_list;

        public CustomAdapter(Context context, List<Project> project_list) {
            super(context, R.layout.project_list_design, project_list); // TODO this String array iterate get view according to size
            this.context = context;
            this.project_list = project_list;
        }

        @Override
        public Project getItem(int position) {
            return super.getItem(position);
        }


        public void hideView(int pos) {
            project_list.remove(pos);
            notifyDataSetChanged();
        }

        public void showView(int pos) {
            project_list.add(pos, project_list.get(pos));
            notifyDataSetChanged();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // super.getView(position, convertView, parent);

            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater myLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = myLayoutInflater.inflate(R.layout.project_list_design, null);
                viewHolder = new ViewHolder();
                viewHolder.id = (TextView) convertView.findViewById(R.id.project_id);
                viewHolder.description = (TextView) convertView.findViewById(R.id.project_description);
                viewHolder.status = (TextView) convertView.findViewById(R.id.project_status);
                viewHolder.startDate = (TextView) convertView.findViewById(R.id.project_startDate);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Project project = project_list.get(position);  // reference point to already created object
            viewHolder.id.setText(project.getProject_id());
            viewHolder.description.setText(project.getDescription());
            viewHolder.status.setText(project.getStatus());
            viewHolder.startDate.setText(project.getStart_date());

            /*if (convertView == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.project_list_design, null);
            }


            final ViewHolder holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.project_id);
            holder.description = (TextView) convertView.findViewById(R.id.project_description);
            holder.status = (TextView) convertView.findViewById(R.id.project_status);
            holder.startDate = (TextView) convertView.findViewById(R.id.project_startDate);

            Project project = project_list.get(position);

            holder.id.setText(project.getProject_id());
            holder.description.setText(project.getDescription());
            holder.status.setText(project.getStatus());
            holder.startDate.setText(project.getStart_date());*/


            return convertView;
        }

        private class ViewHolder {
            private TextView description, id, status, startDate;

        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_long, menu);
        menu.setHeaderTitle("Select Option For Project");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        String s = "";
        Class cls = null;
        switch (item.getItemId()) {

            case R.id.sync_long:
                s = "Sync";
                pmsDatabase.sendDataForParticularId(currentId);
                break;

            case R.id.expense_long:
                s = "Expense";
                cls = ExpenseActivity.class;

                break;
            case R.id.receipt_long:
                s = "Receipt";
                cls = ReceiptActivity.class;
                break;
            case R.id.image_long:
                s = "Image";
                cls = ImageSelectionActivity.class;

                break;
        }
        if (cls != null)
            startActivity(new Intent(this, cls).putExtra("PROJECT_ID", currentId));
        Utility.showToast(ProjectActivity.this, s);
        return super.onContextItemSelected(item);


    }

}

