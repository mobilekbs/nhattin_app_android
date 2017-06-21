package vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.ordermanagement.Commons.Animations.MyAnimation;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;


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
                Commons.showToast(activity, 
                        activity.getString(R.string.toast_compare_job));
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
                for (int i = 0; i < mListOrder.size(); i++)
                    mListOrder.get(i).setCheck(true);
                adpater.notifyDataSetChanged();
                if (!checkJobTypeInList()) {
                    showButton.set(true);
                } else {
                    Commons.showToast(activity,
                            activity.getString(R.string.toast_compare_job));
                    showButton.set(false);
                }
            } else {
                for (int i = 0; i < mListOrder.size(); i++)
                    mListOrder.get(i).setCheck(false);
                adpater.notifyDataSetChanged();
                showButton.set(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkJobTypeInList() {
        List<Bill> mListBillChecked = new ArrayList<>();
        //TODO: Get list item checked
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck())
                mListBillChecked.add(mListOrder.get(i));
        }
        //TODO: Kiểm tra xem trong list đã checked có trùng jobtype không
        int job = mListBillChecked.get(0).getJobType();
        for (int i = 0; i < mListBillChecked.size(); i++) {
            if (job != mListBillChecked.get(i).getJobType()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadSuccessNull() {
        /*Commons.showToast(activity, activity.getString(R.string.toast_update_success));
        removeItemList();
        ((MainActivity)activity).getChildFragment(1, 2).reload();*/
    }

    private void removeItemList() {
        List<Bill> mListBillDeleted = new ArrayList<>();
        for (int i = 0; i < mListOrder.size(); i++) {
            if (mListOrder.get(i).isCheck()) {
                mListOrder.get(i).setCheck(false);
                mListBillDeleted.add(mListOrder.get(i));
                mListMain.remove(mListOrder.get(i));
                mListMainSup.remove(mListOrder.get(i));
            }
        }
        //CompletedFragment.getInstanse().updateListAll(mListOrderDeleted);
        /*((CompletedFragment) ((MainActivity) activity)
                .getChildFragment(1, 2)).updateListAll(mListOrderDeleted);*/
        setListOrderShow(s2.getSelectedItemPosition());
        checkAll.set(false);
        updateBadgeView();
    }

    //TODO: Set Click View ----------------------------------------------------------

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
       /* JSUpdateStatus data = new JSUpdateStatus(activity);
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
        }*/
    }
    //TODO: End call API -----------------------------------------------------/
}
