package vn.ntlogistics.app.ordermanagement.Commons.Interfaces;

import java.io.Serializable;

/**
 * Created by Zanty on 31/07/2017.
 */

public interface ICallback extends Serializable{
    void onSuccess(Object result);
}
