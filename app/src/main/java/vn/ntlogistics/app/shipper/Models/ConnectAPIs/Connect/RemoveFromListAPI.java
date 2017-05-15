package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu.MyOrderFragment;

/**
 * Created by Zanty on 22/07/2016.
 */
public class RemoveFromListAPI extends BaseConnectAPI {
    int action;
    BaseFragment fragment;
    public RemoveFromListAPI(Context context, String data, BaseFragment fragment) {
        super(context, ConstantURLs.URL_REMOVE_FROM_LIST, data, false, Method.POST);
        this.fragment = fragment;
        this.action = 0;
    }
    public RemoveFromListAPI(Context context, String data, BaseFragment fragment, int action) {
        super(context, ConstantURLs.URL_REMOVE_FROM_LIST, data, false, Method.POST);
        this.fragment = fragment;
        this.action = action;
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
        if (result.get("errorMessage").isJsonNull()) {
            if(fragment != null)
                fragment.loadSuccess(null);
            else {
                if(action == 1)
                    MyOrderFragment.flag = 6; //Xóa đơn hàng đã hoàn thành
                else
                    MyOrderFragment.flag = 5; //Xóa đơn hàng đã hoàn thành trong DA HUY
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                Message.showToast(context,context.getResources().getString(R.string.toast_deny));
            }
        }
        else {
            Message.showToast(
                    context,
                    result.get("errorMessage").getAsString()
            );
        }
    }
}
