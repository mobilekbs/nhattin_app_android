package vn.ntlogistics.app.ordermanagement.Views.Fragments.MyOrders;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs.BaseMyOrderViewModel;
import vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs.UncompletedViewModel;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentUncompletedBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class UncompletedFragment extends BaseFragment {
    public static final String          TAG = "UncompletedFragment";

    private FragmentUncompletedBinding  binding;
    private UncompletedViewModel        viewModel;
    private View                        view;

    //TODO: Progress Dialog
    View lnDialog;

    public UncompletedFragment() {
        // Required empty public constructor
    }

    public static UncompletedFragment newInstance(){
        return new UncompletedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_uncompleted, container, false);
        viewModel = new UncompletedViewModel(
                getActivity(), this,
                binding.spinner, binding.spinner1, binding.rv, binding.swipeRefresh,
                Constants.STATUS_UNCOMPLETED
        );
        binding.setViewModel(viewModel);
        view = binding.getRoot();
        lnDialog = view.findViewById(R.id.lnDialog);
        return view;
    }

    public BaseMyOrderViewModel getViewModelOrder() {
        return viewModel;
    }

    @Override
    public void showControls(boolean check, int action) {
        viewModel.showControls(check, action);
    }

    @Override
    public void loadSuccess(List<Bill> mList) {

    }

    @Override
    public void reload() {
        try {
            viewModel.reloadOrder();
        } catch (Exception e) {
            Log.d(TAG, "reload");
            e.printStackTrace();
        }
    }
}
