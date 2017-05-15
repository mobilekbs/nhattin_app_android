package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

import java.util.List;

/**
 * Created by Zanty on 21/07/2016.
 */
public class JSUpdateStatus extends JSBase{
    private String statusId;
    private String comment;
    private String note;
    private int jobType; // 0 de update ShipK
    private List<String> photo;
    private List<String> shippingCode;
    private List<String> data; //List BL
    private List<String> lstOrderKCode;

    public JSUpdateStatus(Context context) {
        super(context);
    }

    public List<String> getLstOrderKCode() {
        return lstOrderKCode;
    }

    public void setLstOrderKCode(List<String> lstOrderKCode) {
        this.lstOrderKCode = lstOrderKCode;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }


    public List<String> getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(List<String> shippingCode) {
        this.shippingCode = shippingCode;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
