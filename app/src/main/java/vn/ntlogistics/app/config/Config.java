package vn.ntlogistics.app.config;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by AND on 30-03-2018.
 */

//F119639637
// AnhHN29002
// loipham29000

public class Config {

    public static boolean imageSelctedWhiteBill = false;

    public static boolean onceSendAllPriceFields = false;

//    public static String testingBillNumber = "AnhHN29002";
    public static String testingBillNumber = "F119642069";

    //todo: set debug mode to false always during release!!!
  //  public static final boolean debug_mode = true;
      public static final boolean debug_mode = false;

    public static String fileTimeStamp = "";

    public static ProgressDialog progressDialog;
    public static int billNumber = 1;

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String KEY_GREEN_BILL_NUMBER = "greenBillNumber";
    public static boolean caseQyanLy = false;
    public static boolean caseTraBaoCaoSuCo = false;
    public static String serviceSelectedNhan = "";
    public static String toCitySelectedNhan = "";
    public static String toDistrictSelectedNhan = "";

    public static int servicePos = 0, toCityPos = 0, toDistrictPos = 0;
    public static String valueToDistrict = "";
    public static String valueToCity = "";
    public static String valueService = "";

    public static void initPrefs(Activity activity) {

        sharedPreferences = activity.getSharedPreferences( "prefs", Context.MODE_PRIVATE );
        editor = sharedPreferences.edit();

    }//initPrefs

    public static void setGreenBillNumber(int billNumber) {
        editor.putInt( KEY_GREEN_BILL_NUMBER, billNumber );
        editor.commit();
    }

    public static int getGreenBillNumber() {
        return sharedPreferences.getInt( KEY_GREEN_BILL_NUMBER, 1 );
    }


    public static void setProgressDialog(Activity activity, Context context, String message) {

        progressDialog = new ProgressDialog( context );
        progressDialog.setIndeterminate( true );
        progressDialog.setCancelable( false );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_SPINNER );
        progressDialog.setMessage( message );

    }//setProgressDialog

}//Config
