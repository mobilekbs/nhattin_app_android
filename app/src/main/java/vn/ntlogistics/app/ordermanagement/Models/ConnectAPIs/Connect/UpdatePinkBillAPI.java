package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.content.Context;

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

public class UpdatePinkBillAPI extends BaseConnectAPI {
    private String  bill;
    private boolean isMultiCall;
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

    public UpdatePinkBillAPI(Context context, String data,
                             boolean isMultiCall, String bill) {
        super(context, ConstantURLs.UPDATE_PINK_BILL, data, isMultiCall, Method.POST);
        this.bill = bill;
        this.isMultiCall = isMultiCall;
        if(!isMultiCall){
            initDialogWithTitle(context.getString(R.string.sending),false);
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
    public void onPost(JsonObject result) {
        try {
            Message.makeToastSuccess(context);
            if(isMultiCall) { // Gọi từ BillFail
                if (bill != null) {
                    SqliteManager db = new SqliteManager(context);
                    db.deleteDataFromTable(Variables.TBL_BILLFAIL,
                            Variables.KEY_BILL, bill);
                    ((BaseActivity) context).onSuccess();
                }
            }
            else { //Gọi từ BillDOAcvity
                ((BaseActivity) context).onSuccess();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onFailed(int errorCode, String errorMessage) {
        if(!isMultiCall) { //Gọi từ BillDOAcvity
            super.onFailed(errorCode, errorMessage);
        }
        else { //Gọi từ BillFail
            String[] field = {Variables.KEY_STATUS};
            String[] values = {errorCode + ""};
            SqliteManager db = new SqliteManager(context);
            db.updateData4Table(Variables.TBL_BILLFAIL,
                    Variables.KEY_BILL, bill, field, values);
            ((BaseActivity) context).onSuccess();
        }
    }
}
