package vn.ntlogistics.app.shipper.ViewModels.LoginVMs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.SendOTPAPI;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.shipper.Views.Activities.ConfirmCodeActivity;

/**
 * Created by minhtan2908 on 1/12/17.
 */

public class LoginFragmentViewModel extends ViewModel {
    public static String TAG = "LoginFragmentViewModel";

    private Context         context;
    private BaseFragment    fragment;

    private ObservableField<String> textPhone = new ObservableField<>();

    public LoginFragmentViewModel(BaseFragment fragment) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        textPhone.set("");
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!textPhone.get().equalsIgnoreCase(s.toString()))
            textPhone.set(s.toString());
    }

    public void onClickLogin(View view) {
        //Sau 2s mới có thể nhấn lại
        Commons.setEnabledButton(view);
        handleLogin();
    }

    public boolean onEditorLogin(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
            handleLogin();
        }
        return false;
    }

    private void handleLogin(){
        if (textPhone.get().length() < 1) {
            Toast.makeText(context,
                    context.getResources().getString(R.string.inputNotComplete),
                    Toast.LENGTH_SHORT).show();
        } else if (textPhone.get().length() < 10) {
            Toast.makeText(context,
                    context.getResources().getString(R.string.min_phone_invalid),
                    Toast.LENGTH_SHORT).show();
        } else {
            //Huỷ đăng ký connect API của rxjava
            /*if (subscription != null && !subscription.isUnsubscribed())
                subscription.unsubscribe();*/

            /*api = new SendOTPAPI(
                    context,
                    textPhone.get(),
                    1,
                    LoginFragmentViewModel.this
            );
            // Chạy hàm kết nối api
            connectAPI(api);*/
            new SendOTPAPI(context, textPhone.get(), 1, fragment).execute();
            /**
             * subscription đã được khai báo ở class ViewModel
             * Đã gọi huỷ đăng ký trong onDestroy nên phải khai báo
             */
            //subscription = api.run();
        }
    }

    public void onClickRegister(View view) {
        //((LoginActivity) context).loadFragment(ELogin.REGISTER);
        Intent i = new Intent(context, ConfirmCodeActivity.class);
        i.putExtra("status", 1);
        ((Activity)context).startActivity(i);
    }

    @Override
    public void loadSuccess(List<?> mList) {
        Intent i = new Intent(context, ConfirmCodeActivity.class);
        i.putExtra("status", 1);
        ((Activity)context).startActivity(i);
    }
}
