package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vn.ntlogistics.app.config.Config;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CommonInput;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CreateBillResponseInput;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;

/**
 * Created by Zanty on 25/07/2017.
 */

public class CreateBillResponseAPI extends BaseConnectAPI {
    public static final String TAG = "CreateBillResponseAPI";
    private ViewModel viewModel;
    public CreateBillResponseAPI(Context context, CreateBillResponseInput data, ViewModel viewModel) {
        super(context, ConstantURLs.CREATE_BILL_RESPONSE, null, false, Method.POST);
        CommonInput<CreateBillResponseInput> input = new CommonInput<CreateBillResponseInput>(data);
        this.data = new Gson().toJson(input);
        this.viewModel = viewModel;
        Log.e(TAG, this.data);
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
        Message.makeToastSuccess(context, context.getString(R.string.toast_success_update));
        viewModel.onSuccess();
    }

    @Override
    public void onFailed(int errorCode, String errorMessage) {
        super.onFailed(errorCode, errorMessage);
    }

    @Override
    public void onError() {
        super.onError();
    }
}
