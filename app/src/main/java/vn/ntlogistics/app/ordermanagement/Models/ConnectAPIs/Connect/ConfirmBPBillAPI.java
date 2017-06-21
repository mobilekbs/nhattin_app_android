package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by Zanty on 02/06/2017.
 */

public class ConfirmBPBillAPI extends BaseConnectAPI {
    private String              bill;
    private BaseActivity        activity;
    private boolean             isMultiCall;

    /**
     * @refresh
     *
     * true
     * - Gọi từ BillFail
     * - Không cho hiện dialog
     * - Xóa bill khi thành công và update khi thất bại
     *
     * false
     * - Gọi từ BillDOActivity
     * - Cho hiện dialog vì call 1 bill mới
     * - Không có trong sqlite nên không làm gì thêm
     *
     */

    public ConfirmBPBillAPI(BaseActivity activity, String data,
                            String bill, boolean isMultiCall) {
        super(activity, ConstantURLs.CONFIRM_DO, data, isMultiCall, Method.POST);
        this.activity = activity;
        this.bill = bill;
        this.isMultiCall = isMultiCall;
        try {
            if(!isMultiCall)
                initDialogWithTitle(context.getString(R.string.sending),false);
        } catch (Exception e) {
        }
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void doInBG() {

    }

    @Override
    public void onPost(JsonObject result) { //Result data
        try {
            Message.makeToastSuccess(context);
            if(isMultiCall) { // Gọi từ BillFail
                if (bill != null) {
                    SqliteManager db = new SqliteManager(context);
                    db.deleteDataFromTable(Variables.TBL_BILLFAIL,
                            Variables.KEY_BILL, bill);
                }
            }
            else { //Gọi từ BillDOAcvity
                activity.onSuccess();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onFailed(int errorCode, String errorMessage) {
        if(!isMultiCall) { //Gọi từ BillDOAcvity
            super.onFailed(errorCode, errorMessage);
        }
        else { // Gọi từ BillFail
            String[] field = {Variables.KEY_STATUS};
            String[] values = {errorCode + ""};
            SqliteManager db = new SqliteManager(context);
            db.updateData4Table(Variables.TBL_BILLFAIL,
                    Variables.KEY_BILL, bill, field, values);
        }
    }
}
