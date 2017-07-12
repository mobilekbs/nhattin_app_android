package vn.ntlogistics.app.ordermanagement.Commons.Singleton;

import android.content.Context;

import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login.User;


/**
 * Created by Zanty on 22/06/2016.
 */
public class SCurrentUser {
    private static User instance = null;
    public static User getCurrentUser(Context context){
        if(instance == null) {
            instance = SSqlite.getInstance(context).getUser();
        }
        return instance;
    }

    public static boolean checkCurrentUser(Context context){
        User user = SSqlite.getInstance(context).getUser();
        return user.getPublickey() == null ? true : false;
    }

    public static void setCurrentUser(User user){
        instance = user;
    }

    public static void delCurrentUser(){
        instance = null;
    }
}
