package vn.ntlogistics.app.ordermanagement.ViewModels.Base;

import android.databinding.BaseObservable;

/**
 * Created by minhtan2908 on 1/12/17.
 */

public abstract class ViewModel extends BaseObservable{
    public void loadSuccess(String action, boolean b){}
}
