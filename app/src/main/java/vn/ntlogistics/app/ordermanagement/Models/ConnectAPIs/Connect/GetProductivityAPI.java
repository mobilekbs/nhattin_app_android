package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.GetProductivityInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Shipper.Productivity;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;

/**
 * Created by Zanty on 16/08/2017.
 */

public class GetProductivityAPI extends BaseConnectAPI {
    private ViewModel viewModel;
    public GetProductivityAPI(Context context, GetProductivityInput data, ViewModel viewModel) {
        super(context, ConstantURLs.GET_PRODUCTIVITY, null, true, Method.POST);
        //CommonInput<GetProductivityInput> input = new CommonInput<GetProductivityInput>(data);
        data.setCbPartnerID(SCurrentUser.getCurrentUser(context).getIdStaff()+"");
        this.data = data.toJson();
        this.viewModel = viewModel;
        Log.e("Productivity", this.data);
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
        JsonArray array = result.get("data").getAsJsonArray();

        if (array != null) {
            ArrayList<Productivity> lstResult = new ArrayList<>();
            for (JsonElement item : array){
                try {
                    lstResult.add(new Gson().fromJson(item, Productivity.class));
                } catch (Exception e) {
                }
            }
            if (viewModel != null)
                viewModel.onSuccess(lstResult);
        }
        else
            onError();
    }

    @Override
    public void onError() {
        if (viewModel != null)
            viewModel.onFailed();
    }

    @Override
    public void onFailed(int errorCode, String errorMessage) {
        onError();
    }
}
