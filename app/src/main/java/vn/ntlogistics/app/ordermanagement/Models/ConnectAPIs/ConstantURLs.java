package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs;

/**
 * Created by Zanty on 19/05/2017.
 */

public class ConstantURLs {
    public static final String URL = "https://ws.ntlogistics.vn:6443/nhattin-rest-app-service/";

    public final static String CHECK_USER_KEY = URL + "checkUserKey";

    public final static String UPDATE_PINK_BILL = URL + "updatePinkBill";
    public final static String IMPORT_WHITE_BILL = URL + "importWhiteBill";
    public final static String CONFIRM_DO = URL + "confirmBPBill";
    public final static String PUBLIC_PRICE = URL + "calculateTempBillPrice";
    public final static String UPDATE_FCM_TOKEN = URL + "updateFCMToken";
    public final static String CREATE_BILL_RESPONSE = URL + "createBillResponse";
    public final static String CHECK_TH_BILL = URL + "checkThBill";
    public final static String CONFIRM_IB_BILL = URL + "confirmIBBill";
    public final static String GET_PRODUCTIVITY = URL + "getProductivity";
    public final static String CHECK_VERSION = URL + "checkNewestVersion";

    //TODO: Google API
    public static final String DISTANCE_MATRIX = "https://maps.googleapis.com/maps/api/distancematrix/json?mode=driving";
    public static final String ORIGINS = "&origins=";
    public static final String DESTINATIONS = "&destinations=";
    public static final String KEYS = "&key=";

    //Ghi log lỗi
    public static final String URL_WEB = "https://ws.ntlogistics.vn:6443/nhattin_rest_web_service/";
    public static final String REPORT_ERROR_WEB = URL_WEB + "reportRealtimeError";


}
