package vn.ntlogistics.app.ordermanagement.Olds.Activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.io.File;
import java.io.Serializable;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Olds.ScanMS.CameraMS;
import vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary.ItemBill;
import vn.ntlogistics.app.ordermanagement.R;

public class ScanMSActivity extends BaseActivity implements Serializable {

    private Camera mCamera;
    private CameraMS mPreview;
    private Handler autoFocusHandler;
    private TextView scanText;
    FrameLayout preview;

    ImageScanner scanner;
    private boolean barcodeScanned = false;
    private boolean previewing = true;
    ItemBill itembill = new ItemBill();

    /*static {
        System.loadLibrary("iconv");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_ms);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= 23 &&
            checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CAMERA}, 1);
            finish();
        }
        else {
            //ZXingScannerActivity.openScanner(this);
            moCame();
        }
    }

    public void moCame() {
        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraMS(this, mCamera, previewCb, autoFocusCB);

        preview = (FrameLayout) findViewById(R.id.FrameScan1);
        preview.addView(mPreview);

        scanText = (TextView) findViewById(R.id.tvScanning1);
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
        }
        return c;
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if (mCamera != null) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
                previewing = false;
            }
            if (mPreview != null) {
                preview.removeView(mPreview);
                mPreview = null;
                previewing = false;
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        try {
            if (mCamera == null) {
                mCamera = getCameraInstance();
                previewing = true;
            }
            if (mPreview == null) {
                mPreview = new CameraMS(this, mCamera, previewCb, autoFocusCB);
                preview.addView(mPreview);
                previewing = true;
            }
        } catch (Exception e) {
        }
    }

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (previewing)
                mCamera.autoFocus(autoFocusCB);
        }
    };
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };
    //nt11026789
    PreviewCallback previewCb = new PreviewCallback() {
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Size size = parameters.getPreviewSize();

            // Image barcode = new Image(size.width, size.height, "Y800");
            Image barcode = new Image(size.width, size.height, "NV21");
            barcode.setData(data);
            barcode = barcode.convert("Y800");

            int result = scanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();

                SymbolSet syms = scanner.getResults();
                for (Symbol sym : syms) {
                    scanText.setText("barcode result " + sym.getData());
                    barcodeScanned = true;
                    /*int cmnt = getIntent().getExtras().getInt("cmnt");
					int cmdo = getIntent().getExtras().getInt("cmdo");
					int cmw = getIntent().getExtras().getInt("cmw");*/
                    int cmcrop = 0;
                    try {
                        cmcrop = getIntent().getExtras().getInt("cmcrop");
                    } catch (Exception e) {
                    }
                    if (cmcrop == Variables.BILLCROP) {
                        returnDataCropped(sym);
                    } else
                        returnData(sym);
					/*if (cmnt == Variables.BILLNT)
						returnDataNT(sym);
					else if (cmdo == Variables.BILLDO)
						returnDataDO(sym);
					else if (cmw == Variables.BILLW)
						returnDataW(sym);
					else*/


                }
            }
        }
    };

    public void returnDataCropped(Symbol sym) {
        Toast.makeText(getApplicationContext(),
                "Vận đơn: " + sym.getData().toString(), Toast.LENGTH_SHORT)
                .show();
        //Variables.KEYBILLCROP = sym.getData().toString();
        itembill.setBill(sym.getData().toString());
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + "img.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 1);
    }

    public void returnData(Symbol sym) {
        Intent sIntent = new Intent();
        Bundle sBundle = new Bundle();
        sBundle.putString("symbol", sym.getData().toString());
        sIntent.putExtras(sBundle);
        setResult(RESULT_OK, sIntent);
        finish();
    }

    public void returnDataNT(Symbol sym) {
        Intent sIntent = new Intent();
        Bundle sBundle = new Bundle();
        sBundle.putString("symbol", sym.getData().toString());
        sIntent.putExtras(sBundle);
        setResult(RESULT_OK, sIntent);
        finish();
    }

    public void returnDataW(Symbol sym) {
        Intent sIntent = new Intent();
        Bundle sBundle = new Bundle();
        sBundle.putString("symbol", sym.getData().toString());
        sIntent.putExtras(sBundle);
        setResult(RESULT_OK, sIntent);
        //startActivity(sIntent);
        finish();
    }

    /*public void returnDataDO(Symbol sym) {
        Intent sIntent = new Intent(getApplication(), BillDOActivity.class);
        Bundle sBundle = new Bundle();
        sBundle.putString("symbol", sym.getData().toString());
        sIntent.putExtras(sBundle);
        startActivity(sIntent);
        finish();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        /*String m = ZXingScannerActivity.getCodeAfterScan(requestCode, resultCode, data);
        Bundle bb = new Bundle();
        bb.putString("sysbom", m);*/


        if (requestCode == 1) {
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "img.jpg");
            try {
				/* the user's device may not support cropping */
                cropCapturedImage(Uri.fromFile(file));
            } catch (ActivityNotFoundException aNFE) {
                // display an error message if user device doesn't support
                String errorMessage = "Sorry - your device doesn't support the crop action!";
                Toast toast = Toast.makeText(this, errorMessage,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        if (requestCode == 2) {

            // Create an instance of bundle and get the returned data
            Bundle extras = data.getExtras();
            // get the cropped bitmap from extras
            Bitmap thePic = extras.getParcelable("data");
            // set image bitmap to image view
            //Variables.IMAGECROPPED = thePic;
            itembill.setImg(thePic);
            Variables.LST_ItemBill.add(itembill);
            Intent it = new Intent(this, MyCropImage.class);
            Bundle b = new Bundle();
            b.putInt("cropxongcmnr", 8);
            it.putExtras(b);
            startActivity(it);
            finish();
        }

    }

    public void cropCapturedImage(Uri picUri) {
        // call the standard crop action intent
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        // indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        // set crop properties
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("scale", true);
        // indicate aspect of desired crop
//		cropIntent.putExtra("aspectX", 2);
//		cropIntent.putExtra("aspectY", 3);
        // indicate output X and Y
//		cropIntent.putExtra("outputX", 200);
//		cropIntent.putExtra("outputY", 300);
        // retrieve data on return
        cropIntent.putExtra("return-data", true);
        // start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
        //finish();
    }

}
