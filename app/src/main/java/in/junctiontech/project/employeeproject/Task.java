package in.junctiontech.project.employeeproject;

import java.util.List;

/**
 * Created by Junction Software on 26-Nov-15.
 */
public class Task extends Project {

    String task_id;
    private byte priority;



    public Task(String project_id, String task_id,  String status, String start_date, String end_date, String description) {
        super(project_id, status, start_date, end_date, description);
        this.task_id=task_id;
    }

    public Task(String project_id, String task_id, String status, String description) {
        super(project_id, status, description);
        this.task_id=task_id;

    }


    public Task(String project_id, String task_id, String description) {
        super(project_id, description);
        this.task_id=task_id;

    }

    public Task(String project_id, String task_id) {
        super(project_id);
        this.task_id=task_id;

    }

    public Task() {
        super();

    }




    /*
    *
    *           GETTER METHODS
    *
    * */

    public String getTask_id() {
        return task_id;
    }

    public byte getPriority() {
        return priority;
    }



   /*
    *
    *           SETTER METHODS
    *
    * */

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }


}
