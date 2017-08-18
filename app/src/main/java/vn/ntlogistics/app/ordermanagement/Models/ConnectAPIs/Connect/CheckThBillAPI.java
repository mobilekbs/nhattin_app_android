package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CheckThBillInput;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;

/**
 * Created by Zanty on 04/08/2017.
 */

public class CheckThBillAPI extends BaseConnectAPI {
    private ViewModel viewModel;

    public CheckThBillAPI(Context context, String bill, ViewModel viewModel) {
        super(context, ConstantURLs.CHECK_TH_BILL, null, true, Method.POST);
        CheckThBillInput input = new CheckThBillInput(context, bill);
        this.viewModel = viewModel;
        this.data = new Gson().toJson(input);
        try {
            initDialogWithTitle(context.getString(R.string.checking), false);
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

    @Override
    public void onPost(JsonObject result) {
        if(viewModel != null){
            boolean check = result.get("data").getAsBoolean();
            viewModel.onSuccess(check);
        }
    }
}
