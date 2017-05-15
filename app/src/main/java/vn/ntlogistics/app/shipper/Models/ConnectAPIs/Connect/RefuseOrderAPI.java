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
 * Created by Zanty on 21/07/2016.
 */
public class RefuseOrderAPI extends BaseConnectAPI {
    BaseFragment fragment;
    public RefuseOrderAPI(Context context, String data, BaseFragment fragment) {
        super(context, ConstantURLs.URL_REFUSE_ORDER, data, false, Method.POST);
        this.fragment = fragment;
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
                if(MyOrderFragment.flag != 1)
                    MyOrderFragment.flag = 3; // Từ chối đơn hàng
                else
                    MyOrderFragment.flag = -1;
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
