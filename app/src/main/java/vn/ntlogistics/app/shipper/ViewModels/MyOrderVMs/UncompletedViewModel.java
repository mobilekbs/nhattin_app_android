package vn.ntlogistics.app.shipper.ViewModels.MyOrderVMs;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.MyAnimation;
import vn.ntlogistics.app.shipper.Commons.Singleton.SJob;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.UpdateOrderStatusAPI;
import vn.ntlogistics.app.shipper.Models.Inputs.JSUpdateStatus;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.Models.Outputs.Others.Item;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;
import vn.ntlogistics.app.shipper.Views.Fragments.MyOrders.CompletedFragment;

/**
 * Created by minhtan2908 on 2/22/17.
 */

public class UncompletedViewModel extends BaseMyOrderViewModel {
    public UncompletedViewModel(Activity activity, BaseFragment fragment, Spinner s1, Spinner s2, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout, int statusFragment) {
        super(activity, fragment, s1, s2, recyclerView, swipeRefreshLayout, statusFragment);
    }

    public void showControls(boolean check, int action) {
        if (action == 1)
            checkAll.set(true);
        else
            checkAll.set(false);
        if (check) {
            if (!checkJobTypeInList()) {
                showButton.set(true);
            } else {
                Message.showToast(activity, activity.getString(R.string.toast_compare_job));
                showButton.set(false);
            }
        } else {
            showButton.set(false);
        }
    }

    @Override
    public void checkAll(boolean check) {
        try {
            if (check) {
                for (int i = 0; i < mListItem.size(); i++)
                    mListItem.get(i).setCheck(true);
                adpater.notifyDataSetChanged();
                if (!checkJobTypeInList()) {
                    showButton.set(true);
                } else {
                    Message.showToast(activity, activity.getString(R.string.toast_compare_job));
                    showButton.set(false);
                }
            } else {
                for (int i = 0; i < mListItem.size(); i++)
                    mListItem.get(i).setCheck(false);
                adpater.notifyDataSetChanged();
                showButton.set(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkJobTypeInList() {
        List<Order> mListOrderChecked = new ArrayList<>();
        //TODO: Get list item checked
        for (int i = 0; i < mListItem.size(); i++) {
            if (mListItem.get(i).isCheck())
                mListOrderChecked.add(mListItem.get(i).getOrder());
        }
        //TODO: Kiểm tra xem trong list đã checked có trùng jobtype không
        int job = mListOrderChecked.get(0).getJobType();
        for (int i = 0; i < mListOrderChecked.size(); i++) {
            if (job != mListOrderChecked.get(i).getJobType()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadSuccessNull() {
        Message.showToast(activity, activity.getString(R.string.toast_update_success));
        removeItemList();
        ((MainActivity)activity).getChildFragment(1, 2).reload();
    }

    private void removeItemList() {
        List<Item> mListItemDeleted = new ArrayList<>();
        for (int i = 0; i < mListItem.size(); i++) {
            if (mListItem.get(i).isCheck()) {
                mListItem.get(i).setCheck(false);
                mListItemDeleted.add(mListItem.get(i));
                mListMain.remove(mListItem.get(i));
                mListMainSup.remove(mListItem.get(i));
            }
        }
        //CompletedFragment.getInstanse().updateListAll(mListItemDeleted);
        ((CompletedFragment) ((MainActivity) activity).getChildFragment(1, 2)).updateListAll(mListItemDeleted);
        setListItemShow(s2.getSelectedItemPosition());
        checkAll.set(false);
        updateBadgeView();
    }

    //TODO: Set Click View ----------------------------------------------------------

    /**
     * Click vào button Cập Nhật Nhanh
     *
     * @param view
     */
    public void onClickUpdateEx(final View view) {
        Commons.setEnabledButton(view);
        final CustomDialog dialog = new CustomDialog(activity);
        dialog.setTextTitle(activity.getString(R.string.update_now));
        dialog.setTitleMessage(activity.getString(R.string.message_dialog_update_now));
        dialog.setShow(true);
        dialog.setTextButton(activity.getString(R.string.yes), activity.getString(R.string.no));
        dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
            @Override
            public void onClickOk() {
                callUpdateStatusOrderAPI();
                MyAnimation.setVisibilityAnimationDown(view, false);
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //TODO: End set Click View -----------------------------------------------------/

    //TODO: Call API ----------------------------------------------------------
    private void callUpdateStatusOrderAPI() {
        JSUpdateStatus data = new JSUpdateStatus(activity);
        data.setData(checkedFormList());
        int job = getJobTypeInList();
        int jobApi = SJob.getIdSuccessByJobType(job);
        data.setStatusId(jobApi + ""); //8 là giao hàng thành công
        data.setShippingCode(mListShippingCode);
        if (job == 8) { //Update ShipK
            //data.setJobType(0);
            //data.setLstOrderKCode(mListOrderKCode);
            //String json = new Gson().toJson(data);
            //new UpdateOrderKStatusAPI(activity, json, null, jobApi, fragment).execute();
        } else {
            data.setJobType(job);
            String json = new Gson().toJson(data);
            new UpdateOrderStatusAPI(activity, json, fragment, jobApi, null).execute();
        }
    }
    //TODO: End call API -----------------------------------------------------/
}
