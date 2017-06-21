package vn.ntlogistics.app.ordermanagement.Views.Fragments.MyOrders;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Animations.MyAnimation;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.ordermanagement.Commons.Location.GetCurrentLocation;
import vn.ntlogistics.app.ordermanagement.Commons.Location.Lonlat;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SJob;
import vn.ntlogistics.app.ordermanagement.Commons.Sort.BillCommon;
import vn.ntlogistics.app.ordermanagement.Commons.Sort.CompareItemByLocation;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Job;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.RecyclerViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedFragment extends BaseFragment {
    //static CompletedFragment instance;
    Spinner s1, s2;
    String[] ss1,ss2;
    List<Bill> mListBill;
    //TODO: list lưu toàn bộ đơn hàng tạm
    List<Bill> mListMain;
    List<Bill> mListMainSup;
    //TODO: Progress Dialog
    View lnDialog;

    //TODO: lấy vị trí hiện tại
    GetCurrentLocation getCurrentLocation;
    Lonlat position;

    CustomDialog dialog;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    View lnDefault, btnDismiss;
    CheckBox cbAll;

    RecyclerViewAdapter adpater;
    //TODO: load my order
    int pageNumber = 0;

    LinearLayoutManager linearLayoutManager;
    boolean loading = false;
    boolean reload = true;

    public CompletedFragment() {
        // Required empty public constructor
    }

    /*public static CompletedFragment getInstanse(){
        return instance;
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_completed, container, false);

        lnDialog = view.findViewById(R.id.lnDialog);
        init(view);
        lnDialog.setVisibility(View.GONE);
        /**
         * Tạo instance sau khi khởi tạo để lấy được mListOrder
         * giá trị của mListOrder khi get data từ server về.
         * Nếu để trước init() thì mListOrder sẽ rỗng.
         * */
        /*if (instance == null || instance != CompletedFragment.this)
            instance = CompletedFragment.this;*/
        /*final BaseAsystask load = new BaseAsystask(){

            @Override
            public void onPre() {
                lnDialog.setVisibility(View.VISIBLE);
            }

            @Override
            public void doInBG() {
                init(view);
            }

            @Override
            public void onPost() {
                lnDialog.setVisibility(View.GONE);
                *//**
                 * Tạo instance sau khi khởi tạo để lấy được mListOrder
                 * giá trị của mListOrder khi get data từ server về.
                 * Nếu để trước init() thì mListOrder sẽ rỗng.
                 * *//*
                if (instance == null || instance != CompletedFragment.this)
                    instance = CompletedFragment.this;
            }
        };
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                load.execute();
            }
        });*/
        return view;
    }

    void init(View view) {
        ss1 = new String[]{getResources().getString(R.string.sp_neworder), getResources().getString(R.string.sp_nearly)};
        List<Job> mListJob = new SJob().createListJobMain(getContext());
        ss2 = new String[mListJob.size()+1];
        ss2[0] = getString(R.string.totle_work);
        for (int i =0; i < mListJob.size(); i++){
            ss2[i+1] = mListJob.get(i).getName();
        }
        //TODO: Lấy vị trí hiện tại
        try {
            getCurrentLocation = new GetCurrentLocation(getContext());
            position = new Lonlat(getCurrentLocation.getLongitude(),
                    getCurrentLocation.getLatitude());
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        s1 = (Spinner) view.findViewById(R.id.spinner);
        s2 = (Spinner) view.findViewById(R.id.spinner1);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);

        cbAll = (CheckBox) view.findViewById(R.id.cbAll1);
        /*cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListOrder.size() > 0)
                    checkAll(cbAll.isChecked());
            }
        });*/

        lnDefault = view.findViewById(R.id.lnDefault);
        btnDismiss = view.findViewById(R.id.btnDismiss);

        setupSpinner();
        setupRecyclerView();
        setupSwipeRefresh();
        setupOnClickButton();
        setupDialog();
    }

    private void setupDialog() {
        dialog = new CustomDialog(getActivity());
        dialog.setTitleMessage(getResources().getString(R.string.dialog_completed));
        dialog.setShow(true);
        dialog.setTextTitle(getResources().getString(R.string.note_dialog));
        dialog.setTextButton(getResources().getString(R.string.yes),getResources().getString(R.string.no));
        dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
            @Override
            public void onClickOk() {
                callRemoveFromListOrderAPI();
                MyAnimation.setVisibilityAnimationDown(lnDefault, false);
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                dialog.dismiss();
            }
        });
    }

    /*public void checkAll(boolean check) {
        try {
            if (check) {
                for (int i = 0; i < mListOrder.size(); i++)
                    mListOrder.get(i).setCheck(true);
                adpater.notifyDataSetChanged();
                MyAnimation.setVisibilityAnimationDown(lnDefault, true);
            } else {
                for (int i = 0; i < mListOrder.size(); i++)
                    mListOrder.get(i).setCheck(false);
                adpater.notifyDataSetChanged();
                MyAnimation.setVisibilityAnimationDown(lnDefault, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void setupOnClickButton() {
        lnDefault.setVisibility(View.GONE);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(btnDismiss);
                dialog.show();
            }
        });
    }

    @Override
    public void showControls(boolean check, int action) {
        if (action == 1)
            cbAll.setChecked(true);
        else
            cbAll.setChecked(false);
        if (check) {
            if (lnDefault.getVisibility() != View.VISIBLE) {
                MyAnimation.setVisibilityAnimationDown(lnDefault, true);
            }
        } else {
            if (lnDefault.getVisibility() == View.VISIBLE) {
                MyAnimation.setVisibilityAnimationDown(lnDefault, false);
            }
        }
    }

    /*private List<String> checkedFormList() {
        List<String> mData = new ArrayList<>();
        List<String> mListBLCode = new ArrayList<>();
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck()) {
                mListBLCode = mListOrder.get(i).getOrder().getLstBLCode();
                for (int j = 0; j < mListBLCode.size(); j++)
                    mData.add(new String(mListBLCode.get(j)));
            }
        }
        return mData;
    }*/


    private void callRemoveFromListOrderAPI() {
        /*JSADROrder data = new JSADROrder(getContext());
        data.setData(checkedFormList());
        String json = new Gson().toJson(data);
        new RemoveFromListAPI(getActivity(), json, this).execute();*/
    }

    @Override
    public void loadSuccess(final List<Bill> mList) {
        if (mList != null) {
            /**
             * Nếu mình yêu cầu load 10 Order thì khi nhận đủ 10 Order mình mới cho reload
             * Còn nếu mà nhỏ hơn thì là trong database đã load hết nên không cho load nữa.
             */
            if(mList.size() == Constants.NUMBER_ITEM_LOAD)
                reload = true;
            else
                reload = false;
            /**
             * Gọi hàng finishRefresh để kiểm tra nếu SwipeRefresh đang chạy thì
             * sẽ dừng hoạt động load và clear list.
             * Nếu nó không chạy thì vẫn hoạt động bình thường, xem như bỏ qua nó.
             */
            //finishRefresh();
            if (swipeRefreshLayout.isRefreshing()) {
                mListMain.clear();
                mListMainSup.clear();
                cbAll.setChecked(false);

                new BaseAsystask(){

                    @Override
                    public void onPre() {
                        //lnDialog.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void doInBG() {
                        if(s1.getSelectedItemPosition()==0|| position == null)
                            loadDataIntoList(mList);
                        else if(s1.getSelectedItemPosition()==1) {
                            //countDistanceIntoList(mList);
                        }
                    }

                    @Override
                    public void onPost() {
                        setListOrderShow(s2.getSelectedItemPosition());
                        loading = false;
                        swipeRefreshLayout.setRefreshing(false);
                        //setDistanceIntoList();
                    }
                }.execute();

            }
            else {
                recyclerView.setEnabled(true);
                new BaseAsystask() {

                    @Override
                    public void onPre() {
                        if(mListMain.size() <1)
                            lnDialog.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void doInBG() {
                        if(s1.getSelectedItemPosition()==0|| position == null)
                            loadDataIntoList(mList);
                        else if(s1.getSelectedItemPosition()==1) {
                            //countDistanceIntoList(mList);
                        }
                    }

                    @Override
                    public void onPost() {
                        lnDialog.setVisibility(View.GONE);
                        setListOrderShow(s2.getSelectedItemPosition());
                        loading = false;
                        //setDistanceIntoList();
                    }
                }.execute();
            }
        } else {
            //removeOrderList();
        }
    }

    @Override
    public void loadFailed() {
        super.loadFailed();
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    private void loadDataIntoList(List<Bill> mList){
        for (int i = 0; i < mList.size(); i++) {
            Bill Bill = mList.get(i);
            mListMain.add(Bill);
            mListMainSup.add(Bill);
        }
    }

    /*private void setDistanceIntoList(){
        if (position != null) {
            for (int i = 0; i < mListMain.size(); i++) {
                final Bill bill = mListMain.get(i);
                //Tính khoảng cách và add vào Order

                final int finalI = i;
                LocationCommon.searchLatLngByAddress(
                        getActivity(),
                        bill.getSourceAddress(),
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
    }

    public void countDistanceIntoList(List<Bill> mList) {
        if (position != null) {
            for (int i = 0; i < mList.size(); i++) {
                final Bill bill = mList.get(i);
                //Tính khoảng cách và add vào Order

                final int finalI = i;
                LocationCommon.searchLatLngByAddress(
                        getActivity(),
                        bill.getSourceAddress(),
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
        *//*for (int i = 0; i < mList.size(); i++) {
            Order Order = new Order(mList.get(i), false);
            //Tính khoảng cách và add vào Order
            if (position != null) {
                float distance = LocationCommon.searchDistanceBetween2Location(
                        position,
                        LocationCommon.searchLatLngByAddress(getActivity(), Order.getOrder().getSourceAddress())
                );
                if (distance != -1f)
                    Order.setDistance(distance);
            }
            mListMain.add(Order);
            mListMainSup.add(Order);
        }*//*
        *//**
         * Lưu 1 bản mListMain để khi sort theo mới nhất sẽ trả về giá trị ban đầu.
         *//*
        *//**
         * Sau khi load list order xong thì thêm vào list main để lưu.
         * Sau đó sẽ gọi hàm setListOrderShow để sort theo Job và đưa lên hiển thị.
         *//*

    }*/

    public void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mListMainSup = new ArrayList<>();
        mListBill = new ArrayList<Bill>();
        mListMain = new ArrayList<Bill>();
        callMyOrderAPI();
        adpater = new RecyclerViewAdapter(getContext(),
                mListBill,
                this,
                Constants.STATUS_COMPLETED);
        recyclerView.setAdapter(adpater);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = recyclerView.getAdapter().getItemCount();
                if (newState == 0) {
//                    int firstVisiblePosition = layoutManager.findFirstVisibleOrderPosition();
                    //position Order cuối đang hiển thị mà bằng tổng số Order lst thì load
                    if (reload)
                        if (linearLayoutManager.findLastVisibleItemPosition() >= count - 2 && !loading) {
                            recyclerView.setClickable(false);
                            loading = true;
                            pageNumber++;
                            callMyOrderAPI();
                        }
                }
            }
        });
    }


    public void setupSpinner() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item, //set layout choose
                ss1
        );
        //set layout for spinner
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        s1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                ss2
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        s2.setAdapter(adapter2);

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mListBill.clear();
                if (i == 0) {
                    mListBill.addAll(mListMain);
                } else {
                    mListBill.addAll(BillCommon.sortOrderByJob(mListMain, i));
                }
                //Khi sort theo công việc thì sẽ bỏ check
                //checkAll(false);
                //Set cbAll thành false
                //cbAll.setChecked(false);
                adpater.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {

                if(i==0){
                    mListMain.clear();
                    mListMain.addAll(mListMainSup);
                    setListOrderShow(s2.getSelectedItemPosition());
                }
                else if(i==1){ //Sắp xếp gần nhất
                    new BaseAsystask(){

                        @Override
                        public void onPre() {
                            lnDialog.setVisibility(View.VISIBLE);
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

    void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!loading)
                    refreshSwipe();
            }
        });
    }

    private void refreshSwipe() {
        if (lnDefault.getVisibility() == View.VISIBLE) {
            MyAnimation.setVisibilityAnimationDown(lnDefault, false);
        }
        loading = true; //Không cho load
        recyclerView.setEnabled(false);
        pageNumber=0;
        callMyOrderAPI();
        cbAll.setChecked(false);
    }

    public void finishRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            mListMain.clear();
            mListMainSup.clear();
            s1.setSelection(0);
            cbAll.setChecked(false);
            lnDefault.setVisibility(View.GONE);
            loading = false;
        }
        recyclerView.setEnabled(true);
    }

    void callMyOrderAPI(){
        /*JSMyOrder data = new JSMyOrder(getContext(),
                Constants.NUMBER_Order_LOAD,
                pageNumber,
                Constants.STATUS_COMPLETED);
        new MyOrderAPI(
                getActivity(),
                CompletedFragment.this,
                new Gson().toJson(data),
                true
        ).execute();*/
    }

    public void updateList(Bill Bill){
        mListMain.add(Bill);
        setListOrderShow(s2.getSelectedItemPosition());
    }

    public void updateListAll(List<Bill> listBill){
        mListMain.addAll(listBill);
        setListOrderShow(s2.getSelectedItemPosition());
    }

    /**
     * Hàm dùng để thay đổi giá trị hiển thị lên màn hình
     */
    public void setListOrderShow(int i) {
        mListBill.clear();
        int sp1 = s1.getSelectedItemPosition();
        if(sp1 == 1){ //sort gần nhất
            Collections.sort(mListMain, new CompareItemByLocation());
        }
        if (i == 0)
            mListBill.addAll(mListMain);
        else
            mListBill.addAll(BillCommon.sortOrderByJob(mListMain, i));
        adpater.notifyDataSetChanged();
        lnDialog.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if(MyOrderFragment.flag==5 || MyOrderFragment.flag == 4){
            *//*mListMain.clear();
            mListMainSup.clear();
            JSMyOrder data = new JSMyOrder(getContext(),Common.NUMBER_Order_LOAD,0,Common.STATUS_COMPLETED);
            new MyOrderAPI(
                    getActivity(),
                    CompletedFragment.this,
                    new Gson().toJson(data),
                    true
            ).execute();*//*
            *//*if (!loading){
                swipeRefreshLayout.setRefreshing(true);
                refreshSwipe();
            }
            MyOrderFragment.flag = -1;*//*
            reloadListOrder();
        }*/
        /*else if(MyOrderFragment.flag == 4){ //Cập nhập đơn hàng
            ///updateOrderFollowDetail();
            //Reload data
            *//*pageNumber = 0;
            mListMain.clear();
            mListMainSup.clear();
            mListOrder.clear();
            callMyOrderAPI();
            MyOrderFragment.flag = -1;*//*
            reloadListOrder();
        }*/
    }

    @Override
    public void reload() {
        if (!loading){
            swipeRefreshLayout.setRefreshing(true);
            refreshSwipe();
        }
    }

    /*public void reloadListOrder(){
        if (!loading){
            swipeRefreshLayout.setRefreshing(true);
            refreshSwipe();
        }
    }*/

    /*public void editStatus(){
        if(SC020102Activity.idOrder > -1){
            Order Order = mListOrder.get(SC020102Activity.idOrder);
            mListMain.remove(Order);
            mListMainSup.remove(Order);
            setListOrderShow(s2.getSelectedOrderPosition());
            MyOrderFragment.flag = 0;
            SC020102Activity.idOrder = -1;
            Order.setCheck(false);
            ((UncompletedFragment)((OrderManagementActivity)getActivity()).getChildFragment(1,1)).updateList(Order);
            ((UncompletedFragment)((OrderManagementActivity)getActivity()).getChildFragment(1,1)).updateBadgeView();
        }
        else {
            Order Order = mListOrder.get(mListOrder.size()-1);
            mListMain.remove(Order);
            mListMainSup.remove(Order);
            setListOrderShow(s2.getSelectedOrderPosition());
            MyOrderFragment.flag = 0;
            Order.setCheck(false);
            ((UncompletedFragment)((OrderManagementActivity)getActivity()).getChildFragment(1,1)).updateList(Order);
            ((UncompletedFragment)((OrderManagementActivity)getActivity()).getChildFragment(1,1)).updateBadgeView();
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        /**
         * Nếu không gọi hàm onDestroy set null thì khi signOut đăng nhập lại
         * thì biến instance sẽ lấy giá trị cũ.
         */
        //instance = null;
    }
}
