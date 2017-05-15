package vn.ntlogistics.app.shipper.Commons.Firebase.CloudMessaging.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.oneshipglobal.app.oneshippro.API.Bean.Singleton.SCurrentUser;
import com.oneshipglobal.app.oneshippro.API.Connect.UpdateFCMTokenAPI;
import com.oneshipglobal.app.oneshippro.Util.Sqlite.SqliteManager;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public MyFirebaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCM", "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        SqliteManager db = new SqliteManager(this);
        db.insertFCMToken(refreshedToken);
        try {
            if(SCurrentUser.getCurrentUser(this).getUserID()!= null)
                new UpdateFCMTokenAPI(null,refreshedToken).execute();
        } catch (Exception e) {
        }
    }
}
