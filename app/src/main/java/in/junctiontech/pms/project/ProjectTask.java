package in.junctiontech.pms.project;

import java.util.List;

/**
 * Created by Junction Software on 14-Oct-15.
 */
public class ProjectTask {

    private String projectTaskName, projectTaskDescription,
            projectTaskStartDate, projectTaskEndDate;
    private byte projectTaskPriority;

    private List<Expense> projectTaskExpenseList;
    private List<Receipt> projectTaskReceiptList;
    private List<Image> projectTaskImageList;

     /*
            getter methods
     */

    public String getProjectTaskName() {
        return projectTaskName;
    }

    public String getProjectTaskDescription() {
        return projectTaskDescription;
    }

    public String getProjectTaskStartDate() {
        return projectTaskStartDate;
    }

    public String getProjectTaskEndDate() {
        return projectTaskEndDate;
    }

    public byte getProjectTaskPriority() {
        return projectTaskPriority;
    }

    public List<Expense> getProjectTaskExpenseList() {
        return projectTaskExpenseList;
    }

    public List<Receipt> getProjectTaskReceiptList() {
        return projectTaskReceiptList;
    }

    public List<Image> getProjectTaskImageList() {
        return projectTaskImageList;
    }
     /*
            setter methods
     */

    public void setProjectTaskName(String projectTaskName) {
        this.projectTaskName = projectTaskName;
    }

    public void setProjectTaskDescription(String projectTaskDescription) {
        this.projectTaskDescription = projectTaskDescription;
    }

    public void setProjectTaskStartDate(String projectTaskStartDate) {
        this.projectTaskStartDate = projectTaskStartDate;
    }

    public void setProjectTaskEndDate(String projectTaskEndDate) {
        this.projectTaskEndDate = projectTaskEndDate;
    }

    public void setProjectTaskPriority(byte projectTaskPriority) {
        this.projectTaskPriority = projectTaskPriority;
    }

    public void setProjectTaskExpenseList(List<Expense> projectTaskExpenseList) {
        this.projectTaskExpenseList = projectTaskExpenseList;
    }

    public void setProjectTaskReceiptList(List<Receipt> projectTaskReceiptList) {
        this.projectTaskReceiptList = projectTaskReceiptList;
    }

    public void setProjectTaskImageList(List<Image> projectTaskImageList) {
        this.projectTaskImageList = projectTaskImageList;
    }
}
