package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 02/06/2017.
 */

public class ImportWhiteBillInput extends BaseInput {
    private String bill;
    private String senderCode;
    private String receiverCode;
    private String sender;
    private String receiver;
    private String senderAddress;
    private String receiverAddress;
    private String senderContact;
    private String receiverContact;
    private String senderProvince;
    private String receiverProvince;
    private String senderDistrict;
    private String receiverDistrict;
    private String isDocument;
    private String isPro;
    private String isOther;
    private String description;
    private String thProductCode;
    private String service;
    private String thPaymentCode;
    private long packageNo;
    private double weight;
    private double dimensionWeight;
    private double length;
    private double wide;
    private double high;
    private long itemQty;
    private long codAmt;
    private long postage;
    private long suburbsFee;
    private long insuranceFee;
    private long packingFee;
    private long liftingFee;
    private long deliveryFee;
    private long otherAmt;

    public ImportWhiteBillInput(Context context) {
        super(context);
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getSenderCode() {
        return senderCode;
    }

    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getSenderContact() {
        return senderContact;
    }

    public void setSenderContact(String senderContact) {
        this.senderContact = senderContact;
    }

    public String getReceiverContact() {
        return receiverContact;
    }

    public void setReceiverContact(String receiverContact) {
        this.receiverContact = receiverContact;
    }

    public String getSenderProvince() {
        return senderProvince;
    }

    public void setSenderProvince(String senderProvince) {
        this.senderProvince = senderProvince;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getSenderDistrict() {
        return senderDistrict;
    }

    public void setSenderDistrict(String senderDistrict) {
        this.senderDistrict = senderDistrict;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getIsDocument() {
        return isDocument;
    }

    public void setIsDocument(String isDocument) {
        this.isDocument = isDocument;
    }

    public String getIsPro() {
        return isPro;
    }

    public void setIsPro(String isPro) {
        this.isPro = isPro;
    }

    public String getIsOther() {
        return isOther;
    }

    public void setIsOther(String isOther) {
        this.isOther = isOther;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThProductCode() {
        return thProductCode;
    }

    public void setThProductCode(String thProductCode) {
        this.thProductCode = thProductCode;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getThPaymentCode() {
        return thPaymentCode;
    }

    public void setThPaymentCode(String thPaymentCode) {
        this.thPaymentCode = thPaymentCode;
    }

    public long getPackageNo() {
        return packageNo;
    }

    public void setPackageNo(long packageNo) {
        this.packageNo = packageNo;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDimensionWeight() {
        return dimensionWeight;
    }

    public void setDimensionWeight(double dimensionWeight) {
        this.dimensionWeight = dimensionWeight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWide() {
        return wide;
    }

    public void setWide(double wide) {
        this.wide = wide;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public long getItemQty() {
        return itemQty;
    }

    public void setItemQty(long itemQty) {
        this.itemQty = itemQty;
    }

    public long getCodAmt() {
        return codAmt;
    }

    public void setCodAmt(long codAmt) {
        this.codAmt = codAmt;
    }

    public long getPostage() {
        return postage;
    }

    public void setPostage(long postage) {
        this.postage = postage;
    }

    public long getSuburbsFee() {
        return suburbsFee;
    }

    public void setSuburbsFee(long suburbsFee) {
        this.suburbsFee = suburbsFee;
    }

    public long getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(long insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public long getPackingFee() {
        return packingFee;
    }

    public void setPackingFee(long packingFee) {
        this.packingFee = packingFee;
    }

    public long getLiftingFee() {
        return liftingFee;
    }

    public void setLiftingFee(long liftingFee) {
        this.liftingFee = liftingFee;
    }

    public long getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public long getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(long otherAmt) {
        this.otherAmt = otherAmt;
    }
}
