package vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;

import vn.ntlogistics.app.shipper.R;


/**
 * Created by Zanty on 27/07/2016.
 */
public class ProgressDialog {
    Activity activity;
    android.app.ProgressDialog pg_dialog;

    public ProgressDialog(Activity activity) {
        this.activity = activity;
        pg_dialog = new android.app.ProgressDialog(activity) {
            @Override
            public void onBackPressed() {
                // TODO Auto-generated method stub
                //pg_dialog.dismiss();
            }
        };
    }

    public void initDialog() {
        if (pg_dialog != null && !pg_dialog.isShowing()) {
            pg_dialog.show();
            pg_dialog.setIndeterminate(false);
            pg_dialog.setCancelable(false);
            pg_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pg_dialog.setContentView(R.layout.layout_progressdialog);
        }
    }

    public void dismissDialog() {
        if (pg_dialog != null && pg_dialog.isShowing())
            pg_dialog.dismiss();
    }
}
