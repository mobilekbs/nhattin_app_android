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
import vn.ntlogistics.app.ordermanagement.ViewModels.ProductivityStatisticsVMs.DayStatisticsVM;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentDayStatisticsBinding;

public class DayStatisticsFragment extends BaseFragment {
    private static final String             TAG = "DayStatisticsFragment";

    private FragmentDayStatisticsBinding    binding;
    private DayStatisticsVM                 viewModel;

    public DayStatisticsFragment() {
    }

    public static DayStatisticsFragment newInstance(Bundle args) {
        DayStatisticsFragment fragment = new DayStatisticsFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_day_statistics, container, false);
        viewModel = new DayStatisticsVM(this, binding);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Bill> mList) {

    }

    @Override
    public void reload() {
        /**
         * Sau khi chọn tháng muốn thống kê thì sẽ reload lại layout theo tháng đó.
         * Tháng được chọn sẽ được lưu tại ProductivityStatisticsActivity - month
         */
        if(viewModel != null)
            viewModel.createListDay();
    }
}
