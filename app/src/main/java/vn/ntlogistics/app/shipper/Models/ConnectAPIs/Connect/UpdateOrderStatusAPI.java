package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu.MyOrderFragment;


/**
 * Created by Zanty on 20/07/2016.
 */
public class UpdateOrderStatusAPI extends BaseConnectAPI {
    BaseFragment fragment;
    int statusID;
    String shippingCode;

    public UpdateOrderStatusAPI(Context context, String data, BaseFragment fragment, int statusID, String shippingCode) {
        super(context, ConstantURLs.URL_UPDATE_ORDER_STATUS, data, false, Method.POST);
        this.fragment = fragment;
        this.shippingCode = shippingCode;
        this.statusID = statusID;
    }

    public UpdateOrderStatusAPI(Context context, String data, int statusID, String shippingCode) {
        super(context, ConstantURLs.URL_UPDATE_ORDER_STATUS, data, false, Method.POST);
        this.statusID = statusID;
        this.shippingCode = shippingCode;
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
            if (fragment != null)
                fragment.loadSuccess(null);
            else {
                if (statusID == 10) { //Khách hang yêu cầu huỷ
                    MyOrderFragment.flag = 7;
                }
                else {
                    MyOrderFragment.flag = 4; //Cập nhật đơn hàng
                }
                try {
                    ((BaseActivity) context).updateStatusSuccess(statusID);
                } catch (Exception e) {
                }
            }
        } else {
            Message.showToast(
                    context,
                    result.get("errorMessage").getAsString()
            );
        }
    }
}
