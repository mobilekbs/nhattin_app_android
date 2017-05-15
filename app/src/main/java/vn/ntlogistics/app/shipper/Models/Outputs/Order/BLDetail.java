package vn.ntlogistics.app.shipper.Models.Outputs.Order;

import java.io.Serializable;

/**
 * Created by Zanty on 28/07/2016.
 */
public class BLDetail implements Serializable {
    private String blCode;
    private String shippingCode;

    /**
     * Thông tin người gửi
     */
    private String senderID;
    private String senderCode;
    private String senderName;
    private String senderAddress;
    private String senderPhone;
    private String senderTrust;
    private String senderUntrust;
    private String senderTrusted;
    private String senderLat;
    private String senderLng;

    /**
     * Thông tin người nhận
     */
    private String consigneeID;
    private String consigneeCode;
    private String consigneeName;
    private String consigneeAddress;
    private String consigneePhone;
    private String consigneeTrust;
    private String consigneeUntrust;
    private String consigneeTrusted;
    private String consigneeLat;
    private String consigneeLng;

    /**
     * Loại hàng hóa-os_cargo_type
     */
    private String cargoTitle; //Loại hàng hóa
    private String cargoValue; //Giá trị hàng hóa
    private String cargoContent; //Nội dung hàng hóa
    private String grossWeight; //Tổng khối lượng
    private String netWeight;
    private String length;
    private String width;
    private String height;
    private String quantity; //Số lượng
    /**
     * Loại dịch vụ vận chuyển-os_categories
     */
    private String categoryTitle;
    /**
      * Thông công ty vận chuyển
      * NULL nếu người vận chuyển là shipper tự do
      */
    private String companyName;
    private String companyAddress;
    private String companyId;
    /**
     * Tiền thu người gửi
     */
    private double senderShippingFee; //Cước vận chuyển
    private double senderCodFee; //Phí thu hộ
    private double senderVasFee; //Dịch vụ cộng thêm
    private double senderExtraFee; //Chi phí khác
    private double senderCodAmount; //Tổng tiền thu hộ
    private double senderVatAmount; //VAT

    /**
     * Ín sùa rắn kè người gửi
     */
    private double senderInsuranceFee;
    private double senderLoadingFee;

    /**
     * Tiền thu người nhận
     */
    private double consigneeShippingFee; //Cước vận chuyển
    private double consigneeCodFee; //Phí thu hộ
    private double consigneeVasFee; //Dịch vụ cộng thêm
    private double consigneeExtraFee; //Chi phí khác
    private double consigneeCodAmount; //Tổng tiền thu hộ
    private double consigneeVatAmount; //VAT

    /**
     * Ín sùa rắn kè người nhận
     */
    private double consigneeInsuranceFee; //Bao hiem
    private double consigneeLoadingFee; //Boc xep

    /**
     * Mã xác thực người vận chuyển
     */
    private String deliveryCode;

    /**
     * Ngày giao dự kiến
     */
    private String etaDays;

    /**
     * Giờ giao dự kiến
     */
    private String etaHours;

    /**
     * Status ID - trạng thái của công việc khi đã update.
     * 0 - là chưa update
     */
    private int statusID;
    private String note;

    /**
     * Thông tin người phụ trách
     */
    private String orderPicName;
    private String orderPicPhone;

    private boolean show;
    private boolean first;

    private boolean clickTrust = true;

    public BLDetail() {
    }

    public String getOrderPicName() {
        return orderPicName;
    }

    public void setOrderPicName(String orderPicName) {
        this.orderPicName = orderPicName;
    }

    public String getOrderPicPhone() {
        return orderPicPhone;
    }

    public void setOrderPicPhone(String orderPicPhone) {
        this.orderPicPhone = orderPicPhone;
    }

    public boolean checkInsurance(){
        if(senderInsuranceFee > 0 ||
                consigneeInsuranceFee > 0)
            return true;
        else
            return false;
    }

    public boolean checkPorter(){
        if(senderLoadingFee > 0 ||
                consigneeLoadingFee > 0)
            return true;
        else
            return false;
    }

    public boolean checkCOD(){
        if(senderCodFee > 0 ||
                consigneeCodFee > 0)
            return true;
        else
            return false;
    }

    public boolean isClickTrust() {
        return clickTrust;
    }

