package in.junctiontech.pms.project;

/**
 * Created by Junction Software on 14-Oct-15.
 */
public class Expense {

    private String expenseDate, expenseType,
           expenseDescription;
    private int expenseAmount;

     /*
            getter methods
     */

    public String getExpenseDate() {
        return expenseDate;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public int getExpenseAmount() {
        return expenseAmount;
    }

   /*
            setter methods
     */

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public void setExpenseAmount(int expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
