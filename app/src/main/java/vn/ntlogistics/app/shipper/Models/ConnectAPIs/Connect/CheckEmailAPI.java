package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.ViewModels.MyProfileVMs.ChangeInfoActivityViewModel;

/**
 * Created by minhtan2908 on 9/28/16.
 */

public class CheckEmailAPI extends BaseConnectAPI {
    ChangeInfoActivityViewModel viewModel;
    public CheckEmailAPI(Context context, String data, ChangeInfoActivityViewModel viewModel) {
        super(context, ConstantURLs.URL_CHECK_EMAIL, data, false, Method.POST);
        this.viewModel = viewModel;
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
        if(viewModel != null) {
            if (result.get("errorMessage").isJsonNull()) {
                viewModel.checkSuccess(true);
            } else {
                viewModel.checkSuccess(false);
            }
        }
    }
}
