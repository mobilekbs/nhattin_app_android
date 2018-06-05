package vn.ntlogistics.app.ordermanagement.Olds.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.ntlogistics.app.config.Config;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.CameraUtil;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.MyTextWatcher;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.BaseLocation;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.PublicPriceInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Pricing.PublicPriceOutput;
import vn.ntlogistics.app.ordermanagement.Olds.FTPFile.MyFTPClientFunctions;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ZXingScannerActivity;

public class ManagerBillGreenActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "ManagerBillGreen";

    private Activity activity;
    private Context context;

    private TextView tvAcc;
    private TextView tvMakhSend;
    private TextView tvNameImgGreen;

    private EditText edtBillGreen;
    private EditText edtMakhSendGreen;
    private EditText edtSodienthoaiGreen;

    private EditText edtFeeBaoHiem;
    private EditText edtFeeDongGoi;
    private EditText edtFeeKhac;
    private EditText edtFeeNangHa;
    private EditText edtFeeCuocChinh;
    private EditText edtFeeNTX;
    private EditText edtFeeCOD;
    private EditText edtDCGui;


    private Button btnPicBillGreen;
    private Button btnScanGreen;
    private Button btnSendGreen;
    private Spinner btnSpinHtt;

    private CameraUtil cameraUtil;

    private LinearLayout layoutSaveImg;

    private String imagePath = "";

    private PublicPriceOutput price;
    private PublicPriceInput input;

    private ImageView imgReload;

    private MyFTPClientFunctions ftpclient = null;

    ArrayList<BaseLocation> mListHTTT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_bill_green);

        Config.initPrefs(this);

        Config.setProgressDialog(this, this, "please wait, uploading your bill.");

        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGreenBill);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mListHTTT = new ArrayList<>();
        mListHTTT.add(new BaseLocation(10, "10-NGTTN"));
        mListHTTT.add(new BaseLocation(11, "11-NGTTS"));
        mListHTTT.add(new BaseLocation(20, "20-NNTTN"));

        tvNameImgGreen = (TextView) findViewById(R.id.tvNameImgGreen);

        tvAcc = (TextView) findViewById(R.id.tvAccountGreen);
        tvMakhSend = (TextView) findViewById(R.id.tvMakhSenddGreen);

        tvAcc.setText(SCurrentUser.getCurrentUser(this).getValue_staff());

        edtBillGreen = (EditText) findViewById(R.id.edtBillGreen);
        edtMakhSendGreen = (EditText) findViewById(R.id.edtMaKhSenddGreen);

        edtSodienthoaiGreen = (EditText) findViewById(R.id.edtssddttGreen);

        edtFeeBaoHiem = (EditText) findViewById(R.id.edtFeeBaoHiemmGreen);
        edtFeeBaoHiem.addTextChangedListener(new MyTextWatcher(edtFeeBaoHiem, 0));

        edtFeeDongGoi = (EditText) findViewById(R.id.edtFeeDGgGreen);
        edtFeeDongGoi.addTextChangedListener(new MyTextWatcher(edtFeeDongGoi, 0));

        edtFeeKhac = (EditText) findViewById(R.id.edtFeeKhaccGreen);
        edtFeeKhac.addTextChangedListener(new MyTextWatcher(edtFeeKhac, 0));

        edtFeeNangHa = (EditText) findViewById(R.id.edtFeeNangHaaGreen);
        edtFeeNangHa.addTextChangedListener(new MyTextWatcher(edtFeeNangHa, 0));

        edtFeeCuocChinh = (EditText) findViewById(R.id.edtfctGreen);
        edtFeeCuocChinh.addTextChangedListener(new MyTextWatcher(edtFeeCuocChinh, 0));

        edtFeeNTX = (EditText) findViewById(R.id.edtFeeNTXxGreen);
        edtFeeNTX.addTextChangedListener(new MyTextWatcher(edtFeeNTX, 0));

        edtFeeCOD = (EditText) findViewById(R.id.edtFcodGreen);
        edtFeeCOD.addTextChangedListener(new MyTextWatcher(edtFeeCOD, 0));

        edtDCGui = (EditText) findViewById(R.id.edtDCcGuiGreen);
        edtDCGui.addTextChangedListener(new MyTextWatcher(edtDCGui, 0));

        btnScanGreen = (Button) findViewById(R.id.btnScanGreen);
        btnScanGreen.setOnClickListener(this);

        imgReload = (ImageView) findViewById(R.id.menuReloadGreenBill);
        imgReload.setOnClickListener(this);

        btnPicBillGreen = (Button) findViewById(R.id.btnPicBillGreen);
        btnPicBillGreen.setOnClickListener(this);

        btnSpinHtt = (Spinner) findViewById(R.id.spinHTTTtGreen);

        btnSendGreen = (Button) findViewById(R.id.btnSendGreen);
        btnSendGreen.setOnClickListener(this);

        cameraUtil = new CameraUtil(this);

        layoutSaveImg = (LinearLayout) findViewById(R.id.layoutSaveImgGreen);

        ftpclient = new MyFTPClientFunctions();

        Config.billNumber = Config.getGreenBillNumber();

        fillSpin();
        getBill();

    }//onCreate

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    public void onClick(View view) {

        String bill;

        switch (view.getId()) {

            case R.id.menuReloadGreenBill:
                myRF();
                break;

            case R.id.btnScanGreen:
                scanBill();
                break;

            case R.id.btnPicBillGreen:

                this.cameraUtil.onceDone = false;

                bill = edtBillGreen.getText().toString();
                if (bill.equalsIgnoreCase("")) {
                    Message.makeToastWarning(this, getString(R.string.toast_error_null_id_bill));
                    //makeWarning("Bạn chưa nhập số vận đơn.");
                } else {
                    String send = tvAcc.getText().toString().concat("^").concat(bill);
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    cameraUtil.getImageFromCamera(send);
                }

                break;

            case R.id.btnSendGreen:

                Log.e(TAG, "-------- btn Send clicked ");

                bill = edtBillGreen.getText().toString() != null ? edtBillGreen.getText().toString() : "";

                if (bill.equals("")) {
                    Message.makeToastWarning(this, getString(R.string.toast_error_null_id_bill));
                } else {
                    if (!imagePath.equalsIgnoreCase("")) {
                        sendImage();
                    } else {
                        Message.makeToastError(this, this.getString(R.string.image_error));
                    }
                }

                break;

        }//switch

    }//onClick

    public void myRF() {
        // TODO Auto-generated method stub
        edtBillGreen.setText("");
        edtBillGreen.setEnabled(true);
        //edtFeeBaoHiem.setText("");
        //edtFeeDongGoi.setText("");
        //edtFeeKhac.setText("");
        //edtFeeNangHa.setText("");
        btnSpinHtt.setSelection(1);
        edtMakhSendGreen.setText("");

        //edtFeeCuocChinh.setText("");
        //edtFeeNTX.setText("");

        //edtFeeCOD.setText("");
        edtSodienthoaiGreen.setText("");
        //edtDCGui.setText("");

//        edtFeeBaoHiem.setText(price.getInsuranceFeeShow());
//        edtFeeDongGoi.setText(price.getPackingFeeShow());
//        edtFeeKhac.setText(price.getOtherAmtShow());
//        edtFeeNangHa.setText(price.getLiftingFeeShow());
//        edtFeeCuocChinh.setText(price.getPublicPostageShow());
//        edtFeeNTX.setText(price.getSuburbsFeeShow());
//        edtFeeCOD.setText(price.getCodShow());
//        edtDCGui.setText(price.getPackingFeeShow());
    }


    public void fillSpin() {
        setupSpinner(btnSpinHtt, mListHTTT);
        btnSpinHtt.setSelection(1);
        btnSpinHtt.setOnItemSelectedListener(this);
    }


    private <T extends BaseLocation> void setupSpinner(Spinner spinner, final ArrayList<T> mList) {
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                this,
                android.R.layout.simple_spinner_item
                , mList) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setText(mList.get(position).getName());
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setText(mList.get(position).getName());
                tv.setTextColor(ContextCompat.getColor(
                        tv.getContext(), R.color.colorAccentSub1
                ));
                return view;
                //return super.getView(position, convertView, parent);
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onSuccess() {
        Message.makeToastSuccess(ManagerBillGreenActivity.this,
                getString(R.string.toast_success_send));
        onBackPressed();
    }

    public long formatMoney(String input) {
        if (input == null || input.isEmpty())
            return 0;
        String del = "";
        char check[] = {' ', ','};

        for (int j = 0; j < check.length; j++) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == check[j]) {
                    input = input.replaceAll("\\" + input.charAt(i) + "", del);
                }
            }
        }
        return Long.parseLong(input);
    }


    public void getBill() {
        try {
            Bundle b = getIntent().getExtras();
            try {
                price = (PublicPriceOutput) b.get("price");
                input = (PublicPriceInput) b.get("input");
            } catch (Exception e) {
            }
            if (price == null)
                price = new PublicPriceOutput();
            /*if (fromPrice == Variables.THISISPRICE) {
                edtBillWhite.setText(Variables.POWbill);
                edtFeeBaoHiem.setText(Variables.POWfeebaohiem);
                edtFeeDongGoi.setText(Variables.POWfeedonggoi);
                edtFeeKhac.setText(Variables.POWfeekhac);
                edtFeeNangHa.setText(Variables.POWfeenangha);
                spinHTTT.setSelection(Variables.POWhttt);
                edtMakhSend.setText(Variables.POWmkh);
                edtFeeCuocChinh.setText(Variables.POWfeeChinh);
                edtFeeNTX.setText(Variables.POWfeeNTX);

                edtFeeCOD.setText(Variables.POWfeeCOD);
                edtSodienthoai.setText(Variables.POWsdt);
                edtDCGui.setText(Variables.POWdcgui);
            }*/
            //edtBillWhite.setText(price.getTotalPriceShow());
            edtFeeBaoHiem.setText(price.getInsuranceFeeShow());
            edtFeeDongGoi.setText(price.getPackingFeeShow());
            edtFeeKhac.setText(price.getOtherAmtShow());
            edtFeeNangHa.setText(price.getLiftingFeeShow());
            edtFeeCuocChinh.setText(price.getPublicPostageShow());
            edtFeeNTX.setText(price.getSuburbsFeeShow());
            edtFeeCOD.setText(price.getCodShow());
            edtDCGui.setText(price.getPackingFeeShow());
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public void scanBill() {

        /*Intent intent = new Intent(this, ScanMSActivity.class);
        *//*Bundle b = new Bundle();
        b.putInt("cmw", Variables.BILLW);
        intent.putExtras(b);*//*
        startActivityForResult(intent,REQUEST_CODE);*/
        //startActivity(intent);

        ZXingScannerActivity.openScanner(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "-------- on Activity Result ");

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Log.e(TAG, "-------- resultCode == RESULT_OK ");

            switch (requestCode) {

                case CameraUtil.RESULT_LOAD_CAMERA_IMAGE:
                    Log.e(TAG, "-------- case CameraUtil. RESULT_LOAD_CAMERA_IMAGE: ");

                    cameraUtil.performcamCrop();
                    break;
                case CameraUtil.RESULT_CROP_IMAGE:
                    Log.e(TAG, "-------- case CameraUtil. RESULT_LOAD_CAMERA_IMAGE: ");

                    imagePath = cameraUtil.setCroopedImage();
                    cameraUtil.deleteOrgPic();
                    layoutSaveImg.setVisibility(View.VISIBLE);
                    tvNameImgGreen.setText(tvAcc.getText().toString().concat("^").concat(edtBillGreen.getText().toString()) + "^bill^" + Config.billNumber + "^" + Config.fileTimeStamp + ".jpg");
                    break;

            }//switch

            String m = ZXingScannerActivity.getCodeAfterScan(requestCode, resultCode, data);

            if (m != null) {
                edtBillGreen.setEnabled(false);
                edtBillGreen.setText(m);
            }

        }//if(resultCode == RESULT_OK)

    }//onActivityResult


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.e(TAG, "-------- on Request Permissions Result ");

        switch (requestCode) {

            case CameraUtil.REQUEST_ID_CAMERA_PERMISSIONS:
                cameraUtil.getImageFromCamera(tvAcc.getText().toString().concat("^").concat(edtBillGreen.getText().toString()));
                break;

        }//switch

    }//onRequestPermissionsResult

    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;

    }//isOnline

    public void sendImage() {
        Log.e(TAG, "-------- send Image ");

        if (isOnline(this)) {
            Log.e(TAG, "Net Fine");
            Log.e(TAG, "Net Fine");
            connectFTP();
        } else {
            Log.e(TAG, "Net Fail");
        }

    }//sendImage

    private void connectFTP() {
        Log.e(TAG, "-------- connect FTP");

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
        final String desFileName = "billdoc/" + tvAcc.getText().toString().concat("^").concat(edtBillGreen.getText().toString()) + "^bill^" + Config.billNumber + "^" + Config.fileTimeStamp + ".jpg";
        Config.billNumber++;

        Log.e(TAG, "-------- desFileName = " + desFileName);

        String[] des = {host, username, password, port, imagePath, desFileName, desDirectory};
        // final File fdelete = new File(srcFilePath);

        Log.e(TAG, "-------- des[] " + des);

        new ManagerBillGreenActivity.SendImageBill().execute(des);

    }//connectFTP

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        if (parent.getId() == R.id.spinHTTTt) {
            //int posSpinner = spinHTTT.getSelectedItemPosition();
            //TextView value = (TextView) view.findViewById(R.id.tv_areacode);
            //String val = value.getText().toString();
            if (mListHTTT.get(position).getId() == 11) {
                tvMakhSend.setVisibility(View.VISIBLE);
                edtMakhSendGreen.setVisibility(View.VISIBLE);
            } else {
                tvMakhSend.setVisibility(View.GONE);
                edtMakhSendGreen.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private class SendImageBill extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (Config.progressDialog != null && !Config.progressDialog.isShowing()) {
                Config.progressDialog.show();
            }
        }

        /*        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Config.setProgressDialog( activity, context, "please wait, uploading your bill." );

            if (Config.progressDialog != null && !Config.progressDialog.isShowing()) {
                Config.progressDialog.show();
            }

        }//onPreExecute*/

        @Override
        protected Boolean doInBackground(String... pr) {
            Log.e(TAG, "-------- doInBackground ");


            Log.e(TAG,"---------------- pr[0] " + pr[0]);
            Log.e(TAG,"---------------- pr[1] " + pr[1]);
            Log.e(TAG,"---------------- pr[2] " + pr[2]);
            Log.e(TAG,"---------------- pr[3] " + pr[3] );

            Log.e(TAG,"---------------- pr[0] " + pr[4]);
            Log.e(TAG,"---------------- pr[1] " + pr[5]);
            Log.e(TAG,"---------------- pr[2] " + pr[6]);

            boolean status = false;
            status = ftpclient.ftpConnect(pr[0], pr[1], pr[2], Integer.parseInt(pr[3]));

            Log.e(TAG, "-------- ftp status = " + status);

            if (status) {
                boolean a = ftpclient.ftpUpload(pr[4], pr[5], pr[6], ManagerBillGreenActivity.this);
                Log.e(TAG, "-------- a = " + a);

                return a;
            } else {
                return false;
            }

        }//doInBackground

        @Override
        protected void onPostExecute(Boolean result) {
            Log.e(TAG, "-------- onPostExecute ");
            super.onPostExecute(result);
            Log.e(TAG, "------- Result ftp client = " + result);


            if (Config.progressDialog != null && Config.progressDialog.isShowing()) {
                Config.progressDialog.dismiss();
            }

            edtBillGreen.setText("");

            if (result) {
                Toast.makeText(getApplicationContext(), "Gửi ảnh thành công", Toast.LENGTH_LONG).show();
                layoutSaveImg.setVisibility(View.GONE);
                tvNameImgGreen.setText("");
                imagePath = "";
                ftpclient.ftpDisconnect();
            } else {
                Toast.makeText(getApplicationContext(), "Không gửi được ảnh", Toast.LENGTH_LONG).show();
                ftpclient.ftpDisconnect();
            }

        }//onPostExecute

    }//SendImageBill

    @Override
    protected void onDestroy() {
        Config.setGreenBillNumber(1);
        super.onDestroy();

    }//onDestroy

    public static String getCurrentTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        //date output format
        return dateFormat.format(cal.getTime());
    }

}//ManagerBillGreenActivity
