package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 02/06/2017.
 */

public class ConfirmBPBillInput extends BaseInput {

    private short packNo;
    private long itemQty;
    private long weight;
    private long dimensionWeight;
    private String doCode;
    private String isactive;

    public ConfirmBPBillInput(Context context) {
        super(context);
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
}
