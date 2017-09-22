package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Shipper.Productivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ViewPagerAdapter;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.ProductivityStatistics.DayStatisticsFragment;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.ProductivityStatistics.MonthStatisticsFragment;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityProductivityStatisticsBinding;


public class ProductivityStatisticsActivity extends BaseActivity {
    private ActivityProductivityStatisticsBinding       binding;

    //TODO: View Pager
    private ViewPagerAdapter                            adapter;
    private ArrayList<BaseFragment>                     mListFragment;
    private String[]                                    titles;

    /**
     * Khi click vào item month thì sẽ lưu tại đây.
     * Khi chuyển qua thống kê ngày thì sẽ dùng nó
     * để khai báo giao diện cho các ngày của tháng đó.
     */
    private Productivity                                currentProductivity;

    public static void startIntentActivity(Context context){
        Intent i = new Intent(context, ProductivityStatisticsActivity.class);
        context.startActivity(i);
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_productivity_statistics);
        initToolbar();
        if (!Commons.hasConnection(this)) {
            binding.loNotConectPS.setVisibility(View.VISIBLE);
        } else {
            initViewPager();
            setupViewPager();
        }
    }

    private void initToolbar(){
        setSupportActionBar(binding.toolbarStatistic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarStatistic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void initViewPager() {
        mListFragment = new ArrayList<>();
        titles = new String[]{
                getResources().getString(R.string.statistics_week),
                getResources().getString(R.string.statistics_day)
        };
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, mListFragment, titles);

        binding.vpPS.setAdapter(adapter);
        //binding.tabLayoutPS.setupWithViewPager(binding.vpPS);
    }

    private void setupViewPager() {
        mListFragment.add(MonthStatisticsFragment.newInstance(null));
        mListFragment.add(DayStatisticsFragment.newInstance(null));
        adapter.notifyDataSetChanged();

        /*for (int i = 0; i < binding.tabLayoutPS.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayoutPS.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        binding.tabLayoutPS.getTabAt(0).getCustomView().setSelected(true);*/
        binding.vpPS.setOffscreenPageLimit(2);
    }

    public void changeFragment(int position){
        try {
            binding.vpPS.setCurrentItem(position);
            if(position != 0)
                adapter.getItem(position).reload();
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        if(binding.vpPS.getCurrentItem() != 0){
            binding.vpPS.setCurrentItem(binding.vpPS.getCurrentItem() - 1);
        }
        else {
            super.onBackPressed();
        }
    }

    public Productivity getCurrentProductivity() {
        return currentProductivity;
    }

    public void setCurrentProductivity(Productivity currentProductivity) {
        this.currentProductivity = currentProductivity;
    }
}
