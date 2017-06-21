package vn.ntlogistics.app.ordermanagement.Commons;

/**
 * Created by Zanty on 15/05/2017.
 */

public class Constants {
    public static final int APP_ID = 4;
    public static final String OS = "a";

    //TODO: resize maintain aspect ratio
    public static final int RESIZE_WIDTH = 400;
    public static final int RESIZE_HEIGHT = 400;
    //TODO: param MyLocationService
    public static final int TIME_GET_LOCATION = 2000;
    public static final int ALLTIME_GET_LOCATION = 20000;
    //TODO: Google Sign-in
    public static final int SIGN_IN_GG = 110;
    //TODO: Broadcast Receiver real SMS OTP
    //public static final String OUR_PHONE_NUMBER = "";
    //TODO: Load my order
    public static final int NUMBER_ITEM_LOAD = 20;
    //public static final int STATUS_NEW_ORDER = 26;
    public static final int STATUS_UNCOMPLETED = 27;
    public static final int STATUS_COMPLETED = 28;
    public static final int STATUS_CANCEL_ORDER = 10;
    //TODO: Update user status
    public static final int STATUS_WORKING_USER = 30;
    public static final int STATUS_RESTED_USER = 31;

    //TODO: Params ShipK
    //public static final int TYPE_SHIP_K = 3;
    public static final int TYPE_SHIP_CARGO = 0;
    public static final int JOB_SHIP_K = 8;

    //TODO: My Shared Reference
    public static final String SR_MY_PROFILE = "My_Profile";
    public static final String SR_SSTOKEN = "sessionToken";
    public static final String SR_USER_ID = "userID";
    public static final String SR_PHONE = "phoneNo";
    public static final String SR_FULL_NAME = "fullName";
    public static final String SR_PHOTO = "urlPhoto";
    public static final String SR_EMAIL = "email";
    public static final String SR_REF_CODE = "refCode";
    public static final String SR_SHOW_LIST_SHIPK = "List_ShipK";
    public static final String SR_SHOW = "show";
    public static final String SR_FIREBASE = "Firebase";
    public static final String SR_SHORT_LINK = "short_link";

    //TODO: Dynamic link
    public static final String DYNAMIC_LINK = "https://fwvm9.app.goo.gl/?link=https://1shipglobal.com&apn=com.oneshipglobal.app.oneshippro&amv=21&afl=https://play.google.com/store/apps/details?id%3Dcom.oneshipglobal.app.oneshippro%26hl%3Den&isi=1146426350&ibi=com.oneshipglobal.app.oneship-pro&ifl=https://itunes.apple.com/us/app/1ship-pro/id1146426350?ls%3D1%26mt%3D8&utm_campaign=";

    static String URL_WEB = "https://www.1shipglobal.com/";
//    static String URL_WEB = "http://sandbox.1shipglobal.com:8080/";
//    static String URL_WEB = "http://192.168.10.108:8080/";



    //TODO: Gmail sender
    public static final String CC_GMAIL = "team-android@1shipglobal.com,minh.tan2908@gmail.com";
    public static final String USER_GMAIL = "bug.1shipglobal@gmail.com";
    public static final String PASSWORD_GMAIL = "23c6bb9ec5a2977fa604e8194ea2ab05";

    //TODO: init permission
    public static final int PERMISSION_CAMERA = 0;
    public static final int PERMISSION_LOCATION = 1;
    public static final int PERMISSION_PHONE = 2;
    public static final int PERMISSION_WAKE_UP = 3;
    public static final int PERMISSION_STORAGE = 4;
}
