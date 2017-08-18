package vn.ntlogistics.app.ordermanagement.ViewModels.ProductivityStatisticsVMs;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.GetProductivityAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.GetProductivityInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Shipper.Productivity;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ProductivityStatisticsActivity;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ItemWeekStatisticsAdapter;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.ProductivityStatistics.MonthStatisticsFragment;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentMonthStatisticsBinding;

/**
 * Created by Zanty on 16/08/2017.
 */

public class MonthStatisticsVM extends ViewModel {
    private FragmentMonthStatisticsBinding          binding;
    private MonthStatisticsFragment                 fragment;
    private Context                                 context;

    //TODO: Recycler View
    private ArrayList<Productivity>                 mList;
    private ItemWeekStatisticsAdapter               adapter;

    public MonthStatisticsVM(MonthStatisticsFragment fragment, FragmentMonthStatisticsBinding binding) {
        this.binding = binding;
        this.fragment = fragment;
        this.context = fragment.getContext();
        setupRecyclerView();
        callGetProductivityAPI();
    }

    private void setupRecyclerView(){
        mList = new ArrayList<>();
        adapter = new ItemWeekStatisticsAdapter(context, mList, this);

        binding.rvWeekStatistics.setLayoutManager(new LinearLayoutManager(context));
        binding.rvWeekStatistics.setAdapter(adapter);
    }

    //region TODO: Call API __________________________________
    private void callGetProductivityAPI(){
        GetProductivityInput data = new GetProductivityInput(context);
        data.setAction(1);
        new GetProductivityAPI(context, data, this).execute();
    }

    @Override
    public void onSuccess(Object result) {
        if(result != null){
            ArrayList<Productivity> lst = (ArrayList<Productivity>) result;
            mList.addAll(lst);
            adapter.notifyDataSetChanged();
        }
        binding.pbLoadingItemMonth.setVisibility(View.GONE);
    }

    @Override
    public void onFailed() {
        binding.pbLoadingItemMonth.setVisibility(View.GONE);
    }

    //endregion TODO: Call API _______________________________End/

    @Override
    public void onSuccess(int action) {
        //Change fragment, action is page position
        try {
            /**
             * Khi click vào item month thì sẽ chuyển qua thống kê theo ngày.
             * Gọi hàm changeFragment để chuyển fragment và reload lại fragment day
             */
            ((ProductivityStatisticsActivity) fragment.getActivity())
                    .setCurrentProductivity(mList.get(action));
            ((ProductivityStatisticsActivity) fragment.getActivity()).changeFragment(1);
        } catch (Exception e) {
        }
    }
}
