package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 02/06/2017.
 */

public class UpdatePinkBillInput extends BaseInput{
    private String bill;
    private String receiverCode;
    private String receiverName;
    private String phoneNo;
    private String payAmt;
    private String codAmt;
    private String cityCode;
    private String districtCode;

    public UpdatePinkBillInput(Context context) {
        super(context);
    }

    public UpdatePinkBillInput(Context context, String bill, String receiverCode, String receiverName, String phoneNo, String payAmt, String codAmt, String cityCode, String districtCode) {
        super(context);
        this.bill = bill;
        this.receiverCode = receiverCode;
        this.receiverName = receiverName;
        this.phoneNo = phoneNo;
        this.payAmt = payAmt;
        this.codAmt = codAmt;
        this.cityCode = cityCode;
        this.districtCode = districtCode;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getCodAmt() {
        return codAmt;
    }

    public void setCodAmt(String codAmt) {
        this.codAmt = codAmt;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
}
