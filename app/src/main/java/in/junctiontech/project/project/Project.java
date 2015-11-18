package in.junctiontech.project.project;


import java.util.List;

/**
 * Created by Junction Software on 14-Oct-15.
 */
public class Project {

    private String projectName;
    private List<ProjectTask> projectTaskList;

    private List<Expense> projectExpenseList;
    private List<Receipt> projectReceiptList;
    private List<Image> projectImageList;

    /*
            getter methods
     */
    public String getProjectName() {
        return projectName;
    }

    public List<ProjectTask> getProjectTaskList() {
        return projectTaskList;
    }

    public List<Expense> getProjectExpenseList() {
        return projectExpenseList;
    }

    public List<Receipt> getProjectReceiptList() {
        return projectReceiptList;
    }

    public List<Image> getProjectImageList() {
        return projectImageList;
    }

    /*
        setter methods
 */

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectTaskList(List<ProjectTask> projectTaskList) {
        this.projectTaskList = projectTaskList;
    }

    public void setProjectExpenseList(List<Expense> projectExpenseList) {
        this.projectExpenseList = projectExpenseList;
    }

    public void setProjectReceiptList(List<Receipt> projectReceiptList) {
        this.projectReceiptList = projectReceiptList;
    }

    public void setProjectImageList(List<Image> projectImageList) {
        this.projectImageList = projectImageList;
    }
}
