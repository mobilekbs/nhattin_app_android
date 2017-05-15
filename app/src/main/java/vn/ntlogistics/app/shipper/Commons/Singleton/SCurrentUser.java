package vn.ntlogistics.app.shipper.Commons.Singleton;

import android.content.Context;
import android.content.SharedPreferences;

import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.SharedPreference.MySharedReference;
import vn.ntlogistics.app.shipper.Models.Outputs.Login.CurrentUser;


/**
 * Created by Zanty on 22/06/2016.
 */
public class SCurrentUser {

    /*private static CurrentUser currentUser = new CurrentUser();

    public static CurrentUser getCurrentUser(){
        if(currentUser == null)
            currentUser = new CurrentUser();
        return currentUser;
    }

    public static String getSessionToken() {
        return currentUser.getSessionToken();
    }

    public static void setSessionToken(String sessionToken) {
        if(currentUser == null)
            currentUser = new CurrentUser();
        currentUser.setSessionToken(sessionToken);
    }

    public static void delCurrentUser(){
        currentUser = null;
    }*/

    public static CurrentUser getCurrentUser(Context context){
        SharedPreferences mShared = new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .get();
        CurrentUser currentUser = new CurrentUser();
        currentUser.setFullName(mShared.getString(Constants.SR_FULL_NAME,null));
        currentUser.setUserID(mShared.getString(Constants.SR_USER_ID, null));
        currentUser.setPhoneNo(mShared.getString(Constants.SR_PHONE, null));
        currentUser.setEmail(mShared.getString(Constants.SR_EMAIL, null));
        currentUser.setRefCode(mShared.getString(Constants.SR_REF_CODE, null));
        currentUser.setUrlPhoto(mShared.getString(Constants.SR_PHOTO, null));
        currentUser.setSessionToken(mShared.getString(Constants.SR_SSTOKEN, null));

        return currentUser;
    }

    public static void setSessionToken(Context context,String s){
        new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .putString(Constants.SR_SSTOKEN, s)
                .save();
    }

    public static void setUrlPhoto(Context context,String s){
        new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .putString(Constants.SR_PHOTO, s)
                .save();
    }

    public static void setRefCode(Context context,String s){
        new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .putString(Constants.SR_REF_CODE, s)
                .save();
    }

    public static void setFullName(Context context,String s){
        new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .putString(Constants.SR_FULL_NAME, s)
                .save();
    }

    public static void setUserID(Context context,String s){
        new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .putString(Constants.SR_USER_ID, s)
                .save();
    }

    public static void setPhoneNo(Context context,String s){
        new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .putString(Constants.SR_PHONE, s)
                .save();
    }

    public static void setEmail(Context context,String s){
        new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .putString(Constants.SR_EMAIL, s)
                .save();
    }

    public static void setCurrentUser(Context context, CurrentUser user){
        if(user.getFullName() != null) {
            new MySharedReference
                    .build()
                    .init(context, Constants.SR_MY_PROFILE)
                    .putString(Constants.SR_FULL_NAME, user.getFullName())
                    .putString(Constants.SR_USER_ID, user.getUserID())
                    .putString(Constants.SR_PHONE, user.getPhoneNo())
                    .putString(Constants.SR_EMAIL, user.getEmail())
                    .putString(Constants.SR_REF_CODE, user.getRefCode())
                    .putString(Constants.SR_PHOTO, user.getUrlPhoto())
                    .putString(Constants.SR_SSTOKEN, user.getSessionToken())
                    .save();
        }
        else {
            new MySharedReference
                    .build()
                    .init(context, Constants.SR_MY_PROFILE)
                    .putString(Constants.SR_USER_ID, user.getUserID())
                    .putString(Constants.SR_PHONE, user.getPhoneNo())
                    .putString(Constants.SR_SSTOKEN, user.getSessionToken())
                    .save();
        }
    }

    public static String getSessionToken(Context context){
        SharedPreferences mShared = new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .get();
        return mShared.getString(Constants.SR_SSTOKEN,null);
    }

    public static void delCurrentUser(Context context){
        new MySharedReference
                .build()
                .init(context, Constants.SR_MY_PROFILE)
                .putString(Constants.SR_FULL_NAME,null)
                .putString(Constants.SR_USER_ID,null)
                .putString(Constants.SR_PHONE,null)
                .putString(Constants.SR_EMAIL,null)
                .putString(Constants.SR_REF_CODE,null)
                .putString(Constants.SR_PHOTO,null)
                .putString(Constants.SR_SSTOKEN,null)
                .save();
    }
}
