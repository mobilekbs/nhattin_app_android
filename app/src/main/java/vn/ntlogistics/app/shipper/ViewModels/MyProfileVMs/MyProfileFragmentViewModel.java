package vn.ntlogistics.app.shipper.ViewModels.MyProfileVMs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.shipper.Commons.Image.GetPathFromURI;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.RequestCode;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Singleton.SVehicle;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.UpdateMyProfileAPI;
import vn.ntlogistics.app.shipper.Models.Inputs.JSUpdateMyProfile;
import vn.ntlogistics.app.shipper.Models.Outputs.Others.Vehicle;
import vn.ntlogistics.app.shipper.Models.Outputs.User.MyProfile;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.shipper.Views.Activities.ChangeInfoActivity;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;
import vn.ntlogistics.app.shipper.Views.Fragments.Main.MyProfileFragment;

import static vn.ntlogistics.app.shipper.Views.Fragments.Main.MyProfileFragment.check;


/**
 * Created by minhtan2908 on 2/17/17.
 */

public class MyProfileFragmentViewModel extends ViewModel {
    public static final String TAG = "MyProfileFragmentViewModel";

    private MyProfileFragment       fragment;
    private Activity                activity;

    String                          pathImage = "";
    GetPathFromURI                  getPathFromURI;
    /**
     * Lưu 1 bản cũ myProfile lại để khi hoàn tác trả lại giá trị cho người dùng
     * myProfile sẽ được sử dụng cho bên edit thông tin nên sử dụng static,
     * để khỏi chuyền biến khi chuyển activity.
     */
    private MyProfile myProfileMain;
    private MyProfile myProfile;
    /**
     * Biến để nhận biết được là edit image avatar
     * để làm hình tròn.
     */
    //int iAvatar = 0;

    private ObservableField<String> textName, textAge, textSex, textCompleted;
    private ObservableField<String> textTrust, textUntrust;
    private ObservableField<String> textAddress, textPhone, textEmail;
    private ObservableField<String> textVehicle, textPlateNo;

    private ObservableField<String> urlAvatar;
    private ObservableField<String> urlImage11, urlImage12, urlImage21, urlImage22;
    private ObservableField<String> urlImage31, urlImage32, urlImage41, urlImage42;

    public MyProfileFragmentViewModel(MyProfileFragment fragment) {
        this.fragment = fragment;
        this.activity = fragment.getActivity();

        textName = new ObservableField<>();
        textAge = new ObservableField<>();
        textSex = new ObservableField<>();
        textCompleted = new ObservableField<>();
        textTrust = new ObservableField<>();
        textUntrust = new ObservableField<>();
        textAddress = new ObservableField<>();
        textPhone = new ObservableField<>();
        textEmail = new ObservableField<>();
        textVehicle = new ObservableField<>();
        textPlateNo = new ObservableField<>();

        urlAvatar = new ObservableField<>();
        urlImage11 = new ObservableField<>();
        urlImage12 = new ObservableField<>();
        urlImage21 = new ObservableField<>();
        urlImage22 = new ObservableField<>();
        urlImage31 = new ObservableField<>();
        urlImage32 = new ObservableField<>();
        urlImage41 = new ObservableField<>();
        urlImage42 = new ObservableField<>();

        myProfileMain = new MyProfile();
    }

