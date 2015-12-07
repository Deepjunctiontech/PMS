package in.junctiontech.project.employeeproject;

import java.util.List;

/**
 * Created by JUNCTION SOFTWARE on 26-Nov-15.
 */
public class Project {

    String project_id, status, start_date, end_date, description;
    protected List<Expense> expense_list;
    protected List<Receipt> receipt_list;
    private List<Task> task_list;

    public Project(String project_id, String status, String start_date, String end_date, String description, List<Task> task_list) {
        this(project_id, status, start_date, end_date, description);
        this.task_list = task_list;
    }

    public Project(String project_id, String status, String start_date, String end_date, String description) {
        this.project_id = project_id;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
    }

    public Project(String project_id, String status, String start_date, String description) {
        this.project_id = project_id;
        this.status = status;
        this.start_date = start_date;
        this.description = description;
    }

    public Project(String project_id, String status, String description) {
        this.project_id = project_id;
        this.status = status;
        this.description = description;
    }

    public Project(String project_id, String description) {
        this.project_id = project_id;
        this.description = description;
    }

    public Project() {

    }

    public Project(String project_id) {
        this.project_id = project_id;
    }

    /*
    *
    *       GETTER METHODS
    *
    * */

    public List<Expense> getExpense_list() {
        return expense_list;
    }

    public List<Receipt> getReceipt_list() {
        return receipt_list;
    }


    public String getProject_id() {
        return project_id;
    }

    public String getStatus() {
        return status;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getDescription() {
        return description;
    }

    public List<Task> getTask_list() {
        return task_list;
    }

   /*
    *
    *       SETTER METHODS
    *
    * */

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTask_list(List<Task> task_list) {
        this.task_list = task_list;
    }

    public void setExpense_list(List<Expense> expense_list) {
        this.expense_list = expense_list;
    }

    public void setReceipt_list(List<Receipt> receipt_list) {
        this.receipt_list = receipt_list;
    }

}
