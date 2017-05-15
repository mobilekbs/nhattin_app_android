package vn.ntlogistics.app.shipper.Models.Outputs.User;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

import java.io.Serializable;

/**
 * Created by Zanty on 25/07/2016.
 */
public class MyProfile extends BaseObservable implements Cloneable, Serializable{
    /**
     * Tuyệt chiêu cuối *****
     */
    //private static final long serialVersionUID = 1L;

    private String completedCount;
    private String trust;
    private String unTrust;
    private String avatarPhoto;
    private String fullName;
    private String address;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String vehicleType;
    private String plateNo;
    private String cmndPhoto1;
    private String cmndPhoto2;
    private String platePhoto1;
    private String platePhoto2;
    private String facePhoto1;
    private String facePhoto2;
    private String drivingLicensePhoto1;
    private String drivingLicensePhoto2;
    private String phoneNo;


    public MyProfile() {
    }

    public void setMyProfile(MyProfile pro){
        completedCount = pro.completedCount;
        trust = pro.trust;
        unTrust = pro.unTrust;
        avatarPhoto = pro.avatarPhoto;
        fullName = pro.fullName;
        address = pro.address;
        email = pro.email;
        gender = pro.gender;
        dateOfBirth = pro.dateOfBirth;
        vehicleType = pro.vehicleType;
        plateNo = pro.plateNo;
        cmndPhoto1 = pro.cmndPhoto1;
        cmndPhoto2 = pro.cmndPhoto2;
        platePhoto1 = pro.platePhoto1;
        platePhoto2 = pro.platePhoto2;
        facePhoto1 = pro.facePhoto1;
        facePhoto2 = pro.facePhoto2;
        drivingLicensePhoto1 = pro.drivingLicensePhoto1;
        drivingLicensePhoto2 = pro.drivingLicensePhoto2;
        phoneNo = pro.phoneNo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Bindable
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Bindable
    public String getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(String completedCount) {
        this.completedCount = completedCount;
    }

    @Bindable
    public String getTrust() {
        return trust;
    }

    public void setTrust(String trust) {
        this.trust = trust;
    }

    @Bindable
    public String getUnTrust() {
        return unTrust;
    }

    public void setUnTrust(String unTrust) {
        this.unTrust = unTrust;
    }

    public String getAvatarPhoto() {
        return avatarPhoto;
    }

    public void setAvatarPhoto(String avatarPhoto) {
        this.avatarPhoto = avatarPhoto;
    }

    @Bindable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
        notifyPropertyChanged(BR.fullName);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
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

    @Bindable
    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
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

    public String getPlatePhoto1() {
        return platePhoto1;
    }

    public void setPlatePhoto1(String platePhoto1) {
        this.platePhoto1 = platePhoto1;
    }

    public String getPlatePhoto2() {
        return platePhoto2;
    }

    public void setPlatePhoto2(String platePhoto2) {
        this.platePhoto2 = platePhoto2;
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

    public String getDrivingLicensePhoto1() {
        return drivingLicensePhoto1;
    }

    public void setDrivingLicensePhoto1(String drivingLicensePhoto1) {
        this.drivingLicensePhoto1 = drivingLicensePhoto1;
    }

    public String getDrivingLicensePhoto2() {
        return drivingLicensePhoto2;
    }

    public void setDrivingLicensePhoto2(String drivingLicensePhoto2) {
        this.drivingLicensePhoto2 = drivingLicensePhoto2;
    }
}
