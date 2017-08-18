package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs.UpdateBillVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityUpdateBillBinding;


public class UpdateBillActivity extends BaseActivity {
    private UpdateBillVM                viewModel;
    private ActivityUpdateBillBinding   binding;

    public static void startIntentActivity(Context context, Bundle b,
                                           Integer requestCode, boolean isFinish){
        Intent intent = new Intent(context, UpdateBillActivity.class);
        if(b == null)
            b = new Bundle();
        intent.putExtras(b);
        if(requestCode != null)
            ((Activity) context).startActivityForResult(intent, requestCode);
        else
            context.startActivity(intent);
        if(isFinish)
            ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_bill);
        viewModel = new UpdateBillVM(this, binding);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == Constants.REQUEST_CODE_SCAN){
                Bundle b = data.getExtras();
                String m = null;
                try {
                    m = b.getString("symbol").toString();
                } catch (Exception e) {
                }
                if (m != null) {
                    binding.etDOCode.setEnabled(false);
                    binding.etDOCode.setText(m);
                    viewModel.showScan(false);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
