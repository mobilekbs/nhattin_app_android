package vn.ntlogistics.app.ordermanagement.ViewModels.LoginVMs;

import android.databinding.ObservableInt;
import android.view.View;
import android.widget.EditText;

import vn.ntlogistics.app.ordermanagement.Commons.Animations.IAnimationCallback;
import vn.ntlogistics.app.ordermanagement.Commons.Animations.MyAnimation;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login.User;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.CheckPublicKeyAPI;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.LoginActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.MainActivity;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityLoginBinding;


/**
 * Created by Zanty on 19/05/2017.
 */

public class LoginActivityVM extends ViewModel {
    private LoginActivity               activity;
    private ActivityLoginBinding        binding;


    //0 - Active confirm code | 1 - Create password | 2 - Enter Password
    private ObservableInt               flag;
//    private ObservableField<String>     textButtonActive;

    public LoginActivityVM(LoginActivity activity, ActivityLoginBinding binding, int flag) {
        this.activity = activity;
        this.binding = binding;
        this.flag = new ObservableInt(flag);
//        this.textButtonActive = new ObservableField<>();


        Commons.hideSoftKeyboard(activity, binding.loMainLogin);

        init();
    }

    private void init(){
        if(flag.get() == 1){
            //binding.lnActiveAccountLogin.setVisibility(View.VISIBLE);
            MyAnimation.setVisibilityAnimationSlide(binding.lnCreatePwLogin, true, 500);
            binding.lnActiveAccountLogin.setVisibility(View.GONE);
        }
        else {
            binding.lnCreatePwLogin.setVisibility(View.GONE);
            //binding.lnCreatePwLogin.setVisibility(View.VISIBLE);
            MyAnimation.setVisibilityAnimationSlide(binding.lnActiveAccountLogin, true, 500);
        }

        binding.btnKeyPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActive(v);
            }
        });

        binding.btnSetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSetPw(v);
            }
        });
    }

    public void onClickActive(View view){
        Commons.setEnabledButton(view);
        if(flag.get() == 0) {
            if (validateNullFields(binding.edtKeyPublic,
                    activity.getString(R.string.val_your_key_null))) {
                //onSuccess(CheckPublicKeyAPI.ACTION,true);
                callAPICheckPublicKey(binding.edtKeyPublic.getText().toString());
            }
        }
        else {
            if (validateNullFields(binding.edtKeyPublic,
                    activity.getString(R.string.val_pass_null))) {
                if(binding.edtKeyPublic.getText().toString().equals(
                        SCurrentUser.getCurrentUser(activity).getLocalkey()
                )){
                    MainActivity.startIntentActivity(activity);
                }
                else {
                    Message.makeToastError(activity, activity.getString(R.string.val_pass));
                }
            }
        }
    }

    public void onClickSetPw(View view){
        Commons.setEnabledButton(view);
        if(validateNullFields(binding.edtPass, activity.getString(R.string.val_pass_null)) &&
                validateMinFields(binding.edtPass, activity.getString(R.string.val_pass_4_char)) &&
                validateNullFields(binding.edtPassConfirm, activity.getString(R.string.val_pass_null)) &&
                validateEqualsFields(binding.edtPass, binding.edtPassConfirm)
                ){
            User user = SCurrentUser.getCurrentUser(activity);
            user.setLocalkey(binding.edtPass.getText().toString());
            if(SSqlite.getInstance(activity).inserOrUpdatetUser(user)){
                Message.makeToastSuccess(activity);
                MainActivity.startIntentActivity(activity);
            }
            else {
                Message.makeToastError(activity, activity.getString(R.string.toast_error));
            }
        }
    }

    private boolean validateNullFields(EditText et, String error){
        if(et.getText().toString().length() == 0){
            et.setError(error);
            et.requestFocus();
            return false;
        }
        else {
            et.setError(null);
            return true;
        }
    }

    private boolean validateMinFields(EditText et, String error){
        if(et.getText().toString().length() < 4){
            et.setError(error);
            et.requestFocus();
            return false;
        }
        else {
            et.setError(null);
            return true;
        }
    }
    private boolean validateEqualsFields(EditText et1, EditText et2){
        if(!et1.getText().toString().equals(et2.getText().toString())){
            et2.setError(activity.getString(R.string.val_pass_equals));
            et2.requestFocus();
            return false;
        }
        else {
            et2.setError(null);
            return true;
        }
    }

    @Override
    public void onSuccess(String action, boolean b) {
        if(action.equals(CheckPublicKeyAPI.ACTION)) {
            if (b) {
                flag.set(1);
                MyAnimation.setVisibilityAnimationSlide(
                        binding.lnActiveAccountLogin, false, 200,
                        new IAnimationCallback(){

                            @Override
                            public void callback() {
                                MyAnimation.setVisibilityAnimationSlide(
                                        binding.lnCreatePwLogin, true, 200);
                            }
                        });

                //binding.lnCreatePwLogin.setVisibility(View.GONE);
            } else {

            }
        }
    }

    //TODO: Call APIs ---------------------
    public void callAPICheckPublicKey(String key){
        new CheckPublicKeyAPI(activity, key, this).execute();
    }
    //TODO: Call APIs --------------------- End/
    //TODO: Get / Set ---------------------


    /*public ObservableField<String> getTextButtonActive() {
        return textButtonActive;
    }

    public void setTextButtonActive(ObservableField<String> textButtonActive) {
        this.textButtonActive = textButtonActive;
    }*/

    public ObservableInt getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag .set(flag);
    }
}
