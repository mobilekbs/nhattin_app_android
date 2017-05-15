package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.StatusBL;

/**
 * Created by Zanty on 31/07/2016.
 */
public class StatusHistoryAPI extends BaseConnectAPI {
    List<StatusBL> mListHistory;
    BaseFragment fragment;
    public StatusHistoryAPI(Context context, String data, BaseFragment fragment) {
        super(context, ConstantURLs.URL_STATUS_HISTORY, data, true, Method.POST);
        mListHistory = new ArrayList<>();
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
        /*if (result.get("errorMessage").isJsonNull()) {
            JsonArray array = result.get("data").getAsJsonArray();
            if (array.isJsonArray()) {
                for (int i = 0; i < array.size(); i++) {
                    mListHistory.add(
                            new Gson().fromJson(
                                    array.get(i),
                                    StatusBL.class
                            )
                    );
                }
                //TODO: handling data and update data to UI
                ((StatusOrderFragment)fragment).loadSuccessStatus(mListHistory);
            }
        }
        else {
            Message.showToast(
                    context,
                    result.get("errorMessage").getAsString()
            );
        }*/
    }
}
