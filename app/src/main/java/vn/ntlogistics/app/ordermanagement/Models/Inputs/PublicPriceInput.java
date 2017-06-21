package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Zanty on 20/05/2017.
 */

public class PublicPriceInput extends BaseInput implements Serializable {
    private double wide; // 0,
    private double high; // 0,
    private int itemQty; // 0,
    private long codAmt; // 0,
    private String senderProvince; // 50",
    private double weight; // 1,
    private double dimensionWeight; // 0,
    private int packageNo; // 1,
    private String receiverProvince; // 50",
    private String service; // 10",
    private long packageLong; // 0,
    private String senderDistrict; // 5001",
    private String receiverDistrict; // 5001"

    public PublicPriceInput(Context context) {
        super(context);
    }

    public double getWide() {
        return wide;
    }

    public void setWide(double wide) {
        this.wide = wide;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public long getCodAmt() {
        return codAmt;
    }

    public void setCodAmt(long codAmt) {
        this.codAmt = codAmt;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDimensionWeight() {
        return dimensionWeight;
    }

    public void setDimensionWeight(double dimensionWeight) {
        this.dimensionWeight = dimensionWeight;
    }

    public int getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(int packageNo) {
        this.packageNo = packageNo;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public long getPackageLong() {
        return packageLong;
    }

    public void setPackageLong(long packageLong) {
        this.packageLong = packageLong;
    }

    public String getSenderDistrict() {
        return senderDistrict;
    }

    public void setSenderDistrict(String senderDistrict) {
        this.senderDistrict = senderDistrict;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }
}
