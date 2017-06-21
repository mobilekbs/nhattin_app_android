package vn.ntlogistics.app.ordermanagement.Commons.Singleton;

import android.content.Context;

import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login.User;


/**
 * Created by Zanty on 22/06/2016.
 */
public class SCurrentUser {
    private static User instance = null;
    public static User getCurrentUser(Context context){
        if(instance == null) {
            SqliteManager db = new SqliteManager(context);
            instance = db.getUser();
        }
        return instance;
    }

    public static boolean checkCurrentUser(Context context){
        SqliteManager db = new SqliteManager(context);
        User user = db.getUser();
        return user.getPublickey() == null ? true : false;
    }

    public static void setCurrentUser(User user){
        instance = user;
    }

    public static void delCurrentUser(){
        instance = null;
    }
}
