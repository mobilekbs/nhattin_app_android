package vn.ntlogistics.app.ordermanagement.ViewModels.OrderManagementVMs;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.OrderManagementActivity;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.FragmentMainAdapter;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.MyOrders.UncompletedFragment;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityOrderManagementBinding;

/**
 * Created by Zanty on 17/05/2017.
 */

public class OrderManagementActivityVM extends BaseObservable {
    public static final String                  TAG = "OrderManagementVM";
    private OrderManagementActivity             activity;
    private ActivityOrderManagementBinding      binding;

    //TODO: ViewPager
    private FragmentMainAdapter adapter;

    public OrderManagementActivityVM(OrderManagementActivity activity,
                                     ActivityOrderManagementBinding binding) {
        this.activity = activity;
        this.binding = binding;

        setupViewPager();
        initToolbar();
    }

    private void initToolbar(){
        activity.setSupportActionBar(binding.toolbarMainAct);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarMainAct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
    }

    private void setupViewPager(){
        adapter = new FragmentMainAdapter(
                activity.getSupportFragmentManager(),
                activity);
        //adapter.addFragment(new MyOrderFragment(), activity.getString(R.string.menu_my_order));
        adapter.addFragment(UncompletedFragment.newInstance(),
                activity.getString(R.string.menu_my_order));
        binding.vpOrderManagement.setAdapter(adapter);
    }

    public void reload(){
        try {
            adapter.getFragment(0).reload();
        } catch (Exception e) {
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        for (int i = 0; i < adapter.getCount(); i++){
            try {
                adapter.getFragment(i).onActivityResult(requestCode, resultCode, data);
            } catch (Exception e) {
            }
        }
    }

}
