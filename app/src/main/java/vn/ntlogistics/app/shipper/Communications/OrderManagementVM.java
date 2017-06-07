package vn.ntlogistics.app.shipper.Communications;

import android.databinding.BaseObservable;

import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Adapters.FragmentMainAdapter;
import vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu.MyOrderFragment;
import vn.ntlogistics.app.shipper.databinding.ActivityOrderManagementBinding;

/**
 * Created by Zanty on 17/05/2017.
 */

public class OrderManagementVM extends BaseObservable {
    public static final String TAG = "OrderManagementVM";
    private OrderManagementActivity             activity;
    private ActivityOrderManagementBinding      binding;
    private String                              androidKey;

    //TODO: ViewPager
    private FragmentMainAdapter                 adapter;

    public OrderManagementVM(OrderManagementActivity activity,
                             ActivityOrderManagementBinding binding,
                             String androidKey) {
        this.activity = activity;
        this.binding = binding;
        this.androidKey = androidKey;

        setupViewPager();
    }

    private void setupViewPager(){
        adapter = new FragmentMainAdapter(
                activity.getSupportFragmentManager(),
                activity);
        adapter.addFragment(new MyOrderFragment(), activity.getString(R.string.menu_my_order));
        binding.vpOrderManagement.setAdapter(adapter);
    }

}
