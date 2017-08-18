package vn.ntlogistics.app.ordermanagement.ViewModels.ItemAdapterVMs;

import android.content.Context;
import android.databinding.Bindable;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

import vn.ntlogistics.app.ordermanagement.BR;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ConfirmBPBillInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ConfirmDOActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.UpdateBillActivity;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ItemOrderAdapter;

/**
 * Created by Zanty on 21/07/2017.
 */

public class ItemOrderVM extends ViewModel implements Serializable {
    private Context             context;
    private Bill                item;
    private int                 position;
    private ItemOrderAdapter    adapter;

    public ItemOrderVM(Context context, Bill item, int position, ItemOrderAdapter adapter) {
        this.context = context;
        this.item = item;
        this.position = position;
        this.adapter = adapter;
    }

    public void onClickSuccess(View v){
        Commons.setEnabledButton(v);
        ConfirmBPBillInput data = new ConfirmBPBillInput(context);
        data.setDoCode(item.getBillID());
        Bundle b = new Bundle();
        b.putInt("position", position);
        ConfirmDOActivity.startIntentActivity(context, data,  b, Constants.REQUEST_CODE_SUCCESS_BILL, false);
    }

    public void onClickFailed(View v){
        Commons.setEnabledButton(v);
        Bundle b = new Bundle();
        b.putSerializable("item", item);
        b.putInt("flag", 1);
        b.putInt("position", position);
        UpdateBillActivity.startIntentActivity(
                context, b, Constants.REQUEST_CODE_CANCEL_BILL, false);
    }

    /*//region TODO: Call API_________________________
    private void callCreateBillResponse(String responceStatus){
        CreateBillResponseInput data = new CreateBillResponseInput(
                context,
                item.getBillID(),
                SCurrentUser.getCurrentUser(context).getIdStaff()+"",
                "2",
                responceStatus,
                null);
        new CreateBillResponseAPI(context, data).execute();
    }
    //endregion TODO: Call API______________________End/*/

    @Bindable
    public Bill getItem() {
        return item;
    }

    public void setItem(Bill item, int position) {
        this.item = item;
        this.position = position;
        notifyPropertyChanged(BR.item);
    }
}
