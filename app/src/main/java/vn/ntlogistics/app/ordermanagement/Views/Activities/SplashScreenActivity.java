package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.SplashScreenVMs.SplashScreenActivityViewModel;
import vn.ntlogistics.app.ordermanagement.databinding.ActivitySplashScreenBinding;


public class SplashScreenActivity extends AppCompatActivity {
    public static final String              TAG = "SplashScreenActivity";

    private ActivitySplashScreenBinding binding;
    private SplashScreenActivityViewModel viewModel;

    //private BroadcastReceiver               broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        viewModel = new SplashScreenActivityViewModel(this);
        binding.setViewModel(viewModel);
        //networkChangeConnection();
        //viewModel.checkLogin();
    }

    public SplashScreenActivityViewModel getViewModel(){
        return viewModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Hủy đăng ký
        //unregisterReceiver(broadcastReceiver);
    }
}
