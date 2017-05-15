package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 28/07/2016.
 */
public class JSUpdateMyProfile extends JSBase {
    private String fullName;
    private String phoneNo;
    private String email;
    private String address;
    private String gender;
    private String dateOfBirth;
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
    private String avatarPhoto;

    public JSUpdateMyProfile(Context context) {
        super(context);
    }

    public JSUpdateMyProfile(Context context, String fullName, String phoneNo, String email, String address, String gender, String dateOfBirth, String vehicleType, String plateNo) {
        super(context);
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.vehicleType = vehicleType;
        this.plateNo = plateNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getAvatarPhoto() {
        return avatarPhoto;
    }

    public void setAvatarPhoto(String avatarPhoto) {
        this.avatarPhoto = avatarPhoto;
    }
}
