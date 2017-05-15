package vn.ntlogistics.app.shipper.Commons.AbstractClass;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.ViewModels.MyOrderVMs.BaseMyOrderViewModel;

/**
 * Created by Zanty on 16/06/2016.
 */
public abstract class BaseFragment extends Fragment {
    public abstract void showControls(boolean check, int action);
    public abstract void loadSuccess(List<Order> mList);
    public void loadSuccessSup(List<?> mList){}
    public void loadFailed(){}
    public void changeFragment(){}
    public void handleNotification(int action, Bundle b){}
    public void reload(){}
    public void loadSuccess(Object object){}

    /**
     * Gọi ở UpdateOrderKStatusAPI
     * Sử dụng để update lại list order khi update status order
     * @return
     */
    public BaseMyOrderViewModel getViewModelOrder(){
        return null;
    }
}
