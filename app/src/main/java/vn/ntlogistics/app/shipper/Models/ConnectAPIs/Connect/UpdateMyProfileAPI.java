package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;

/**
 * Created by Zanty on 28/07/2016.
 */
public class UpdateMyProfileAPI extends BaseConnectAPI {
    ViewModel viewModel;
    BaseFragment fragment;
    public UpdateMyProfileAPI(Context context, String data, BaseFragment fragment) {
        super(context, ConstantURLs.URL_UPDATE_MY_PROFILE, data, false, Method.POST);
        this.fragment = fragment;
    }
    public UpdateMyProfileAPI(Context context, String data, ViewModel viewModel) {
        super(context, ConstantURLs.URL_UPDATE_MY_PROFILE, data, false, Method.POST);
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
        try {
            if (result.get("errorMessage").isJsonNull()) {
                if (fragment != null)
                    fragment.loadSuccess(null);
                else
                    viewModel.loadSuccess(null);
            }
            else
                Message.showToast(
                        context,
                        result.get("errorMessage").getAsString()
                );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
