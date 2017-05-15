package vn.ntlogistics.app.shipper.ViewModels.Base;

import android.os.Bundle;

import java.util.List;

/**
 * Created by minhtan2908 on 1/12/17.
 */

public abstract class ViewModel {
    public abstract void loadSuccess(List<?> mList);
    public void showControls(boolean check, int action){}
    public void loadSuccessSup(List<?> mList){}
    public void loadFailed(){}
    public void changeFragment(){}
    public void handleNotification(int action, Bundle b){}
}
