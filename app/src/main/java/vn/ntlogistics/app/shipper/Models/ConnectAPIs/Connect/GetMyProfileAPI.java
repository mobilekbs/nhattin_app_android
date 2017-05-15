package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Models.Inputs.JSBase;
import vn.ntlogistics.app.shipper.Models.Outputs.User.MyProfile;
import vn.ntlogistics.app.shipper.Views.Fragments.Main.MyProfileFragment;

/**
 * Created by Zanty on 25/07/2016.
 */
public class GetMyProfileAPI extends BaseConnectAPI {
    MyProfile myProfile;
    BaseFragment fragment;
    public GetMyProfileAPI(Context context, BaseFragment fragment, boolean refresh) {
        super(context, ConstantURLs.URL_MY_PROFILE, null, refresh, Method.POST);
        this.fragment = fragment;
        JSBase json = new JSBase(context);
        this.data = new Gson().toJson(json);
        myProfile = new MyProfile();
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
            myProfile = new Gson().fromJson(result,MyProfile.class);
            SCurrentUser.setEmail(context,myProfile.getEmail());
            SCurrentUser.setUrlPhoto(context, myProfile.getAvatarPhoto());
            SCurrentUser.setFullName(context,myProfile.getFullName());
            if(fragment != null)
                ((MyProfileFragment)fragment).loadMyProfile(myProfile);
        }
        else {
            Message.showToast(
                    context,
                    result.get("errorMessage").getAsString()
            );
        }
    }
}
