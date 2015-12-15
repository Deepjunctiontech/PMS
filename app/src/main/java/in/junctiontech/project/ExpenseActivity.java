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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.junctiontech.project.employeeproject.Expense;

/**
 * Created by Junction Software on 1-Oct-15.
 */
public class ExpenseActivity extends AppCompatActivity {

    private Spinner expenses_spinner_project, expenses_spinner_task;
    private PMSDatabase pmsDatabase;
    private ArrayAdapter adapterProjectTask;
    private in.junctiontech.project.employeeproject.Expense expense;
    private RadioButton expenses_rb_daily, expenses_rb_weekly;
    private EditText expenses_type, expenses_amount, expenses_description;
    private Calendar calendar;
    private int year, month, day;
    private Button expense_date;
    private String projectId, taskId;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        animation();
        referenceInitialization();
        eventRegistration();
        update();

    }

    protected void update() {
        //
       key= this.getIntent().getStringExtra("KEY");
        if(key!=null) {
            Expense expense = pmsDatabase.getExpenseDataById(key);
            if (expense != null) {
            /*expense.getProject_id();
            expense.getTask_id();*/
                expenses_spinner_project.setEnabled(false);
                expenses_spinner_task.setEnabled(false);
                expense_date.setText(expense.getDate());
                expenses_type.setText(expense.getExpense_type());
                expenses_amount.setText(expense.getAmount());
                expenses_description.setText(expense.getDescription());
                if ("Weekly".equalsIgnoreCase(expense.getType())) {
                    expenses_rb_weekly.setChecked(true);
                }
            }
        }


    }

    private void referenceInitialization() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        expense_date = (Button) findViewById(R.id.expense_date);
        String curr = year + "-" + (month + 1) + "-" + day;
        expense_date.setText(curr);

        expense = new in.junctiontech.project.employeeproject.Expense();
        expenses_rb_daily = (RadioButton) findViewById(R.id.expenses_rb_daily);
        expenses_rb_weekly = (RadioButton) findViewById(R.id.expenses_rb_weekly);
        expenses_amount = (EditText) findViewById(R.id.expenses_amount);
        expenses_description = (EditText) findViewById(R.id.expenses_description);
        expenses_type = (EditText) findViewById(R.id.expenses_type);
        pmsDatabase = PMSDatabase.getInstance(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        expenses_spinner_project = (Spinner) findViewById(R.id.expenses_spinner_project);
        expenses_spinner_task = (Spinner) findViewById(R.id.expenses_spinner_task);
        //expenses_spinner_project.onSaveInstanceState()

// TODO put list from database and populate

       /* ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.list_of_projects, R.layout.myspinner_layout);
        adapter.setDropDownViewResource(R.layout.myspinner_dropdown_item);
        expenses_spinner_project.setAdapter(adapter);
        */
       /* String s=this.getIntent().getStringExtra("TASK_ID");
        if(s==null)
        {
            task_status=true;
            expense.setTask_id("NULL");
        }*/

        String selectedProjectId = this.getIntent().getStringExtra("PROJECT_ID");
        List<String> projectListIDS = pmsDatabase.getProjectIDS();


        projectListIDS = (projectListIDS != null) ? projectListIDS : new ArrayList<String>(1);
        projectListIDS.add(0, "null");
        // fab_image_button.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapterProject = new ArrayAdapter(this, R.layout.myspinner_layout, projectListIDS);
        adapterProject.setDropDownViewResource(R.layout.myspinner_dropdown_item);
        expenses_spinner_project.setAdapter(adapterProject);
        expenses_spinner_project.setSelection(adapterProject.getPosition(selectedProjectId));


    }

    private void populateDataForTaskList(String selectedProjectId) {


        List<String> taskListIDS = pmsDatabase.getTaskIDSFromProjectID(selectedProjectId);
        taskListIDS = (taskListIDS != null) ? taskListIDS : new ArrayList<String>(1);
        taskListIDS.add(0, "null");

        if (adapterProjectTask == null) {
            Log.d("ADAPTER", "CREATED");
            adapterProjectTask = new ArrayAdapter(this, R.layout.myspinner_layout, taskListIDS);
            adapterProjectTask.setDropDownViewResource(R.layout.myspinner_dropdown_item);
            expenses_spinner_task.setAdapter(adapterProjectTask);
            String selectedTaskId = this.getIntent().getStringExtra("TASK_ID");
            expenses_spinner_task.setSelection(adapterProjectTask.getPosition(selectedTaskId));
            // for null no issue, when user directly come to this page TASK_ID not get, no problm
        } else {
            Log.d("ADAPTER", "MODIFIED");
            adapterProjectTask.clear();
            adapterProjectTask.addAll(taskListIDS);
            adapterProjectTask.notifyDataSetChanged();
        }

    }


    private void eventRegistration() {

        expenses_spinner_project.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    //imageSelection.setProject_id(parent.getItemAtPosition(position) + "");
                    populateDataForTaskList(parent.getItemAtPosition(position) + "");
                    //  Utility.showToast(ExpenseActivity.this, parent.getItemAtPosition(position) + "");
                    expense.setProject_id(parent.getItemAtPosition(position) + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        expenses_spinner_task.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    expense.setTask_id(parent.getItemAtPosition(position) + "");
                    Utility.showToast(ExpenseActivity.this, parent.getItemAtPosition(position) + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void animation() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.expenses1);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.expenses2);
        LinearLayout l3 = (LinearLayout) findViewById(R.id.expenses3);
        LinearLayout l4 = (LinearLayout) findViewById(R.id.expenses4);
        LinearLayout l5 = (LinearLayout) findViewById(R.id.expenses5);

        TranslateAnimation trans1 = new TranslateAnimation(-1000, 1, -100, 1);
        TranslateAnimation trans2 = new TranslateAnimation(1000, 1, 100, 1);
        TranslateAnimation trans3 = new TranslateAnimation(100, 1, 1000, 1);

        trans1.setDuration(1100);
        trans2.setDuration(1100);
        trans3.setDuration(1100);


        ll.setAnimation(trans1);
        l2.setAnimation(trans2);
        l3.setAnimation(trans1);
        l5.setAnimation(trans1);
        l4.setAnimation(trans3);

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

    public void expenseSaving(View view) {
        expenses_spinner_project.setFocusableInTouchMode(false);
        expenses_spinner_project.clearFocus();


        if (!"null".equalsIgnoreCase(expense.getProject_id()) && !isEmptyAmount()) {
            expense.setExpense_type((expenses_rb_daily.isChecked() ? expenses_rb_daily.getText() : expenses_rb_weekly.getText()).toString());
            expense.setAmount(expenses_amount.getText().toString());
            expense.setDescription(expenses_description.getText().toString());
            expense.setType(expenses_type.getText().toString());
            expense.setDate(expense_date.getText().toString());

            if(key==null)
            expense.setKey("USER_ID=" +
                    getSharedPreferences("Login", MODE_PRIVATE).getString("user_id", "Not Found")
                    + "," + "PROJECT_ID=" + expense.getProject_id() + "," + "TASK_ID=" + expense.getTask_id() + "," +
                    (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date())));
            else
            expense.setKey(key);

            Utility.showToast(this,  key);

            if (pmsDatabase.setExpenseData(expense))
                finish();

        } else {
            if ("null".equalsIgnoreCase(expense.getProject_id())) {
                Utility.showToast(this, "please select project id");
                TextView errorText = (TextView) expenses_spinner_project.getSelectedView();
                errorText.setError("invalid projectid");
                expenses_spinner_project.setFocusableInTouchMode(true);
                expenses_spinner_project.requestFocus();
                //errorText.setTextColor(Color.RED);just to highlight that this is an error
                //errorText.setText("my actual error text");//changes the selected item text to this
            } else if (isEmptyAmount()) {
                expenses_amount.setError("please enter amount");
                expenses_amount.setFocusableInTouchMode(true);
                expenses_amount.requestFocus();

            }
        }

    }

    private boolean isEmptyAmount() {
        return expenses_amount.getText() == null || expenses_amount.getText().toString() == null
                || expenses_amount.getText().toString().equals("") || expenses_amount.getText().toString().isEmpty();
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
            expense_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

        }
    };

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


}
