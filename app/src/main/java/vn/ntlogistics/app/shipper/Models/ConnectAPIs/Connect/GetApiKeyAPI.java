package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.Singleton.SApiKey;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Models.Inputs.JSGetAPIKey;
import vn.ntlogistics.app.shipper.Models.Outputs.Login.ApiKey;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;
import vn.ntlogistics.app.shipper.Views.Activities.SplashScreenActivity;

/**
 * Created by Zanty on 23/06/2016.
 */
public class GetApiKeyAPI extends BaseConnectAPI {
    SqliteManager db;
    public GetApiKeyAPI(Context context) {
        super(context,
                ConstantURLs.URL_GET_API_KEY,
                new Gson().toJson(
                        new JSGetAPIKey(
                                Commons.getAndroidID(context),
                                SCurrentUser.getCurrentUser(context).getSessionToken(),
                                SCurrentUser.getCurrentUser(context).getUserID(),
                                Constants.OS,
                                Constants.APP_ID,
                                Commons.getVersionCode(context),
                                Commons.getVersionName(context)
                        )
                ),
                true,
                Method.POST);
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onPost(JsonObject result) {
        ApiKey api = new Gson().fromJson(
                result,
                ApiKey.class
        );
        SApiKey.setOurInstance(api);
        db = new SqliteManager(context);
        try {
            db.insertApiKey(api);
        } catch (Exception e) {
            Log.d("Get API Key","Cannot insert API into Sqlite");
        }
        try {
            ((SplashScreenActivity) context).getViewModel().loadMainScreen(false);
        }
        catch (Exception e){
            if(SCurrentUser.getSessionToken(context)!=null){
                new UpdateFCMTokenAPI(context,db.getFCMToken()).execute();
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
                ((Activity)context).finish();
            }
            else {
                Intent i = new Intent(context, SplashScreenActivity.class);
                context.startActivity(i);
                ((Activity)context).finish();
            }
        }
    }

    @Override
    public void updateApp() {
        try {
            ((SplashScreenActivity) context).getViewModel().loadMainScreen(true);
        }
        catch (Exception e){
            if(SCurrentUser.getSessionToken(context)!=null){
                new UpdateFCMTokenAPI(context,db.getFCMToken()).execute();
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
                ((Activity)context).finish();
            }
            else {
                Intent i = new Intent(context, SplashScreenActivity.class);
                context.startActivity(i);
                ((Activity)context).finish();
            }
        }
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void doInBG() {
    }
}
