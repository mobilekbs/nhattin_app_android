package vn.ntlogistics.app.ordermanagement.Commons.Singleton;

import android.content.Context;

import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.SQLiteManager;

/**
 * Created by Zanty on 04/07/2017.
 */

public class SSQLite {
    private static SQLiteManager instance = null;

    public static SQLiteManager getInstance(Context context){
        if(instance == null) {
            instance = new SQLiteManager(context);
        }

        if (instance.getDb() == null)
            instance.open();

        return instance;
    }

    public static void deleteInstance(){
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }
}
