package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 16/08/2017.
 */

public class GetProductivityInput extends BaseInput {

    private String cbPartnerID; //ID Nhân Viên - tương ứng C_BPartner_ID
    private long fromDate; //Tính Từ Ngày , Time in Miliseconds
    private long toDate; //Tính Đến Ngày , Time in Miliseconds
    private int action; // 0 - từ ngày đến ngày | 1 - tính 2 tháng gần nhất

    public GetProductivityInput(Context context) {
        super(context);
    }

    public String getCbPartnerID() {
        return cbPartnerID;
    }

    public void setCbPartnerID(String cbPartnerID) {
        this.cbPartnerID = cbPartnerID;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
