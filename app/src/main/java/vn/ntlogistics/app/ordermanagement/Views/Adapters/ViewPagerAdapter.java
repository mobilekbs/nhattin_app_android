package vn.ntlogistics.app.ordermanagement.Views.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.BadgeView;
import vn.ntlogistics.app.ordermanagement.R;


/**
 * Created by Zanty on 13/04/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    private List<BaseFragment> mFragmentList;
    int tag;
    String[] titles;
    static BadgeView badgeView;
    int number = 0;

    public ViewPagerAdapter(FragmentManager fm, Context context, List<BaseFragment> mFragmentList, String[] titles) {
        super(fm);
        this.context = context;
        this.mFragmentList = mFragmentList;
        this.titles = titles;
    }

    public ViewPagerAdapter(FragmentManager fm, Context context, List<BaseFragment> mFragmentList) {
        super(fm);
        this.context = context;
        this.mFragmentList = mFragmentList;
        titles = new String[]{
                context.getResources().getString(R.string.orderdetail_tab1),
                context.getResources().getString(R.string.orderdetail_tab2),
                context.getResources().getString(R.string.orderdetail_tab3),
                context.getResources().getString(R.string.orderdetail_tab4)
        };
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableString sb = new SpannableString(titles[position]);
        return sb;
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tablayout, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        //TextView tvBadge = (TextView) v.findViewById(R.id.tvBadge);
        tv.setText(titles[position]);
        tv.setTextColor(ContextCompat.getColorStateList(context,R.color.text_tabs));
        if(position==1) {
            badgeView = new BadgeView(context, tv);
            badgeView.setTextSize(10);
            badgeView.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
            badgeView.setBackgroundResource(R.drawable.bg_badge);
        }
        return v;
    }

    public static BadgeView getBadgeView() {
        return badgeView;
    }

    public BaseFragment getFragmentAdapter(int position){
        return mFragmentList.get(position);
    }
}
