package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;

/**
 * Created by Zanty on 29/07/2016.
 */
public class RatingAPI extends BaseConnectAPI {
    BaseFragment fragment;
    int sender, status, startPlace;

    public RatingAPI(Context context, String data, BaseFragment fragment, int status, int sender, int startPlace) {
        super(context, ConstantURLs.URL_RATING, data, true, Method.POST);
        this.fragment = fragment;
        this.status = status;
        this.sender = sender;
        this.startPlace = startPlace;
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
            String ratedID = null;
            JsonArray array = result.get("ratedID").getAsJsonArray();
            if (array.isJsonArray()) {
                ratedID = array.get(0).getAsString();
            }
            if (fragment != null)
                ((OrderInfoFragment) fragment).handleRatingSuccess(ratedID, status, sender);
            else
                ((OrderDetailHubActivity)context).handleRatingSuccess(sender,status,startPlace);
        } else {
            //Message.showToast(activity, result.get("errorMessage").getAsString());
        }*/
    }
}
