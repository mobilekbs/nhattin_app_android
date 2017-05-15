package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

import java.util.List;

/**
 * Created by Zanty on 01/08/2016.
 */
public class JSDeletePhoto extends JSBase {
    private List<String> url;

    public JSDeletePhoto(Context context) {
        super(context);
    }

    public JSDeletePhoto(Context context, List<String> url) {
        super(context);
        this.url = url;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
