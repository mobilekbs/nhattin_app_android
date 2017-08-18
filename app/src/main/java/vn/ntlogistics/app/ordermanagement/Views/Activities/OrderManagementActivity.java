package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.OrderManagementVMs.OrderManagementActivityVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityOrderManagementBinding;

public class OrderManagementActivity extends BaseActivity {
    public static final String                  TAG = "OrderManagementActivity";

    private ActivityOrderManagementBinding      binding;
    private OrderManagementActivityVM           viewModel;

    public static void startIntentActivity(Context context){
        Intent i = new Intent(context, OrderManagementActivity.class);
        //i.putExtras(b);
        context.startActivity(i);
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_management);
        dataIntent();
        viewModel = new OrderManagementActivityVM(this, binding);
        binding.setViewModel(viewModel);
    }

    private void dataIntent(){
        Bundle b = getIntent().getExtras();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    public void reload(){
        if(viewModel != null){
            viewModel.reload();
        }
    }

    /**
     * Lấy intent gửi về từ Notification
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /**
         * Hàm xử lý khi nhấn vào notifacation sẽ chạy getOrder  */
        /*try {
            *//*String myOrder = String.valueOf(intent.getExtras().get("myOrder"));
            Order order = new Gson().fromJson(myOrder, Order.class);
            callOrderDetailAPI(order);*//*
            //viewModel.setCurrentFragment(1);
            viewModel.setChoose(1);
            ((MyOrderFragment)adapter.getFragment(1)).setCurrentFragment(0);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(viewModel != null)
            viewModel.onActivityResult(requestCode, resultCode, data);
    }
}
