package in.junctiontech.project.employeeproject;

/**
 * Created by JUNCTION SOFTWARE on 26-Nov-15.
 */
public class Receipt extends Task {

    private String material, quantity, rate, unit;
    private String date, key;


    public Receipt(String project_id, String task_id, String description, String material, String quantity, String rate, String unit, String date) {
        super(project_id, task_id, description);
        this.material = material;
        this.quantity = quantity;
        this.rate = rate;
        this.unit = unit;
        this.date = date;
    }

    public Receipt(String material, String quantity, String rate, String unit, String date, String key) {
        this.date = date;
        this.material = material;
        this.quantity = quantity;
        this.rate = rate;
        this.unit = unit;
        this.key = key;
    }

    public Receipt() {
    }

    public Receipt(String key, String status) {
        this.key = key;
        this.status = status;
    }

    public Receipt(String project_id, String task_id, String description, String date, String material) {
        super(project_id, task_id, description);
        this.date = date;
        this.material = material;
    }






    /*
    *
    *           GETTER METHODS
    *
    * */


    public String getKey() {
        return key;
    }

    public String getMaterial() {
        return material;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getRate() {
        return rate;
    }

    public String getUnit() {
        return unit;
    }

    public String getDate() {
        return date;
    }

    /*
    *
    *           SETTER METHODS
    *
    * */

    public void setKey(String key) {
        this.key = key;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
