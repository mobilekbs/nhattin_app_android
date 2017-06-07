package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SApiKey;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.shipper.Views.Activities.LoginActivity;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;

/**
 * Created by Zanty on 23/06/2016.
 */
public class SignInAPI extends BaseConnectAPI {
    String          token, phone;
    SqliteManager   db;
    ViewModel       viewModel;
    public SignInAPI(Context context, String data, ViewModel viewModel) {
        super(context, ConstantURLs.URL_SIGN_IN, data, false, Method.POST);
        phone = SCurrentUser.getCurrentUser(context).getPhoneNo();
        JsonObject json = SApiKey.getJsonApiKey();
        json.addProperty("phoneNo", phone);
        json.addProperty("userID", SCurrentUser.getCurrentUser(context).getUserID());
        json.addProperty("otp",data);
        this.data = json.toString();
        db = new SqliteManager(context);
        this.viewModel = viewModel;
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onPost(JsonObject result) {
        if (result.get("errorMessage").isJsonNull()) {
            token = result.get("sessionToken").getAsString();
            String userID = result.get("userID").getAsString();
            SCurrentUser.setSessionToken(context, token);
            SCurrentUser.setUserID(context, userID);

            new UpdateFCMTokenAPI(context,db.getFCMToken()).execute();

            try {
                //TODO: add session token into db
                db.insertOrUpdateLogin(userID, token, phone);
            }
            catch (Exception e){
            }
            try {
                //TODO: lưu phone number vào db để sử dụng cho auto complete
                db.insertPhoneReminder(phone);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent i = new Intent(context, MainActivity.class);
            ((Activity)context).startActivity(i);
            //TODO: animation
            ((Activity)context).finish();
            LoginActivity.getActivityLogin().finish();
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
        }
        else {
            if(viewModel != null)
                viewModel.loadSuccess(null);
            Message.showToast(
                    context,
                    context.getResources().getString(R.string.toast_otp)
            );
        }
    }

    @Override
    public void onError() {
        if(viewModel != null)
            viewModel.loadSuccess(null);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void doInBG() {

    }
}
