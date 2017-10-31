package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by Zanty on 02/06/2017.
 */

public class ImportWhiteBillAPI extends BaseConnectAPI {

    public ImportWhiteBillAPI(Context context, String data) {
        super(context, ConstantURLs.IMPORT_WHITE_BILL, data, true, Method.POST);
        initDialogWithTitle(context.getString(R.string.sending),false);
        Log.e("ImportWhiteBillAPI", data);
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
        try {
            ((BaseActivity) context).onSuccess();
        } catch (Exception e) {
        }
    }
}
