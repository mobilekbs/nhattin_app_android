package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;

/**
 * Created by Zanty on 19/08/2017.
 */

public class CheckVersionInput extends BaseInput {
    private String osID; //Hệ điều hành: Android : a IOS : i
    private int versionCode; //Phiên bản ứng dụng
    private int appID; //ID mỗi app 1: Nhất tín app cho shipper 2: Nhất tín app cho khách hàng

    public CheckVersionInput(Context context) {
        super(context);
        versionCode = Commons.getVersionCode(context);
        osID = "a";
        appID = 1;
    }

    public String getOsID() {
        return osID;
    }

    public void setOsID(String osID) {
        this.osID = osID;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }
}
