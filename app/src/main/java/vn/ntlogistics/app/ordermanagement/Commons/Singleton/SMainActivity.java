package vn.ntlogistics.app.ordermanagement.Commons.Singleton;


import java.lang.ref.WeakReference;

import vn.ntlogistics.app.ordermanagement.Views.Activities.OrderManagementActivity;


/**
 * Created by minhtan2908 on 10/20/16.
 */

public class SMainActivity {
    private static WeakReference<OrderManagementActivity> instance;

    public static WeakReference<OrderManagementActivity> getInstance(){
        return instance;
    }

    public static void updateActivity(OrderManagementActivity activity) {
        instance = new WeakReference<OrderManagementActivity>(activity);
    }

    public static void clear() {
        instance = null;
    }
}
