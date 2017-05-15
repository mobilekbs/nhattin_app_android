package vn.ntlogistics.app.shipper.Models.Inputs;

/**
 * Created by Zanty on 22/07/2016.
 */
public class JSGetAPIKey {
    private String deviceIdentifier;
    private String sessionToken;
    private String userID;
    /*
      * Hệ điều hành:
      * Android : a
      * IOS     : i
      * Winphone: w
    */
    private String os;
    private int appID;
    private int versionCode;
    private String versionName;

    public JSGetAPIKey() {
    }

    public JSGetAPIKey(String deviceIdentifier, String sessionToken, String userID, String os, int appID, int versionCode, String versionName) {
        this.deviceIdentifier = deviceIdentifier;
        this.sessionToken = sessionToken;
        this.userID = userID;
        this.os = os;
        this.appID = appID;
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
