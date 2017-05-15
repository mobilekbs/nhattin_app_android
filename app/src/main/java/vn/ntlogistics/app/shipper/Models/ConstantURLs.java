package vn.ntlogistics.app.shipper.Models;

/**
 * Created by Zanty on 15/05/2017.
 */

public class ConstantURLs {
    public static final String URL = "https://www.1shipglobal.com/";
    public static final String URL_SERVICE = URL + "rest-app-service/";

    public static final String URL_GET_API_KEY = URL_SERVICE +"getApiKey";
    public static final String URL_SIGN_IN = URL_SERVICE +"appSignin";
    public static final String URL_SIGNOUT = URL_SERVICE + "signout";
    public static final String URL_REGISTER = URL_SERVICE + "appRegister";
    public static final String URL_MY_ORDER = URL_SERVICE +"myOrder";
    public static final String URL_ORDER_DETAIL = URL_SERVICE +"orderDetail";
    public static final String URL_CHECK_PHONE_NO = URL_SERVICE +"checkPhoneNo";
    public static final String URL_SEND_OTP = URL_SERVICE +"sendOTP";
    public static final String URL_UPDATE_ORDER_STATUS = URL_SERVICE +"updateOrderStatus";
    public static final String URL_ACCEPT_ORDER = URL_SERVICE +"acceptOrder";
    public static final String URL_DENY_ORDER = URL_SERVICE +"denyOrders";
    public static final String URL_REFUSE_ORDER = URL_SERVICE +"refuseOrder";
    public static final String URL_REMOVE_FROM_LIST = URL_SERVICE +"removeFromList";
    public static final String URL_MY_PROFILE = URL_SERVICE +"getMyProfile";
    public static final String URL_UPDATE_MY_PROFILE = URL_SERVICE +"updateMyProfile";
    public static final String URL_RATING = URL_SERVICE +"rating";
    public static final String URL_STATUS_HISTORY = URL_SERVICE +"getStatusHistory";
    public static final String URL_GET_STATUS_OF_BILL = URL_SERVICE +"getStatusOfBill";
    public static final String URL_DELETE_PHOTO = URL_SERVICE +"deletePhoto";
    public static final String URL_MY_WALLET_INFO = URL_SERVICE +"getMyWalletInfo";
    public static final String URL_UPDATE_USER_STATUS = URL_SERVICE +"updateUserStatus";
    public static final String URL_UPDATE_FCM_TOKEN = URL_SERVICE +"updateFCMToken";
    public static final String URL_CHECK_EMAIL = URL_SERVICE +"checkEmail";


    //TODO: ShipK
    public static final String URL_SHIPK_ORDER_DETAIL = URL_SERVICE + "getShipKOrderDetail";
    public static final String URL_SHIPK_UPDATE_ORDER_STATUS = URL_SERVICE + "updateOrderKStatus";

    //TODO: Link web

    public static final String URL_WEB_TERM = URL + "web-frontend/policy";
    public static final String URL_WEB_ABOUT_US = URL + "web-frontend/about";
}
