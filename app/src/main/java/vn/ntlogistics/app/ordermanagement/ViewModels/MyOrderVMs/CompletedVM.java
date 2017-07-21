package vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs;

import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.MyOrders.CompletedFragment;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentCompletedBinding;

/**
 * Created by Zanty on 19/07/2017.
 */

public class CompletedVM extends BaseMyOrderViewModel {
    private CompletedFragment           fragmentMain;
    private FragmentCompletedBinding    binding;


    public CompletedVM(CompletedFragment fragment, FragmentCompletedBinding binding) {
        super(fragment.getActivity(), fragment, binding.spinner, binding.spinner1, binding.rv, binding.swipeRefresh, Constants.STATUS_COMPLETED);
        this.fragmentMain = fragment;
        this.binding = binding;
    }
}
