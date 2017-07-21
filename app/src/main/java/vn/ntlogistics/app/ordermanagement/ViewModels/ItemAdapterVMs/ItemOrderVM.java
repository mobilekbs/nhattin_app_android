package vn.ntlogistics.app.ordermanagement.ViewModels.ItemAdapterVMs;

import android.content.Context;
import android.databinding.Bindable;
import android.view.View;

import vn.ntlogistics.app.ordermanagement.BR;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;

/**
 * Created by Zanty on 21/07/2017.
 */

public class ItemOrderVM extends ViewModel {
    private Context     context;
    private Bill        item;
    private int         position;

    public ItemOrderVM(Context context, Bill item, int position) {
        this.context = context;
        this.item = item;
        this.position = position;
    }

    public void onClickSuccess(View v){
        Commons.setEnabledButton(v);

    }

    public void onClickFailed(View v){
        Commons.setEnabledButton(v);

    }

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
