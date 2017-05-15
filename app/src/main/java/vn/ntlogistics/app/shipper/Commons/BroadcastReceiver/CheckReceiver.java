package vn.ntlogistics.app.shipper.Commons.BroadcastReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog.InitDialogCheck;
import vn.ntlogistics.app.shipper.Views.Application.MainApplication;

public class CheckReceiver extends BroadcastReceiver {
    Activity activity;

    public CheckReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            activity = ((MainApplication) context.getApplicationContext()).getCurrentActivity();
            InitDialogCheck.checkInternetGPS(activity);
        }
        catch (Exception e){
        }
    }

}
