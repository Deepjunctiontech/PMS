package in.junctiontech.project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Junction Software on 17-Oct-15.
 */
public class ReceiptActivity extends AppCompatActivity {

    private Spinner receipt_spinner_project, receipt_spinner_task;
    private PMSDatabase pmsDatabase;
    private ArrayAdapter adapterProjectTask;
    private in.junctiontech.project.employeeproject.Receipt receipt;
    private EditText receipt_quantity, receipt_material, receipt_rate, receipt_unit;
    private Calendar calendar;
    private int year, month, day;
    private Button receipt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        animation();
        referenceInitialization();
        eventRegistration();

        //   expenses_spinner_project.setAdapter(new ArrayAdapter<String>(this, R.layout.myspinner_layout, new String[]{"hello", "hi", "tata", "bye"}));
        //expenses_spinner_project.onSaveInstanceState()

       /* ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.list_of_projects, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_item);
        receipt_spinner_project.setAdapter(adapter);


        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.list_of_tasks, R.layout.myspinner_layout);
        adapter1.setDropDownViewResource(R.layout.myspinner_dropdown_item);
        receipt_spinner_task.setAdapter(adapter1);*/

    }

    private void referenceInitialization() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        receipt_date = (Button) findViewById(R.id.receipt_date);
        String curr = day + "/" + (month + 1) + "/" + year + "";
        receipt_date.setText(curr);

        receipt_material = (EditText) findViewById(R.id.receipt_material);
        receipt_quantity = (EditText) findViewById(R.id.receipt_quantity);
        receipt_rate = (EditText) findViewById(R.id.receipt_rate);
        receipt_unit = (EditText) findViewById(R.id.receipt_unit);

        receipt = new in.junctiontech.project.employeeproject.Receipt();
        pmsDatabase = PMSDatabase.getInstance(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        receipt_spinner_project = (Spinner) findViewById(R.id.receipt_spinner_project);
        receipt_spinner_task = (Spinner) findViewById(R.id.receipt_spinner_task);

        String selectedProjectId = this.getIntent().getStringExtra("PROJECT_ID");
        List<String> projectListIDS = pmsDatabase.getProjectIDS();
        projectListIDS = (projectListIDS != null) ? projectListIDS : new ArrayList<String>(1);
        projectListIDS.add(0, "null");

        ArrayAdapter<String> adapterProject = new ArrayAdapter(this, R.layout.myspinner_layout, projectListIDS);
        adapterProject.setDropDownViewResource(R.layout.myspinner_dropdown_item);
        receipt_spinner_project.setAdapter(adapterProject);
        receipt_spinner_project.setSelection(adapterProject.getPosition(selectedProjectId));


    }

    private void animation() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.receipt1);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.receipt2);
        LinearLayout l3 = (LinearLayout) findViewById(R.id.receipt3);
        LinearLayout l4 = (LinearLayout) findViewById(R.id.receipt4);

        TranslateAnimation trans1 = new TranslateAnimation(-1000, 1, -100, 1);
        TranslateAnimation trans2 = new TranslateAnimation(1000, 1, 100, 1);
        TranslateAnimation trans3 = new TranslateAnimation(100, 1, 1000, 1);
        trans1.setDuration(1100);
        trans2.setDuration(1100);
        trans3.setDuration(1100);

        ll.setAnimation(trans1);
        l2.setAnimation(trans2);
        l3.setAnimation(trans1);
        l4.setAnimation(trans3);
    }

    private void populateDataForTaskList(String selectedProjectId) {
        List<String> taskListIDS = pmsDatabase.getTaskIDSFromProjectID(selectedProjectId);
        taskListIDS = (taskListIDS != null) ? taskListIDS : new ArrayList<String>(1);
        taskListIDS.add(0, "null");
        if (adapterProjectTask == null) {
            Log.d("ADAPTER", "CREATED");
            adapterProjectTask = new ArrayAdapter(this, R.layout.myspinner_layout, taskListIDS);
            adapterProjectTask.setDropDownViewResource(R.layout.myspinner_dropdown_item);
            receipt_spinner_task.setAdapter(adapterProjectTask);
            String selectedTaskId = this.getIntent().getStringExtra("TASK_ID");
            receipt_spinner_task.setSelection(adapterProjectTask.getPosition(selectedTaskId));
            // for null no issue, when user directly come to this page TASK_ID not get, no problm
        } else {
            Log.d("ADAPTER", "MODIFIED");
            adapterProjectTask.clear();
            adapterProjectTask.addAll(taskListIDS);
            adapterProjectTask.notifyDataSetChanged();

        }

    }


    private void eventRegistration() {

        receipt_spinner_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    //imageSelection.setProject_id(parent.getItemAtPosition(position) + "");
                    populateDataForTaskList(parent.getItemAtPosition(position) + "");
                    //   Utility.showToast(ReceiptActivity.this, parent.getItemAtPosition(position) + "");
                    receipt.setProject_id(parent.getItemAtPosition(position) + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        receipt_spinner_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null)
                    receipt.setTask_id(parent.getItemAtPosition(position) + "");
                // Utility.showToast(ReceiptActivity.this, parent.getItemAtPosition(position) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


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

    public void receiptSaving(View view) {
        if (!"null".equalsIgnoreCase(receipt.getProject_id())) {
            receipt.setQuantity(receipt_quantity.getText().toString());
            receipt.setMaterial(receipt_material.getText().toString());
            receipt.setRate(receipt_rate.getText().toString());
            receipt.setUnit(receipt_unit.getText().toString());
            receipt.setDate(receipt_date.getText().toString());
            receipt.setKey("USER_ID=" +
                    getSharedPreferences("Login", MODE_PRIVATE).getString("user_id", "Not Found")
                    + "," + "PROJECT_ID=" + receipt.getProject_id() + "," + "TASK_ID=" + receipt.getTask_id() + "," +
                    (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date())));
            pmsDatabase.setReceiptData(receipt);
        } else
            Utility.showToast(this, "Please select project id");

    }

    public void selectDate(View v) {
        showDialog(999);
        Utility.showToast(this, "Date Selection");
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            receipt_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
        }
    };

}
