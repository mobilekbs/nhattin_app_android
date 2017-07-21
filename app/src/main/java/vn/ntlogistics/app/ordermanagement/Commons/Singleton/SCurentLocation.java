package vn.ntlogistics.app.ordermanagement.Commons.Singleton;

import android.content.Context;

import vn.ntlogistics.app.ordermanagement.Commons.Location.GetCurrentLocation;

/**
 * Created by Zanty on 19/07/2017.
 */

public class SCurentLocation {
    private static GetCurrentLocation instance = null;

    public static GetCurrentLocation getInstance(Context context){
        if(instance == null)
            instance = new GetCurrentLocation(context);
        return instance;
    }

    public static void deleteInstance(){
        instance = null;
    }
}
