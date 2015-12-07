package in.junctiontech.project.image;

/**
 * Created by lenovo on 23-Nov-15.
 */
public class ImageSelection {

    private String project_id, task_id, image_location, status;

    public ImageSelection(String project_id, String task_id, String image_location, String status) {
        this.project_id = project_id;
        this.task_id = task_id;
        this.image_location = image_location;
        this.status = status;
    }

    public ImageSelection() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getImage_location() {
        return image_location;
    }

    public void setImage_location(String image_location) {
        this.image_location = image_location;
    }
}
