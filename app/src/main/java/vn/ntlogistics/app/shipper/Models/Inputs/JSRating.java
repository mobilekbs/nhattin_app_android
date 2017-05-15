package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

import java.util.List;

/**
 * Created by Zanty on 29/07/2016.
 */
public class JSRating extends JSBase {
    private List<String> ratedID; //ID người được đánh giá
    private String trustType; //1: trust, 0: untrust
    private String deleteFlag; //0: insert, 1: delete
    private String shippingCode;
    private String blCode;

    public JSRating(Context context) {
        super(context);
    }

    public JSRating(Context context, List<String> ratedID, String trustType, String deleteFlag, String shippingCode, String blCode) {
        super(context);
        this.ratedID = ratedID;
        this.trustType = trustType;
        this.deleteFlag = deleteFlag;
        this.shippingCode = shippingCode;
        this.blCode = blCode;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getBlCode() {
        return blCode;
    }

    public void setBlCode(String blCode) {
        this.blCode = blCode;
    }

    public List<String> getRatedID() {
        return ratedID;
    }

    public void setRatedID(List<String> ratedID) {
        this.ratedID = ratedID;
    }

    public String getTrustType() {
        return trustType;
    }

    public void setTrustType(String trustType) {
        this.trustType = trustType;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
