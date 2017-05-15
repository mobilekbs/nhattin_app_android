package vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.R;

/**
 * Created by Zanty on 02/07/2016.
 */
public class InitDialogCheck {
    private static CustomDialog dialogInternet, dialogGPS;

    public static void delDialogCheck() {
        if(dialogGPS != null && dialogGPS.isShowing())
            dialogGPS.dismiss();
        if(dialogInternet != null && dialogInternet.isShowing())
            dialogInternet.dismiss();
    }



    public static void checkInternetGPS(final Activity activity) {
        try {
            setupDialog(activity);
            if (Commons.hasConnection(activity)) {
                if (!Commons.checkGPS(activity)) {
                    if (!dialogGPS.isShowing()) {
                        dialogGPS.show();
                    }
                } else if (dialogInternet.isShowing()) {
                    dialogInternet.dismiss();
                    dialogInternet = null;
                } else if (dialogGPS.isShowing()) {
                    dialogGPS.dismiss();
                    dialogGPS = null;
                }
            } else {
                if (!dialogInternet.isShowing()) {
                    dialogInternet.show();
                }
            }

        } catch (Exception e) {
            Log.d("Check Internet GPS","-----------------");
            e.printStackTrace();
        }
    }

    public static void setupDialog(final Activity activity) {
        boolean b = false;
        if(dialogGPS != null && dialogGPS.activity != activity){
            b = true;
        }
        if (dialogInternet == null || b)
            dialogInternet = initDialog(activity,
                    activity.getResources().getString(R.string.not_internet),
                    false,
                    new CustomDialog.SetOnClickDialog() {
                        @Override
                        public void onClickOk() {
                            if (Commons.hasConnection(activity))
                                InitDialogCheck.dialogInternet.dismiss();
                            else {
                                InitDialogCheck.dialogInternet.dismiss();
                                InitDialogCheck.dialogInternet.show();
                            }
                        }

                        @Override
                        public void onClickCancel() {

                        }
                    });
        if (dialogGPS == null || b)
            dialogGPS = initDialog(activity,
                    activity.getResources().getString(R.string.message_turnon_gps),
                    true,
                    new CustomDialog.SetOnClickDialog() {
                        @Override
                        public void onClickOk() {
                            Intent callGPSSettingIntent = new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivity(callGPSSettingIntent);
                        }

                        @Override
                        public void onClickCancel() {

                        }
                    });
    }

    static CustomDialog initDialog(final Activity activity, String message, boolean show, CustomDialog.SetOnClickDialog clickDialog) {
        final CustomDialog init = new CustomDialog(activity);
        init.setGPS(show);
        init.setTitleMessage(message);
        init.setTextTitle(activity.getResources().getString(R.string.note_dialog));
        init.setOnClickButton(clickDialog);
        init.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                activity.moveTaskToBack(true);
            }
        });
        init.setCanceledOnTouchOutside(false);
        return init;
    }
}
