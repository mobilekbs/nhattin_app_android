package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.GoogleAPIs;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URLEncoder;
import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.Interfaces.ICallback;
import vn.ntlogistics.app.ordermanagement.Commons.Location.GetCurrentLocation;
import vn.ntlogistics.app.ordermanagement.Commons.Location.LocationCommon;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurentLocation;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by Zanty on 08/08/2017.
 */

public class DistanceMatrixAPI extends BaseConnectAPI {
    private ICallback callback;

    public DistanceMatrixAPI(Context context, ArrayList<String> lstAddress, ICallback callback) {
        super(context, null, "", true, Method.GET);
        this.url = getLink(lstAddress).toString();
        this.callback = callback;
    }

    private StringBuffer getLink(ArrayList<String> lstAddress) {
        StringBuffer url = new StringBuffer();
        if (lstAddress == null || lstAddress.size() == 0)
            return url;

        url.append(ConstantURLs.DISTANCE_MATRIX);

        url.append(ConstantURLs.ORIGINS);

        //thêm điểm đầu
        GetCurrentLocation mLonlat = SCurentLocation.getInstance(context);
        if (mLonlat != null)
            url.append(mLonlat.getLatitude() + "," + mLonlat.getLongitude());

        url.append(ConstantURLs.DESTINATIONS);
        //thêm những điểm cần tính
        for (int i = 0; i < lstAddress.size(); i++) {
            String address = "";
            try {
                address = URLEncoder.encode(LocationCommon.LongLocation(lstAddress.get(i)), "UTF-8");
            } catch (Exception e) {
            }
            url.append(address);

            if (i < lstAddress.size() - 1) {
                url.append("|");
            }
        }

        //thêm keys
        url.append(ConstantURLs.KEYS);
        url.append(context.getString(R.string.google_maps_key));

        Log.e("DISTANCE", url.toString());
        return url;
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

    }

    @Override
    public void onPostMain(String result) {
        try {
            if (result != null) {
                JsonObject rootObject = new JsonParser().parse(result).getAsJsonObject();
                if (rootObject != null) {
                    JsonArray rowsObject = rootObject.get("rows").getAsJsonArray();
                    if (rowsObject != null) {
                        JsonArray array = rowsObject.get(0).getAsJsonObject().get("elements").getAsJsonArray();
                        if (array != null && array.size() > 0) {
                            ArrayList<Long> lstDistance = new ArrayList<>();
                            for (JsonElement item : array) {
                                long itemResult = 0;
                                try {
                                    itemResult = item.getAsJsonObject().get("distance").getAsJsonObject()
                                            .get("value").getAsLong();
                                } catch (Exception e) {
                                }
                                lstDistance.add(itemResult);
                            }
                            if (callback != null)
                                callback.onSuccess(lstDistance);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onError() {
    }

    @Override
    public void onFailed(int errorCode, String errorMessage) {
    }
}
