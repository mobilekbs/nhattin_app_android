package vn.ntlogistics.app.shipper.Commons.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by minhtan2908 on 10/10/16.
 */

public class MySharedReference {

    public static class build{
        Context context;
        String name;
        SharedPreferences pre;
        SharedPreferences.Editor crea;
        public build init(Context context, String name){
            this.context = context;
            this.name = name;
            pre= context.getSharedPreferences (
                    name
                    ,MODE_PRIVATE
            );
            crea = pre.edit();
            return this;
        }

        public build putString(String key, String value){
            crea.putString(key,value);
            return this;
        }

        public build putInt(String key, int value){
            crea.putInt(key,value);
            return this;
        }

        public build putBoolean(String key, boolean value){
            crea.putBoolean(key,value);
            return this;
        }

        public build putFloat(String key, float value){
            crea.putFloat(key,value);
            return this;
        }

        public build putLong(String key, long value){
            crea.putLong(key,value);
            return this;
        }

        public build save(){
            crea.commit();
            return this;
        }

        public SharedPreferences get(){
            return pre;
        }
    }
}
