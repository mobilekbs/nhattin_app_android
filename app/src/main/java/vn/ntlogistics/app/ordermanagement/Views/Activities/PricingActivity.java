package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.PricingVMs.PricingActivityVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityPricingBinding;

public class PricingActivity extends BaseActivity {
    private ActivityPricingBinding      binding;
    private PricingActivityVM           viewModel;

    public static void startIntentActivity(Context context){
        Intent i = new Intent(context, PricingActivity.class);
        context.startActivity(i);
        ((Activity)context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pricing);
        viewModel = new PricingActivityVM(binding, this);
        binding.setViewModel(viewModel);
        initToolbar();

    }

    private void initToolbar(){
        setSupportActionBar(binding.toolbarPricing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarPricing.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
}
