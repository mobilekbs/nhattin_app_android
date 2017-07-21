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
import vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs.CompletedVM;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentCompletedBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedFragment extends BaseFragment {
    public static final String          TAG = "CompletedFragment";

    private FragmentCompletedBinding    binding;
    private CompletedVM                 viewModel;

    public CompletedFragment() {
        // Required empty public constructor
    }

    public static CompletedFragment newInstance(){
        return new CompletedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_completed, container, false);
        viewModel = new CompletedVM(this, binding);
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
