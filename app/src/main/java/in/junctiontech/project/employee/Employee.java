package in.junctiontech.project.employee;


import android.content.Context;

import java.util.List;

import in.junctiontech.project.project.Project;

/**
 * Created by Junction Software on 13-Oct-15.
 */
public class Employee {


    private String employeeName;
    private String employeeIMEI;  //  TODO MAKE IT INT
    private List<Project> employeeProjectList;
    private List<EmployeeLocation> employeeLocationList;

  public Employee(Context context)
    {
        employeeIMEI= context.getSharedPreferences("employee_data", Context.MODE_PRIVATE).getString("IMEI", "notfound");
    }

    /*
               getter methods
        */
    public String getEmployeeIMEI() {
        return employeeIMEI;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public List<Project> getEmployeeProjectList() {
        return employeeProjectList;
    }

    public List<EmployeeLocation> getEmployeeLocationList() {
        return employeeLocationList;
    }

    /*
           setter methods
    */
    public  void setEmployeeIMEI(final String employeeIMEI) {
        this.employeeIMEI = employeeIMEI;
    }

    public void setEmployeeName(final String employeeName) {
        this.employeeName = employeeName;
    }


    public void setEmployeeProjectList(final List<Project> employeeProjectList) {
        this.employeeProjectList = employeeProjectList;
    }

    public void setEmployeeLocationList(final List<EmployeeLocation> employeeLocationList) {
        this.employeeLocationList = employeeLocationList;
    }
}
