package vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Animations.MyAnimation;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.Spinner.BaseSpinner;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.Spinner.Beans.ItemSpinner;
import vn.ntlogistics.app.ordermanagement.Commons.Interfaces.ICallback;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SJob;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Commons.Sort.CompareItemByLocation;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.GoogleAPIs.DistanceMatrixAPI;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Job;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ItemOrderAdapter;


/**
 * Created by minhtan2908 on 2/20/17.
 */

public class BaseMyOrderViewModel {
    public static final String TAG = "BaseMyOrderViewModel";

    protected Activity activity;
    protected BaseFragment fragment;

    protected ObservableBoolean checkAll, showButton;
    protected ObservableInt showDialog;

    //TODO: Status Fragment
    protected int statusFragment;
    //TODO: Init spinner
    protected ArrayList<ItemSpinner>        mListSortJob, mListSort;
    protected Spinner s1, s2;

    //TODO: Init Recycler View
    protected ArrayList<Bill> mListShow;
    //TODO: list lưu toàn bộ đơn hàng tạm
    protected ArrayList<Bill> mListMain;
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    //protected View lnDefault;
    //TODO: load my order
    protected ItemOrderAdapter adapter;
    protected LinearLayoutManager linearLayoutManager;
    protected boolean loading = false;
    protected boolean reload = true;

    protected CustomDialog dialog;

    public BaseMyOrderViewModel(Activity activity,
                                BaseFragment fragment,
                                Spinner s1,
                                Spinner s2,
                                RecyclerView recyclerView,
                                SwipeRefreshLayout swipeRefreshLayout,
                                int statusFragment) {
        this.activity = activity;
        this.fragment = fragment;
        this.s1 = s1;
        this.s2 = s2;
        this.recyclerView = recyclerView;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.statusFragment = statusFragment;

        checkAll = new ObservableBoolean(false);
        showButton = new ObservableBoolean(false);
        showDialog = new ObservableInt(View.GONE);

        init();
    }

    private void init() {
        setupSpinner();
        setupRecyclerView();
    }

