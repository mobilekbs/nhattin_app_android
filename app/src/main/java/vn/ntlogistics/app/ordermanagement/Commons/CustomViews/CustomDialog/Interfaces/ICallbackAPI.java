package vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.Interfaces;

import java.io.Serializable;

/**
 * Created by Zanty on 31/07/2017.
 */

public interface ICallbackAPI extends Serializable {
    void onSuccess();
    void onFailed();
}
