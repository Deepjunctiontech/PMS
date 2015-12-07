package in.junctiontech.project.employee;


import android.content.Context;

import java.util.List;

/**
 * Created by Junction Software on 13-Oct-15.
 */
public class EmployeeOtherDetails extends Employee {


    private List<EmployeeLocation> employeeLocationList;


    public EmployeeOtherDetails(Context context) {

        super(context);
        getEmployeeName();// For Testing if we comment super(context) give error there is no default constructor in parent class
    }

    /*
               getter methods
        */


    public List<EmployeeLocation> getEmployeeLocationList() {
        return employeeLocationList;
    }

    /*
           setter methods
    */


    public void setEmployeeLocationList(final List<EmployeeLocation> employeeLocationList) {
        this.employeeLocationList = employeeLocationList;
    }
}
