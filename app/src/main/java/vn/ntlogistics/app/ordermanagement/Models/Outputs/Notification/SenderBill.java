package vn.ntlogistics.app.ordermanagement.Models.Outputs.Notification;

import java.io.Serializable;

/**
 * Created by Zanty on 12/06/2017.
 */

public class SenderBill implements Serializable{

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
    private int    status;

    public SenderBill() {
    }

    public SenderBill(String billID, String senderNumberPhone, String senderAddress, String senderName, String senderNode, String receiverNumberPhone, String receiverAddress, String receiverName, String receiverNode, String length, String width, String height, String weight, String cod, String service, int status) {
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
        this.cod = cod;
        this.service = service;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
