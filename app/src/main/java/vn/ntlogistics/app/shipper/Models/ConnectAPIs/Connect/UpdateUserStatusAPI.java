package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Models.Inputs.JSUpdateUserStatus;
import vn.ntlogistics.app.shipper.ViewModels.MainVMs.MainActivityViewModel;


/**
 * Created by Zanty on 18/08/2016.
 */
public class UpdateUserStatusAPI extends BaseConnectAPI {
    int status = 0; //đang làm việc
    int destroy;
    MainActivityViewModel viewModel;

    public UpdateUserStatusAPI(Context context, String data, int destroy, MainActivityViewModel viewModel) {
        super(context, ConstantURLs.URL_UPDATE_USER_STATUS, data, false, Method.POST);
        this.viewModel = viewModel;
        try {
            status = Integer.parseInt(data);
        } catch (Exception e) {
            Log.d("Update User Status", "Status not parseInt");
            e.printStackTrace();
        }
        this.destroy = destroy;
        if(destroy == 1)
            this.refresh = true;
        JSUpdateUserStatus json = new JSUpdateUserStatus(context,data);
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
        if (result.get("errorMessage").isJsonNull()) {
            if (status > 0) {
                /**
                 * Khi kill app thì destroy == 1, sao khi update lại user status thì mới kill app
                 */
                if(destroy==0) {
                    boolean b = true;
                    if (status == Constants.STATUS_RESTED_USER) //30 làm việc | 31 nghỉ ngơi
                        b = false;
                    viewModel.updateUserStatusSuccess(b);
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
