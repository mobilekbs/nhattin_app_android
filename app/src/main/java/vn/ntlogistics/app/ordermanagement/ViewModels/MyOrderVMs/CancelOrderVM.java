package vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs;

import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.MyOrders.CancelOrderFragment;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentCancelOrderBinding;

/**
 * Created by Zanty on 19/07/2017.
 */

public class CancelOrderVM extends BaseMyOrderViewModel {
    private CancelOrderFragment             fragmentMain;
    private FragmentCancelOrderBinding      binding;

    public CancelOrderVM(CancelOrderFragment fragment, FragmentCancelOrderBinding binding) {
        super(fragment.getActivity(), fragment, binding.spinner, binding.spinner1, binding.rvCancelDetail, binding.swipeRefresh, Constants.STATUS_COMPLETED);
        this.fragmentMain = fragment;
        this.binding = binding;
    }
}
