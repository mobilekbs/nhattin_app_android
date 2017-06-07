package vn.ntlogistics.app.shipper.Views.Fragments.OrderDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomLayoutManager;
import vn.ntlogistics.app.shipper.Commons.Singleton.SJob;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.StatusHistoryAPI;
import vn.ntlogistics.app.shipper.Models.Inputs.JSStatusHistory;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.BLDetail;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.OrderDetail;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.StatusBL;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Adapters.FoldingStatusAdapter;
import vn.ntlogistics.app.shipper.Views.Adapters.StatusAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusOrderFragment extends BaseFragment {

    View view;

    //TODO: Init Controls
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;

    //TODO: Init params
    List<StatusBL> mListStatus;
    OrderDetail detail;
    int jobType = -1;
    List<String> mListBL;
    List<String> mListStatusBL;
    List<List<StatusBL>> mListList;

    //TODO: Adapter
    StatusAdapter adapter;
    FoldingStatusAdapter adapterFolding;

    Order order;


    boolean loadLayout = false;

    int pageNumber = 0;

    public StatusOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_status_order, container, false);

        //init(view);

        return view;
    }

    private void init() {
        //TODO: lấy data OrderDetail từ SOrderDetail
        //detail = SOrderDetail.getOurInstance();
        try {
            detail = (OrderDetail) getActivity().getIntent().getExtras().getSerializable("orderDetail");
        } catch (Exception e) {
        }
        try {
            order = (Order) getActivity().getIntent().getExtras().getSerializable("order");
        } catch (Exception e) {
        }
        //setupSwipeRefresh(view);
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        mListList = new ArrayList<>();
        mListStatusBL = new ArrayList<>();
        try {
            jobType = order.getJobType();
        } catch (Exception e) {
        }

        List<BLDetail> mList = detail.getData();
        mListBL = new ArrayList<>();
        if (mList != null)
            for (int i = 0; i < mList.size(); i++) {
                mListBL.add(mList.get(i).getConsigneeAddress());
            }

        rv = (RecyclerView) view.findViewById(R.id.rv);
        final CustomLayoutManager linearLayoutManager = new CustomLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);
        mListStatus = new ArrayList<>();

        adapterFolding = new FoldingStatusAdapter(
                getContext(),
                mListBL,
                SJob.getJobNameById(getContext(), jobType),
                "",
                mListList,
                rv,
                mListStatusBL
        );
        rv.setAdapter(adapterFolding);

    }


    /**
     * Khởi tạo Swipe Refresh
      * @param view
     *//*
    private void setupSwipeRefresh(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!loading)
                    refreshSwipe();
            }
        });
    }

    private void refreshSwipe() {
        loading = true; //Không cho load
        rv.setEnabled(false);
        pageNumber = 0;
        callStatusHistoryAPI();
    }

    public void finishRefresh() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            mListStatus.clear();
            loading = false;
        }
        rv.setEnabled(true);
    }

    */

    /**
     * Khởi tạo Recycler View
     * @param view
     *//*
    private void setupRecyclerView(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        //rv.setHasFixedSize(true);
        mListStatus = new ArrayList<>();
        callStatusHistoryAPI();
        adapter = new StatusAdapter(getContext(), mListStatus);
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int count = recyclerView.getAdapter().getItemCount();
                if (newState == 0) {
                    //position item cuối đang hiển thị mà bằng tổng số item lst thì load
                    *//**
                     * Nếu không xét size của mListItem thì nó sẽ không nhận
                     * SwpieRefesh vì item nhỏ sẽ ưu tiên chạy onScroll
                     * *//*
                    if (mListStatus.size() > ((Common.NUMBER_ITEM_LOAD-1)*(pageNumber+1)))
                        if (linearLayoutManager.findLastVisibleItemPosition() >= count - 2 && !loading) {
                            recyclerView.setClickable(false);
                            loading = true;
                            pageNumber++;
                            callStatusHistoryAPI();
                        }
                }
            }
        });
    }*/

    public void loadSuccessStatus(List<StatusBL> mList){
        if(mList != null){
            List<StatusBL> mListResponce = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                StatusBL item = mList.get(i);
                mListResponce.add(item);
            }
            mListStatusBL.add(mListResponce.get(0).getStatus());
            mListList.add(mListResponce);
            adapterFolding.notifyDataSetChanged();
        }
    }

    /**
     * Hàm gọi API
     */
    void callStatusHistoryAPI(){

        for (int i = 0; i < detail.getData().size(); i++) {
            JSStatusHistory data = new JSStatusHistory(
                    getContext(),
                    detail.getData().get(i).getBlCode(),
                    Constants.NUMBER_ITEM_LOAD,
                    pageNumber
            );
            String json = new Gson().toJson(data);
            new StatusHistoryAPI(getActivity(),json,this).execute();
        }
    }

    @Override
    public void changeFragment() {
        if(!loadLayout){
            init();
            callStatusHistoryAPI();
            loadLayout = true;
        }
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Order> mList) {

    }
}
