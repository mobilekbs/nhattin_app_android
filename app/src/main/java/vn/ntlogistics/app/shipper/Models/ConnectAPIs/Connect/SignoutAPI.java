package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Location.Services.MyLocationService;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SApiKey;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Activities.LoginActivity;

/**
 * Created by (TB0) on 24/06/2016.
 */
public class SignoutAPI extends BaseConnectAPI {
    SqliteManager db;
    public SignoutAPI(Context context) {
        super(context, ConstantURLs.URL_SIGNOUT, null, false, Method.POST);
        JsonObject json = SApiKey.getJsonApiKey();
        json.addProperty("userID", SCurrentUser.getCurrentUser(context).getUserID());
        json.addProperty("phoneNo",SCurrentUser.getCurrentUser(context).getPhoneNo());
        json.addProperty("appID",4);
        json.addProperty("deviceIdentifier", Commons.getAndroidID(context));
        this.data = json.toString();
        db = new SqliteManager(context);
    }
    @Override
    public void onPost(JsonObject result) {
        if (!result.get("errorMessage").isJsonNull()) {
            Message.showToast(context, result.get("errorMessage").getAsString());
        } else {
            SCurrentUser.delCurrentUser(context); //TODO: xoa het thong tin user
            db.deleteSessionToken();

            try {
                if(Commons.isMyServiceRunning(context,MyLocationService.class))
                    context.stopService(new Intent(context, MyLocationService.class));
            } catch (Exception e) {
            }
            Intent i = new Intent(context, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("invites",1);
            ((Activity)context).startActivity(i);
            ((Activity)context).finish();
            ((Activity)context).overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
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
