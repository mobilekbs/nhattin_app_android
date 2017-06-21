package vn.ntlogistics.app.ordermanagement.ViewModels.MainVMs;

import android.view.View;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.InputManagementActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.MainActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.OrderManagementActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.PricingActivity;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityMainBinding;

/**
 * Created by Zanty on 19/05/2017.
 */

public class MainActivityVM extends ViewModel {
    private MainActivity            activity;
    private ActivityMainBinding     binding;

    public MainActivityVM(MainActivity activity, ActivityMainBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    public void onClickOrder(View view){
        Commons.setEnabledButton(view);
        OrderManagementActivity.startIntentActivity(activity);
    }
    public void onClickPricing(View view){
        Commons.setEnabledButton(view);
        PricingActivity.startIntentActivity(activity);
        /*Intent a = new Intent(this, CountPrice.class);
        Bundle b = new Bundle();
        b.putInt("menu", Variables.THISISMENU);
        a.putExtras(b);
        startActivity(a);*/
    }
    public void onClickReceive(View view){
        Commons.setEnabledButton(view);
        InputManagementActivity.startIntentActivity(activity,0);
        /*Intent intent = new Intent(this, Quanlynhap.class);
        Bundle a = new Bundle();
        a.putInt("keyloz", 3);
        intent.putExtras(a);
        startActivity(intent);*/
    }
    public void onClickReturn(View view){
        Commons.setEnabledButton(view);
        InputManagementActivity.startIntentActivity(activity,1);
        /*Intent intent = new Intent(this, Quanlynhap.class);
        Bundle a = new Bundle();
        a.putInt("keyloz", 4);
        intent.putExtras(a);
        startActivity(intent);*/
    }
}
