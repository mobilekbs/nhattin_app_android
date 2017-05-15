package vn.ntlogistics.app.shipper.Commons.Firebase.CloudMessaging.Services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vn.ntlogistics.app.shipper.Commons.Firebase.CloudMessaging.Handle.HandleCloudMessaging;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    HandleCloudMessaging handle;
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        handle = new HandleCloudMessaging(remoteMessage, getApplicationContext());
        handle.run();
    }
}
