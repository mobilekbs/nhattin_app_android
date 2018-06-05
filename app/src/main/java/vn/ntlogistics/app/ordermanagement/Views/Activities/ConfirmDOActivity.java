package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import vn.ntlogistics.app.config.Config;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.CameraUtil;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ConfirmBPBillInput;
import vn.ntlogistics.app.ordermanagement.Olds.FTPFile.MyFTPClientFunctions;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.ConfirmDOVMs.ConfirmDOVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityConfirmDoBinding;


public class ConfirmDOActivity extends BaseActivity {

    private static final String TAG = "ConfirmDOActivity";

    private ActivityConfirmDoBinding        binding;
    private ConfirmDOVM                     viewModel;

    private MyFTPClientFunctions ftpclient = null;
    public static String imagePath = "";
    CameraUtil cameraUtil;
    String send = "";
    private String billNo = "";
    public static ConfirmDOActivity confirmDOActivity;

    public static void startIntentActivity(Context context,
                                           ConfirmBPBillInput item,
                                           Bundle b,
                                           Integer requestCode,
                                           boolean isFinish){
        Intent i = new Intent(context, ConfirmDOActivity.class);

        if(b == null)
            b = new Bundle();
        b.putSerializable("item", item);
        i.putExtras(b);

        if (requestCode != null)
            ((Activity) context).startActivityForResult(i, requestCode);
        else
            ((Activity) context).startActivity(i);

        if(isFinish){
            ((Activity) context).finish();
        }
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        confirmDOActivity = this;
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_do);
        viewModel = new ConfirmDOVM(this, binding);

        Config.initPrefs(this);
        Config.setProgressDialog(this, this, "please wait, processing.");

        binding.setViewModel(viewModel);

        billNo = SCurrentUser.getCurrentUser(this).getValue_staff();

        if (Config.debug_mode){
            billNo = Config.testingBillNumber;
        }

        setSupportActionBar(binding.toolbarBillDO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarBillDO.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        cameraUtil = new CameraUtil(this);

        ftpclient = new MyFTPClientFunctions();

        binding.btnPicBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bill = binding.etDOCode.getText().toString();
                if (bill.equalsIgnoreCase("")) {
                    Message.makeToastWarning(ConfirmDOActivity.this,
                            getString(R.string.error_do_number_null));
                } else {
                    try {


                        send = billNo.concat("^").concat(bill);
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        cameraUtil.getImageFromCamera(send);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    @Override
    public void onSuccess() {
        if(viewModel != null)
            viewModel.onSuccess();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case CameraUtil.RESULT_LOAD_CAMERA_IMAGE:
                    cameraUtil.performcamCrop();
                    break;
                case CameraUtil.RESULT_CROP_IMAGE:
                    imagePath = cameraUtil.setCroopedImage();
                    cameraUtil.deleteOrgPic();
                    binding.layoutSaveImg.setVisibility(View.VISIBLE);
                    binding.tvNameImg.setText(send+".jpg");
                    break;
            }

            String m = ZXingScannerActivity.getCodeAfterScan(requestCode, resultCode, data);
            if (m != null) {
                binding.etDOCode.setEnabled(false);
                binding.etDOCode.setText(m);
                viewModel.checkDOCodeInvalid(m, false);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case CameraUtil.REQUEST_ID_CAMERA_PERMISSIONS:
                cameraUtil.getImageFromCamera(send);
                break;
        }
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void sendImage() {
        Log.e(TAG,"---------------- in sendImage() " );

        if (isOnline(this)) {
            Log.e(TAG,"---------------- in  if (isOnline " );

            Log.d("NETWORK", "Net Fine");
            Log.d("NETWORK", "Net Fine");
            connectFTP();
        } else {
            Log.e(TAG,"---------------- in  else of if (isOnline " );

            Log.d("NETWORK", "Net Fail");
        }
    }

    private void connectFTP() {

        Log.e(TAG,"---------------- in connectFTP() " );

        final String host = "123.30.145.168";
        final String username = "ntftp";
        final String password = "ntftpd@123$";
        final String port = "21";

		/*final String srcFilePath = Environment.getExternalStorageDirectory()
				+ File.separator
				+ "DCIM/ImgAppCrop/"
				+ tvAccountPink.getText().toString().concat("^")
						.concat(edtBill.getText().toString()).concat(".jpg");*/
        final String desDirectory = "";
        final String desFileName = "billwhite/"
                + send + ".jpg";
        String des[] = { host, username, password, port,
                imagePath, desFileName, desDirectory };
        // final File fdelete = new File(srcFilePath);

        new ConfirmDOActivity.SendImageBill().execute(des);

    }


    /*----------------------------------------------------------------------*/
    private class SendImageBill extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (Config.progressDialog != null && !Config.progressDialog.isShowing()) {
                Config.progressDialog.show();
            }
        }

        @Override
        protected Boolean doInBackground(String... pr) {

            Log.e(TAG,"---------------- in doInBackground() SendImageBill" );

            // TODO Auto-generated method stub
            boolean status = false;

            Log.e(TAG,"---------------- calling ftpclient.ftpConnect() " );

            Log.e(TAG,"---------------- pr[0] " + pr[0]);
            Log.e(TAG,"---------------- pr[1] " + pr[1]);
            Log.e(TAG,"---------------- pr[2] " + pr[2]);
            Log.e(TAG,"---------------- pr[3] " + pr[3] );

            Log.e(TAG,"---------------- pr[0] " + pr[4]);
            Log.e(TAG,"---------------- pr[1] " + pr[5]);
            Log.e(TAG,"---------------- pr[2] " + pr[6]);

            status = ftpclient.ftpConnect(pr[0], pr[1], pr[2],
                    Integer.parseInt(pr[3]));

            Log.e(TAG,"---------------- status = " + status );

            if (status == true) {

                boolean a = ftpclient.ftpUpload(pr[4], pr[5], pr[6], ConfirmDOActivity.this);
                if (a)
                    return true;
                else
                    return false;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.e(TAG,"---------------- in onPostExecute() SendImageBill" );

            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.d("", "Result ftpclient: " + result + "aaaaa");

            if (Config.progressDialog != null && Config.progressDialog.isShowing()) {
                Config.progressDialog.dismiss();
            }

            Log.e(TAG,"---------------- result = " + result );

            if (result) {
                Toast.makeText(getApplicationContext(), "Gửi ảnh thành công",
                        Toast.LENGTH_LONG).show();
                binding.layoutSaveImg.setVisibility(View.GONE);
                binding.tvNameImg.setText("");
                imagePath = "";
                ftpclient.ftpDisconnect();
            } else {
                Toast.makeText(getApplicationContext(), "Không gửi được ảnh",
                        Toast.LENGTH_LONG).show();
                ftpclient.ftpDisconnect();
            }

        }

    }

}
