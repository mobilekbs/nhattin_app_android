package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.R;

/**
 * Created by Zanty on 21/07/2016.
 */
public class AcceptOrderAPI extends BaseConnectAPI {
    BaseFragment fragment;
    int idItem, orderType;


    public AcceptOrderAPI(Context context, String data, BaseFragment fragment, int orderType) {
        super(context, ConstantURLs.URL_ACCEPT_ORDER, data, false, Method.POST);
        this.fragment = fragment;
        this.orderType = orderType;
    }
    public AcceptOrderAPI(Context context, String data, BaseFragment fragment, int idItem, int orderType) {
        super(context, ConstantURLs.URL_ACCEPT_ORDER, data, false, Method.POST);
        this.fragment = fragment;
        this.idItem = idItem;
        this.orderType = orderType;
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
                //((BaseActivity)context).receiveSuccess();
                //MyOrderFragment.flag = 1; //Nhận đơn hàng trả về NewOrder
                /*activity.finish();
                activity.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);*/
            }
            try {
                //((NewOrderFragment)fragment).receiveOrderSuccess(idItem,orderType);
            } catch (Exception e) {
            }
            Message.showToast(context,context.getResources().getString(R.string.toast_received));
        }
        else {
            Message.showToast(
                    context,
                    result.get("errorMessage").getAsString()
            );
        }
    }
}
