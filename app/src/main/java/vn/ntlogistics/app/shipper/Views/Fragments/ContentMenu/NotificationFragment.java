package vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.CustomViews.SwipeLayout.util.Attributes;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.Outputs.Login.Notify;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;
import vn.ntlogistics.app.shipper.Views.Activities.WebViewActivity;
import vn.ntlogistics.app.shipper.Views.Adapters.RecyclerViewNotifyAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment {
    View view;
    //public static NotificationFragment instance;

    List<Notify> mList;
    RecyclerView recyclerView;
    RecyclerViewNotifyAdapter adpater;

    //TODO: Sqlite manager
    SqliteManager sqliteManager;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        init(view);
        setupDataList();
        /*if(instance == null || instance != this)
            instance = this;*/

        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        setupRecyclerView();
        setupBadge();
    }

    private void setupBadge() {
        /*BadgeView badgeView = new BadgeView(getContext(), MainActivity.title_toolbar);
        badgeView.setTextSize(15);
        badgeView.setBadgePosition(BadgeView.POSITION_BOTTOM_RIGHT);
        badgeView.setBackgroundResource(R.drawable.bg_badge);
        badgeView.setText(mList.size()+"");
        badgeView.show();*/
    }

    private void setupRecyclerView() {
        mList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adpater = new RecyclerViewNotifyAdapter(getContext(), mList, this);
        ((RecyclerViewNotifyAdapter) adpater).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(adpater);

        ((MainActivity)getActivity()).setBadgeNotification(countNotifucationNotSeen(mList));
    }

    public void setupDataList() {
        sqliteManager = new SqliteManager(getContext());

        new BaseAsystask(){

            @Override
            public void onPre() {

            }

            @Override
            public void doInBG() {
                mList.clear();
                mList.addAll(sortNotification(sqliteManager.getListNotificationFromData()));
            }

            @Override
            public void onPost() {
                adpater.notifyDataSetChanged();
                /**
                 * Set badge thông báo khi danh sách notifi được tạo
                 */
                try {
                    ((MainActivity)getActivity()).setBadgeNotification(countNotifucationNotSeen(mList));
                } catch (Exception e) {
                }
            }
        }.execute();
    }

    private List<Notify> sortNotification(List<Notify> mList){
        List<Notify> mListSort = new ArrayList<>();
        List<Notify> mListSortSeen = new ArrayList<>();

        for(int i = mList.size()-1; i > -1; i--){
            if(mList.get(i).isSeen())
                mListSortSeen.add(mList.get(i));
            else
                mListSort.add(mList.get(i));
        }
        mListSort.addAll(mListSortSeen);
        return mListSort;
    }

    private Integer countNotifucationNotSeen(List<Notify> mListNotify) {
        int count = 0;
        for (int i = 0; i < mListNotify.size(); i++) {
            if(!mListNotify.get(i).isSeen())
                count++;
        }
        return count;
    }

    @Override
    public void showControls(boolean check, int action) {
        if(action == -1) {
            if (adpater != null) {
                ((MainActivity)getActivity()).setBadgeNotification(countNotifucationNotSeen(mList));
                adpater.notifyDataSetChanged();
            }
        }
        else {
            Intent i = new Intent(getContext(), WebViewActivity.class);
            i.putExtra("title", mList.get(action).getTitle());
            i.putExtra("URL", mList.get(action).getUrl());
            startActivity(i);
        }
    }

    @Override
    public void loadSuccess(List<Order> mList) {
        /**
         * Set lại badge view khi load thành công list Notification
         */
        //MainActivity.instance.setBadgeNotification(countNotifucationNotSeen(this.mList));
    }



    @Override
    public void onResume() {
        super.onResume();
        try {
            if (adpater != null) {
                ((MainActivity)getActivity()).setBadgeNotification(countNotifucationNotSeen(mList));
                adpater.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //instance = null;
    }
}
