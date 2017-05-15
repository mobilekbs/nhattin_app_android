package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 06/07/2016.
 */
public class JSMyOrder extends JSBase {
    private int limit;
    private int offset;
    private int statusId;

    public JSMyOrder(Context context) {
        super(context);
    }

    public JSMyOrder(Context context, int limit, int offset, int statusId) {
        super(context);
        this.limit = limit;
        this.offset = offset;
        this.statusId = statusId;
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

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
