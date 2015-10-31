package in.junctiontech.pms.project;

/**
 * Created by Junction Software on 14-Oct-15.
 */
public class Image {

    private boolean imageStatus;
    private String imageName;

     /*
            getter methods
     */

    public boolean isImageStatus() {
        return imageStatus;
    }

    public String getImageName() {
        return imageName;
    }

  /*
            setter methods
     */

    public void setImageStatus(boolean imageStatus) {
        this.imageStatus = imageStatus;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
