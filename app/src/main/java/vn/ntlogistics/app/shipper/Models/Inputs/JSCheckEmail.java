package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by minhtan2908 on 9/28/16.
 */

public class JSCheckEmail extends JSBase {
    private String email;

    public JSCheckEmail(Context context, String email) {
        super(context);
        this.email = email;
    }
}
