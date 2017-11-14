package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

import com.google.gson.Gson;

import java.io.Serializable;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;

/**
 * Created by Zanty on 14/11/2017.
 */

public class ReportError implements Serializable {
    private long userId;
    private String inputParam;
    private String outputParam;
    private String apiAddress;
    private String osDevice;
    private String versionApp;

    public ReportError() {
    }

    public ReportError(Context context, String inputParam, String outputParam, String apiAddress) {
        this.userId = SCurrentUser.getCurrentUser(context).getIdStaff();
        this.inputParam = inputParam;
        this.outputParam = outputParam;
        this.apiAddress = apiAddress;
        this.osDevice = "a";
        this.versionApp = Commons.getVersionCode(context) + "";
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getInputParam() {
        return inputParam;
    }

    public void setInputParam(String inputParam) {
        this.inputParam = inputParam;
    }

    public String getOutputParam() {
        return outputParam;
    }

    public void setOutputParam(String outputParam) {
        this.outputParam = outputParam;
    }

    public String getApiAddress() {
        return apiAddress;
    }

    public void setApiAddress(String apiAddress) {
        this.apiAddress = apiAddress;
    }

    public String getOsDevice() {
        return osDevice;
    }

    public void setOsDevice(String osDevice) {
        this.osDevice = osDevice;
    }

    public String getVersionApp() {
        return versionApp;
    }

    public void setVersionApp(String versionApp) {
        this.versionApp = versionApp;
    }
}
