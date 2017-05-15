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
import vn.ntlogistics.app.shipper.Views.Activities.ConfirmCodeActivity;

/**
 * Created by Zanty on 15/07/2016.
 */
public class SendOTPAPI extends BaseConnectAPI {
    BaseFragment fragment;

    public SendOTPAPI(Context context, String data, int type, BaseFragment fragment) {
        super(
                context,
                ConstantURLs.URL_SEND_OTP,
                data,
                false,
                Method.POST
        );
        SCurrentUser.setPhoneNo(context, data);
        JsonObject json = SApiKey.getJsonApiKey();
        json.addProperty("phoneNo", data);
        json.addProperty("type", type);
        this.data = json.toString();
        this.fragment = fragment;
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
            ConfirmCodeActivity.countSendOtp = 0;
            if (!result.get("userID").isJsonNull()) {
                SCurrentUser.setUserID(context, result.get("userID").getAsString());
            }
            if (fragment != null)
                fragment.loadSuccess(null);
        } else
            Message.showToast(context, result.get("errorMessage").getAsString());
    }
}
