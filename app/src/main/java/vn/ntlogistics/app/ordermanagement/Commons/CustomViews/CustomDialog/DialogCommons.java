package vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog;

import android.content.Context;

import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.Interfaces.ICallbackAPI;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.UpdateBill.UpdateBillDialog;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;

/**
 * Created by Zanty on 24/07/2017.
 */

public class DialogCommons {
    /**
     * @param flag
     * 1 - Nhận hàng
     *   - Hiện 3 trạng thái 1 2 3 và không có scan
     * 2 - Trả hàng
     *   - Hiện 3 trạng thái 2 3 4 và có hiện scan
     */
    public static void showDialogUpdateStatusBill(Context context,
                                                  int flag,
                                                  Bill item, ICallbackAPI callback){
        UpdateBillDialog dialog = new UpdateBillDialog(context, flag, item, callback);
        dialog.show();
    }
}
