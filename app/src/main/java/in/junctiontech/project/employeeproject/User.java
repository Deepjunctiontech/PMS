package in.junctiontech.project.employeeproject;

import java.util.List;

/**
 * Created by JUNCTION SOFTWARE on 26-Nov-15.
 */
public class User {
    private String user_id;
    private List<Project> project_List;
    private String database_name;

    public User(String user_id, List<Project> project_List) {
        this.user_id = user_id;
        this.project_List = project_List;
    }

    /*
    *
    *       GETTER METHODS
    *
    * */

    public String getDatabase_name() {
        return database_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public List<Project> getProject_List() {
        return project_List;
    }

   /*
    *
    *       SETTER METHODS
    *
    * */

    public void setDatabase_name(String database_name) {
        this.database_name = database_name;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setProject_List(List<Project> project_List) {
        this.project_List = project_List;
    }
}
