package in.junctiontech.pms.project;

/**
 * Created by Junction Software on 14-Oct-15.
 */
public class Receipt {

    private String receiptMaterial;
    private int receiptQuantity, receiptRate,
            receiptUnit;
    private String receiptDate;


     /*
            getter methods
     */

    public String getReceiptMaterial() {
        return receiptMaterial;
    }

    public int getReceiptQuantity() {
        return receiptQuantity;
    }

    public int getReceiptRate() {
        return receiptRate;
    }

    public int getReceiptUnit() {
        return receiptUnit;
    }

    public String getReceiptDate() {
        return receiptDate;
    }
/*
            setter methods
     */

    public void setReceiptMaterial(String receiptMaterial) {
        this.receiptMaterial = receiptMaterial;
    }

    public void setReceiptQuantity(int receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public void setReceiptRate(int receiptRate) {
        this.receiptRate = receiptRate;
    }

    public void setReceiptUnit(int receiptUnit) {
        this.receiptUnit = receiptUnit;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }
}
