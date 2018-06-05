package vn.ntlogistics.app.ordermanagement.Commons.Location.GeocodeMaps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URLEncoder;

import vn.ntlogistics.app.ordermanagement.Commons.Location.LocationCommon;
import vn.ntlogistics.app.ordermanagement.Commons.Location.Lonlat;
import vn.ntlogistics.app.ordermanagement.R;


/**
 * Created by minhtan2908 on 9/27/16.
 */

public class GetLocationFromAddress {

    Activity activity;
    String address;

    public GetLocationFromAddress(Activity activity, String address) {
        this.activity = activity;
        this.address = address;
    }

    public void run(final IGeocode geocode) {
        final boolean[] finish = {false};
        final String url = getURL(address);
        Log.d("URL GEOCODE", url);
        @SuppressLint("StaticFieldLeak") GeocodeAPI api = new GeocodeAPI(url){
            @Override
            public void onPost(String s) {
                try {
                    //String responce = new GeocodeAPI(url).execute().get();
                    if (s != null) {
                        JsonObject data = new JsonParser().parse(s).getAsJsonObject();
                        JsonArray array = data.get("results").getAsJsonArray();
                        JsonObject result = array.get(0).getAsJsonObject();
                        JsonObject geometry = result.get("geometry").getAsJsonObject();
                        JsonObject location = geometry.get("location").getAsJsonObject();
                        Lonlat latLng = new Lonlat(
                                location.get("lat").getAsDouble(),
                                location.get("lng").getAsDouble()
                        );
                        geocode.loadSuccess(latLng);
                    }
                } catch (Exception e) {
                }
            }
        };
        api.execute();
    }

    private String getURL(String address) {
        String result = "https://maps.googleapis.com/maps/api/geocode/json?";
        try {
            result += "address=" + URLEncoder.encode(LocationCommon.LongLocation(address), "UTF-8");
        } catch (Exception e) {
        }

        //result += "&components=administrative_area:Ho+Chi+Minh|country:viet+nam&sensor=true";
        result += "&components=administrative_area_level_1:Ho%20Chi%20Minh|country:vn&sensor=true";

        String key = "&key=" + activity.getString(R.string.google_maps_key);
        result += key;
        return result;
    }


}

