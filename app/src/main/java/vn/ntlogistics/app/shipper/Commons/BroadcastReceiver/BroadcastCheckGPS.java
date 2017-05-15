package vn.ntlogistics.app.shipper.Commons.BroadcastReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;

import vn.ntlogistics.app.shipper.R;


/**
 * Created by Zanty on 01/07/2016.
 */
public class BroadcastCheckGPS extends BroadcastReceiver {

    Activity activity;

    public BroadcastCheckGPS(Activity activity) {
        this.activity = activity;
        //Bộ lọc lắng nghe sự thay đổi kết nối
        IntentFilter filter = new IntentFilter("android.location.PROVIDERS_CHANGED");
        //Đăng lý Broadcast Receiver
        activity.registerReceiver(this, filter);
    }

    @Override
    public void onReceive(final Context c, Intent intent) {
        if (!checkGPS()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);
            alertDialogBuilder.setMessage(c.getString(R.string.message_turnon_gps));
            alertDialogBuilder.setPositiveButton(c.getString(R.string.action_settings),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            c.startActivity(callGPSSettingIntent);
                        }
                    });
            alertDialogBuilder.setNegativeButton(c.getString(R.string.btnCancle),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alertDialogBuilder.setCancelable(false);
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }

    boolean checkGPS() {
        LocationManager locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }
}
