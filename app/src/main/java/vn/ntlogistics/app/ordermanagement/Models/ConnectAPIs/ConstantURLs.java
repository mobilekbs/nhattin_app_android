package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs;

/**
 * Created by Zanty on 19/05/2017.
 */

public class ConstantURLs {
    //public static final String URL = "http://ws.ntlogistics.vn:5656/NTAndroidService/webresources/";
    //public static final String URL = "https://1shipglobal.com/nhattin-rest-app-service/";
//    public static final String URL = "https://partner.ntlogistics.vn/nhattin-rest-app-service/";
    public static final String URL = "https://ws.ntlogistics.vn:6443/nhattin-rest-app-service/";

    // LINK
    public final static String CHECK_USER_KEY = URL + "checkUserKey";

    public final static String UPDATE_PINK_BILL = URL + "updatePinkBill";
    public final static String IMPORT_WHITE_BILL = URL + "importWhiteBill";
    public final static String CONFIRM_DO = URL + "confirmBPBill";
    public final static String PUBLIC_PRICE = URL + "calculateTempBillPrice";
    public final static String UPDATE_FCM_TOKEN = URL + "updateFCMToken";

}
