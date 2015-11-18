package in.junctiontech.project.employee;

/**
 * Created by Junction Software on 13-Oct-15.
 */
public class EmployeeLocation {

    private byte employeeLocationBatteryLevel;
    private String employeeLocationDate, employeeLocationTime, employeeLocationProviderName;
    private double employeeLocationLatitude, employeeLocationLongitude;

    public EmployeeLocation() {
    }

    public EmployeeLocation(final String employeeLocationDate,final String employeeLocationTime,
                            final double employeeLocationLatitude,final double employeeLocationLongitude,
                            final byte employeeLocationBatteryLevel,final String employeeLocationProviderName)
    {
        this.employeeLocationDate=employeeLocationDate;
        this.employeeLocationTime=employeeLocationTime;
        this.employeeLocationLatitude=employeeLocationLatitude;
        this.employeeLocationLongitude=employeeLocationLongitude;
        this.employeeLocationBatteryLevel=employeeLocationBatteryLevel;
        this.employeeLocationProviderName=employeeLocationProviderName;

    }

    @Override
    public String toString()
    {
        return "";
    }


    /*
            getter methods
     */

    public byte getEmployeeLocationBatteryLevel() {
        return employeeLocationBatteryLevel;
    }

    public String getEmployeeLocationDate() {
        return employeeLocationDate;
    }

    public String getEmployeeLocationTime() {
        return employeeLocationTime;
    }

    public String getEmployeeLocationProviderName() {
        return employeeLocationProviderName;
    }

    public double getEmployeeLocationLatitude() {
        return employeeLocationLatitude;
    }

    public double getEmployeeLocationLongitude() {
        return employeeLocationLongitude;
    }

    /*
            setter methods
     */

    public void setEmployeeLocationBatteryLevel(final byte employeeLocationBatteryLevel) {
        this.employeeLocationBatteryLevel = employeeLocationBatteryLevel;
    }

    public void setEmployeeLocationDate(final String employeeLocationDate) {
        this.employeeLocationDate = employeeLocationDate;
    }

    public void setEmployeeLocationTime(final String employeeLocationTime) {
        this.employeeLocationTime = employeeLocationTime;
    }

    public void setEmployeeLocationProviderName(final String employeeLocationProviderName) {
        this.employeeLocationProviderName = employeeLocationProviderName;
    }

    public void setEmployeeLocationLatitude(final double employeeLocationLatitude) {
        this.employeeLocationLatitude = employeeLocationLatitude;
    }

    public void setEmployeeLocationLongitude(final double employeeLocationLongitude) {
        this.employeeLocationLongitude = employeeLocationLongitude;
    }
}
