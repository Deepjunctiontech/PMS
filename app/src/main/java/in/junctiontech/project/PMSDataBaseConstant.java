package in.junctiontech.project;

/**
 * Created by Junction Software on 14-Oct-15.
 */


//  default visibility only access within the package

interface PMSDataBaseConstant {

    byte EMPLOYEE_LOCATION_VERSION_NUMBER = 1;

    /*
    *       Constants for table- employeeLocation
    *
    * */
    String EMPLOYEE_LOCATION_TABLE_NAME = "employeeLocation";
    String EMPLOYEE_LOCATION_DATE = "employeeLocationDate";
    String EMPLOYEE_LOCATION_TIME = "employeeLocationTime";
    String EMPLOYEE_LOCATION_LATITUDE = "employeeLocationLatitude";
    String EMPLOYEE_LOCATION_LONGITUDE = "employeeLocationLongitude";
    String EMPLOYEE_LOCATION_PROVIDER_NAME = "employeeLocationProviderName";
    String EMPLOYEE_LOCATION_BATTERY_LEVEL = "employeeLocationBatteryLevel";

    /*
    *       Constants for table- employeeProjectList
    *
    * */

    String EMPLOYEE_PROJECT_LIST_TABLE_NAME = "employeeProjectList";
    String EMPLOYEE_PROJECT_LIST_PROJECT_ID = "employeeProjectListProjectId";
    String EMPLOYEE_PROJECT_LIST_PROJECT_DESCRIPTION = "employeeProjectListDescription";
    String EMPLOYEE_PROJECT_LIST_STATUS = "employeeProjectListStatus";
    String EMPLOYEE_PROJECT_LIST_START_DATE = "employeeProjectListStartDate";

     /*
    *       Constants for table- employeeProjectTaskList
    *
    * */

    String EMPLOYEE_PROJECT_TASK_LIST_TABLE_NAME = "employeeProjectTaskList";
    //  String EMPLOYEE_PROJECT_TASK_LIST_PROJECT_ID = "employeeProjectTaskListProjectId";
    String EMPLOYEE_PROJECT_TASK_LIST_TASK_ID = "employeeProjectTaskListTaskId";
    String EMPLOYEE_PROJECT_TASK_LIST_DESCRIPTION = "employeeProjectTaskListDescription";
    String EMPLOYEE_PROJECT_TASK_LIST_STATUS = "employeeProjectTaskListStatus";

    /*
  *       Constants for table- employeeProjectTaskImageList
  *
  * */
    String EMPLOYEE_PROJECT_TASK_IMAGE_LIST_TABLE_NAME = "employeeProjectTaskImageList";
    String EMPLOYEE_PROJECT_TASK_IMAGE_LOCATION = "employeeProjectTaskImageLocation";
    String EMPLOYEE_PROJECT_TASK_IMAGE_STATUS = "employeeProjectTaskImageStatus";

    /*
      *       Constants for table- employeeExpense
      *
      * */
    String EMPLOYEE_EXPENSE_TABLE_NAME = "employeeExpense";
    String EMPLOYEE_EXPENSE_EXPENSE_TYPE = "employeeExpenseExpense_Type";
    String EMPLOYEE_EXPENSE_TYPE = "employeeExpenseType";
    String EMPLOYEE_EXPENSE_AMOUNT = "employeeExpenseAmount";
    String EMPLOYEE_EXPENSE_DESCRIPTION = "employeeExpenseDescription";
    String EMPLOYEE_EXPENSE_DATE = "employeeExpenseDate";
    String EMPLOYEE_EXPENSE_KEY = "employeeExpenseKey";
    String EMPLOYEE_EXPENSE_STATUS = "employeeExpenseStatus";

    /*
    *       Constants for table - employeeReceipt
    *
    * */
    String EMPLOYEE_RECEIPT_TABLE_NAME = "employeeReceipt";
    String EMPLOYEE_RECEIPT_MATERIAL = "employeeReceiptMaterial";
    String EMPLOYEE_RECEIPT_RATE = "employeeReceiptRate";
    String EMPLOYEE_RECEIPT_QUANTITY = "employeeReceiptQuantity";
    String EMPLOYEE_RECEIPT_UNIT = "employeeReceiptUnit";
    String EMPLOYEE_RECEIPT_DATE = "employeeReceiptDate";
    String EMPLOYEE_RECEIPT_KEY = "employeeReceiptKey";
    String EMPLOYEE_RECEIPT_STATUS = "employeeReceiptStatus";

}
