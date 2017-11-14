package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ReportError;

/**
 * Created by Zanty on 14/11/2017.
 */

public class ReportErrorAPI extends BaseConnectAPI {

    public ReportErrorAPI(Context context, String url, String inputParam, String outputParam) {
        super(context, ConstantURLs.REPORT_ERROR_WEB, null, true, Method.POST);
        ReportError input = new ReportError(context, inputParam, outputParam, url);
        this.data = input.toJson();
        this.isReport = true;
        Log.e("ReportErrorAPI", this.data);
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

    @Override
    public void onPost(JsonObject result) {

    }

    @Override
    public void onError() {
    }

    @Override
    public void onFailed(int errorCode, String errorMessage) {
    }
}