    public void setClickTrust(boolean clickTrust) {
        this.clickTrust = clickTrust;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getEtaDateTime(){
        String s = "";
        if(etaHours != null )
            s = etaHours;
        else if(etaDays != null)
            s = etaDays;
        else if(etaHours != null  && etaDays != null)
            s = etaHours + " " + etaDays;
        return s;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getEtaDays() {
        return etaDays;
    }

    public void setEtaDays(String etaDays) {
        this.etaDays = etaDays;
    }

    public String getEtaHours() {
        return etaHours;
    }

    public void setEtaHours(String etaHours) {
        this.etaHours = etaHours;
    }

    public double getTotalFeeReceiver(){
        double total = 0;
        try {
            total =consigneeShippingFee +
                    consigneeCodFee +
                    consigneeVasFee +
                    consigneeExtraFee +
                    consigneeCodAmount +
                    consigneeVatAmount;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return total;
    }

    public double getTotalFeeSend(){
        double total = 0;
        try {
            total = senderShippingFee +
                    senderCodFee +
                    senderVasFee +
                    senderExtraFee +
                    senderCodAmount +
                    senderVatAmount;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSenderLat() {
        return senderLat;
    }

    public void setSenderLat(String senderLat) {
        this.senderLat = senderLat;
    }

    public String getSenderLng() {
        return senderLng;
    }

    public void setSenderLng(String senderLng) {
        this.senderLng = senderLng;
    }

    public String getConsigneeLat() {
        return consigneeLat;
    }

    public void setConsigneeLat(String consigneeLat) {
        this.consigneeLat = consigneeLat;
    }

    public String getConsigneeLng() {
        return consigneeLng;
    }

    public void setConsigneeLng(String consigneeLng) {
        this.consigneeLng = consigneeLng;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSenderTrusted() {
        return senderTrusted;
    }

    public String getConsigneeTrusted() {
        return consigneeTrusted;
    }

    public void setConsigneeTrusted(String consigneeTrusted) {
        this.consigneeTrusted = consigneeTrusted;
    }

    public void setSenderTrusted(String senderTrusted) {
        this.senderTrusted = senderTrusted;
    }

    public String getBlCode() {
        return blCode;
    }

    public void setBlCode(String blCode) {
        this.blCode = blCode;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderCode() {
        return senderCode;
    }

    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
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

    public String getSenderTrust() {
        return senderTrust;
    }

    public void setSenderTrust(String senderTrust) {
        this.senderTrust = senderTrust;
    }

    public String getSenderUntrust() {
        return senderUntrust;
    }

    public void setSenderUntrust(String senderUntrust) {
        this.senderUntrust = senderUntrust;
    }

    public String getConsigneeID() {
        return consigneeID;
    }

    public void setConsigneeID(String consigneeID) {
        this.consigneeID = consigneeID;
    }

    public String getConsigneeCode() {
        return consigneeCode;
    }

    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
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

    public String getConsigneeTrust() {
        return consigneeTrust;
    }

    public void setConsigneeTrust(String consigneeTrust) {
        this.consigneeTrust = consigneeTrust;
    }

    public String getConsigneeUntrust() {
        return consigneeUntrust;
    }

    public void setConsigneeUntrust(String consigneeUntrust) {
        this.consigneeUntrust = consigneeUntrust;
    }

    public String getCargoTitle() {
        return cargoTitle;
    }

    public void setCargoTitle(String cargoTitle) {
        this.cargoTitle = cargoTitle;
    }

    public String getCargoValue() {
        return cargoValue;
    }

    public void setCargoValue(String cargoValue) {
        this.cargoValue = cargoValue;
    }

    public String getCargoContent() {
        return cargoContent;
    }

    public void setCargoContent(String cargoContent) {
        this.cargoContent = cargoContent;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public double getSenderShippingFee() {
        return senderShippingFee;
    }

    public void setSenderShippingFee(double senderShippingFee) {
        this.senderShippingFee = senderShippingFee;
    }

    public double getSenderCodFee() {
        return senderCodFee;
    }

    public void setSenderCodFee(double senderCodFee) {
        this.senderCodFee = senderCodFee;
    }

    public double getSenderVasFee() {
        return senderVasFee;
    }

    public void setSenderVasFee(double senderVasFee) {
        this.senderVasFee = senderVasFee;
    }

    public double getSenderExtraFee() {
        return senderExtraFee;
    }

    public void setSenderExtraFee(double senderExtraFee) {
        this.senderExtraFee = senderExtraFee;
    }

    public double getSenderCodAmount() {
        return senderCodAmount;
    }

    public void setSenderCodAmount(double senderCodAmount) {
        this.senderCodAmount = senderCodAmount;
    }

    public double getSenderVatAmount() {
        return senderVatAmount;
    }

    public void setSenderVatAmount(double senderVatAmount) {
        this.senderVatAmount = senderVatAmount;
    }

    public double getSenderInsuranceFee() {
        return senderInsuranceFee;
    }

    public void setSenderInsuranceFee(double senderInsuranceFee) {
        this.senderInsuranceFee = senderInsuranceFee;
    }

    public double getSenderLoadingFee() {
        return senderLoadingFee;
    }

    public void setSenderLoadingFee(double senderLoadingFee) {
        this.senderLoadingFee = senderLoadingFee;
    }

    public double getConsigneeShippingFee() {
        return consigneeShippingFee;
    }

    public void setConsigneeShippingFee(double consigneeShippingFee) {
        this.consigneeShippingFee = consigneeShippingFee;
    }

    public double getConsigneeCodFee() {
        return consigneeCodFee;
    }

    public void setConsigneeCodFee(double consigneeCodFee) {
        this.consigneeCodFee = consigneeCodFee;
    }

    public double getConsigneeVasFee() {
        return consigneeVasFee;
    }

    public void setConsigneeVasFee(double consigneeVasFee) {
        this.consigneeVasFee = consigneeVasFee;
    }

    public double getConsigneeExtraFee() {
        return consigneeExtraFee;
    }

    public void setConsigneeExtraFee(double consigneeExtraFee) {
        this.consigneeExtraFee = consigneeExtraFee;
    }

    public double getConsigneeCodAmount() {
        return consigneeCodAmount;
    }

    public void setConsigneeCodAmount(double consigneeCodAmount) {
        this.consigneeCodAmount = consigneeCodAmount;
    }

    public double getConsigneeVatAmount() {
        return consigneeVatAmount;
    }

    public void setConsigneeVatAmount(double consigneeVatAmount) {
        this.consigneeVatAmount = consigneeVatAmount;
    }

    public double getConsigneeInsuranceFee() {
        return consigneeInsuranceFee;
    }

    public void setConsigneeInsuranceFee(double consigneeInsuranceFee) {
        this.consigneeInsuranceFee = consigneeInsuranceFee;
    }

    public double getConsigneeLoadingFee() {
        return consigneeLoadingFee;
    }

    public void setConsigneeLoadingFee(double consigneeLoadingFee) {
        this.consigneeLoadingFee = consigneeLoadingFee;
    }
}
