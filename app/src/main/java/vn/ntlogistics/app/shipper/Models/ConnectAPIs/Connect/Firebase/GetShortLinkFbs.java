package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.Firebase;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.Inputs.FirebaseInput.ShortLinkFbsInput;
import vn.ntlogistics.app.shipper.Models.Outputs.FirebaseOutput.ShortLinkFbsOutput;
import vn.ntlogistics.app.shipper.R;


/**
 * Created by minhtan2908 on 3/22/17.
 */

public class GetShortLinkFbs extends BaseConnectAPI {
    public static final String TAG = "GetShortLinkFbs";

    private BaseFragment fragment;
    private String urlFbs = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=";

    public GetShortLinkFbs(Context context, String data, BaseFragment fragment) {
        super(context, null, data, true, Method.POST);
        this.fragment = fragment;
        this.url = urlFbs + context.getString(R.string.google_maps_key);
        ShortLinkFbsInput json = new ShortLinkFbsInput(Constants.DYNAMIC_LINK + data);
        this.data = new Gson().toJson(json);
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
                ShortLinkFbsOutput output = new Gson().fromJson(result, ShortLinkFbsOutput.class);
                fragment.loadSuccess(output);
            }
            else {
                fragment.loadSuccess(null);
            }
        } catch (Exception e) {
            Log.d(TAG, "onPost --------------------------");
            e.printStackTrace();
            fragment.loadSuccess(null);
        }
    }
}
