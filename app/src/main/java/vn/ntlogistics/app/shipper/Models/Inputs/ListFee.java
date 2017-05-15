package vn.ntlogistics.app.shipper.Models.Inputs;

/**
 * Created by minhtan2908 on 9/18/16.
 */
public class ListFee {
    private String blCode;
    private double amount; //shipping fee

    public ListFee() {
    }

    public ListFee(String blCode, double amount) {
        this.blCode = blCode;
        this.amount = amount;
    }

    public String getBlCode() {
        return blCode;
    }

    public void setBlCode(String blCode) {
        this.blCode = blCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
