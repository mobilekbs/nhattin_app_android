package vn.ntlogistics.app.ordermanagement.Views.Fragments.ProductivityStatistics;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.ProductivityStatisticsVMs.MonthStatisticsVM;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentMonthStatisticsBinding;

public class MonthStatisticsFragment extends BaseFragment {

    private FragmentMonthStatisticsBinding      binding;
    private MonthStatisticsVM                   viewModel;

    public MonthStatisticsFragment() {
        // Required empty public constructor
    }

    public static MonthStatisticsFragment newInstance(Bundle args) {
        MonthStatisticsFragment fragment = new MonthStatisticsFragment();
        if(args != null)
            fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_month_statistics, container, false);
        viewModel = new MonthStatisticsVM(this, binding);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Bill> mList) {

    }
}
