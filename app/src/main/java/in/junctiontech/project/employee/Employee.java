package in.junctiontech.project.employee;

import android.content.Context;

/**
 * Created by JUNCTION SOFTWARE on 18-Nov-15.
 *
 * Date
 */


public class Employee {

    protected String employeeName;
    protected String employeeIMEI;   //  TODO MAKE IT INT
    protected String employeeOrganizationName;
    protected String employeeMobileNumber;
    private String employeePassword;
     private String employeeId;
     // may be change to protected

    /* Constructor  */

    public Employee(String employeeOrganizationName, String employeeMobileNumber, String employeePassword) {
        this.employeeOrganizationName = employeeOrganizationName;
        this.employeeMobileNumber = employeeMobileNumber;
        this.employeePassword = employeePassword;
    }

    public Employee(Context context) {
        employeeIMEI = context.getSharedPreferences("employee_data", Context.MODE_PRIVATE).getString("IMEI", "notfound");
        // this constructor is very necessary because child class have parametrized constructor
        // -- internally call constructor of this class of no argument constructor (inheritance rule)
    }

    public Employee(Context context, String employeeName, String employeeOrganizationName, String employeeMobileNumber, String employeePassword,String employeeId) {
        this(context);
        this.employeeId=employeeId;
        this.employeeName = employeeName;
        this.employeeOrganizationName = employeeOrganizationName;
        this.employeeMobileNumber = employeeMobileNumber;
        this.employeePassword = employeePassword;
    }

   /*
    getter methods
    */

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeIMEI() {
        return employeeIMEI;
    }

    public String getEmployeeOrganizationName() {
        return employeeOrganizationName;
    }

    public String getEmployeeMobileNumber() {
        return employeeMobileNumber;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    /*
    setter methods
    */

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeIMEI(String employeeIMEI) {
        this.employeeIMEI = employeeIMEI;
    }

    public void setEmployeeOrganizationName(String employeeOrganizationName) {
        this.employeeOrganizationName = employeeOrganizationName;
    }

    public void setEmployeeMobileNumber(String employeeMobileNumber) {
        this.employeeMobileNumber = employeeMobileNumber;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }
}
