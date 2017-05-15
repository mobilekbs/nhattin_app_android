package vn.ntlogistics.app.shipper.Views.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;


/**
 * Created by Zanty on 06/07/2016.
 */
public class FragmentMainAdapter extends FragmentStatePagerAdapter {
    AppCompatActivity activity;
    private final List<BaseFragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public FragmentMainAdapter(FragmentManager fm, AppCompatActivity activity) {
        super(fm);
        this.activity = activity;
    }

    public void addFragment(BaseFragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public BaseFragment getFragment(int position){
        return mFragmentList.get(position);
    }

}
