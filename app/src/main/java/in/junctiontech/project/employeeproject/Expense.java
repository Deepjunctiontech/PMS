package in.junctiontech.project.employeeproject;

/**
 * Created by JUNCTION SOFTWARE on 26-Nov-15.
 */
public class Expense extends Task {

    private String expense_type,date, type, amount,key;

    public Expense(String project_id, String task_id, String description, String date, String type, String amount,String expense_type) {
        super(project_id, task_id, description);
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.expense_type = expense_type;
    }

    public Expense()
    {
        super();
    }

    public Expense(String type,String amount, String description, String date, String expense_type,String key) {
        this.date=date;
        this.type = type;
        this.expense_type = expense_type;
        this.amount = amount;
        this.key=key;
        setDescription(description);
    }

    public Expense(String key, String status) {
        this.key=key;
        this.status=status;
    }



    /*
    *
    *           GETTER METHODS
    *
    * */

    public String getKey() {
        return key;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

     /*
    *
    *           SETTER METHODS
    *
    * */

    public void setKey(String key) {
        this.key = key;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
