package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 31/07/2016.
 */
public class JSStatusOfBill extends JSBase {
    private String blCode;
    private int jobType;

    public JSStatusOfBill(Context context) {
        super(context);
    }

    public JSStatusOfBill(Context context, String blCode, int jobType) {
        super(context);
        this.blCode = blCode;
        this.jobType = jobType;
    }

    public String getBlCode() {
        return blCode;
    }

    public void setBlCode(String blCode) {
        this.blCode = blCode;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }
}
