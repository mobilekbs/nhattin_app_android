package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Activities.LoginActivity;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;

/**
 * Created by Zanty on 25/06/2016.
 */
public class RegisterAPI extends BaseConnectAPI {
    SqliteManager db;
    String token=null;
    public RegisterAPI(Context context, String data) {
        super(context, ConstantURLs.URL_REGISTER, data, false, Method.POST);
    }
    @Override
    public void onPost(JsonObject result) {
        if (result.get("errorMessage").isJsonNull()) {
            token = result.get("sessionToken").getAsString();
            String userID = result.get("userID").getAsString();
            String phoneNo = result.get("phoneNo").getAsString();
            SCurrentUser.setSessionToken(context, token);
            SCurrentUser.setUserID(context, userID);
            try {
                //TODO: add session token into db
                db.insertOrUpdateLogin(userID, token,phoneNo);
                db.readSessionToken();
                db.insertPhoneReminder(phoneNo);
            }
            catch (Exception e){
            }
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("Register",1);
            ((Activity)context).startActivity(i);
            //TODO: animation
            ((Activity)context).finish();
            LoginActivity.getActivityLogin().finish();
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        }
        else {
            Message.showToast(
                    context,
                    context.getResources().getString(R.string.toast_otp)
            );
        }
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

}
