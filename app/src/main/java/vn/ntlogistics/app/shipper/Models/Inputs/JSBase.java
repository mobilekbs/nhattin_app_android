package vn.ntlogistics.app.shipper.Models.Inputs;

import android.app.Activity;
import android.content.Context;

import vn.ntlogistics.app.shipper.Commons.Singleton.SApiKey;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.GetApiKeyAPI;
import vn.ntlogistics.app.shipper.Models.Outputs.Login.ApiKey;


/**
 * Created by Zanty on 06/07/2016.
 */
public class JSBase{
    private String userID;
    private String sessionToken;
    private String apiKey;
    private String apiSecretKey;

    /*public JSBase() {
        apiKey = SApiKey.getOurInstance().getApiKey();
        apiSecretKey = SApiKey.getOurInstance().getApiSecretKey();
        userID = SCurrentUser.getCurrentUser().getUserID();
        sessionToken = SCurrentUser.getCurrentUser().getSessionToken();
        if (OneShipApplication.getCurrentActivity() != null) {
            SqliteManager db = new SqliteManager(OneShipApplication.getCurrentActivity());

            //TODO: get userID, sessionToken into SCurrentUser
            try {
                if(sessionToken == null || userID == null) {
                    db.readSessionToken();
                    userID = SCurrentUser.getCurrentUser().getUserID();
                    sessionToken = SCurrentUser.getCurrentUser().getSessionToken();
                }
            } catch (Exception e) {
            }
            try {
                if (apiKey == null || apiSecretKey == null) {
                    apiKey = db.getApiKey().apiKey;
                    apiSecretKey = db.getApiKey().apiSecretKey;
                    if (apiKey == null) {
                        new GetApiKeyAPI(OneShipApplication.getCurrentActivity()).execute();
                    } else {
                        SApiKey.setOurInstance(new ApiKey(apiKey, apiSecretKey));
                    }
                }
            } catch (Exception e) {
            }
        }
        //Restart app
        if(apiKey == null && apiSecretKey == null){
            Intent mStartActivity = new Intent(OneShipApplication.getCurrentActivity(), SplashScreenActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(OneShipApplication.getCurrentActivity(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)OneShipApplication.getCurrentActivity().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        }
    }*/

    public JSBase(Context context) {
        apiKey = SApiKey.getOurInstance().getApiKey();
        apiSecretKey = SApiKey.getOurInstance().getApiSecretKey();
        try {
            userID = SCurrentUser.getCurrentUser(context).getUserID();
        }
        catch (Exception e){
        }
        sessionToken = SCurrentUser.getCurrentUser(context).getSessionToken();
        SqliteManager db = new SqliteManager(context);

        //TODO: get userID, sessionToken into SCurrentUser
        try {
            if(sessionToken == null || userID == null) {
                db.readSessionToken();
                userID = SCurrentUser.getCurrentUser(context).getUserID();
                sessionToken = SCurrentUser.getCurrentUser(context).getSessionToken();
            }
        } catch (Exception e) {
        }
        try {
            if (apiKey == null || apiSecretKey == null) {
                ApiKey api = db.getApiKey();
                SApiKey.setOurInstance(api);
                apiKey = api.apiKey;
                apiSecretKey = api.apiSecretKey;
                if (apiKey == null) {
                    new GetApiKeyAPI((Activity) context).execute();
                }
            }
        } catch (Exception e) {

        }
        //Restart app
        /*if(apiKey == null && apiSecretKey == null){
            Intent mStartActivity = new Intent(context, SplashScreenActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(OneShipApplication.getCurrentActivity(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)OneShipApplication.getCurrentActivity().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        }*/
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
