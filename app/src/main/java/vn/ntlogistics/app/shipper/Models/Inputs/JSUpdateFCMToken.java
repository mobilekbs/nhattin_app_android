package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 18/08/2016.
 */
public class JSUpdateFCMToken extends JSBase {
    private String fcmToken;
    private String deviceIdentifier;
    private int appID;

    public JSUpdateFCMToken(Context context) {
        super(context);
    }

    public JSUpdateFCMToken(Context context, String fcmToken, String deviceIdentifier, int appID) {
        super(context);
        this.fcmToken = fcmToken;
        this.deviceIdentifier = deviceIdentifier;
        this.appID = appID;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}