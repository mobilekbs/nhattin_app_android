package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ConfirmBPBillInput;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.ConfirmDOVMs.ConfirmDOVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityConfirmDoBinding;


public class ConfirmDOActivity extends BaseActivity {

    private ActivityConfirmDoBinding        binding;
    private ConfirmDOVM                     viewModel;

    public static void startIntentActivity(Context context,
                                           ConfirmBPBillInput item,
                                           Bundle b,
                                           Integer requestCode,
                                           boolean isFinish){
        Intent i = new Intent(context, ConfirmDOActivity.class);

        if(b == null)
            b = new Bundle();
        b.putSerializable("item", item);
        i.putExtras(b);

        if (requestCode != null)
            ((Activity) context).startActivityForResult(i, requestCode);
        else
            ((Activity) context).startActivity(i);

        if(isFinish){
            ((Activity) context).finish();
        }
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_do);
        viewModel = new ConfirmDOVM(this, binding);

        binding.setViewModel(viewModel);

        setSupportActionBar(binding.toolbarBillDO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarBillDO.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onSuccess() {
        if(viewModel != null)
            viewModel.onSuccess();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            String m = ZXingScannerActivity.getCodeAfterScan(requestCode, resultCode, data);
            if (m != null) {
                binding.etDOCode.setEnabled(false);
                binding.etDOCode.setText(m);
                viewModel.checkDOCodeInvalid(m, false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
