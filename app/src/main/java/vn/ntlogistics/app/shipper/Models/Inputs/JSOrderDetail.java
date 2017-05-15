package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 11/07/2016.
 */
public class JSOrderDetail extends JSBase {
    private String shippingCode;
    private String statusId;
    private String jobType;

    public JSOrderDetail(Context context) {
        super(context);
    }

    public JSOrderDetail(Context context, String shippingCode, String statusId, String jobType) {
        super(context);
        this.shippingCode = shippingCode;
        this.statusId = statusId;
        this.jobType = jobType;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}
