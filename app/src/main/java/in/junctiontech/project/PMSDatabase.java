package in.junctiontech.project;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import in.junctiontech.project.employee.EmployeeLocation;
import in.junctiontech.project.employee.EmployeeOtherDetails;
import in.junctiontech.project.employeeproject.DashBoard;
import in.junctiontech.project.employeeproject.Expense;
import in.junctiontech.project.employeeproject.Project;
import in.junctiontech.project.employeeproject.Receipt;
import in.junctiontech.project.employeeproject.Task;
import in.junctiontech.project.employeeproject.User;
import in.junctiontech.project.image.ImageSelection;

import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_EXPENSE_AMOUNT;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_EXPENSE_DATE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_EXPENSE_DESCRIPTION;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_EXPENSE_EXPENSE_TYPE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_EXPENSE_KEY;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_EXPENSE_STATUS;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_EXPENSE_TABLE_NAME;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_EXPENSE_TYPE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_LOCATION_BATTERY_LEVEL;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_LOCATION_DATE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_LOCATION_LATITUDE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_LOCATION_LONGITUDE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_LOCATION_PROVIDER_NAME;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_LOCATION_TABLE_NAME;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_LOCATION_TIME;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_LOCATION_VERSION_NUMBER;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_LIST_PROJECT_DESCRIPTION;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_LIST_PROJECT_ID;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_LIST_START_DATE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_LIST_STATUS;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_LIST_TABLE_NAME;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_TASK_IMAGE_LOCATION;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_TASK_IMAGE_STATUS;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_TASK_LIST_DESCRIPTION;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_TASK_LIST_STATUS;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_PROJECT_TASK_LIST_TASK_ID;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_DATE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_DESCRIPTION;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_KEY;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_MATERIAL;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_QUANTITY;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_RATE;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_STATUS;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_TABLE_NAME;
import static in.junctiontech.project.PMSDataBaseConstant.EMPLOYEE_RECEIPT_UNIT;


/**
 * Created by Junction Software on 14-Oct-15.
 * published on github 31-oct-2015
 */
public class PMSDatabase extends SQLiteOpenHelper {
    private static PMSDatabase pmsDatabase = null;
    private Context context;

    /**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */

    private PMSDatabase(Context context) {
        super(context, "employee", null, EMPLOYEE_LOCATION_VERSION_NUMBER);
        this.context = context;
    }

