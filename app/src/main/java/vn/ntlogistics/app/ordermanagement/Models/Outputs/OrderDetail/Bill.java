package vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail;

import android.content.Context;

import java.io.Serializable;
import java.util.Observable;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by Zanty on 25/06/2016.
 */
public class Bill extends Observable implements Serializable, Cloneable {

    private String billID;
    private String senderNumberPhone;
    private String senderAddress;
    private String senderName;
    private String senderNode;
    private String receiverNumberPhone;
    private String receiverAddress;
    private String receiverName;
    private String receiverNode;
    private String length;
    private String width;
    private String height;
    private String weight;
    private String cod;
    private String service;
    private String status;

    private String assignDatetime;
    private String amount;
    private int jobType;

    private double shipperAmount; //Tiền shipper nhận được
    private double codAmount; //Tiền COD
    private double advanceCodAmount; //Tiền ứng trước

    /*
   * Tổng khoảng cách
   */
    private long totalDistance;

    private boolean check;

    private String sendDate;
    private String senderProvinceID;
    private String otpCode;
    private String emsBpbillID;

    public Bill() {
    }

    public Bill(String billID, String senderNumberPhone, String senderAddress,
                String senderName, String senderNode, String receiverNumberPhone,
                String receiverAddress, String receiverName, String receiverNode,
                String length, String width, String height, String weight,
                String cod, String ship, String service, String status,
                String sendDate, String senderProvinceID, String otpCode, String emsBpbillID) {
        this.billID = billID;
        this.senderNumberPhone = senderNumberPhone;
        this.senderAddress = senderAddress;
        this.senderName = senderName;
        this.senderNode = senderNode;
        this.receiverNumberPhone = receiverNumberPhone;
        this.receiverAddress = receiverAddress;
        this.receiverName = receiverName;
        this.receiverNode = receiverNode;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        try {
            this.codAmount = Double.parseDouble(cod);
        } catch (NumberFormatException e) {
            this.codAmount = 0;
        }
        try {
            this.shipperAmount = Double.parseDouble(ship);
        } catch (NumberFormatException e) {
            this.shipperAmount = 0;
        }
        this.service = service;
        this.status = status;
        this.sendDate = sendDate;
        this.senderProvinceID = senderProvinceID;
        this.otpCode = otpCode;
        this.emsBpbillID = emsBpbillID;
    }

    public String getShowCodAmount(Context context){
        return Commons.DinhDangChuoiTien(codAmount) + context.getString(R.string.unit);
    }

    public String getShowShippingFee(Context context){
        return Commons.DinhDangChuoiTien(shipperAmount) + context.getString(R.string.unit);
    }

    public String getDate(){
        try {
            return sendDate.split(" ")[0];
        } catch (Exception e) {
            return null;
        }
    }

    public String getTime(){
        try {
            return sendDate.split(" ")[1];
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Bill clone() throws CloneNotSupportedException {
        return (Bill) super.clone();
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getSenderNumberPhone() {
        return senderNumberPhone;
    }

    public void setSenderNumberPhone(String senderNumberPhone) {
        this.senderNumberPhone = senderNumberPhone;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderNode() {
        return senderNode;
    }

    public void setSenderNode(String senderNode) {
        this.senderNode = senderNode;
    }

    public String getReceiverNumberPhone() {
        return receiverNumberPhone;
    }

    public void setReceiverNumberPhone(String receiverNumberPhone) {
        this.receiverNumberPhone = receiverNumberPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverNode() {
        return receiverNode;
    }

    public void setReceiverNode(String receiverNode) {
        this.receiverNode = receiverNode;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignDatetime() {
        return assignDatetime;
    }

    public void setAssignDatetime(String assignDatetime) {
        this.assignDatetime = assignDatetime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public double getShipperAmount() {
        return shipperAmount;
    }

    public void setShipperAmount(double shipperAmount) {
        this.shipperAmount = shipperAmount;
    }

    public double getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(double codAmount) {
        this.codAmount = codAmount;
    }

    public double getAdvanceCodAmount() {
        return advanceCodAmount;
    }

    public void setAdvanceCodAmount(double advanceCodAmount) {
        this.advanceCodAmount = advanceCodAmount;
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(long totalDistance) {
        this.totalDistance = totalDistance;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSenderProvinceID() {
        return senderProvinceID;
    }

    public void setSenderProvinceID(String senderProvinceID) {
        this.senderProvinceID = senderProvinceID;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public String getEmsBpbillID() {
        return emsBpbillID;
    }

    public void setEmsBpbillID(String emsBpbillID) {
        this.emsBpbillID = emsBpbillID;
    }
}
