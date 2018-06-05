package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;
import android.util.Log;

/**
 * Created by Zanty on 25/07/2017.
 */

public class CreateBillResponseInput extends BaseInput {
    private String emsBpbillID;
    private String cBpartnerID;
    private String doStatus;
    private String respondStatus;
    private String note;

    /**
     Bảng doStatus
     * "01"	hàng bị pending , vẫn đang chờ đi lấy
     * "02"	từ chối đi lấy đơn hàng

     Bảng respondStatus
     * "01"	Sai Địa Chỉ
     * "02"	Khách Không Có Ở Địa Chỉ Gửi
     * "03"	Khách Hủy Đơn Hàng
     * "04"	Điều Độ Post Hủy Đơn Hàng
     * "05"	Lý Do Khác

     * @param context
     */

    public CreateBillResponseInput(Context context) {
        super(context);
    }

    public CreateBillResponseInput(Context context, String emsBpbillID, String cBpartnerID, String doStatus, String respondStatus, String note) {
        super(context);

        Log.e("TAG","-------------------- emsBpbillID "  + emsBpbillID);
        Log.e("TAG","-------------------- cBpartnerID "  + cBpartnerID);
        Log.e("TAG","-------------------- doStatus "  + doStatus);
        Log.e("TAG","-------------------- respondStatus "  + respondStatus);
        Log.e("TAG","-------------------- note "  + note);
        

        this.emsBpbillID = emsBpbillID;
        this.cBpartnerID = cBpartnerID;
        this.doStatus = doStatus;
        this.respondStatus = respondStatus;
        this.note = note;
    }

    public String getEmsBpbillID() {
        return emsBpbillID;
    }

    public void setEmsBpbillID(String emsBpbillID) {
        this.emsBpbillID = emsBpbillID;
    }

    public String getcBpartnerID() {
        return cBpartnerID;
    }

    public void setcBpartnerID(String cBpartnerID) {
        this.cBpartnerID = cBpartnerID;
    }

    public String getDoStatus() {
        return doStatus;
    }

    public void setDoStatus(String doStatus) {
        this.doStatus = doStatus;
    }

    public String getRespondStatus() {
        return respondStatus;
    }

    public void setRespondStatus(String respondStatus) {
        this.respondStatus = respondStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
