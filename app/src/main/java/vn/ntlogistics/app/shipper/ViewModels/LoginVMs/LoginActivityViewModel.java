package vn.ntlogistics.app.shipper.ViewModels.LoginVMs;

import android.content.Context;

import java.util.List;

import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;

/**
 * Created by minhtan2908 on 1/12/17.
 */

public class LoginActivityViewModel extends ViewModel {
    private static final String TAG = "LoginActivityViewModel";

    private Context context;

    public LoginActivityViewModel(Context context) {
        this.context = context;
    }



    @Override
    public void loadSuccess(List<?> mList) {

    }
}
