package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.CheckVersionAPI;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.BillFailActivity;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.ChangePassActivity;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.InfoProductActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.MainVMs.MainActivityVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding         binding;
    private MainActivityVM              viewModel;

    //TODO: Twice press back finish
    private boolean                     doubleBackToExitPressedOnce = false;

    public static void startIntentActivity(Context context){
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityVM(this, binding);
        setSupportActionBar(binding.toolbarMain);
        binding.setViewModel(viewModel);

        //Call api check version
        CheckVersionAPI.execute(this);

        binding.btnOrderMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                OrderManagementActivity.startIntentActivity(MainActivity.this);
            }
        });
        binding.btnPricingMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                PricingActivity.startIntentActivity(MainActivity.this);
            }
        });
        binding.btnRecieveMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                InputManagementActivity.startIntentActivity(MainActivity.this, 0);
            }
        });
        binding.btnReturnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                InputManagementActivity.startIntentActivity(MainActivity.this, 1);
            }
        });
        binding.btnStatisticsMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                ProductivityStatisticsActivity.startIntentActivity(MainActivity.this);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //MenuInflater inflater = menu.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnTTSP:
                if (existsUser()) {
                    Intent i = new Intent(this, InfoProductActivity.class);
                    startActivity(i);
                    //finish();
                }
                break;
            case R.id.acChangePass:
                if (existsUser()) {
                    Intent i = new Intent(this, ChangePassActivity.class);
                    startActivity(i);
                    //finish();
                }
                break;
            case R.id.mnBillFail:
                if (existsUser()) {
                    Intent i = new Intent(this, BillFailActivity.class);
                    startActivity(i);
                    //finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean existsUser() {
        if (SCurrentUser.getCurrentUser(this).getPublickey() == null) {
            Message.makeToastError(this,
                    getString(R.string.error_block_account));
            return false;
        } else
            return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (doubleBackToExitPressedOnce)
            moveTaskToBack(true);
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.twice_back), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
