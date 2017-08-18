package vn.ntlogistics.app.ordermanagement.Commons;

/**
 * Created by Zanty on 15/05/2017.
 */

public class Constants {
    //TODO: Load my order
    public static final int STATUS_UNCOMPLETED = 27;
    public static final int STATUS_COMPLETED = 28;
    public static final int STATUS_CANCEL = 29;

    //TODO: Request Code
    public static final int REQUEST_CODE_SCAN = 201;
    public static final int REQUEST_CODE_CANCEL_BILL = 202;
    public static final int REQUEST_CODE_SUCCESS_BILL = 203;


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
