package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 16/08/2016.
 */
public class JSWalletInfo extends JSBase {
    private int limit; // số item muốn load 1 lần
    private int offset; //số trang

    public JSWalletInfo(Context context) {
        super(context);
    }

    public JSWalletInfo(Context context, int limit, int offset) {
        super(context);
        this.limit = limit;
        this.offset = offset;
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
