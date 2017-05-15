package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SApiKey;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;


/**
 * Created by Zanty on 15/07/2016.
 */
public class CheckPhoneNoAPI extends BaseConnectAPI {
    BaseFragment fragment;
    String phone;

    public CheckPhoneNoAPI(Context context, String data, BaseFragment fragment) {
        super(context, ConstantURLs.URL_CHECK_PHONE_NO, data, false, Method.POST);
        this.fragment = fragment;
        JsonObject json = SApiKey.getJsonApiKey();
        json.addProperty("phoneNo", data);
        this.data = json.toString();
        phone = data;
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
            SCurrentUser.setPhoneNo(context, phone);
            fragment.loadSuccess(null);
        } else
            Message.showToast(context, result.get("errorMessage").getAsString());
    }
}
