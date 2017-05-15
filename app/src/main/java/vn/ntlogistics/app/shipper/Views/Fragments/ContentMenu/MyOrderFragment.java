package vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Adapters.ViewPagerAdapter;
import vn.ntlogistics.app.shipper.Views.Fragments.MyOrders.CancelOrderFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.MyOrders.CompletedFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.MyOrders.UncompletedFragment;

/**
 * A simple {@link Fragment} subclass.
 * TODO: Đơn hàng của tôi
 */
public class MyOrderFragment extends BaseFragment {
    View view;
    //TODO: init controls
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter;

    ProgressBar progressBar;
    List<BaseFragment> mListFragment;
    View lnBody;

    String[]                    titles;
    int a=0;
    //TODO: Id item để xóa trong RecyclerView
    public static int flag = 0;
//    BadgeView badgeView;
    public MyOrderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_my_order, container, false);

        init(view);
        final BaseAsystask asystask = new BaseAsystask(){

            @Override
            public void onPre() {

            }

            @Override
            public void doInBG() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupViewPager();
                    }
                });
            }

            @Override
            public void onPost() {
                lnBody.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        };
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                asystask.execute();
            }
        }, 50);

        return view;
    }

    void init(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        lnBody = view.findViewById(R.id.lnBody);
        titles = new String[]{
                getResources().getString(R.string.donhang_tab1),
                getResources().getString(R.string.donhang_tab2),
                getResources().getString(R.string.donhang_tab3),
                getResources().getString(R.string.donhang_tab4)
        };
        initViewPager(view);
    }

    void initViewPager(View view) {
        mListFragment = new ArrayList<>();
        adapter = new ViewPagerAdapter(getChildFragmentManager(), getContext(), mListFragment, titles);
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

        /*mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 1:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }

    private void setupViewPager() {
        final List<BaseFragment> list = new ArrayList<>();
        //NewOrderFragment dhm = new NewOrderFragment();
        UncompletedFragment dn = new UncompletedFragment();
        CompletedFragment dht = new CompletedFragment();
        CancelOrderFragment cancel = new CancelOrderFragment();
        //list.add(dhm);
        list.add(dn);
        list.add(dht);
        list.add(cancel);
        mListFragment.addAll(list);
        adapter.notifyDataSetChanged();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        tabLayout.getTabAt(0).getCustomView().setSelected(true);
        //TODO: Số lượng page load sẵn, nhỏ nhất là 4
        mViewPager.setOffscreenPageLimit(4);
    }

    public void setCurrentFragment(int position){
        mViewPager.setCurrentItem(position);
    }

    /**
     * Hàm được gọi từ MainActivity để xử lý khi nhận được notification từ firebase
     * @param action 1 - Thông báo | 2 - Xoá shipping code | 3 - Đơn hàng mới | 6 huỷ đơn hàng
     */
    @Override
    public void handleNotification(int action, Bundle b) {
        switch (action){
            case 6:
                handleCancelOder();
                break;
        }
    }

    public void handleCancelOder(){
        adapter.getFragmentAdapter(1).handleNotification(0,null);
        adapter.getFragmentAdapter(3).handleNotification(0,null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        handleUpdateStatusOrder();
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Order> mList) {

    }

    private void handleUpdateStatusOrder(){
        try {
            if(flag > 0){
                switch (flag){
                    //CompletedFragment
                    case 4:
                        adapter.getFragmentAdapter(1).reload();
                    case 5:
                        adapter.getFragmentAdapter(2).reload();
                        break;
                    //CancelOrderFragment
                    case 6:
                    case 7:
                        adapter.getFragmentAdapter(3).reload();
                        break;
                    default:
                        break;
                }
                flag = -1;
            }
        } catch (Exception e) {
        }
    }

    /**
     * Gọi từ MainActivity, truyền trạng thái nút online
     * @param b
     */
    public void updateUserStatusSuccess(boolean b){
        //((NewOrderFragment)adapter.getFragmentAdapter(0)).getViewModel().isOnline(b);
    }

    public BaseFragment getFragmentAdapter(int position){
        return adapter.getFragmentAdapter(position);
    }
}
