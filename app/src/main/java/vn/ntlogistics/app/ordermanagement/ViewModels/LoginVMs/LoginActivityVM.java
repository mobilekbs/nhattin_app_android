package vn.ntlogistics.app.ordermanagement.ViewModels.LoginVMs;

import android.databinding.ObservableInt;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.ntlogistics.app.ordermanagement.Commons.Animations.MyAnimation;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.MaterialTapTargetPrompt.MaterialTapTargetPrompt;
import vn.ntlogistics.app.ordermanagement.Commons.MaterialTapTargetPrompt.extras.backgrounds.FullscreenPromptBackground;
import vn.ntlogistics.app.ordermanagement.Commons.MaterialTapTargetPrompt.extras.focals.RectanglePromptFocal;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.SharedPreference.MySharedPreference;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
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

public class LoginActivityVM extends ViewModel implements TextView.OnEditorActionListener {
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
        if(flag.get() == 1){ // create pwd
            showFullscreenRectPrompt(binding.edtPass,
                    MySharedPreference.CREATE_PWD_PROMPT,
                    activity.getString(R.string.prompt_create_pwd_title),
                    activity.getString(R.string.prompt_create_pwd_content));
            //binding.lnActiveAccountLogin.setVisibility(View.VISIBLE);
            MyAnimation.setVisibilityAnimationSlide(binding.lnCreatePwLogin, true, 500);
            binding.lnActiveAccountLogin.setVisibility(View.GONE);
        }
        else { //activate or login
            if (SCurrentUser.getCurrentUser(activity).getValue_staff() == null){ //login
                showFullscreenRectPrompt(binding.edtKeyPublic,
                        MySharedPreference.ACTIVATE_PROMPT,
                        activity.getString(R.string.prompt_activate_title),
                        activity.getString(R.string.prompt_activate_content));
            }
            else {
                showFullscreenRectPrompt(binding.edtKeyPublic,
                        MySharedPreference.LOGIN_PROMPT,
                        activity.getString(R.string.prompt_login_title),
                        activity.getString(R.string.prompt_login_content));
            }

            binding.lnCreatePwLogin.setVisibility(View.GONE);
            //binding.lnCreatePwLogin.setVisibility(View.VISIBLE);
            MyAnimation.setVisibilityAnimationSlide(binding.lnActiveAccountLogin, true, 500);
        }

        binding.edtKeyPublic.setOnEditorActionListener(this);
        binding.edtPassConfirm.setOnEditorActionListener(this);

        binding.btnKeyPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: ");
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

    public void showFullscreenRectPrompt(View view, String key, String title, String message)
    {
        if (MySharedPreference.getLoginPrompt(activity, key) == 0) {
            new MaterialTapTargetPrompt.Builder(activity)
                    .setTarget(view)
                    .setPrimaryText(title)
                    .setSecondaryText(message)
                    .setPromptBackground(new FullscreenPromptBackground())
                    .setPromptFocal(new RectanglePromptFocal())
                    .show();
            MySharedPreference.setLoginPrompt(activity, key, 1);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_DONE){
            switch (v.getId()){
                case R.id.edtKeyPublic:
                    onClickActive(v);
                    break;
                case R.id.edtPassConfirm:
                    onClickSetPw(v);
                    break;
            }
        }
        return false;
    }

    public void onClickActive(View view){
        Commons.setEnabledButton(view);
        Log.e("TAG", "onClickActive: "+flag.get());
        if(flag.get() == 0||flag.get() == 2) {
            if (validateNullFields(binding.edtKeyPublic,
                    activity.getString(R.string.val_your_key_null))) {
                //onSuccess(CheckPublicKeyAPI.ACTION,true);
                Log.e("TAG", "onClickActive: "+binding.edtKeyPublic.getText().toString() );
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
            if(SSQLite.getInstance(activity).inserOrUpdatetUser(user)){
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
               // flag.set(1);


                MainActivity.startIntentActivity(activity);
                /*MyAnimation.setVisibilityAnimationSlide(
                        binding.lnActiveAccountLogin, false, 200,
                        new IAnimationCallback(){

                            @Override
                            public void callback() {
                                MyAnimation.setVisibilityAnimationSlide(
                                        binding.lnCreatePwLogin, true, 200);
                            }
                        });
                showFullscreenRectPrompt(binding.edtPass,
                        MySharedPreference.CREATE_PWD_PROMPT,
                        activity.getString(R.string.prompt_create_pwd_title),
                        activity.getString(R.string.prompt_create_pwd_content));*/
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
