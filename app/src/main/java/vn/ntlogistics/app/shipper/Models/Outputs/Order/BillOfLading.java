package vn.ntlogistics.app.shipper.Models.Outputs.Order;

/**
 * Created by (TB0) on 27/06/2016.
 */
public class BillOfLading {
    private String vasType;
    private String seqInOrder;
    private String senderName;
    private String senderAddress;
    private String senderPhone;
    private String senderCode;
    private String consigneeName;
    private String consigneeAddress;
    private String consigneePhone;
    private String consigneeCode;

    public BillOfLading() {
    }


    public String getVasType() {
        return vasType;
    }

    public void setVasType(String vasType) {
        this.vasType = vasType;
    }

    public String getSeqInOrder() {
        return seqInOrder;
    }

    public void setSeqInOrder(String seqInOrder) {
        this.seqInOrder = seqInOrder;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderCode() {
        return senderCode;
    }

    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }
}