    public static PMSDatabase getInstance(Context context) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (pmsDatabase == null) {
            pmsDatabase = new PMSDatabase(context.getApplicationContext());
        }
        return pmsDatabase;
    }


    @Override
    public void onCreate(SQLiteDatabase database) {

        // TODO USE STRINGBUILDER CLASS TO STORE VALUES RATHER THEN STRING CLASS TO SAVE
        String query = "CREATE TABLE " + EMPLOYEE_LOCATION_TABLE_NAME + "(" +
                EMPLOYEE_LOCATION_DATE + " TEXT," +
                EMPLOYEE_LOCATION_TIME + " TEXT," +
                EMPLOYEE_LOCATION_LATITUDE + " REAL," +
                EMPLOYEE_LOCATION_LONGITUDE + " REAL," +
                EMPLOYEE_LOCATION_BATTERY_LEVEL + " INTEGER," +
                EMPLOYEE_LOCATION_PROVIDER_NAME + " TEXT" +
                ")";

        database.execSQL(query);
        Log.d("onCreate()", "DATABASE CREATED FOR TABLE LOCATION");

        query = "CREATE TABLE " + EMPLOYEE_PROJECT_LIST_TABLE_NAME + "(" +
                EMPLOYEE_PROJECT_LIST_PROJECT_ID + " TEXT PRIMARY KEY," +
                EMPLOYEE_PROJECT_LIST_PROJECT_DESCRIPTION + " TEXT," +
                EMPLOYEE_PROJECT_LIST_STATUS + " TEXT," +
                EMPLOYEE_PROJECT_LIST_START_DATE + " TEXT" +
                ")";

        database.execSQL(query);
        Log.d("onCreate()", "DATABASE CREATED FOR TABLE PROJECT LIST");


        query = "CREATE TABLE " + EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME + "(" +
                EMPLOYEE_PROJECT_LIST_PROJECT_ID + " TEXT," +
                EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " TEXT," +
                EMPLOYEE_PROJECT_TASK_LIST_DESCRIPTION + " TEXT," +
                EMPLOYEE_PROJECT_TASK_LIST_STATUS + " TEXT," +
                "PRIMARY KEY (" + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + ")," +
                "FOREIGN KEY (" + EMPLOYEE_PROJECT_LIST_PROJECT_ID + ") REFERENCES " +
                EMPLOYEE_PROJECT_LIST_TABLE_NAME + "(" + EMPLOYEE_PROJECT_LIST_PROJECT_ID + ")" +
                ")";

        database.execSQL(query);
        Log.d("onCreate()", "DATABASE CREATED FOR TABLE PROJECT TASK LIST");

        query = "CREATE TABLE " + EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME + "(" +
                EMPLOYEE_PROJECT_LIST_PROJECT_ID + " TEXT," +
                EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " TEXT," +
                EMPLOYEE_PROJECT_TASK_IMAGE_LOCATION + " TEXT," +
                EMPLOYEE_PROJECT_TASK_IMAGE_STATUS + " TEXT," +
                "FOREIGN KEY (" + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + ") REFERENCES " +
                EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME + "(" +
                EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + ")" +
                ")";

        database.execSQL(query);
        Log.d("onCreate()", "DATABASE CREATED FOR TABLE PROJECT TASK IMAGE LIST");


        query = "CREATE TABLE " + EMPLOYEE_EXPENSE_TABLE_NAME + "(" +
                EMPLOYEE_PROJECT_LIST_PROJECT_ID + " TEXT," +
                EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " TEXT," +
                EMPLOYEE_EXPENSE_TYPE + " TEXT," +
                EMPLOYEE_EXPENSE_EXPENSE_TYPE + " TEXT," +
                EMPLOYEE_EXPENSE_DATE + " TEXT," +
                EMPLOYEE_EXPENSE_AMOUNT + " INTEGER," +
                EMPLOYEE_EXPENSE_DESCRIPTION + " TEXT," +
                EMPLOYEE_EXPENSE_KEY + " TEXT," +
                EMPLOYEE_EXPENSE_STATUS + " TEXT," +
                "FOREIGN KEY (" + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + ") REFERENCES " +
                EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME + "(" +
                EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + ")" +
                ")";

        database.execSQL(query);
        Log.d("onCreate()", "DATABASE CREATED FOR TABLE EXPENSE");

        query = "CREATE TABLE " + EMPLOYEE_RECEIPT_TABLE_NAME + "(" +
                EMPLOYEE_PROJECT_LIST_PROJECT_ID + " TEXT," +
                EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " TEXT," +
                EMPLOYEE_RECEIPT_MATERIAL + " TEXT," +
                EMPLOYEE_RECEIPT_DATE + " TEXT," +
                EMPLOYEE_RECEIPT_DESCRIPTION + " TEXT," +
                EMPLOYEE_RECEIPT_RATE + " INTEGER," +
                EMPLOYEE_RECEIPT_QUANTITY + " INTEGER," +
                EMPLOYEE_RECEIPT_UNIT + " INTEGER," +
                EMPLOYEE_RECEIPT_KEY + " TEXT," +
                EMPLOYEE_RECEIPT_STATUS + " TEXT," +
                "FOREIGN KEY (" + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + ") REFERENCES " +
                EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME + "(" +
                EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + ")" +
                ")";

        database.execSQL(query);
        Log.d("onCreate()", "DATABASE CREATED FOR TABLE RECEIPT");

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // TODO if multiple fields are added then split it in to two statement i.e. alter table..., then call execsql multiple times
        /*String query = "Drop Table " + EMPLOYEE_LOCATION_TABLE_NAME;
        database.execSQL(query);
        Log.d("onUpgrade()", "DATABASE DELETED");
        onCreate(database);*/
        String upgradeQuery = "ALTER TABLE " + EMPLOYEE_LOCATION_TABLE_NAME + " ADD employeeeName TEXT";

        if (oldVersion == 1 && newVersion == 2) {
            database.execSQL(upgradeQuery);
            Log.d("onUpgrade()", "DATABASE UPDATED");
        }
    }

    public void setEmployeeData(final EmployeeLocation employeeLocation) {

        /*
        automatically boxing new Integer(e.getEmployee_location_batteryLevel()) primitive
        in to Object JAVA 5 and above set bydefault
        */

        SQLiteDatabase database = super.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(EMPLOYEE_LOCATION_DATE, employeeLocation.getEmployeeLocationDate());
        contentValue.put(EMPLOYEE_LOCATION_TIME, employeeLocation.getEmployeeLocationTime());
        contentValue.put(EMPLOYEE_LOCATION_LATITUDE, employeeLocation.getEmployeeLocationLatitude());  // new Double(e.getEmployee_location_latitude())
        contentValue.put(EMPLOYEE_LOCATION_LONGITUDE, employeeLocation.getEmployeeLocationLongitude());
        contentValue.put(EMPLOYEE_LOCATION_BATTERY_LEVEL, employeeLocation.getEmployeeLocationBatteryLevel());
        contentValue.put(EMPLOYEE_LOCATION_PROVIDER_NAME, employeeLocation.getEmployeeLocationProviderName());

        if (database.update(EMPLOYEE_LOCATION_TABLE_NAME, contentValue, EMPLOYEE_LOCATION_DATE + "=? AND " + EMPLOYEE_LOCATION_TIME + "=?",
                new String[]{employeeLocation.getEmployeeLocationDate(), employeeLocation.getEmployeeLocationTime()}) != 0) {
            Log.d("setEmployeeData()", "Record Updated");
        } else if (database.insert(EMPLOYEE_LOCATION_TABLE_NAME, null, contentValue) != -1) {
            Log.d("setEmployeeData()", "Record Inserted");
        } else {
            Log.d("setEmployeeData()", "Record Failure");
        }

        database.close();

    }

    public void deleteEmployeeData() {
        SQLiteDatabase database = super.getWritableDatabase();
        database.delete(EMPLOYEE_LOCATION_TABLE_NAME, null, null);
        database.close();
    }


    // TODO use GSON library to convert Object to JSON Formated String and vice versa

    public EmployeeOtherDetails getEmployeeData(final EmployeeOtherDetails employeeOtherDetails) {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_LOCATION_TABLE_NAME, null);   // TODO searching according to asc ki value means sorting
        // TODO size according to cursor.getCount() value
        int countRecord = cursor.getCount();
        if (countRecord > 0) {
            List<EmployeeLocation> data = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {

                data.add(new EmployeeLocation(cursor.getString(cursor.getColumnIndex(EMPLOYEE_LOCATION_DATE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_LOCATION_TIME)),
                        cursor.getDouble(cursor.getColumnIndex(EMPLOYEE_LOCATION_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(EMPLOYEE_LOCATION_LONGITUDE)),
                        (byte) cursor.getInt(cursor.getColumnIndex(EMPLOYEE_LOCATION_BATTERY_LEVEL)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_LOCATION_PROVIDER_NAME))));

            }
            employeeOtherDetails.setEmployeeIMEI(context.getSharedPreferences("employee_data",
                    Context.MODE_PRIVATE).getString("IMEI", "notfound"));  // TODO SET IMEI
            SharedPreferences sharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            employeeOtherDetails.setEmployeeOrganizationName(sharedPrefs.getString("organization_name", "NULL"));
            employeeOtherDetails.setEmployeeLocationList(data);


        }

        cursor.close();
        database.close();

        return employeeOtherDetails;

    }

    public int getNumberOfEmployeeRecord() {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_LOCATION_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public void setProjectList(List<in.junctiontech.project.employeeproject.Project> project_list) {
        int length = project_list.size();
        if (length > 0) {
            SQLiteDatabase database = super.getWritableDatabase();
            ContentValues contentValueProject = new ContentValues();
            ContentValues contentValueList = new ContentValues();
            in.junctiontech.project.employeeproject.Project projectList;
            for (int i = 0; i < length; i++) {
                projectList = project_list.get(i);

                /*if (database.update(EMPLOYEE_LOCATION_TABLE_NAME, contentValue, EMPLOYEE_LOCATION_DATE + "=? AND " + EMPLOYEE_LOCATION_TIME + "=?",
                        new String[]{employeeLocation.getEmployeeLocationDate(), employeeLocation.getEmployeeLocationTime()}) != 0) {
                    Log.d("setEmployeeData()", "Record Updated");
                } else*/ // TODO PUT UPDATE QUERY WHEN STATUS IS UPDATE FROM SERVER MEANS RECORD UPDATE

                Cursor cq = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROJECT_LIST_TABLE_NAME + " where " + EMPLOYEE_PROJECT_LIST_PROJECT_ID
                        + "=?", new String[]{projectList.getProject_id()});
                if (cq.moveToNext()) {
                    //   Toast.makeText(c, cq.getInt(cq.getColumnIndex("id")) + "\nId Selected!", Toast.LENGTH_LONG).show();

                } else {

                    contentValueProject.put(EMPLOYEE_PROJECT_LIST_PROJECT_ID, projectList.getProject_id());
                    contentValueProject.put(EMPLOYEE_PROJECT_LIST_PROJECT_DESCRIPTION, projectList.getDescription());
                    contentValueProject.put(EMPLOYEE_PROJECT_LIST_START_DATE, projectList.getStart_date());
                    contentValueProject.put(EMPLOYEE_PROJECT_LIST_STATUS, projectList.getStatus());

                    if (database.insert(EMPLOYEE_PROJECT_LIST_TABLE_NAME, null, contentValueProject) != -1) {
                        Log.d("setProjectList()", "Record Inserted");
                    } else {
                        Log.d("setProjectList()", "Record Failure");
                    }
                    List<Task> task_list = projectList.getTask_list();
                    if (task_list != null) {
                        int length1 = task_list.size();
                        //   Utility.showToast(context, length1 + "");
                        if (length1 > 0) {
                            for (int j = 0; j < length1; j++) {
                                Task task = task_list.get(j);
                                contentValueList.put(EMPLOYEE_PROJECT_LIST_PROJECT_ID, task.getProject_id());
                                contentValueList.put(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID, task.getTask_id());
                                contentValueList.put(EMPLOYEE_PROJECT_TASK_LIST_DESCRIPTION, task.getDescription());
                                contentValueList.put(EMPLOYEE_PROJECT_TASK_LIST_STATUS, task.getStatus());

                                if (database.insert(EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME, null, contentValueList) != -1) {
                                    Log.d("setProjectList()", "Record Inserted");
                                } else {
                                    Log.d("setProjectList()", "Record Failure");
                                }
                            }
                        }
                    }
                }
                cq.close();
            }

            database.close();
        }
    }

    public List<in.junctiontech.project.employeeproject.Project> getProjectList() {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROJECT_LIST_TABLE_NAME, null);   // TODO searching according to asc ki value means sorting
        // TODO size according to cursor.getCount() value
        int countRecord = cursor.getCount();
        List<in.junctiontech.project.employeeproject.Project> project_list = null;
        if (countRecord > 0) {
            project_list = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                in.junctiontech.project.employeeproject.Project project = new in.junctiontech.project.employeeproject.Project
                        (
                                cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                                cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_STATUS)),
                                cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_START_DATE)),
                                cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_DESCRIPTION)));

                project_list.add(project);

            }
        }
        cursor.close();
        database.close();
        return project_list;
    }

    public List<Task> getProjectTaskList(final String projectId) {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME + " where " + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "=?", new String[]{projectId});   // TODO searching according to asc ki value means sorting
        int countRecord = cursor.getCount();
        //  Utility.showToast(context, countRecord + "");
        List<Task> task_list = null;
        if (countRecord > 0) {
            task_list = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                Task task = new Task(
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_STATUS)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_DESCRIPTION)));
                task_list.add(task);

            }
        }
        cursor.close();
        database.close();
        return task_list;
    }

    public void setImageData(final ImageSelection imageSelection) {
        SQLiteDatabase database = super.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(EMPLOYEE_PROJECT_LIST_PROJECT_ID, imageSelection.getProject_id());
        contentValue.put(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID, imageSelection.getTask_id());
        contentValue.put(EMPLOYEE_PROJECT_TASK_IMAGE_LOCATION, imageSelection.getImage_location());
        contentValue.put(EMPLOYEE_PROJECT_TASK_IMAGE_STATUS, imageSelection.getStatus());

        if (database.insert(EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME, null, contentValue) != -1) {
            Utility.showToast(context, imageSelection.getProject_id() + "\n" + imageSelection.getTask_id() + "\n" + imageSelection.getImage_location());
            Log.d("setImageData()", "Record Inserted");
        } else {
            Log.d("setImageData()", "Record Failure");
        }

        database.close();

    }

    /*
    *   WE CANT TAKE SECOND TABLE FIELDS TO SENDS PROJECT IDS,BECAUSE MULTIPLE ENTRIES OF PROJECT IDS IN SECOND TABLE SO OUTPUT WILL ABSURT
    *   this is for only project list
    * */

    public List<String> getProjectIDS() {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + EMPLOYEE_PROJECT_LIST_PROJECT_ID + " FROM " + EMPLOYEE_PROJECT_LIST_TABLE_NAME, null);
        // TODO searching according to asc ki value means sorting
        int countRecord = cursor.getCount();
        List<String> projectListIDS = null;
        if (countRecord > 0) {

            projectListIDS = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                projectListIDS.add(cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)));
            }

        }
        cursor.close();
        database.close();
        return projectListIDS;
    }

    public List<String> getTaskIDSFromProjectID(final String projectId) {

        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID
                + " FROM " + EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME +
                " where " + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "=?", new String[]{projectId + ""}); // TODO: 25-Nov-15 remove +"" make systematic coding, projectId contain null when expense is open
        // TODO searching according to asc ki value means sorting
        int countRecord = cursor.getCount();
        //  Utility.showToast(context, countRecord + "");
        List<String> task_list = null;
        if (countRecord > 0) {

            task_list = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                task_list.add(cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID)));
            }


        }
        cursor.close();
        database.close();
        return task_list;
    }

    public List<ImageSelection> getImageData() {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME, null);
        // not filter according to status because its used in image displaying
        int countRecord = cursor.getCount();
        List<ImageSelection> imageSelections = null;
        if (countRecord > 0) {
            imageSelections = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                ImageSelection imageSelection = new ImageSelection(cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_IMAGE_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_IMAGE_STATUS)));

                imageSelections.add(imageSelection);

            }
        }
        cursor.close();
        database.close();
        return imageSelections;
    }

    public boolean setExpenseData(in.junctiontech.project.employeeproject.Expense expenseData) {
        boolean check;
        SQLiteDatabase database = super.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_EXPENSE_TABLE_NAME + " where " + EMPLOYEE_EXPENSE_KEY + " = ?",
                new String[]{expenseData.getKey()});
        ContentValues contentValue = new ContentValues();
        contentValue.put(EMPLOYEE_EXPENSE_STATUS, "pending");
        contentValue.put(EMPLOYEE_PROJECT_LIST_PROJECT_ID, expenseData.getProject_id());
        contentValue.put(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID, expenseData.getTask_id());
        contentValue.put(EMPLOYEE_EXPENSE_TYPE, expenseData.getType());
        contentValue.put(EMPLOYEE_EXPENSE_EXPENSE_TYPE, expenseData.getExpense_type());
        contentValue.put(EMPLOYEE_EXPENSE_DESCRIPTION, expenseData.getDescription());
        contentValue.put(EMPLOYEE_EXPENSE_AMOUNT, expenseData.getAmount());
        contentValue.put(EMPLOYEE_EXPENSE_DATE, expenseData.getDate());


        int countRecord = cursor.getCount();
        if (countRecord > 0) {
            if (database.update(EMPLOYEE_EXPENSE_TABLE_NAME, contentValue, EMPLOYEE_EXPENSE_KEY + "=?", new String[]{expenseData.getKey()}) != 0)
            {
                check = true;
                Log.d("setExpenseData()", "Record Updated Successfully");
                Utility.showToast(context, "Record Updated Successfully " + expenseData.getKey());
            }

            else {
                Log.d("setExpenseData()", "Record Updated Failure");
                Utility.showToast(context, "Record Updated Failure");
                check = false;
            }
        } else {
        /*cursor.close();
        database.close();*/
            contentValue.put(EMPLOYEE_EXPENSE_KEY, expenseData.getKey());
            if (database.insert(EMPLOYEE_EXPENSE_TABLE_NAME, null, contentValue) != -1) {
                Log.d("setExpenseData()", "Record Inserted");
                Utility.showToast(context, "Data Inserted Successfully");
                check = true;

            } else {
                check = false;
                Log.d("setExpenseData()", "Record Failure");
                Utility.showToast(context, "Data Inserted Failure");
            }
        }
        cursor.close();
        database.close();
        return check;
    }

    public boolean setReceiptData(in.junctiontech.project.employeeproject.Receipt receiptData) {
        boolean check;
        SQLiteDatabase database = super.getWritableDatabase();
        ContentValues contentValue = new ContentValues();

        contentValue.put(EMPLOYEE_PROJECT_LIST_PROJECT_ID, receiptData.getProject_id());
        contentValue.put(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID, receiptData.getTask_id());
        contentValue.put(EMPLOYEE_RECEIPT_STATUS, "pending");
        contentValue.put(EMPLOYEE_RECEIPT_DESCRIPTION, receiptData.getDescription());
        contentValue.put(EMPLOYEE_RECEIPT_MATERIAL, receiptData.getMaterial());
        contentValue.put(EMPLOYEE_RECEIPT_RATE, receiptData.getRate());
        contentValue.put(EMPLOYEE_RECEIPT_QUANTITY, receiptData.getQuantity());
        contentValue.put(EMPLOYEE_RECEIPT_UNIT, receiptData.getUnit());
        contentValue.put(EMPLOYEE_RECEIPT_DATE, receiptData.getDate());

        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_RECEIPT_TABLE_NAME + " where " + EMPLOYEE_RECEIPT_KEY + " = ?",
                new String[]{receiptData.getKey()});

        int countRecord = cursor.getCount();
        if (countRecord > 0) {
            if (database.update(EMPLOYEE_RECEIPT_TABLE_NAME, contentValue, EMPLOYEE_RECEIPT_KEY + "=?", new String[]{receiptData.getKey()}) != 0)
            {
                check = true;
                Log.d("setReceiptData()", "Record Updated Successfully");
                Utility.showToast(context, "Record Updated Successfully " + receiptData.getKey());
            }

            else {
                Log.d("setReceiptData()", "Record Updated Failure");
                Utility.showToast(context, "Record Updated Failure");
                check = false;
            }
        } else {
            contentValue.put(EMPLOYEE_RECEIPT_KEY, receiptData.getKey());
            if (database.insert(EMPLOYEE_RECEIPT_TABLE_NAME, null, contentValue) != -1) {
                Log.d("setReceiptData()", "Record Inserted");
                Utility.showToast(context, "Data Inserted Successfully");
                check = true;

            } else {
                check = false;
                Log.d("setReceiptData()", "Record Failure");
                Utility.showToast(context, "Data Inserted Failure");
            }
        }
        cursor.close();
        database.close();
        return check;



    }

    public void flushAllData() {
        SQLiteDatabase database = super.getWritableDatabase();

        int i = database.delete(EMPLOYEE_RECEIPT_TABLE_NAME, null, null);
        int j = database.delete(EMPLOYEE_EXPENSE_TABLE_NAME, null, null);
        int k = database.delete(EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME, null, null);
        int l = database.delete(EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME, null, null);
        int m = database.delete(EMPLOYEE_PROJECT_LIST_TABLE_NAME, null, null);

        //   boolean result = database.deleteDatabase(f);
        //   File f = new File(database.getPath());
        // TODO TRUNCATE EACH TABLE BECAUSE THIS METHOD NOT WORK IN LATEST VERSION OF LOLLIPOP AND EMPLOYEE DATA ALSO BE DELETED

        database.close();
        Log.d("logout", "total=" + i + j + k + l + m + i + "\n" + j + "\n" + k + "\n" + l + "\n" + m);
        return;
    }

    public void sendDataForParticularId(final String currentId) {


        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID
                + " FROM " + EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME +
                " where " + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "=?", new String[]{currentId + ""}); // TODO: 25-Nov-15 remove +"" make systematic coding, projectId contain null when expense is open

        int countRecord = cursor.getCount();


        //  Utility.showToast(context, countRecord + "");
        List<Task> task_list=null;

        if (countRecord > 0) {
            task_list = new ArrayList<>(countRecord);

            while (cursor.moveToNext()) {

                String currentTaskId = cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID));


                Utility.showToast(context, currentTaskId);

                List<Receipt> receipt_list = getReceiptData(currentId, currentTaskId);

                // projectTasks.set(getReceiptData(currentId, cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID))));
                Task task = new Task(currentId, currentTaskId);
                if (receipt_list != null) {
                    task.setReceipt_list(receipt_list);

                }

                List<Expense> expense_list = getExpenseData(currentId, currentTaskId);

                if (expense_list != null) {
                    task.setExpense_list(expense_list);

                }
                task_list.add(task);


            }
        }
            Project project = new Project(currentId);
            List<Expense> expense_list = getExpenseData(currentId, "null");
            project.setExpense_list(expense_list);
            List<Receipt> receipt_list = getReceiptData(currentId, "null");
            project.setReceipt_list(receipt_list);
            project.setTask_list(task_list);
            Gson gson = new GsonBuilder().create();
            List<Project> projectList = new ArrayList<>(1);
            projectList.add(project);
            User user = new User(
                    context.getSharedPreferences("Login", Context.MODE_PRIVATE).getString("user_id", "Not Found")
                    , projectList);
            SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
            user.setDatabase_name(sharedPrefs.getString("organization_name", "NULL"));

            String data = gson.toJson(user);
            Utility.longInfo(data);
            SendProjectData.SendData(context, data);


        cursor.close();
        database.close();


    }

    public List<Receipt> getReceiptData(String currentProjectId, String currentTaskId) {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_RECEIPT_TABLE_NAME + " where " +

                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID
                        + " = ? AND " + EMPLOYEE_RECEIPT_STATUS + " = ?",
                new String[]{currentProjectId, currentTaskId, "pending"});

        int countRecord = cursor.getCount();
        List<Receipt> receipt_list = null;
        if (countRecord > 0) {
            receipt_list = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                Receipt receipt = new Receipt(cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_MATERIAL)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_RATE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_UNIT)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_DATE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_KEY)));

                receipt_list.add(receipt);

            }
        }
        cursor.close();
        database.close();
        return receipt_list;
    }

    public List<Expense> getExpenseData(String currentProjectId, String currentTaskId) {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_EXPENSE_TABLE_NAME + " where " +

                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID
                        + " = ? AND " + EMPLOYEE_EXPENSE_STATUS + " = ?",
                new String[]{currentProjectId, currentTaskId, "pending"});

        int countRecord = cursor.getCount();
        List<Expense> expenseList = null;
        if (countRecord > 0) {
            expenseList = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                Expense expense = new Expense(cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_TYPE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_AMOUNT)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_DATE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_EXPENSE_TYPE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_KEY)));

                expenseList.add(expense);

            }
        }
        cursor.close();
        database.close();
        return expenseList;
    }

    public void updateImageStatus(String image_location) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMPLOYEE_PROJECT_TASK_IMAGE_STATUS, "success");
        long i = database.update(EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME, cv, EMPLOYEE_PROJECT_TASK_IMAGE_LOCATION + "=?", new String[]{image_location});
        // Toast.makeText(c,i+"",Toast.LENGTH_LONG).show();
        database.close();
    }

    public void updateExpenseStatus(List<Expense> expenses) {

        int length = expenses.size();
        if (length > 0) {
            SQLiteDatabase database = super.getWritableDatabase();


            in.junctiontech.project.employeeproject.Expense expense;
            for (int i = 0; i < length; i++) {
                expense = expenses.get(i);
                ContentValues contentValue = new ContentValues();

                contentValue.put(EMPLOYEE_EXPENSE_STATUS, expense.getStatus());

                if (database.update(EMPLOYEE_EXPENSE_TABLE_NAME, contentValue, EMPLOYEE_EXPENSE_KEY + "=?",
                        new String[]{expense.getKey()}) != 0) {
                    Log.d("updateExpenseStatus()", "Record Updated");

                }


            }
            database.close();

        }
    }

    public void updateReceiptStatus(List<Receipt> receipts) {

        int length = receipts.size();
        if (length > 0) {
            SQLiteDatabase database = super.getWritableDatabase();


            in.junctiontech.project.employeeproject.Receipt receipt;
            for (int i = 0; i < length; i++) {
                receipt = receipts.get(i);
                ContentValues contentValue = new ContentValues();

                contentValue.put(EMPLOYEE_RECEIPT_STATUS, receipt.getStatus());

                if (database.update(EMPLOYEE_RECEIPT_TABLE_NAME, contentValue, EMPLOYEE_RECEIPT_KEY + "=?",
                        new String[]{receipt.getKey()}) != 0) {
                    Log.d("updateReceiptStatus()", "Record Updated");

                }


            }
            database.close();
        }


    }

    public void updateEmployeeStatus(List<EmployeeLocation> employeeLocations) {

        int length = employeeLocations.size();
        if (length > 0) {
            SQLiteDatabase database = super.getWritableDatabase();


            EmployeeLocation employeeLocation;
            for (int i = 0; i < length; i++) {
                employeeLocation = employeeLocations.get(i);
                if (database.delete(EMPLOYEE_LOCATION_TABLE_NAME, EMPLOYEE_LOCATION_DATE
                                + " = ? AND " + EMPLOYEE_LOCATION_TIME + " = ?",
                        new String[]{employeeLocation.getEmployeeLocationDate(), employeeLocation.getEmployeeLocationTime()}) != 0) {
                    Log.d("updateEmployeeStatus()", "Record deleted");

                }


            }
            database.close();
        }

    }

    public List<DashBoard> getDashBoardTaskData() {

        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID +
                " FROM " + EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME, null);

        int countRecord = cursor.getCount();
        List<DashBoard> dashBoards = null;
        if (countRecord > 0) {
            dashBoards = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {

                DashBoard dashBoard = new DashBoard(cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID)));
                /*Cursor cursor1 = database.rawQuery("SELECT " + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID +
                        " FROM " + EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME, null);*/

                long total = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_EXPENSE_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid()});

                long count = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_EXPENSE_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ? AND " +
                                EMPLOYEE_EXPENSE_STATUS + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid(), "success"});

              //  Utility.showToast(context, count + "/" + total);
                dashBoard.setExpense(count + "/" + total);

                total = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_RECEIPT_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid()});

                count = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_RECEIPT_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ? AND " +
                                EMPLOYEE_RECEIPT_STATUS + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid(), "success"});

                dashBoard.setReceipt(count + "/" + total);

                total = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid()});

                count = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ? AND " +
                                EMPLOYEE_PROJECT_TASK_IMAGE_STATUS + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid(), "success"});

                dashBoard.setImage(count + "/" + total);
                dashBoards.add(dashBoard);

            }
        }
        cursor.close();
        database.close();

        return dashBoards;
    }

    public List<DashBoard> getDashBoardProjectData() {

        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + EMPLOYEE_PROJECT_LIST_PROJECT_ID +
                " FROM " + EMPLOYEE_PROJECT_LIST_TABLE_NAME, null);

        int countRecord = cursor.getCount();
        List<DashBoard> dashBoards = null;
        if (countRecord > 0) {
            dashBoards = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {

                DashBoard dashBoard = new DashBoard(cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                        "null");
                /*Cursor cursor1 = database.rawQuery("SELECT " + EMPLOYEE_PROJECT_LIST_PROJECT_ID + "," + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID +
                        " FROM " + EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME, null);*/

                long total = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_EXPENSE_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid()});

                long count = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_EXPENSE_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ? AND " +
                                EMPLOYEE_EXPENSE_STATUS + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid(), "success"});

              //  Utility.showToast(context, count + "/" + total);
                dashBoard.setExpense(count + "/" + total);

                total = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_RECEIPT_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid()});

                count = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_RECEIPT_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ? AND " +
                                EMPLOYEE_RECEIPT_STATUS + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid(), "success"});

                dashBoard.setReceipt(count + "/" + total);

                total = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid()});

                count = DatabaseUtils.queryNumEntries(database,
                        EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME,
                        EMPLOYEE_PROJECT_LIST_PROJECT_ID + " = ? AND " + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " = ? AND " +
                                EMPLOYEE_PROJECT_TASK_IMAGE_STATUS + " = ?",
                        new String[]{dashBoard.getPid(), dashBoard.getTid(), "success"});

                dashBoard.setImage(count + "/" + total);
                dashBoards.add(dashBoard);

            }
        }
        cursor.close();
        database.close();

        return dashBoards;


    }

    public List<Expense> getExpenseList() {

        SQLiteDatabase database = super.getReadableDatabase();
       /* Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_EXPENSE_TABLE_NAME + " ORDER BY date(" + EMPLOYEE_EXPENSE_DATE
        +") ASC", null);
*/
        Cursor cursor = database.query(EMPLOYEE_EXPENSE_TABLE_NAME, null, null, null, null, null,

                EMPLOYEE_PROJECT_LIST_PROJECT_ID + " ASC, "
                        + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " ASC," + // TODO sorting of date
                        EMPLOYEE_EXPENSE_DATE + " ASC "
        );


        int countRecord = cursor.getCount();
        List<Expense> expenseList = null;
        if (countRecord > 0) {
            expenseList = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                Expense expense = new Expense(
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_DATE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_AMOUNT))
                );
                expense.setKey(cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_KEY)));
                expenseList.add(expense);

            }
        }
        cursor.close();
        database.close();
        return expenseList;
    }

    public Expense getExpenseDataById(String key) {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_EXPENSE_TABLE_NAME + " where " + EMPLOYEE_EXPENSE_KEY + " = ?",
                new String[]{key});


        int countRecord = cursor.getCount();
        Expense expense = null;
        if (countRecord > 0) {
            while (cursor.moveToNext()) {
                expense = new Expense(
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_DATE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_AMOUNT))
                );

                expense.setKey(cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_KEY)));
                expense.setType(cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_TYPE)));
                expense.setExpense_type(cursor.getString(cursor.getColumnIndex(EMPLOYEE_EXPENSE_EXPENSE_TYPE)));

            }
        }
        cursor.close();
        database.close();
        return expense;

    }

    public List<Receipt> getReceiptList() {

        SQLiteDatabase database = super.getReadableDatabase();
       /* Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_EXPENSE_TABLE_NAME + " ORDER BY date(" + EMPLOYEE_EXPENSE_DATE
        +") ASC", null);
*/
        Cursor cursor = database.query(EMPLOYEE_RECEIPT_TABLE_NAME, null, null, null, null, null,

                EMPLOYEE_PROJECT_LIST_PROJECT_ID + " ASC, "
                        + EMPLOYEE_PROJECT_TASK_LIST_TASK_ID + " ASC," + // TODO sorting of date
                        EMPLOYEE_RECEIPT_DATE + " ASC "
        );


        int countRecord = cursor.getCount();
        List<Receipt> receiptList = null;
        if (countRecord > 0) {
            receiptList = new ArrayList<>(countRecord);
            while (cursor.moveToNext()) {
                Receipt receipt = new Receipt(
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_DATE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_MATERIAL))
                );
                receipt.setKey(cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_KEY)));
                receiptList.add(receipt);

            }
        }
        cursor.close();
        database.close();
        return receiptList;

    }


    public Receipt getReceiptDataById(String key) {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_RECEIPT_TABLE_NAME + " where " + EMPLOYEE_RECEIPT_KEY + " = ?",
                new String[]{key});


        int countRecord = cursor.getCount();
        Receipt receipt = null;
        if (countRecord > 0) {
            while (cursor.moveToNext()) {

                receipt = new Receipt(
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_LIST_PROJECT_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_PROJECT_TASK_LIST_TASK_ID)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_MATERIAL)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_RATE)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_UNIT)),
                        cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_DATE)));

                receipt.setKey(cursor.getString(cursor.getColumnIndex(EMPLOYEE_RECEIPT_KEY)));

            }
        }
        cursor.close();
        database.close();
        return receipt;

    }

    public void sendAllData() {


            List<String> projectIdList = getProjectIDS();
            if (projectIdList != null) {
                for (String currentProjectId : projectIdList)
                    sendDataForParticularId(currentProjectId);
            } else {
                Log.d("sync", "project id null");
                Utility.showToast(context, "There is no project in database");
            }



    }
        public void checkPreference() {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if ((sharedPreferences.getBoolean("sync_data", false))) {
                sendAllData();
                Log.d("sync", "net available for sending data");
                /*List list = getImageData();
                if (list != null) {
                    SendEmployeeProjectImages.getInstance(context).sendImageData(list);
                }
                else
                    Utility.showToast(context,"No Image In Database");*/

            }

    }







 /*   public void setEmployeeDateTime(final EmployeeLocation employeeLocation) {
        SQLiteDatabase database = super.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(EMPLOYEE_LOCATION_DATE, employeeLocation.getEmployeeLocationDate());
        contentValue.put(EMPLOYEE_LOCATION_TIME, employeeLocation.getEmployeeLocationTime());

        if (database.insert(EMPLOYEE_LOCATION_TABLE_NAME, null, contentValue) != -1) {
            Log.d("setEmployeeDateTime()", "Record Inserted");
        } else {
            Log.d("setEmployeeDateTime()", "Record Failure");
        }

        database.close();
    }*/

}



  /*  public void deleteEmployeeRecords(String date, String time) {
        SQLiteDatabase db = super.getWritableDatabase();

        time = time.replace("\"", "");
        date=date.replace("\"", "");
        Toast.makeText(context ,date+"\n"+time, Toast.LENGTH_SHORT).show();
        db.delete("employee", "date=? AND time=?", new String[]{date, time});
        db.execSQL("DELETE FROM employee WHERE time="+t);
        db.close();

    }*/






