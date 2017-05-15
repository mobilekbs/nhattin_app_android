package vn.ntlogistics.app.shipper.Models.Inputs;


import java.io.Serializable;

import vn.ntlogistics.app.shipper.Commons.Constants;

/**
 * Created by Zanty on 25/06/2016.
 */
public class JSRegister implements Serializable {
    private String apiKey;
    private String apiSecretKey;
    private String fullName;
    private String phoneNo;
    private String email;
    private String refCode;
    private String vehicleType;
    private String plateNo;
    private String vehiclePhoto1;
    private String vehiclePhoto2;
    private String facePhoto1;
    private String facePhoto2;
    private String cmndPhoto1;
    private String cmndPhoto2;
    private String licensePhoto1;
    private String licensePhoto2;
    private String otp;
    private String avatarPhoto;
    private int appID;

    public JSRegister() {
        appID = Constants.APP_ID;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public String getAvatarPhoto() {
        return avatarPhoto;
    }

    public void setAvatarPhoto(String avatarPhoto) {
        this.avatarPhoto = avatarPhoto;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getVehiclePhoto1() {
        return vehiclePhoto1;
    }

    public void setVehiclePhoto1(String vehiclePhoto1) {
        this.vehiclePhoto1 = vehiclePhoto1;
    }

    public String getVehiclePhoto2() {
        return vehiclePhoto2;
    }

    public void setVehiclePhoto2(String vehiclePhoto2) {
        this.vehiclePhoto2 = vehiclePhoto2;
    }

    public String getFacePhoto1() {
        return facePhoto1;
    }

    public void setFacePhoto1(String facePhoto1) {
        this.facePhoto1 = facePhoto1;
    }

    public String getFacePhoto2() {
        return facePhoto2;
    }

    public void setFacePhoto2(String facePhoto2) {
        this.facePhoto2 = facePhoto2;
    }

    public String getCmndPhoto1() {
        return cmndPhoto1;
    }

    public void setCmndPhoto1(String cmndPhoto1) {
        this.cmndPhoto1 = cmndPhoto1;
    }

    public String getCmndPhoto2() {
        return cmndPhoto2;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCmndPhoto2(String cmndPhoto2) {
        this.cmndPhoto2 = cmndPhoto2;
    }

    public String getLicensePhoto1() {
        return licensePhoto1;
    }

    public void setLicensePhoto1(String licensePhoto1) {
        this.licensePhoto1 = licensePhoto1;
    }

    public String getLicensePhoto2() {
        return licensePhoto2;
    }

    public void setLicensePhoto2(String licensePhoto2) {
        this.licensePhoto2 = licensePhoto2;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }
}
