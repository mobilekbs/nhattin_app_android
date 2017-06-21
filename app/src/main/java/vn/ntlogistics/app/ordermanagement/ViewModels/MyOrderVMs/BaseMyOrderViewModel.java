package vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Animations.MyAnimation;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.ordermanagement.Commons.Location.GetCurrentLocation;
import vn.ntlogistics.app.ordermanagement.Commons.Location.Lonlat;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SJob;
import vn.ntlogistics.app.ordermanagement.Commons.Sort.BillCommon;
import vn.ntlogistics.app.ordermanagement.Commons.Sort.CompareItemByLocation;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Job;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.RecyclerViewAdapter;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ViewPagerAdapter;


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
    protected String[] ss1;
    protected String[] ss2;
    protected Spinner s1, s2;

    //TODO: Init Recycler View
    protected List<Bill> mListOrder;
    //TODO: list lưu toàn bộ đơn hàng tạm
    protected List<Bill> mListMain;
    protected List<Bill> mListMainSup;
    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout swipeRefreshLayout;
    //protected View lnDefault;
    //TODO: load my order
    protected int pageNumber = 0;
    protected RecyclerViewAdapter adpater;
    protected LinearLayoutManager linearLayoutManager;
    protected boolean loading = false;
    protected boolean reload = true;

    protected CustomDialog dialog;

    //TODO: lấy vị trí hiện tại
    protected GetCurrentLocation getCurrentLocation;
    protected Lonlat position;

    //TODO: Progress Dialog
    //protected View lnDialog;

    //TODO: API - Uncompleted Fragment
    protected List<String> mListShippingCode;

    //TODO: Sqlite Manager
    protected SqliteManager                 db;

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
        //this.lnDefault = lnDefault;
        //this.lnDialog = lnDialog;
        this.statusFragment = statusFragment;

        checkAll = new ObservableBoolean(false);
        showButton = new ObservableBoolean(false);
        showDialog = new ObservableInt(View.GONE);

        db = new SqliteManager(activity);


        init();
    }

    private void init() {
        //TODO: API - Uncompleted Fragment
        //mListOrderKCode = new ArrayList<>();
        mListShippingCode = new ArrayList<>();

        ss1 = new String[]{activity.getString(R.string.sp_neworder), activity.getString(R.string.sp_nearly)};
        List<Job> mListJob = new SJob().createListJobMain(activity);
        ss2 = new String[mListJob.size()+1];
        ss2[0] = activity.getString(R.string.totle_work);
        for (int i =0; i < mListJob.size(); i++){
            ss2[i+1] = mListJob.get(i).getName();
        }

        //TODO: Lấy vị trí hiện tại
        try {
            getCurrentLocation = new GetCurrentLocation(activity);
            position = new Lonlat(getCurrentLocation.getLongitude(), getCurrentLocation.getLatitude());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //lnDefault.setVisibility(View.GONE);
        setupSpinner();
        setupRecyclerView();
        //setupDialog();
        //lnDialog.setVisibility(View.GONE);
    }

    public void setupSpinner() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_item, //set layout choose
                ss1
        );
        //set layout for spinner
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        s1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                activity,
                android.R.layout.simple_spinner_item,
                ss2
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        s2.setAdapter(adapter2);

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mListOrder.clear();
                if (i == 0) {
                    mListOrder.addAll(mListMain);
                } else {
                    mListOrder.addAll(BillCommon.sortOrderByJob(mListMain, i));
                }
                //Khi sort theo công việc thì sẽ bỏ check
                //checkAll(false);
                //Set cbAll thành false
                //cbAll.setChecked(false);
                checkAll.set(false);
                adpater.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {

                if (i == 0) {
                    mListMain.clear();
                    mListMain.addAll(mListMainSup);
                    setListOrderShow(s2.getSelectedItemPosition());
                } else if (i == 1) { //Sắp xếp gần nhất
                    new BaseAsystask() {

                        @Override
                        public void onPre() {
                            //lnDialog.setVisibility(View.VISIBLE);
                            showDialog.set(View.VISIBLE);
                        }

                        @Override
                        public void doInBG() {
                            Collections.sort(mListMain, new CompareItemByLocation());
                        }

                        @Override
                        public void onPost() {
                            setListOrderShow(s2.getSelectedItemPosition());
                        }
                    }.execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onResume(){
        if (adpater != null)
            adpater.notifyDataSetChanged();
    }

    public void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        mListMainSup = new ArrayList<>();
        mListOrder = new ArrayList<Bill>();
        mListMain = new ArrayList<Bill>();
        callMyOrderAPI();
//        adpater = new HubOrderAdapter(getContext(), mListOrder, this, Common.STATUS_NEW_ORDER);
        adpater = new RecyclerViewAdapter(activity
                , mListOrder, fragment, statusFragment);
        recyclerView.setAdapter(adpater);
        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = recyclerView.getAdapter().getItemCount();
                if (newState == 0) {
                    //position Order cuối đang hiển thị mà bằng tổng số Order lst thì load
                    *//**
                     * Nếu không xét size của mListOrder thì nó sẽ không nhận
                     * SwpieRefesh vì Order nhỏ sẽ ưu tiên chạy onScroll
                     * mListOrder.size() > ((Common.NUMBER_Order_LOAD-1)*(pageNumber+1))
                     * *//*
                    if (reload)
                        if (linearLayoutManager.findLastVisibleItemPosition() >= count - 2 && !loading) {
                            recyclerView.setClickable(false);
                            loading = true;
                            pageNumber++;
                            callMyOrderAPI();
                        }
                }
            }
        });*/
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!loading){
                    refreshSwipe();
                }
            }
        });
    }

    /*private void setupDialog() {
        dialog = new CustomDialog(activity);
        dialog.setTitleMessage(activity.getString(R.string.dialog_not_receiver));
        dialog.setShow(true);
        dialog.setTextTitle(activity.getString(R.string.note_dialog));
        dialog.setTextButton(activity.getString(R.string.yes), activity.getString(R.string.no));
        dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
            @Override
            public void onClickOk() {
                callDenyOrderAPI();
                //MyAnimation.setVisibilityAnimationDown(lnDefault, false);
                showButton.set(false);
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                dialog.dismiss();
            }
        });
    }*/

    /**
     * Hàm dùng để thay đổi giá trị hiển thị lên màn hình
     */
    public void setListOrderShow(int i) {
        mListOrder.clear();
        int sp1 = s1.getSelectedItemPosition();
        if (sp1 == 1) { //sort gần nhất
            Collections.sort(mListMain, new CompareItemByLocation());
        }
        if (i == 0)
            mListOrder.addAll(mListMain);
        else
            mListOrder.addAll(BillCommon.sortOrderByJob(mListMain, i));
        adpater.notifyDataSetChanged();
        showDialog.set(View.GONE);
    }

    public void checkAll(boolean check) {
        try {
            if (check) {
                for (int i = 0; i < mListOrder.size(); i++)
                    mListOrder.get(i).setCheck(true);
                adpater.notifyDataSetChanged();
                showButton.set(true);
            } else {
                for (int i = 0; i < mListOrder.size(); i++)
                    mListOrder.get(i).setCheck(false);
                adpater.notifyDataSetChanged();
                showButton.set(false);
            }
        } catch (Exception e) {
        }
    }

    public void reloadOrder(){
        if (!loading){
            swipeRefreshLayout.setRefreshing(true);
            refreshSwipe();
        }
    }

    private void refreshSwipe() {
        if (showButton.get()) {
            showButton.set(false);
        }
        loading = true;
        try {
            recyclerView.setEnabled(false);
        } catch (Exception e) {
        }
        pageNumber = 0;
        callMyOrderAPI();
        checkAll.set(false);
    }

    //TODO: Set Show/Hide View --------------------------------------------------------
    @BindingAdapter("setShowAnimation")
    public static void setShowViewAnimation(View view, boolean b){
        MyAnimation.setVisibilityAnimationDown(view, b);
    }
    //TODO: End set Show/Hide View ----------------------------------------------------/

    //TODO: Set Handle View ----------------------------------------------------------

    //TODO: End Set Handle View -----------------------------------------------------/
    //TODO: Set Click View ----------------------------------------------------------
    public void onClickCheckAll(View view) {
        if (mListOrder.size() > 0) {
            checkAll.set(!checkAll.get());
            checkAll(checkAll.get());
        }
    }
    //TODO: End set Click View -----------------------------------------------------/

    /*public void reloadFromNotification(final List<Order> mList){
        mListMain.clear();
        mListMainSup.clear();
        checkAll.set(false);

        new BaseAsystask() {

            @Override
            public void onPre() {
                //lnDialog.setVisibility(View.VISIBLE);
                showDialog.set(View.VISIBLE);
            }

            @Override
            public void doInBG() {
                if(s1.getSelectedOrderPosition()==0|| position == null)
                    loadDataIntoList(mList);
                else if(s1.getSelectedOrderPosition()==1)
                    countDistanceIntoList(mList);
            }

            @Override
            public void onPost() {
                //lnDialog.setVisibility(View.GONE);
                showDialog.set(View.GONE);
                setListOrderShow(s2.getSelectedOrderPosition());
                loading = false;
                setDistanceIntoList();
            }
        }.execute();
        if (showButton.get()) {
            showButton.set(false);
        }
    }*/

    public void loadSuccess(final List<?> mList) {
        if (mList != null) {
            final List<Bill> mListO = (List<Bill>) mList;
            if(mListO.size() == Constants.NUMBER_ITEM_LOAD)
                reload = true;
            else
                reload = false;

            /**
             * Gọi hàng finishRefresh để kiểm tra nếu SwipeRefresh đang chạy thì
             * sẽ dừng hoạt động load và clear list.
             * Nếu nó không chạy thì vẫn hoạt động bình thường, xem như bỏ qua nó.
             */
            if (swipeRefreshLayout.isRefreshing()) {
                mListMain.clear();
                mListMainSup.clear();
                //cbAll.setChecked(false);
                checkAll.set(false);
                new BaseAsystask() {

                    @Override
                    public void onPre() {
                        //lnDialog.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void doInBG() {
                        if(s1.getSelectedItemPosition()==0 || position == null)
                            loadDataIntoList(mListO);
                        else if(s1.getSelectedItemPosition()==1) {
                            //countDistanceIntoList(mListO);
                        }
                    }

                    @Override
                    public void onPost() {
                        if(statusFragment == Constants.STATUS_UNCOMPLETED)
                            updateBadgeView();
                        setListOrderShow(s2.getSelectedItemPosition());
                        loading = false;
                        swipeRefreshLayout.setRefreshing(false);
                        //setDistanceIntoList();
                    }
                }.execute();

            } else {
                recyclerView.setEnabled(true);
                new BaseAsystask() {

                    @Override
                    public void onPre() {
                        if (mListMain.size() < 1)
                            showDialog.set(View.VISIBLE);
                    }

                    @Override
                    public void doInBG() {
                        if(s1.getSelectedItemPosition()==0 || position == null)
                            loadDataIntoList(mListO);
                        else if(s1.getSelectedItemPosition()==1) {
                            //countDistanceIntoList(mListO);
                        }
                    }

                    @Override
                    public void onPost() {
                        if(statusFragment == Constants.STATUS_UNCOMPLETED)
                            updateBadgeView();
                        showDialog.set(View.GONE);
                        setListOrderShow(s2.getSelectedItemPosition());
                        loading = false;
                        //setDistanceIntoList();
                    }
                }.execute();
            }
        } else {
            loadSuccessNull();
        }
    }

    public void loadSuccessNull(){

    }

    private void loadDataIntoList(List<Bill> mList){
        for (int i = 0; i < mList.size(); i++) {
            Bill Bill = mList.get(i);
            mListMain.add(Bill);
            mListMainSup.add(Bill);
        }
    }

    /**
     * Hàm update BadgeView, sẽ được gọi khi update hoặc từ chối đơn hàng.
     */
    public void updateBadgeView() {
        try {
            if (mListMain.size() > 0) {
                ViewPagerAdapter.getBadgeView().setText(mListMain.size() + "");
                ViewPagerAdapter.getBadgeView().show();
            } else
                ViewPagerAdapter.getBadgeView().hide();
        } catch (Exception e) {
        }
    }

    /*public void countDistanceIntoList(List<Bill> mList) {
        if (position != null) {
            for (int i = 0; i < mList.size(); i++) {
                final Bill bill = mList.get(i);
                //Tính khoảng cách và add vào Order

                LocationCommon.searchLatLngByAddress(
                        activity,
                        bill.getSenderAddress(),
                        new IGeocode() {
                            @Override
                            public void loadSuccess(final Lonlat latLng) {
                                new BaseAsystask(){
                                    float distance;
                                    @Override
                                    public void onPre() {

                                    }

                                    @Override
                                    public void doInBG() {
                                        distance =
                                                LocationCommon.searchDistanceBetween2Location(
                                                        position, latLng
                                                );
                                    }

                                    @Override
                                    public void onPost() {
                                        if (distance != -1f)
                                            bill.setDistance(distance);
                                        mListMain.add(bill);
                                        mListMainSup.add(bill);
                                    }
                                }.execute();
                            }
                        }
                );
            }
        }
    }*/

    /*private void setDistanceIntoList(){
        if (position != null) {
            for (int i = 0; i < mListMain.size(); i++) {
                final Bill bill = mListMain.get(i);
                //Tính khoảng cách và add vào Order

                final int finalI = i;
                LocationCommon.searchLatLngByAddress(
                        activity,
                        bill.getSenderAddress(),
                        new IGeocode() {
                            @Override
                            public void loadSuccess(final Lonlat latLng) {
                                new BaseAsystask(){
                                    float distance;
                                    @Override
                                    public void onPre() {

                                    }

                                    @Override
                                    public void doInBG() {
                                        distance =
                                                LocationCommon.searchDistanceBetween2Location(
                                                        position, latLng
                                                );
                                    }

                                    @Override
                                    public void onPost() {
                                        if (distance != -1f)
                                            bill.setDistance(distance);
                                        try {
                                            mListMain.set(finalI, bill);
                                            mListMainSup.set(finalI, bill);
                                        } catch (Exception e) {
                                        }
                                    }
                                }.execute();
                            }
                        }
                );
            }
        }
    }*/

    //TODO: Call API ----------------------------------------------------------
    protected void callMyOrderAPI() {
        mListMain.clear();
        /*mListMain.add(new Bill("SC0011","1000000", "1230000",0,"23 Ly Chinh Thang",
                "32 Ly Thuong Kiet", "2", "1", 0, 10000, 1000000, 2000000, 12300));
        mListMain.add(new Bill("SC0011","1000000", "1230000",1,"23 Ly Chinh Thang",
                "32 Ly Thuong Kiet", "2", "1", 0, 10000, 1000000, 2000000, 12300));
        mListMain.add(new Bill("SC0011","1000000", "1230000",1,"23 Ly Chinh Thang",
                "32 Ly Thuong Kiet", "2", "1", 0, 10000, 1000000, 2000000, 12300));
        mListMain.add(new Bill("SC0011","1000000", "1230000",1,"23 Ly Chinh Thang",
                "32 Ly Thuong Kiet", "2", "1", 0, 10000, 1000000, 2000000, 12300));
        mListMain.add(new Bill("SC0011","1000000", "1230000",1,"23 Ly Chinh Thang",
                "32 Ly Thuong Kiet", "2", "1", 0, 10000, 1000000, 2000000, 12300));
        mListMain.add(new Bill("SC0011","1000000", "1230000",1,"23 Ly Chinh Thang",
                "32 Ly Thuong Kiet", "2", "1", 0, 10000, 1000000, 2000000, 12300));*/
        mListMain.addAll(db.getListSenderBillByStatus(statusFragment+""));
        mListOrder.addAll(mListMain);
        loading = false;
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        //adpater.notifyDataSetChanged();
        /*JSMyOrder data = new JSMyOrder(
                activity,
                Constants.NUMBER_Order_LOAD,
                pageNumber,
                statusFragment);
        new MyOrderAPI(
                activity,
                this,
                new Gson().toJson(data),
                true
        ).execute();*/
    }

    /*private void callDenyOrderAPI() {
        JSADROrder data = new JSADROrder(activity);
        data.setData(checkedFormList());
        String json = new Gson().toJson(data);
        new DenyOrdersAPI(activity, json, fragment).execute();
    }*/

    /**
     * Hàm trả về list BL trong list các Order được check
     *
     * @return
     */
    /*protected List<String> checkedFormList() {
        List<String> mData = new ArrayList<>();
        List<String> mListBLCode = new ArrayList<>();
        //mListOrderKCode.clear();
        mListShippingCode.clear();
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck()) {
                //mListOrderKCode.add(mListOrder.get(i).getOrder().getOrderKCode());
                mListShippingCode.add(mListOrder.get(i).getShippingCode());
                mListBLCode = mListOrder.get(i).getLstBLCode();
                for (int j = 0; j < mListBLCode.size(); j++)
                    mData.add(new String(mListBLCode.get(j)));
            }
        }
        return mData;
    }*/

    public void updateList(Bill Bill) {
        mListMain.add(Bill);
        setListOrderShow(s2.getSelectedItemPosition());
    }
    //TODO: End call API -----------------------------------------------------/

    //TODO: Using Uncompleted Fragment --------------------
    protected int getJobTypeInList(){
        int job = 0;
        List<Bill> mListBillChecked = new ArrayList<>();
        //TODO: Get list Order checked
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck()) {
                mListBillChecked.add(mListOrder.get(i));
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
