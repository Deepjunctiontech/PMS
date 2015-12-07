package in.junctiontech.project.employeeproject;

/**
 * Created by Junction Software on 05-Dec-15.
 */
public class DashBoard {
    private String pid, tid, expense, receipt, image;

    public DashBoard(String pid, String tid, String expense, String receipt, String image) {
        this.pid = pid;
        this.tid = tid;
        this.expense = expense;
        this.receipt = receipt;
        this.image = image;
    }

    public DashBoard(String pid, String tid) {
        this.pid = pid;
        this.tid = tid;

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
