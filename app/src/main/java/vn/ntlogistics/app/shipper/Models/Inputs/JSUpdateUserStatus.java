package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 18/08/2016.
 */
public class JSUpdateUserStatus extends JSBase {
    private String status;

    public JSUpdateUserStatus(Context context, String status) {
        super(context);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
