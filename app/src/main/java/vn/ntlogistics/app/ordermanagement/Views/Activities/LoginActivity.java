package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.LoginVMs.LoginActivityVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding        binding;
    private LoginActivityVM             viewModel;
    private int                         flag = 0;
    /**
     *
     * @param context
     * @param flag 0 - Active confirm code | 1 - Create password | 2 - Enter Password
     */
    public static void startIntentActivity(Context context, int flag){
        Bundle b = new Bundle();
        b.putInt("flag", flag);

        Intent i = new Intent(context, LoginActivity.class);
        i.putExtras(b);
        context.startActivity(i);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        dataIntent();
        viewModel = new LoginActivityVM(this, binding, flag);
        binding.setViewModel(viewModel);

    }

    private void dataIntent(){
        Bundle b = getIntent().getExtras();

        try {
            flag = b.getInt("flag");
        } catch (Exception e) {
        }
    }
}
