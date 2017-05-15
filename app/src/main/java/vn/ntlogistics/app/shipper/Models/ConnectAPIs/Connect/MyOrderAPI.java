package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SMainActivity;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;

/**
 * Created by (TB0) on 24/06/2016.
 */
public class MyOrderAPI extends BaseConnectAPI {
    int action;
    List<Order> mListOrder;
    BaseFragment fragment;
    ViewModel viewModel;

    public MyOrderAPI(Context context, BaseFragment fragment, String data, boolean refresh, int action) {
        super(context, ConstantURLs.URL_MY_ORDER, data, refresh, Method.POST);
        this.fragment = fragment;
        this.action = action;
        mListOrder = new ArrayList<>();
    }
    public MyOrderAPI(Context context, ViewModel viewModel, String data, boolean refresh) {
        super(context, ConstantURLs.URL_MY_ORDER, data, refresh, Method.POST);
        this.viewModel = viewModel;
        this.action = 0;
        mListOrder = new ArrayList<>();
    }

    public MyOrderAPI(Context context, BaseFragment fragment, String data, boolean refresh) {
        super(context, ConstantURLs.URL_MY_ORDER, data, refresh, Method.POST);
        this.fragment = fragment;
        action = 0;
        mListOrder = new ArrayList<>();
    }
    @Override
    public void onPost(JsonObject result) {
        if (result.get("errorMessage").isJsonNull()) {
            JsonArray array = result.get("data").getAsJsonArray();
            if (array.isJsonArray()) {
                for (int i = 0; i < array.size(); i++) {
                    mListOrder.add(
                            new Gson().fromJson(
                                    array.get(i),
                                    Order.class
                            )
                    );
                }
                if(action == 0) {
                    //TODO: handling data and update data to UI
                    if(fragment != null)
                        fragment.loadSuccess(mListOrder);
                    else
                        viewModel.loadSuccess(mListOrder);
                }
                else {
                    (SMainActivity.getInstance().get()).reloadFromNotification(mListOrder);
                }
            }
        }
        else {
            Message.showToast(
                    context,
                    result.get("errorMessage").getAsString()
            );
        }
    }

    @Override
    public void onError() {
        super.onError();
        try {
            fragment.loadFailed();
        } catch (Exception e) {
        }
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


}
