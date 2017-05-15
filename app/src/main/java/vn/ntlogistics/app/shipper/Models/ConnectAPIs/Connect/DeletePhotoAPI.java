package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;

/**
 * Created by Zanty on 01/08/2016.
 */
public class DeletePhotoAPI extends BaseConnectAPI {
    int position;
    public DeletePhotoAPI(Context context, String data, int position) {
        super(context, ConstantURLs.URL_DELETE_PHOTO, data, false, Method.POST);
        this.position = position;
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void doInBG() {

    }

    @Override
    public void onPost(JsonObject result) {
        if (result.get("errorMessage").isJsonNull()) {
            //((SC020201Activity)context).removeItemSuccess(position);
        }
        else
            Message.showToast(context,result.get("errorMessage").getAsString());
    }
}