    public void setupSpinner() {
        mListSort = new ArrayList<>();
        mListSort.add(new ItemSpinner(0, activity.getString(R.string.sp_neworder)));
        mListSort.add(new ItemSpinner(1, activity.getString(R.string.sp_nearly)));

        List<Job> mListJob = new SJob().createListJobMain(activity);
        mListSortJob = new ArrayList<>();
        mListSortJob.add(new ItemSpinner(0, activity.getString(R.string.totle_work)));
        for (int i = 0; i < mListJob.size(); i++){
            mListSortJob.add(new ItemSpinner(mListJob.get(i).getId(), mListJob.get(i).getName()));
        }

        BaseSpinner.setupSpinner(s1, mListSort);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Khi sort theo công việc thì sẽ bỏ check
                //checkAll.set(false);
                setListOrderShow();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        BaseSpinner.setupSpinner(s2, mListSortJob);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //Khi sort theo công việc thì sẽ bỏ check
                //checkAll.set(false);
                setListOrderShow();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        mListShow = new ArrayList<Bill>();
        mListMain = new ArrayList<Bill>();
        callMyOrderAPI();
        adapter = new ItemOrderAdapter(activity, mListShow, fragment, statusFragment);
        recyclerView.setAdapter(adapter);
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!loading){
                    refreshSwipe();
                }
            }
        });
    }

    public void removeItem(int position){
        try {
            mListShow.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, mListShow.size());
        } catch (Exception e) {
        }
    }

    /**
     * Hàm dùng để thay đổi giá trị hiển thị lên màn hình
     */
    public void setListOrderShow() {
        mListShow.clear();
        mListShow.addAll(mListMain);
        int sp1 = s1.getSelectedItemPosition();
        switch(sp1){
            case 0: //Mới nhất
                break;
            case 1: //Gần nhất
                Collections.sort(mListShow, new CompareItemByLocation());
                break;
        }

        /*int posJob = s2.getSelectedItemPosition();
        switch (posJob){
            case 0:
                mListShow.addAll(mListMain);
                break;
            case 1:
                mListShow.addAll(BillCommon.sortOrderByJob(
                        mListMain,
                        mListSortJob.get(posJob).getId()
                ));
                break;
        }*/
        try {
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
        }
        showDialog.set(View.GONE);
    }

    public void checkAll(boolean check) {
        try {
            if (check) {
                for (int i = 0; i < mListShow.size(); i++)
                    mListShow.get(i).setCheck(true);
                adapter.notifyDataSetChanged();
                showButton.set(true);
            } else {
                for (int i = 0; i < mListShow.size(); i++)
                    mListShow.get(i).setCheck(false);
                adapter.notifyDataSetChanged();
                showButton.set(false);
            }
        } catch (Exception e) {
        }
    }

    public void reloadOrder(){
        if (!loading){
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
            refreshSwipe();
        }
    }

    private void refreshSwipe() {
        if (showButton.get()) {
            showButton.set(false);
        }
        loading = true;
        callMyOrderAPI();
        checkAll.set(false);
    }

    //TODO: Set Show/Hide View --------------------------------------------------------
    @BindingAdapter("setShowAnimation")
    public static void setShowViewAnimation(View view, boolean b){
        MyAnimation.setVisibilityAnimationDown(view, b);
    }
    //TODO: End set Show/Hide View ----------------------------------------------------/

    //TODO: Set Click View ----------------------------------------------------------
    public void onClickCheckAll(View view) {
        if (mListShow.size() > 0) {
            checkAll.set(!checkAll.get());
            checkAll(checkAll.get());
        }
    }
    //TODO: End set Click View -----------------------------------------------------/

    private void cloneArrayList(ArrayList<Bill> mListRequest, ArrayList<Bill> mListSource){
        for (int i = 0; i < mListSource.size(); i++) {
            try {
                mListRequest.add(mListSource.get(i).clone());
            } catch (Exception e) {
                mListRequest.add(mListSource.get(i));
            }
        }
    }

    //TODO: Call API ----------------------------------------------------------
    protected void callMyOrderAPI() {
        mListMain.clear();
        //clone list
        cloneArrayList(
                mListMain,
                SSqlite.getInstance(activity).getListSenderBillByStatus(statusFragment+"")
        );

        //Tính khoảng cách để lọc gần nhất.
        callDistanceMatrixAPI();

        //set item show
        setListOrderShow();

        loading = false;
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void callDistanceMatrixAPI() {
        final ArrayList<String> lstAddress = new ArrayList<>();

        for (Bill item : mListMain){
            lstAddress.add(item.getSenderAddress());
        }

        new DistanceMatrixAPI(activity, lstAddress, new ICallback() {
            @Override
            public void onSuccess(Object result) {
                if(result != null){
                ArrayList<Long> lstDistance = new ArrayList<>();
                    lstDistance.addAll((Collection<? extends Long>) result);
                    for (int i = 0; i < mListMain.size(); i++){
                        mListMain.get(i).setTotalDistance(lstDistance.get(i));
                        Log.e("DISTANCE",
                                mListMain.get(i).getSenderAddress() + " - " +
                                        mListMain.get(i).getTotalDistance()
                        );
                    }

                    /**
                     * Nếu đã chọn lọc theo khoảng cách thì
                     * khi tính khoảng cách xong mới show list lại.
                     * Còn đang ở mới nhất thì vẫn để vậy.
                     */
                    if(s1.getSelectedItemPosition() == 1)
                        setListOrderShow();
                }
            }
        }).execute();
     }

    public void updateList(Bill Bill) {
        mListMain.add(Bill);
        setListOrderShow();
    }
    //TODO: End call API -----------------------------------------------------/

    //TODO: Using Uncompleted Fragment --------------------
    protected int getJobTypeInList(){
        int job = 0;
        List<Bill> mListBillChecked = new ArrayList<>();
        //TODO: Get list Order checked
        for (int i = 0; i < mListShow.size(); i++) {
            if (mListShow.get(i).isCheck()) {
                mListBillChecked.add(mListShow.get(i));
                break;
            }
        }
        if(mListBillChecked.size() > 0)
            job = mListBillChecked.get(0).getJobType();
        return job;
    }
    //TODO: End using Uncompleted Fragment ---------------/

    //TODO: Get/Set ----------------------------------------------------------

    public ObservableBoolean getCheckAll() {
        return checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll.set(checkAll);
    }

    public ObservableBoolean getShowButton() {
        return showButton;
    }

    public void setShowButton(boolean showButton) {
        this.showButton.set(showButton);
    }

    public ObservableInt getShowDialog() {
        return showDialog;
    }

    public void setShowDialog(ObservableInt showDialog) {
        this.showDialog = showDialog;
    }
    //TODO: End Get/Set -----------------------------------------------------/

}
