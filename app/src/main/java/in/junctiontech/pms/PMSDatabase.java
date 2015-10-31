package in.junctiontech.pms;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.junctiontech.pms.employee.Employee;
import in.junctiontech.pms.employee.EmployeeLocation;
import static in.junctiontech.pms.PMSDataBaseConstant.*;


/**
 * Created by Junction Software on 14-Oct-15.
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

        Log.d("onCreate()", "DATABASE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // TODO if multiple fields are added then split it in to two statement i.e. alter table..., then call execsql multiple times
        /*String query = "Drop Table " + EMPLOYEE_LOCATION_TABLE_NAME;
        database.execSQL(query);
        Log.d("onUpgrade()", "DATABASE DELETED");
        onCreate(database);*/
        String upgradeQuery = "ALTER TABLE "+EMPLOYEE_LOCATION_TABLE_NAME+" ADD employeeeName TEXT";

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

        if (database.insert(EMPLOYEE_LOCATION_TABLE_NAME, null, contentValue) != -1) {
            Log.d("addEmployeeData()", "Record Inserted");
        } else {
            Log.d("addEmployeeData()", "Record Failure");
        }

        database.close();

    }

    public void deleteEmployeeData() {
        SQLiteDatabase database = super.getWritableDatabase();
        database.delete(EMPLOYEE_LOCATION_TABLE_NAME, null, null);
        database.close();
    }


    // TODO use GSON library to convert Object to JSON Formated String and vice versa

    public Employee getEmployeeData(final Employee employee) {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_LOCATION_TABLE_NAME, null);
        // TODO size according to cursor.getCount() value
        int countRecord=cursor.getCount();
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
            employee.setEmployeeLocationList(data);
        }

        cursor.close();
        database.close();

        return employee;

    }

    public int getNumberOfEmployeeRecord() {
        SQLiteDatabase database = super.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + EMPLOYEE_LOCATION_TABLE_NAME, null);
        int count =cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

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






