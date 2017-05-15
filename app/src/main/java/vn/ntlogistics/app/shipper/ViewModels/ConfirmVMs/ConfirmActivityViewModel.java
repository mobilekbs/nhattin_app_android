package vn.ntlogistics.app.shipper.ViewModels.ConfirmVMs;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SApiKey;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.RegisterAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.SendOTPAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.SignInAPI;
import vn.ntlogistics.app.shipper.Models.Inputs.JSRegister;
import vn.ntlogistics.app.shipper.Models.Outputs.User.User;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.shipper.databinding.ActivityConfirmCodeBinding;

import static vn.ntlogistics.app.shipper.Views.Activities.ConfirmCodeActivity.countSendOtp;


/**
 * Created by minhtan2908 on 2/6/17.
 */

public class ConfirmActivityViewModel extends ViewModel {
    //private BaseFragment                fragment;
    private Context                     context;

    //TODO: Params
    private int                         status = 0; //TODO: 0: SignIn - 1: Register
    private JSRegister reg;
    private ActivityConfirmCodeBinding binding;

    CountDownTimer                      mCountDownTimer;
    int                                 i = 0;

    private ObservableField<String>     confirmCode = new ObservableField<>();

    public ConfirmActivityViewModel(Context context, int status, JSRegister reg, ActivityConfirmCodeBinding b) {
        this.context = context;
        this.status = status;
        this.reg = reg;
        this.binding = b;
        this.confirmCode.set("");
        mCountDownTimer = new CountDownTimer(20000, 200) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;
                binding.pbConfirmCode.setProgress(i);
            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                binding.pbConfirmCode.setProgress(i);
                binding.tvConfirmCode.setVisibility(View.VISIBLE);
                binding.pbConfirmCode.setVisibility(View.GONE);
            }
        };
        mCountDownTimer.start();
    }

    private void handleConfirm(){
        if (status == 1){ //Sign In
            if (confirmCode.get().length() < 1)
                Message.showToast(context, context.getResources().getString(R.string.toast_otp_valid));
            else if(confirmCode.get().length() < 4){
                Message.showToast(context, context.getResources().getString(R.string.toast_otp_valid_4));
            }
            else
                callSignInAPI();
        }
        else if(status == 0){ //Register
            registerNewAccount();
        }
    }

    public void onClickConfirm(View v){
        Commons.setEnabledButton(v);
        handleConfirm();
    }

    public boolean onEditorSend(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            handleConfirm();
        }
        return false;
    }

    public void onClickResend(View view){
        Commons.setEnabledButton(view);
        if(countSendOtp < 4) {
            callSendOTPAPI();
            i = 0;
            binding.tvConfirmCode.setVisibility(View.GONE);
            binding.pbConfirmCode.setVisibility(View.VISIBLE);
            mCountDownTimer.start();
            countSendOtp++;
            if(countSendOtp == 3){
                binding.loResend.setVisibility(View.GONE);
            }
        }
    }

    public void onClickExit(View v){
        ((Activity)context).finish();
        ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!confirmCode.get().equalsIgnoreCase(s.toString()))
            confirmCode.set(s.toString());
    }

    private void registerNewAccount() {
        //TODO: Create json form object class JSRegister, using toJson form Gson() parse to String
        User user = SCurrentUser.getCurrentUser(context);
        reg.setApiKey(SApiKey.getOurInstance().getApiKey());
        reg.setApiSecretKey(SApiKey.getOurInstance().getApiSecretKey());
        reg.setFullName(user.getFullName());
        reg.setPhoneNo(user.getPhoneNo());
        reg.setRefCode(user.getRefCode());
        try {
            if (SCurrentUser.getCurrentUser(context).getEmail() != null)
                reg.setEmail(SCurrentUser.getCurrentUser(context).getEmail());
        }
        catch (Exception e){
        }
        if(reg.getVehiclePhoto1()!=null)
            reg.setVehiclePhoto1(Commons.convertPathToBase64WithResize(reg.getVehiclePhoto1()));
        if(reg.getVehiclePhoto2()!=null)
            reg.setVehiclePhoto2(Commons.convertPathToBase64WithResize(reg.getVehiclePhoto2()));
        if(reg.getCmndPhoto1()!=null)
            reg.setCmndPhoto1(Commons.convertPathToBase64WithResize(reg.getCmndPhoto1()));
        if(reg.getCmndPhoto2()!=null)
            reg.setCmndPhoto2(Commons.convertPathToBase64WithResize(reg.getCmndPhoto2()));
        if(reg.getLicensePhoto1()!=null)
            reg.setLicensePhoto1(Commons.convertPathToBase64WithResize(reg.getLicensePhoto1()));
        if(reg.getLicensePhoto2()!=null)
            reg.setLicensePhoto2(Commons.convertPathToBase64WithResize(reg.getLicensePhoto2()));
        if(reg.getFacePhoto1()!=null)
            reg.setFacePhoto1(Commons.convertPathToBase64WithResize(reg.getFacePhoto1()));
        if(reg.getFacePhoto2()!=null)
            reg.setFacePhoto2(Commons.convertPathToBase64WithResize(reg.getFacePhoto2()));
        /*reg.setVehicleType();
        reg.setPlateNo();
        reg.setVehiclePhoto1();
        reg.setVehiclePhoto2();
        reg.setFacePhoto1();
        reg.setFacePhoto2();
        reg.setCmndPhoto1();
        reg.setCmndPhoto2();
        reg.setLicensePhoto1();
        reg.setLicensePhoto2();*/
        try {
            reg.setAvatarPhoto(SCurrentUser.getCurrentUser(context).getUrlPhoto());
        } catch (Exception e) {
            e.printStackTrace();
        }
        reg.setOtp(confirmCode.get());
        callRegisterAPI();
        /*String json = new Gson().toJson(reg);
        new RegisterAPI(this,json).execute();*/
    }

    //TODO: Call API ----------------
    private void callSignInAPI(){
        /*SignInAPI api = new SignInAPI(context, confirmCode.get());
        // Chạy hàm kết nối api
        connectAPI(api);*/
        new SignInAPI(context, confirmCode.get()).execute();
    }

    private void callRegisterAPI(){
        /*RegisterAPI api = new RegisterAPI(context, reg);
        // Chạy hàm kết nối api
        connectAPI(api);*/
        String json = new Gson().toJson(reg);
        new RegisterAPI(context, json).execute();
    }

    private void callSendOTPAPI(){
        /*SendOTPAPI api = new SendOTPAPI(
                context,
                SCurrentUser.getCurrentUser(context).getPhoneNo(),
                status,
                this,
                false);
        // Chạy hàm kết nối api
        connectAPI(api);*/
        new SendOTPAPI(context,
                SCurrentUser.getCurrentUser(context).getPhoneNo(),
                status,
                null).execute();
    }

    //TODO: End call API -----------/

    @Override
    public void loadSuccess(List<?> mList) {

    }
}
