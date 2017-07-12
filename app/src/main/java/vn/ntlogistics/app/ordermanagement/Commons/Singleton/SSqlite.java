package vn.ntlogistics.app.ordermanagement.Commons.Singleton;

import android.content.Context;

import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.SqliteManager;

/**
 * Created by Zanty on 04/07/2017.
 */

public class SSqlite {
    private static SqliteManager instance= null;

    public static SqliteManager getInstance(Context context){
        if(instance == null)
            instance = new SqliteManager(context);
        return instance;
    }

    public static void deleteInstance(){
        instance = null;
    }
}
