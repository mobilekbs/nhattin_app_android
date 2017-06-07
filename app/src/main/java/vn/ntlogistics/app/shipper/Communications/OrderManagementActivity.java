package vn.ntlogistics.app.shipper.Communications;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.databinding.ActivityOrderManagementBinding;

public class OrderManagementActivity extends AppCompatActivity {
    public static final String TAG = "OrderManagementActivity";

    private ActivityOrderManagementBinding      binding;
    private OrderManagementVM                   viewModel;

    private String                              androidKey = "";

    public static void startIntentActivity(Context context, String androidKey){
        Bundle b = new Bundle();
        b.putString("AndroidKey", androidKey);

        Intent i = new Intent(context, OrderManagementActivity.class);
        i.putExtras(b);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_management);

        dataIntent();

        viewModel = new OrderManagementVM(this, binding, androidKey);
        binding.setViewModel(viewModel);
    }

    private void dataIntent() {
        Bundle b = getIntent().getExtras();

        try {
            androidKey = b.getString("AndroidKey");
        } catch (Exception e) {
        }

    }
}
