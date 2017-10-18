package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;

/**
 * Created by Zanty on 02/06/2017.
 */

public class ConfirmBPBillInput extends BaseInput implements Cloneable {

    private short packNo;
    private long itemQty;
    private long weight;
    private long dimensionWeight;
    private String doCode;
    private String bill;
    private String isactive;
    private String cbPartnerId;

    @Override
    public ConfirmBPBillInput clone() throws CloneNotSupportedException {
        return (ConfirmBPBillInput) super.clone();
    }

    public ConfirmBPBillInput(Context context) {
        super(context);
        this.cbPartnerId = SCurrentUser.getCurrentUser(context).getIdStaff()+"";
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public short getPackNo() {
        return packNo;
    }

    public void setPackNo(short packNo) {
        this.packNo = packNo;
    }

    public long getItemQty() {
        return itemQty;
    }

    public void setItemQty(long itemQty) {
        this.itemQty = itemQty;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getDimensionWeight() {
        return dimensionWeight;
    }

    public void setDimensionWeight(long dimensionWeight) {
        this.dimensionWeight = dimensionWeight;
    }

    public String getDoCode() {
        return doCode;
    }

    public void setDoCode(String doCode) {
        this.doCode = doCode;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getCbPartnerId() {
        return cbPartnerId;
    }

    public void setCbPartnerId(String cbPartnerId) {
        this.cbPartnerId = cbPartnerId;
    }
}
