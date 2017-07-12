package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.OrderManagementVMs.OrderManagementActivityVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityOrderManagementBinding;

public class OrderManagementActivity extends AppCompatActivity {
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
        if(SSqlite.getInstance(this).getListSenderBillByStatus(Constants.STATUS_UNCOMPLETED+"").size() == 0) {
            for (int i = 0; i < 20; i++) {
                Bill item = new Bill(
                        "ID111" + i,
                        "Phone 0987654" + i,
                        "Address " + i,
                        "senderName " + i,
                        "senderNode " + i,
                        "receiverPhone " + i,
                        "rAddress " + i,
                        "rName " + i,
                        "rNode " + i,
                        "length " + i,
                        "width " + i,
                        "height " + i,
                        "weight " + i,
                        "cod " + i,
                        "service " + i,
                        Constants.STATUS_UNCOMPLETED+"",
                        "04/07/2017",
                        "50",
                        "0983");
                SSqlite.getInstance(this).insertOrUpdateSendBill(item);
            }
            Bill item = new Bill(
                    "ID222",
                    "Phone 098762254",
                    "Address 222",
                    "senderName 222",
                    "senderNode 222",
                    "receiverPhone 2",
                    "rAddress 2",
                    "rName 2323",
                    "rNode 3123",
                    "length 322",
                    "width 2",
                    "height 2",
                    "weight 2",
                    "cod 2",
                    "service 2",
                    Constants.STATUS_COMPLETED+"",
                    "04/07/2017",
                    "50",
                    "0983");
            SSqlite.getInstance(this).insertOrUpdateSendBill(item);
            item.setStatus(Constants.STATUS_CANCEL_ORDER+"");
            SSqlite.getInstance(this).insertOrUpdateSendBill(item);
        }
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

}
