package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 31/07/2016.
 */
public class JSStatusHistory extends JSBase {
    private String blCode;
    private int limit;
    private int offset;

    public JSStatusHistory(Context context) {
        super(context);
    }

    public JSStatusHistory(Context context, String blCode, int limit, int offset) {
        super(context);
        this.blCode = blCode;
        this.limit = limit;
        this.offset = offset;
    }

    public String getBlCode() {
        return blCode;
    }

    public void setBlCode(String blCode) {
        this.blCode = blCode;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
