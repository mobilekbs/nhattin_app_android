package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Models.Inputs.JSUpdateFCMToken;

/**
 * Created by Zanty on 18/08/2016.
 */
public class UpdateFCMTokenAPI extends BaseConnectAPI {

    public UpdateFCMTokenAPI(Context context, String data) {
        super(context, ConstantURLs.URL_UPDATE_FCM_TOKEN, data, true, Method.POST);
        this.data = new Gson().toJson(
                new JSUpdateFCMToken(context,data, Commons.getAndroidID(context),4));
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
        Log.d("UpdateFCMToken","Thành công");
    }
}
