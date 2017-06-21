package vn.ntlogistics.app.ordermanagement.ViewModels.InputManagementVMs;

import android.os.Bundle;

import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.InputManagementActivity;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.FragmentMainAdapter;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.InputManagement.PinkBillFragment;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.InputManagement.WhiteBillFragment;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityInputManagementBinding;

/**
 * Created by Zanty on 23/05/2017.
 */

public class InputManagementVM extends ViewModel {
    private InputManagementActivity             activity;
    private ActivityInputManagementBinding      binding;

    //TODO: Init ViewPager
    private FragmentMainAdapter                 adapter;
    private int                                 flag = 0;

    public InputManagementVM(InputManagementActivity activity, ActivityInputManagementBinding binding) {
        this.activity = activity;
        this.binding = binding;

        dataIntent();
        initViewPager();
    }

    private void dataIntent() {
        Bundle b = activity.getIntent().getExtras();

        flag = b.getInt("flag"); // 0 - Nhận-Trả | 1 - Trả-Nhận
    }

    private void initViewPager(){
        adapter = new FragmentMainAdapter(
                activity.getSupportFragmentManager(),
                activity);
        String tab1, tab2;
        if(flag == 0) {
            tab1 = activity.getString(R.string.receive_fragment);
            tab2 = activity.getString(R.string.return_fragment);
        }
        else {
            tab1 = activity.getString(R.string.return_fragment);
            tab2 = activity.getString(R.string.receive_fragment);
        }
        adapter.addFragment(new PinkBillFragment(), tab1);
        adapter.addFragment(new WhiteBillFragment(),tab2);
        binding.vpInputManagement.setAdapter(adapter);
        binding.tabInputManagement.setupWithViewPager(binding.vpInputManagement);
        binding.vpInputManagement.setOffscreenPageLimit(2);
    }

}
