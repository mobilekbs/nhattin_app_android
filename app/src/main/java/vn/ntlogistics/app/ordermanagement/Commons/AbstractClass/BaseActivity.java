package vn.ntlogistics.app.ordermanagement.Commons.AbstractClass;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Application.MainApplication;


/**
 * Created by Zanty on 01/07/2016.
 */
public class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setApplication();
        //InitDialogCheck.checkInternetGPS(this);
        checkPermissionGranted();
    }

    protected void onResume() {
        super.onResume();
        //InitDialogCheck.checkInternetGPS(this);
        setApplication();
    }

    protected void onPause() {
        clearReferences();
        super.onPause();
    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Commons.hideSoftKeyboard(this);
        } catch (Exception e) {
        }
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    private void setApplication() {
        MainApplication.setCurrentActivity(this);
    }

    private void clearReferences() {
        if (this.equals(MainApplication.getCurrentActivity()))
            MainApplication.setCurrentActivity(null);
    }

    public void handleNotification(int action, Bundle b){}

    public void checkPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                    ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                    ||
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                    ||
                    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        1);
            }
        }
    }

    public void onSuccess(){}

}
