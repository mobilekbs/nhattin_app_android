package vn.ntlogistics.app.shipper.Commons.Singleton;


import java.lang.ref.WeakReference;

import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;

/**
 * Created by minhtan2908 on 10/20/16.
 */

public class SMainActivity {
    private static WeakReference<MainActivity> instance;

    public static WeakReference<MainActivity> getInstance(){
        return instance;
    }

    public static void updateActivity(MainActivity activity) {
        instance = new WeakReference<MainActivity>(activity);
    }

    public static void clear() {
        instance = null;
    }
}
