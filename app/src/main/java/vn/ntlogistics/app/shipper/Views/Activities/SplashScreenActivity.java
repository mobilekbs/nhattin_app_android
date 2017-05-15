package vn.ntlogistics.app.shipper.Views.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.SplashScreenVMs.SplashScreenActivityViewModel;
import vn.ntlogistics.app.shipper.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends BaseActivity {
    public static final String              TAG = "SplashScreenActivity";

    private ActivitySplashScreenBinding     binding;
    private SplashScreenActivityViewModel   viewModel;

    private BroadcastReceiver               broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        viewModel = new SplashScreenActivityViewModel(this);
        binding.setViewModel(viewModel);

        viewModel.checkLogin();
    }

    public void networkChangeConnection() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                viewModel.checkConnect(Commons.hasConnection(SplashScreenActivity.this));
            }
        };
        //Bộ lọc lắng nghe sự thay đổi kết nối
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        //Đăng lý Broadcast Receiver
        registerReceiver(broadcastReceiver, filter);
    }

    public SplashScreenActivityViewModel getViewModel(){
        return viewModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Hủy đăng ký
        unregisterReceiver(broadcastReceiver);
    }
}
