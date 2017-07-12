package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.SharedPreference.MySharedPreference;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login.User;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CommonInput;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.UpdateFCMTokenInput;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;

/**
 * Created by Zanty on 19/05/2017.
 */

public class CheckPublicKeyAPI extends BaseConnectAPI {
    public static final String ACTION = "CheckPublicKeyAPI";

    private String key;
    private ViewModel viewModel;
    public CheckPublicKeyAPI(Context context, String data, ViewModel viewModel) {
        super(context, ConstantURLs.CHECK_USER_KEY, data, false, Method.POST);
        this.key = data;
        this.viewModel = viewModel;
        JsonObject json = new JsonObject();
        json.addProperty("AndroidKey", data);
        this.data = new Gson().toJson(json);
        initDialogWithTitle(context.getString(R.string.checking), false);
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
        try {
            int valueStaff = result.get("data").getAsInt();

            if(valueStaff == -1){ //Khóa không tồn tại
                Message.makeToastError(context, context.getString(R.string.error_key_not_exist));
            }
            else if(valueStaff == -2){ //Key đã được kích hoạt.
                Message.makeToastError(context, context.getString(R.string.error_key_activated));
            }
            else if(valueStaff == -3){ //Lỗi lưu kích hoạt Key
                Message.makeToastError(context, context.getString(R.string.error_save_key));
            }
            else if(valueStaff > 9999){
                User user = new User();
                user.setValue_staff(valueStaff+"");
                user.setPublickey(key);
                if(SSqlite.getInstance(context).inserOrUpdatetUser(user)){
                    Message.makeToastSuccess(context);
                    if(viewModel != null){
                        viewModel.loadSuccess(ACTION, true);
                    }
                    callAPIUpdateFCMToken();
                }
                else {
                    Message.makeToastError(context, context.getString(R.string.toast_error));
                    if(viewModel != null){
                        viewModel.loadSuccess(ACTION, false);
                    }
                }
            }
        } catch (Exception e) {
            Message.makeToastError(context, context.getString(R.string.toast_error));
            if(viewModel != null){
                viewModel.loadSuccess(ACTION, false);
            }
        }
    }

    private void callAPIUpdateFCMToken(){
        CommonInput<UpdateFCMTokenInput> input = new CommonInput<>();
        input.setData(new UpdateFCMTokenInput(context, MySharedPreference.getFCMToken(context)));
        String data = new Gson().toJson(input);
        new UpdateFCMTokenAPI(context, data).execute();
    }
}
