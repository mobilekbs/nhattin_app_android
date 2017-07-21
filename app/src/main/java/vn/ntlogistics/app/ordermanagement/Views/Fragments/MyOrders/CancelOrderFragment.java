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
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs.CancelOrderVM;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentCancelOrderBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class CancelOrderFragment extends BaseFragment {
    public static final String              TAG = "CancelOrderFragment";

    private FragmentCancelOrderBinding      binding;
    private CancelOrderVM                   viewModel;

    public CancelOrderFragment() {
        // Required empty public constructor
    }

    public static CancelOrderFragment newInstance(){
        return new CancelOrderFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancel_order, container, false);
        viewModel = new CancelOrderVM(this, binding);
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
        try {
            viewModel.reloadOrder();
        } catch (Exception e) {
            Log.d(TAG, "reload");
            e.printStackTrace();
        }
    }
}
