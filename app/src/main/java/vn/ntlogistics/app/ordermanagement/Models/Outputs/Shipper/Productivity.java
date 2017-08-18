package vn.ntlogistics.app.ordermanagement.Models.Outputs.Shipper;

import android.content.Context;

import java.io.Serializable;
import java.util.Calendar;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by Zanty on 16/08/2017.
 */

public class Productivity implements Serializable {
    private String employeeCode;
    private long toDate;
    private long fromDate;
    private int month;
    private int productivity;
    private int quota;

    public Productivity() {
    }

    public Productivity(String employeeCode, long toDate, long fromDate, int month, int productivity, int quota) {
        this.employeeCode = employeeCode;
        this.toDate = toDate;
        this.fromDate = fromDate;
        this.month = month;
        this.productivity = productivity;
        this.quota = quota;
    }

    public String getNameMonth(Context context){
        Calendar calendar = Calendar.getInstance();
        int thisMonth = calendar.get(Calendar.MONTH)+1;
        if(month == 0){
            return null;
        }
        else if(thisMonth == month){
            return context.getString(R.string.this_month);
        }
        else {
            return context.getString(R.string.last_month);
        }
    }

    public String getStringProductivity(){
        return productivity + "";
    }

    public String getStringQuota(){
        return quota + "";
    }

    public String getFromToDate(){
        return Commons.timeStampToDDMM(fromDate) + " - " + Commons.timeStampToDDMM(toDate);
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getProductivity() {
        return productivity;
    }

    public void setProductivity(int productivity) {
        this.productivity = productivity;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }
}
