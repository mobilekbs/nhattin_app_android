package vn.ntlogistics.app.ordermanagement.Commons.AbstractClass;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
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
    }

    private void setApplication() {
        MainApplication.setCurrentActivity(this);
    }

    private void clearReferences() {
        MainApplication.setCurrentActivity(null);
    }

    public void handleNotification(int action, Bundle b){}

    public void checkPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                            != PackageManager.PERMISSION_GRANTED
                    ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        1);
            }

            /*String[] permission = new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WAKE_LOCK
            };

            for(int i = 0; i < permission.length; i++){
                if (checkSelfPermission(permission[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            this,
                            new String[]{
                                    permission[i]
                            },
                            1);
                }
            }*/
        }
    }

    public void onSuccess(){}

}