    public void callUpdateMyProfileAPI() {
        String birthday = null;
        try {
            birthday = Commons.convertDataToMillisecond(myProfile.getDateOfBirth()) + "";
        }
        catch (Exception e){

        }
        JSUpdateMyProfile data = new JSUpdateMyProfile(
                activity,
                myProfile.getFullName(),
                myProfile.getPhoneNo(),
                myProfile.getEmail(),
                myProfile.getAddress(),
                myProfile.getGender(),
                birthday,
                myProfile.getVehicleType(),
                myProfile.getPlateNo()
        );
        data.setCmndPhoto1(compareChangeInfo(myProfile.getCmndPhoto1(), myProfileMain.getCmndPhoto1()));
        data.setCmndPhoto2(compareChangeInfo(myProfile.getCmndPhoto2(), myProfileMain.getCmndPhoto2()));
        data.setFacePhoto1(compareChangeInfo(myProfile.getFacePhoto1(), myProfileMain.getFacePhoto1()));
        data.setFacePhoto2(compareChangeInfo(myProfile.getFacePhoto2(), myProfileMain.getFacePhoto2()));
        data.setLicensePhoto1(compareChangeInfo(myProfile.getDrivingLicensePhoto1(), myProfileMain.getDrivingLicensePhoto1()));
        data.setLicensePhoto2(compareChangeInfo(myProfile.getDrivingLicensePhoto2(), myProfileMain.getDrivingLicensePhoto2()));
        data.setVehiclePhoto1(compareChangeInfo(myProfile.getPlatePhoto1(), myProfileMain.getPlatePhoto1()));
        data.setVehiclePhoto2(compareChangeInfo(myProfile.getPlatePhoto2(), myProfileMain.getPlatePhoto2()));
        data.setAvatarPhoto(compareChangeInfo(myProfile.getAvatarPhoto(), myProfileMain.getAvatarPhoto()));
        String json = new Gson().toJson(data);
        new UpdateMyProfileAPI(activity, json, this).execute();
    }
    /**
     * Hàm kiểm tra xem có sự thay đổi trong myProfileMain vs myProfile không.
     *
     * @param s1 giá trị đã thay đổi - myProfile
     * @param s2 giá trị chưa thay đổi - myProfileMain
     * @return null nếu không thay đổi
     */
    private String compareChangeInfo(String s1, String s2) {
        String s = null;

        try {
            if (!s1.equals(s2)) {
                s = Commons.convertPathToBase64WithResize(s1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    /*public void checkChangeMyProfile() {
        if (check) {
            final CustomDialog dialog = new CustomDialog(activity);
            dialog.setTitleMessage(activity.getString(R.string.dialog_check_change));
            dialog.setShow(true);
            dialog.setTextTitle(activity.getString(R.string.note_dialog));
            dialog.setTextButton(activity.getString(R.string.yes), activity.getString(R.string.no));
            dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                @Override
                public void onClickOk() {
                    callUpdateMyProfileAPI();
                    check = false;
                    dialog.dismiss();
                }

                @Override
                public void onClickCancel() {
                    check = false;
                    try {
                        myProfile = (MyProfile) myProfileMain.clone();
                        setMyProfileIntoLayout();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }*/

    private void setMyProfileIntoLayout(boolean reloadImage) {
        setTextIntoTextView(textCompleted, myProfile.getCompletedCount());
        setTextIntoTextView(textTrust, myProfile.getTrust());
        setTextIntoTextView(textUntrust, myProfile.getUnTrust());
        setTextIntoTextView(textName, myProfile.getFullName());
        setTextIntoTextView(textEmail, myProfile.getEmail());
        setTextIntoTextView(textAddress, myProfile.getAddress());
        setTextIntoTextView(textPhone, myProfile.getPhoneNo());
        setTextIntoTextView(textPlateNo, myProfile.getPlateNo());
        //setTextIntoTextView(textAge, myProfile.getDateOfBirth());
        setTextIntoTextView(textVehicle, getNameVehivle(myProfile.getVehicleType()));

        /** Chuyển chuỗi millisecond thành ngày tháng năm.
         *  Sau đó lấy năm hiện tại trừ để lấy tuổi.
         */
        String dateOfBirth = "";
        String date = "";
        int numyears = 0;
        try {
            date = Commons.timeStampToDate(Long.parseLong(myProfile.getDateOfBirth()));
        }
        catch (Exception e){
            date = myProfile.getDateOfBirth();
        }
        try {
            int year = Integer.parseInt(date.split(Pattern.quote("/"))[2]);
            int yearCurrent = Calendar.getInstance().get(Calendar.YEAR);
            numyears = yearCurrent - year;
        } catch (Exception e) {
            e.printStackTrace();
        }
        dateOfBirth = numyears+ " " + activity.getString(R.string.age);
        setTextIntoTextView(textAge, dateOfBirth);

        String sex = null;
        if (myProfile.getGender() != null) {
            int s = Integer.parseInt(myProfile.getGender().toString());
            if (s == 0)
                sex = activity.getString(R.string.male);
            if (s == 1)
                sex = activity.getString(R.string.female);
        }
        setTextIntoTextView(textSex, sex);
        if(reloadImage)
            setupImage();
    }

    public void changeInfoUser(MyProfile myProfile){
        this.myProfile = myProfile;
        setMyProfileIntoLayout(false);
    }

    //TODO: Gọi từ MainActivityViewModel ---------------
    public void onClickCancelDialog(){
        check = false;
        try {
            myProfile = (MyProfile) myProfileMain.clone();
            setMyProfileIntoLayout(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickOkDialog(){
        callUpdateMyProfileAPI();
        check = false;
    }
    //TODO: End gọi từ MainActivityViewModel -----------/

    private String getNameVehivle(String id) {
        String nameVehicle = null;
        if (id != null) {
            int idV = Integer.parseInt(id);
            List<Vehicle> mListVehicle = SVehicle.getListVehicle();
            for (int i = 0; i < mListVehicle.size(); i++) {
                if (idV == Integer.parseInt(mListVehicle.get(i).getId()))
                    nameVehicle = mListVehicle.get(i).getName();
            }
        }
        return nameVehicle;
    }

    public void setTextIntoTextView(ObservableField<String> tv, String text) {
        if (text != null)
            if (text.length() > 0)
                tv.set(text);
            else
                tv.set("");
        else
            tv.set("");
    }

    private void setupImage(){
        urlImage11.set(myProfile.getPlatePhoto1());
        urlImage12.set(myProfile.getPlatePhoto2());
        urlImage21.set(myProfile.getFacePhoto1());
        urlImage22.set(myProfile.getFacePhoto2());
        urlImage31.set(myProfile.getDrivingLicensePhoto1());
        urlImage32.set(myProfile.getDrivingLicensePhoto2());
        urlImage41.set(myProfile.getCmndPhoto1());
        urlImage42.set(myProfile.getCmndPhoto2());
        urlAvatar.set(myProfile.getAvatarPhoto());
    }

    public void loadMyProfile(final MyProfile myProfile) {
        if (myProfile != null) {
            this.myProfile = myProfile;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    myProfile.setPhoneNo(SCurrentUser.getCurrentUser(activity).getPhoneNo());
                    try {
                        myProfileMain = (MyProfile) myProfile.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }

                    ((MainActivity)activity).setHeaderMenu(myProfile);

                    /**
                     * Gán dữ liệu lấy được vào cho layout
                     */
                    setMyProfileIntoLayout(true);
                }
            });
        } else {
        }
    }

    /**
     * Gọi từ getResultFragment của MyProfileFragment
     * Sau khi nhận hình ảnh từ Camera hoặc Thư viện sẽ lấy url của hình ảnh
     * set vào myProfile và thay đổi hình trên layout
     * @param requestCode
     * @param data
     */
    public void handleBeforeChooseImage(int requestCode, Intent data) {
        String url = null;
        try {
            Uri uri;
            if (data == null)
                uri = Uri.fromFile(new File(pathImage));
            else
                uri = data.getData();
            getUrlImage(uri, requestCode);
            /*CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(activity);*/
        } catch (Exception e) {
        }
    }

    /**
     * Nhận uri hình ảnh, sau đó lấy url thật của hình lưu trong mấy.
     * Sau đó quay hình lại(nếu hình bị quay) xong chuyển thành bitmap và url
     *  + Bitmap vào func addImageIntoObject convert String Base64 add vào myProfile.
     *  + Url sẽ set vào ObservableField<String> để hiển thị lên layout.
     * @param uri
     * @param requestCode
     */
    public void getUrlImage(Uri uri, int requestCode) {
        String url = null;
        Bitmap bitmap = null;
        try {
            Bitmap c = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
            try {
                getPathFromURI = new GetPathFromURI(activity, uri);
                url = getPathFromURI.getRealPathFromURI();
            } catch (Exception e) {
                url = uri.getPath();
            }
            bitmap = Commons.rotateImagePath(url, c);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        addImageIntoObject(url, bitmap, requestCode);
        check = true;
    }

    /**
     * Thay đổi ảnh đã đổi vào thông tin user.
     * Chuyển bitmap thành String Base64 và set vào myProfile
     * @param url
     * @param bitmap
     */
    public void addImageIntoObject(String url, Bitmap bitmap, int requestCode) {
        String s = null;
        try {
            if(bitmap != null){
                s = Commons.convertBitmapToBase64WithResize(bitmap);
            }
            else {
                s = url;
            }
        } catch (Exception e) {
            s = url;
        }
        switch (requestCode) {
            case RequestCode.IMAGE_11:
                myProfile.setPlatePhoto1(s);
                urlImage11.set(url);
                //fragment.getBinding().img11.setImageBitmap(bitmap);
                break;
            case RequestCode.IMAGE_12:
                myProfile.setPlatePhoto2(s);
                urlImage12.set(url);
                break;
            case RequestCode.IMAGE_21:
                myProfile.setFacePhoto1(s);
                urlImage21.set(url);
                break;
            case RequestCode.IMAGE_22:
                myProfile.setFacePhoto2(s);
                urlImage22.set(url);
                break;
            case RequestCode.IMAGE_31:
                myProfile.setDrivingLicensePhoto1(s);
                urlImage31.set(url);
                break;
            case RequestCode.IMAGE_32:
                myProfile.setDrivingLicensePhoto2(s);
                urlImage32.set(url);
                break;
            case RequestCode.IMAGE_41:
                myProfile.setCmndPhoto1(s);
                urlImage41.set(url);
                break;
            case RequestCode.IMAGE_42:
                myProfile.setCmndPhoto2(s);
                urlImage42.set(url);
                break;
            case RequestCode.IMAGE_AVATAR:
                myProfile.setAvatarPhoto(s);
                urlAvatar.set(url);
                break;
        }
    }

    //TODO: Set OnClick Button ------------------------
    public void onClickImage(View view){
        Commons.setEnabledButton(view);
        Intent choose = Commons.chooseImage(activity);
        pathImage = choose.getExtras().get("pathImage").toString();
        switch (view.getId()) {
            case R.id.img11:
                activity.startActivityForResult(choose, RequestCode.IMAGE_11);
                break;
            case R.id.img12:
                activity.startActivityForResult(choose, RequestCode.IMAGE_12);
                break;
            case R.id.img21:
                activity.startActivityForResult(choose, RequestCode.IMAGE_21);
                break;
            case R.id.img22:
                activity.startActivityForResult(choose, RequestCode.IMAGE_22);
                break;
            case R.id.img31:
                activity.startActivityForResult(choose, RequestCode.IMAGE_31);
                break;
            case R.id.img32:
                activity.startActivityForResult(choose, RequestCode.IMAGE_32);
                break;
            case R.id.img41:
                activity.startActivityForResult(choose, RequestCode.IMAGE_41);
                break;
            case R.id.img42:
                activity.startActivityForResult(choose, RequestCode.IMAGE_42);
                break;
            case R.id.ivAvatar:
                activity.startActivityForResult(choose, RequestCode.IMAGE_AVATAR);
                break;
        }
    }

    public void onClickUpdate(View view){
        if (MyProfileFragment.check) {
            final CustomDialog dialog = new CustomDialog(activity);
            dialog.setTitleMessage(activity.getResources().getString(R.string.dialog_update_my_profile));
            dialog.setShow(true);
            dialog.setTextTitle(activity.getResources().getString(R.string.note_dialog));
            dialog.setTextButton(activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
            dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                @Override
                public void onClickOk() {
                    callUpdateMyProfileAPI();
                    check = false;
                    dialog.dismiss();
                }

                @Override
                public void onClickCancel() {
                    check = false;
                    try {
                        myProfile = (MyProfile) myProfileMain.clone();
                        setMyProfileIntoLayout(true);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else
            Message.showToast(activity, activity.getResources().getString(R.string.toast_not_change));
    }
    public void onClickChangeInfo(View view){
        Commons.setEnabledButton(view);
        Intent i = new Intent(activity, ChangeInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("myProfile", myProfile);
        i.putExtras(bundle);
        activity.startActivityForResult(i, RequestCode.CHANGE_INFO);
    }
    //TODO: End set Onclick Button -------------------/

    //TODO: Set avatar -----------------------------
    public ObservableField<String> getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(ObservableField<String> urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
    @BindingAdapter({"setImage"})
    public static void setImage(final ImageView view, final String url){
        Commons.setEnabledButton(view);
        if (url != null && url.length() > 0){
            final Context context = view.getContext();
                /*Picasso.with(context)
                        .load(url)
                        .memoryPolicy(MemoryPolicy.NO_CACHE )
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(view, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                File imgFile = new  File(url);
                                //Uri uri = Uri.fromFile(imgFile);
                                Glide.with(context)
                                        .load(imgFile)
                                        .asBitmap()
                                        .into(view);
                            }
                        });*/
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.mipmap.ic_add_image_gray)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            view.setImageBitmap(resource);
                        }
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            Glide.with(context)
                                    .load(new  File(url))
                                    .asBitmap()
                                    .into(view);
                        }
                    });
        }
        else {
            Glide.with(view.getContext())
                    .load(R.mipmap.ic_add_image_gray)
                    .into(view);
        }
    }

    @BindingAdapter({"setImageAvatar"})
    public static void setImageAvatar(ImageView view, String url) {
        /*Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.ic_avatar_blue)
                .error(R.drawable.ic_avatar_blue)
                .into(view);*/
        Glide.with(view.getContext())
                .load(url)
                .asBitmap()
                .centerCrop()
                .error(R.mipmap.ic_avatar_blue)
                .placeholder(R.mipmap.ic_avatar_blue)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(view.getContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });
        /*Glide.with(view.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .transform(new BitmapBorderTransformation(5, 1000, Color.WHITE))
                .fit().centerCrop()
                .into(ivAvatar, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        File imgFile = new  File(myProfile.getAvatarPhoto());
                        Uri uri = Uri.fromFile(imgFile);
                        Picasso.with(getContext())
                                .load(uri)
                                .memoryPolicy(MemoryPolicy.NO_CACHE )
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .transform(new BitmapBorderTransformation(5, 1000, Color.WHITE))
                                .fit().centerCrop()
                                .into(ivAvatar);
                    }
                });*/
    }

    //TODO: End set avatar ---------------------------/

    public ObservableField<String> getTextName() {
        return textName;
    }

    public void setTextName(ObservableField<String> textName) {
        this.textName = textName;
    }

    public ObservableField<String> getTextAge() {
        return textAge;
    }

    public void setTextAge(ObservableField<String> textAge) {
        this.textAge = textAge;
    }

    public ObservableField<String> getTextSex() {
        return textSex;
    }

    public void setTextSex(ObservableField<String> textSex) {
        this.textSex = textSex;
    }

    public ObservableField<String> getTextCompleted() {
        return textCompleted;
    }

    public void setTextCompleted(ObservableField<String> textCompleted) {
        this.textCompleted = textCompleted;
    }

    public ObservableField<String> getTextTrust() {
        return textTrust;
    }

    public void setTextTrust(ObservableField<String> textTrust) {
        this.textTrust = textTrust;
    }

    public ObservableField<String> getTextUntrust() {
        return textUntrust;
    }

    public void setTextUntrust(ObservableField<String> textUntrust) {
        this.textUntrust = textUntrust;
    }

    public ObservableField<String> getTextAddress() {
        return textAddress;
    }

    public void setTextAddress(ObservableField<String> textAddress) {
        this.textAddress = textAddress;
    }

    public ObservableField<String> getTextPhone() {
        return textPhone;
    }

    public void setTextPhone(ObservableField<String> textPhone) {
        this.textPhone = textPhone;
    }

    public ObservableField<String> getTextEmail() {
        return textEmail;
    }

    public void setTextEmail(ObservableField<String> textEmail) {
        this.textEmail = textEmail;
    }

    public ObservableField<String> getTextVehicle() {
        return textVehicle;
    }

    public void setTextVehicle(ObservableField<String> textVehicle) {
        this.textVehicle = textVehicle;
    }

    public ObservableField<String> getTextPlateNo() {
        return textPlateNo;
    }

    public void setTextPlateNo(ObservableField<String> textPlateNo) {
        this.textPlateNo = textPlateNo;
    }

    public ObservableField<String> getUrlImage11() {
        return urlImage11;
    }

    public void setUrlImage11(ObservableField<String> urlImage11) {
        this.urlImage11 = urlImage11;
    }

    public ObservableField<String> getUrlImage12() {
        return urlImage12;
    }

    public void setUrlImage12(ObservableField<String> urlImage12) {
        this.urlImage12 = urlImage12;
    }

    public ObservableField<String> getUrlImage21() {
        return urlImage21;
    }

    public void setUrlImage21(ObservableField<String> urlImage21) {
        this.urlImage21 = urlImage21;
    }

    public ObservableField<String> getUrlImage22() {
        return urlImage22;
    }

    public void setUrlImage22(ObservableField<String> urlImage22) {
        this.urlImage22 = urlImage22;
    }

    public ObservableField<String> getUrlImage31() {
        return urlImage31;
    }

    public void setUrlImage31(ObservableField<String> urlImage31) {
        this.urlImage31 = urlImage31;
    }

    public ObservableField<String> getUrlImage32() {
        return urlImage32;
    }

    public void setUrlImage32(ObservableField<String> urlImage32) {
        this.urlImage32 = urlImage32;
    }

    public ObservableField<String> getUrlImage41() {
        return urlImage41;
    }

    public void setUrlImage41(ObservableField<String> urlImage41) {
        this.urlImage41 = urlImage41;
    }

    public ObservableField<String> getUrlImage42() {
        return urlImage42;
    }

    public void setUrlImage42(ObservableField<String> urlImage42) {
        this.urlImage42 = urlImage42;
    }

    public MyProfile getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(MyProfile myProfile) {
        this.myProfile = myProfile;
    }

    public void destroy() {
        check = false;
    }

    @Override
    public void loadSuccess(List<?> mList) {
        if (mList == null) {
            try {
                myProfileMain = (MyProfile) myProfile.clone();
                ((MainActivity)activity).setHeaderMenu(myProfileMain);
                check = false;
                Message.showToast(activity,activity.getString(R.string.update_myprofile_success));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
