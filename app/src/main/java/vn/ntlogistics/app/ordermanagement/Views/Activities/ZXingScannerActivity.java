package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityZxingScannerBinding;


public class ZXingScannerActivity extends BaseActivity implements
        DecoratedBarcodeView.TorchListener {

    private ActivityZxingScannerBinding     binding;
    private CaptureManager capture;
    //private DecoratedBarcodeView barcodeScannerView;
    //private Button switchFlashlightButton;

    public static void openScanner(Activity activity){
        if (Build.VERSION.SDK_INT >= 23 &&
                activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.CAMERA}, 1);
            Message.makeToastWarning(activity, activity.getString(R.string.toast_permission_camera));
        }
        else {

            new IntentIntegrator(activity)
                    .setOrientationLocked(false)
                    .setCaptureActivity(ZXingScannerActivity.class)
                    .initiateScan();
        }
    }

    public static String getCodeAfterScan(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            return result.getContents();
        }
        else
            return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_zxing_scanner);
        binding.barcodeScanner.setTorchListener(this);

        if (!hasFlash()) {
            binding.switchFlashlight.setVisibility(View.GONE);
        }
        binding.barcodeScanner.getBarcodeView().getCameraSettings().setAutoFocusEnabled(true);
        capture = new CaptureManager(this, binding.barcodeScanner);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return binding.barcodeScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public void switchFlashlight(View view) {
        if (getString(R.string.turn_on_flashlight).equals(binding.switchFlashlight.getText())) {
            binding.barcodeScanner.setTorchOn();
        } else {
            binding.barcodeScanner.setTorchOff();
        }
    }

    @Override
    public void onTorchOn() {
        binding.switchFlashlight.setText(R.string.turn_off_flashlight);
    }

    @Override
    public void onTorchOff() {
        binding.switchFlashlight.setText(R.string.turn_on_flashlight);
    }
}
