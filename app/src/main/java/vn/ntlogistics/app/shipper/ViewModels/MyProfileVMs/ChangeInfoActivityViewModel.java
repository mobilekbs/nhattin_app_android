package vn.ntlogistics.app.shipper.ViewModels.MyProfileVMs;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.CheckEmailAPI;
import vn.ntlogistics.app.shipper.Models.Inputs.JSCheckEmail;
import vn.ntlogistics.app.shipper.Models.Outputs.User.MyProfile;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.shipper.Views.Activities.ChangeInfoActivity;
import vn.ntlogistics.app.shipper.Views.Fragments.Main.MyProfileFragment;
import vn.ntlogistics.app.shipper.databinding.ActivityChangeInfoBinding;

import static android.app.Activity.RESULT_OK;

/**
 * Created by minhtan2908 on 2/17/17.
 */

public class ChangeInfoActivityViewModel extends ViewModel {
    public static final String      TAG = "ChangeInfoActivityViewModel";
    private ChangeInfoActivity activity;
    private MyProfile myProfile;
    private ActivityChangeInfoBinding binding;

    private ObservableField<String> errorName, errorAddress, errorEmail, errorPlateNo;
    private ObservableField<String> textName, textBirthday, textAddress, textEmail, textPlateNo;
    private ObservableBoolean       isMale;
    private ObservableInt           vehicle;

    public ChangeInfoActivityViewModel(ChangeInfoActivity activity, MyProfile myProfile) {
        this.activity = activity;
        this.myProfile = myProfile;
        binding = activity.getBinding();
        textName = new ObservableField<>(myProfile.getFullName());
        textAddress = new ObservableField<>(myProfile.getAddress());
        textEmail = new ObservableField<>(myProfile.getEmail());
        textPlateNo = new ObservableField<>(myProfile.getPlateNo());

        errorName = new ObservableField<>(null);
        errorAddress = new ObservableField<>(null);
        errorEmail = new ObservableField<>(null);
        errorPlateNo = new ObservableField<>(null);

        vehicle = new ObservableInt();

        isMale = new ObservableBoolean();
        if(myProfile.getGender() != null) {
            if (myProfile.getGender().equalsIgnoreCase("0")) {
                isMale.set(true);
            } else
                isMale.set(false);
        }
        else {
            myProfile.setGender("0");
            isMale.set(true);
        }

        try {
            textBirthday = new ObservableField<>();
            textBirthday.set(Commons.timeStampToDate(Long.parseLong(myProfile.getDateOfBirth())));
        } catch (NumberFormatException e) {
            textBirthday.set(myProfile.getDateOfBirth());
        }
    }

    public void getInfoChange() {
        myProfile.setFullName(textName.get());
        myProfile.setAddress(textAddress.get());
        myProfile.setPlateNo(textPlateNo.get());
        myProfile.setEmail(textEmail.get());
        myProfile.setDateOfBirth(textBirthday.get());
        myProfile.setGender(isMale.get()?"0":"1");
        myProfile.setVehicleType((vehicle.get() + 1) + "");

        MyProfileFragment.check = true;
        Intent i = activity.getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("myProfile",myProfile);
        i.putExtras(bundle);
        activity.setResult(RESULT_OK,i);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //TODO: TextChange -----------------------------------
    public void onTextChangeName(CharSequence s, int start, int before, int count) {
        textName.set(s.toString());
    }
    public void onTextChangeAddress(CharSequence s, int start, int before, int count) {
        textAddress.set(s.toString());
    }
    public void onTextChangeEmail(CharSequence s, int start, int before, int count) {
        textEmail.set(s.toString());
    }
    public void onTextChangePlaceNo(CharSequence s, int start, int before, int count) {
        textPlateNo.set(s.toString());
    }
    //TODO: End TextChange ------------------------------/

    //TODO: Set OnClick -----------------------------------------
    public void onClickUpdate(View view){
        if (textName.get().length() < 1) {
            errorName.set(activity.getString(R.string.reg_invalid));
            binding.etName.requestFocus();
            Message.showToast(activity, activity.getString(R.string.toast_register));
        } else if (textPlateNo.get().length() < 1) {
            errorPlateNo.set(activity.getString(R.string.reg_invalid));
            binding.etPlateNo.requestFocus();
            Message.showToast(activity, activity.getString(R.string.toast_register));
        } else if (textEmail.get() != null && textEmail.get().length() > 0 && !Commons.isEmailValid(textEmail.get())) {
            errorEmail.set(activity.getString(R.string.email_invalid));
            binding.etMail.requestFocus();
        } else {
            callCheckEmailAPI(textEmail.get());
        }
    }
    public void onClickBack(View view){
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_CANCELED, returnIntent);
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void onClickBirthday(View view){
        activity.setDate(view);
    }
    //TODO: End set OnClick -------------------------------------/

    @Override
    public void loadSuccess(List<?> mList) {

    }

    private void callCheckEmailAPI(String email){
        if(email == null) {
            checkSuccess(true);
        }
        else {
            if(email.length() == 0){
                checkSuccess(true);
            }
            else if(myProfile.getEmail() == null){
                JSCheckEmail data = new JSCheckEmail(activity,email);
                String json = new Gson().toJson(data);
                new CheckEmailAPI(activity, json, this).execute();
            }
            else if (!myProfile.getEmail().equalsIgnoreCase(email)) {
                JSCheckEmail data = new JSCheckEmail(activity,email);
                String json = new Gson().toJson(data);
                new CheckEmailAPI(activity, json, this).execute();
            }
            else {
                checkSuccess(true);
            }
        }
    }

    public void checkSuccess(boolean b){
        if(b) {
            getInfoChange();
        }
        else {
            errorEmail.set(activity.getString(R.string.check_email_invalid));
        }
    }

    //TODO: Get/Set

    public ObservableField<String> getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName.set(textName);
    }

    public ObservableField<String> getTextBirthday() {
        return textBirthday;
    }

    public void setTextBirthday(String textBirthday) {
        this.textBirthday.set(textBirthday);
    }

    public ObservableField<String> getTextAddress() {
        return textAddress;
    }

    public void setTextAddress(String textAddress) {
        this.textAddress.set(textAddress);
    }

    public ObservableField<String> getTextEmail() {
        return textEmail;
    }

    public void setTextEmail(String textEmail) {
        this.textEmail.set(textEmail);
    }

    public ObservableField<String> getTextPlateNo() {
        return textPlateNo;
    }

    public void setTextPlateNo(String textPlateNo) {
        this.textPlateNo.set(textPlateNo);
    }

    public ObservableField<String> getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName.set(errorName);
    }

    public ObservableField<String> getErrorAddress() {
        return errorAddress;
    }

    public void setErrorAddress(String errorAddress) {
        this.errorAddress.set(errorAddress);
    }

    public ObservableField<String> getErrorEmail() {
        return errorEmail;
    }

    public void setErrorEmail(String errorEmail) {
        this.errorEmail.set(errorEmail);
    }

    public ObservableField<String> getErrorPlateNo() {
        return errorPlateNo;
    }

    public void setErrorPlateNo(String errorPlateNo) {
        this.errorPlateNo.set(errorPlateNo);
    }

    public ObservableBoolean getIsMale() {
        return isMale;
    }

    public void setIsMale(ObservableBoolean isMale) {
        this.isMale = isMale;
    }

    public MyProfile getMyProfile(){
        return myProfile;
    }
}
