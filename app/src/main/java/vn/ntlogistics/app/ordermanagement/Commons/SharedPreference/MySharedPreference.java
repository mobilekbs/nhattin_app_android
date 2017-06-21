package vn.ntlogistics.app.ordermanagement.Commons.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Zanty on 12/06/2017.
 */

public class MySharedPreference {
    public static final String                  MY_APP = "nhattin_sharedpreference";
    public static final String                  FCM_TOKEN = "fcm_token";

    private static SharedPreferences            instanceGet = null;
    private static BaseSharedReference.build    instanceSet = null;

    private static void setInstanceGet(Context context){
        if(instanceGet == null){
            instanceGet = new BaseSharedReference
                    .build()
                    .init(context, MY_APP)
                    .get();
        }
    }
    private static void setInstanceSet(Context context){
        if(instanceSet == null){
            instanceSet = new BaseSharedReference
                    .build()
                    .init(context, MY_APP);
        }
    }

    /**
     * Get FCM Token from Shared Preference
     * Is called when login user or after onTokenRefresh() in MyFirebaseInstanceIDService
     * @param context
     * @return
     */
    public static String getFCMToken(Context context){
        //Init instance get
        setInstanceGet(context);
        //Get value into shared preference
        return instanceGet.getString(FCM_TOKEN, null);
    }

    /**
     * Set/Save FCM Token from Shared Preference.
     * Is called when onTokenRefresh() in MyFirebaseInstanceIDService
     * @param context
     * @param token
     */
    public static void setFCMToken(Context context, String token){
        //Init instance get
        setInstanceSet(context);
        //Set value into shared preference
        instanceSet.putString(FCM_TOKEN, token).save();
    }
}
