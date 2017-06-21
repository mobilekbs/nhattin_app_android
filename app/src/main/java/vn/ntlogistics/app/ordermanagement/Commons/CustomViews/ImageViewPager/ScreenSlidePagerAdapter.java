package vn.ntlogistics.app.ordermanagement.Commons.CustomViews.ImageViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Views.Fragments.Image.ViewImageFragment;


/**
 * Created by Zanty on 23/06/2016.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mListURLs;

    //Dùng hashmap để add, get, remove fragment theo key
    private HashMap mFragmentMap = new HashMap();

    public ScreenSlidePagerAdapter(FragmentManager fm, List<String> mListURLs) {
        super(fm);
        this.mListURLs = mListURLs;
    }

    public void addFragment(int position, ViewImageFragment fragment) {
        mFragmentMap.put(position, fragment);
    }

    @Override
    public Fragment getItem(int position) {
        try {
            ViewImageFragment fragment = new ViewImageFragment();
            fragment.setImageUrl(mListURLs.get(position));
            addFragment(position, fragment);
            return fragment;
        } catch (Exception e) {
            return null;
        }

    }

    public Fragment getCurrentFragment(int position) {
        // return mFragmentList.get(position);
        return (Fragment) mFragmentMap.get(position);
    }

    @Override
    public int getCount() {
        return mListURLs.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        // mFragmentList.remove(position);
        mFragmentMap.remove(position);
    }
}
