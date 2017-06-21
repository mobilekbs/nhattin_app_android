package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;

/**
 * Created by Zanty on 20/06/2017.
 */

public class UpdateFCMTokenAPI extends BaseConnectAPI {

    public UpdateFCMTokenAPI(Context context, String data) {
        super(context, ConstantURLs.UPDATE_FCM_TOKEN, data, true, Method.POST);

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

    }
}
