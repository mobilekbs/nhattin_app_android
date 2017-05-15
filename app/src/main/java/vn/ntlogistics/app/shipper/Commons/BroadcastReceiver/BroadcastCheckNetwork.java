package vn.ntlogistics.app.shipper.Commons.BroadcastReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import vn.ntlogistics.app.shipper.Commons.Commons;

/**
 * Created by Zanty on 01/07/2016.
 */
public class BroadcastCheckNetwork extends BroadcastReceiver {

    boolean isConnected = false;
    Activity activity;

    public BroadcastCheckNetwork(Activity activity) {
        this.activity = activity;
        //Bộ lọc lắng nghe sự thay đổi kết nối
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        //Đăng lý Broadcast Receiver
        activity.registerReceiver(this, filter);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        isConnected = Commons.hasConnection(context);
        if (isConnected) {

        } else {

        }
    }
}
