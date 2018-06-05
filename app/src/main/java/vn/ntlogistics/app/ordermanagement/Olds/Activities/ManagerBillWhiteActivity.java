package vn.ntlogistics.app.ordermanagement.Olds.Activities;

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
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import vn.ntlogistics.app.config.Config;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.CameraUtil;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.MyTextWatcher;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.BaseLocation;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.City;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.District;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.Service;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.ImportWhiteBillAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ImportWhiteBillInput;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.PublicPriceInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Pricing.PublicPriceOutput;
import vn.ntlogistics.app.ordermanagement.Olds.FTPFile.MyFTPClientFunctions;
import vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary.ItemBill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ConfirmDOActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.PricingActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ZXingScannerActivity;

public class ManagerBillWhiteActivity extends BaseActivity implements OnClickListener,  AdapterView.OnItemSelectedListener  {

    private static final String TAG = "BillWhiteActivity";

    private ArrayList<Service> mListService = new ArrayList<>();
    private ArrayList<City> mListCity = new ArrayList<>();
    private ArrayList<District>  mListDistrictTo = new ArrayList<>();

    private Spinner spinnerServiceNhan, spinnerToCityNhan, spinnerToDistrictNhan;


    public static final int REQUEST_CODE = 100;

    TextView tvMakhSend, tvAcc, tvNameImg;

    EditText edtDCGui, edtSodienthoai, edtFeeNTX, edtFeeCuocChinh, edtFeeCOD;
    EditText edtBillWhite, edtMakhSend;
    EditText edtFeeDongGoi, edtFeeNangHa, edtFeeBaoHiem, edtFeeKhac;

    Button btnScanW, btnSendW, btnPicBill;
    Spinner spinHTTT;
    ArrayList<BaseLocation> mListHTTT;

    //TODO: Toolbar
    View menuReload;

    //TODO: Data Intent
    private PublicPriceOutput price;
    private PublicPriceInput input;

    private LinearLayout layoutSaveImg;

    private MyFTPClientFunctions ftpclient = null;
    private String imagePath = "";
    CameraUtil cameraUtil;
    public static ConfirmDOActivity confirmDOActivity;


    private PricingActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manager_bill_white );
        overridePendingTransition( R.anim.alpha_in, R.anim.alpha_out );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbarWhiteBill );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbar.setNavigationOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );

        Config.initPrefs(this);


        spinnerServiceNhan = (Spinner) findViewById(R.id.spinServiceNhan);
        spinnerToCityNhan = (Spinner) findViewById(R.id.spinToCityNhan);
        spinnerToDistrictNhan = (Spinner) findViewById(R.id.spinToDisNhan);

        mListService = SSQLite.getInstance(this).getListService();
        mListCity = SSQLite.getInstance(activity).getListCity();

    //    mListDistrictTo.addAll();


     /*   mListDistrictTo.clear();
        mListDistrictTo.addAll(SSQLite.getInstance(activity).getListDistrictByCidyID(id + ""));*/

        setupSpinner( spinnerServiceNhan, mListService );
        setupSpinner( spinnerToCityNhan, mListCity );

        spinnerToCityNhan.setOnItemSelectedListener(this);

        Config.setProgressDialog(this, this, "please wait, processing.");

        mListHTTT = new ArrayList<>();
        mListHTTT.add( new BaseLocation( 10, "10-NGTTN" ) );
        mListHTTT.add( new BaseLocation( 11, "11-NGTTS" ) );
        mListHTTT.add( new BaseLocation( 20, "20-NNTTN" ) );
        /*lstHTTT.add("10-NGTTN");
        lstHTTT.add("11-NGTTS");
		lstHTTT.add("20-NNTTN");

		lstValue.add("10");
		lstValue.add("11");
		lstValue.add("20");

		lstID.add("1000000");
		lstID.add("1000000");
		lstID.add("1000000");*/

        tvMakhSend = (TextView) findViewById( R.id.tvMakhSendd );
        tvAcc = (TextView) findViewById( R.id.tvAccountWhite );

		/*Cursor c = db.getAllDataFromTable(Variables.TBL_STAFF,
				Variables.KEY_STAFF_ID, "1=1");
		c.moveToFirst();
		String acccc = "";
		while (!c.isAfterLast()) {
			acccc = c.getString(c.getColumnIndex(Variables.KEY_VALUE_STAFF)) != null ? c
					.getString(c.getColumnIndex(Variables.KEY_VALUE_STAFF))
					: "";
			c.moveToNext();
		}*/
        tvAcc.setText( SCurrentUser.getCurrentUser( this ).getValue_staff() );
        edtBillWhite = (EditText) findViewById( R.id.edtBillWhitee );
        edtMakhSend = (EditText) findViewById( R.id.edtMaKhSendd );

        edtFeeDongGoi = (EditText) findViewById( R.id.edtFeeDGg );
        edtFeeDongGoi.addTextChangedListener( new MyTextWatcher( edtFeeDongGoi, 0 ) );

        edtFeeNangHa = (EditText) findViewById( R.id.edtFeeNangHaa );
        edtFeeNangHa.addTextChangedListener( new MyTextWatcher( edtFeeNangHa, 0 ) );

        edtFeeBaoHiem = (EditText) findViewById( R.id.edtFeeBaoHiemm );

        edtFeeBaoHiem.addTextChangedListener( new MyTextWatcher( edtFeeBaoHiem, 0 ) );

        edtFeeKhac = (EditText) findViewById( R.id.edtFeeKhacc );

        edtFeeKhac.addTextChangedListener( new MyTextWatcher( edtFeeKhac, 0 ) );

        edtFeeCuocChinh = (EditText) findViewById( R.id.edtfct );
        edtFeeCuocChinh.addTextChangedListener( new MyTextWatcher( edtFeeCuocChinh, 0 ) );

        edtDCGui = (EditText) findViewById( R.id.edtDCcGui );


        edtSodienthoai = (EditText) findViewById( R.id.edtssddtt );

        edtFeeNTX = (EditText) findViewById( R.id.edtFeeNTXx );

        edtFeeNTX.addTextChangedListener( new MyTextWatcher( edtFeeNTX, 0 ) );


        edtFeeCOD = (EditText) findViewById( R.id.edtFcod );

        edtFeeCOD.addTextChangedListener( new MyTextWatcher( edtFeeCOD, 0 ) );

        btnScanW = (Button) findViewById( R.id.btnScanWhite );
        btnSendW = (Button) findViewById( R.id.btnSendWhite );
        menuReload = findViewById( R.id.menuReloadWhiteBill );

        spinHTTT = (Spinner) findViewById( R.id.spinHTTTt );

        btnPicBill = (Button) findViewById( R.id.btnPicBill );

        tvNameImg = (TextView) findViewById( R.id.tvNameImg );

        layoutSaveImg = (LinearLayout) findViewById( R.id.layoutSaveImg );

        btnScanW.setOnClickListener( this );
        btnSendW.setOnClickListener( this );
        btnPicBill.setOnClickListener( this );
        menuReload.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                myRF();
            }
        } );

        cameraUtil = new CameraUtil( this );

        ftpclient = new MyFTPClientFunctions();

        fillSpin();
        getBill();

    }//onCreate


    public void getBill() {
        try {
            Bundle b = getIntent().getExtras();
            try {
                price = (PublicPriceOutput) b.get( "price" );
                input = (PublicPriceInput) b.get( "input" );
            } catch (Exception e) {
            }
            if (price == null) price = new PublicPriceOutput();
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
            edtFeeBaoHiem.setText( price.getInsuranceFeeShow() );
            edtFeeDongGoi.setText( price.getPackingFeeShow() );
            edtFeeKhac.setText( price.getOtherAmtShow() );
            edtFeeNangHa.setText( price.getLiftingFeeShow() );
            edtFeeCuocChinh.setText( price.getPublicPostageShow() );
            edtFeeNTX.setText( price.getSuburbsFeeShow() );
            edtFeeCOD.setText( price.getCodShow() );
            edtDCGui.setText( price.getPackingFeeShow() );
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void fillSpin() {
        setupSpinner( spinHTTT, mListHTTT );
        spinHTTT.setSelection( 1 );
        spinHTTT.setOnItemSelectedListener( this );
    }

    private <T extends BaseLocation> void setupSpinner(Spinner spinner, final ArrayList<T> mList) {

        ArrayAdapter<T> adapter = new ArrayAdapter<T>( this, android.R.layout.simple_spinner_item, mList ) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView( position, convertView, parent );
                TextView tv = (TextView) view;
                tv.setText( mList.get( position ).getName() );
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView( position, convertView, parent );
                TextView tv = (TextView) view;
                tv.setText( mList.get( position ).getName() );
                tv.setTextColor( ContextCompat.getColor( tv.getContext(), R.color.colorAccentSub1 ) );
                return view;
                //return super.getView(position, convertView, parent);
            }
        };

        adapter.setDropDownViewResource( android.R.layout.simple_list_item_single_choice );
        spinner.setAdapter( adapter );

        if (spinner.getId() == R.id.spinServiceNhan){

            spinner.setSelection(Config.servicePos);

        }else if (spinner.getId() == R.id.spinToCityNhan){

            spinner.setSelection(Config.toCityPos);

        }else if(spinner.getId() == R.id.spinToDisNhan){

            spinner.setSelection(Config.toDistrictPos);
        }

    }//setupSpinner

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        //getMenuInflater().inflate(R.menu.manager_bill_white, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();

		if (id == R.id.btnbackW) {
			Variables.POWfeeCOD = edtFeeCOD.getText().toString() != null ? edtFeeCOD
					.getText().toString() : "";
			Variables.POWsdt = edtSodienthoai.getText().toString() != null ? edtSodienthoai
					.getText().toString() : "";
			Variables.POWdcgui = edtDCGui.getText().toString() != null ? edtDCGui
					.getText().toString() : "";
			Variables.POWmkh = edtMakhSend.getText().toString() != null ? edtMakhSend
					.getText().toString() : "";
			Variables.POWfeedonggoi = edtFeeDongGoi.getText().toString() != null ? edtFeeDongGoi
					.getText().toString() : "";
			Variables.POWfeenangha = edtFeeNangHa.getText().toString() != null ? edtFeeNangHa
					.getText().toString() : "";
			Variables.POWfeebaohiem = edtFeeBaoHiem.getText().toString() != null ? edtFeeBaoHiem
					.getText().toString() : "";
			Variables.POWfeekhac = edtFeeKhac.getText().toString() != null ? edtFeeKhac
					.getText().toString() : "";
			Variables.POWbill = edtBillWhite.getText().toString() != null ? edtBillWhite
					.getText().toString() : "";
			Log.d("FROM WHITE", Variables.POWbill + ", "
					+ Variables.POWfeedonggoi);
			Intent intent = new Intent(this, CountPrice.class);
			Bundle b = new Bundle();
			b.putInt("white", Variables.THISISWHITE);
			intent.putExtras(b);
			startActivity(intent);
			finish();
		}
		if (id == R.id.btnrfW) {
			myRF();
		}*/
        return super.onOptionsItemSelected( item );
    }

    public void myRF() {
        // TODO Auto-generated method stub
        edtBillWhite.setText( "" );
        edtBillWhite.setEnabled( true );
        //edtFeeBaoHiem.setText("");
        //edtFeeDongGoi.setText("");
        //edtFeeKhac.setText("");
        //edtFeeNangHa.setText("");
        spinHTTT.setSelection( 1 );
        edtMakhSend.setText( "" );

        //edtFeeCuocChinh.setText("");
        //edtFeeNTX.setText("");

        //edtFeeCOD.setText("");
        edtSodienthoai.setText( "" );
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


    private void setupSpinnerDistrict(Spinner spinner, int id) {
        Log.d(TAG, "----------------- setupSpinnerDistrict ");

      if (spinner.getId() == R.id.spinToDisNhan) {
            mListDistrictTo.clear();
            mListDistrictTo.addAll(SSQLite.getInstance(activity).getListDistrictByCidyID(id + ""));
            setupSpinner(spinnerToDistrictNhan, mListDistrictTo);
        }

    }//setupSpinnerDistrict


    @Override
    public void onClick(View v) {

        Log.d(TAG, "----------------- onClick ");

        if (v.getId() == R.id.btnSendWhite) {

            int posService = spinnerServiceNhan.getSelectedItemPosition();

            Log.d(TAG,"------------------- posService = " + posService);

            String serviceName = String.valueOf(mListService.get(posService).getName());
            String serviceValue = String.valueOf(mListService.get(posService).getValue());

            Log.d(TAG,"------------------- service name nHan selected = " + serviceName);
            Log.d(TAG,"------------------- service value nHan selected = " + serviceValue);

            Config.serviceSelectedNhan = serviceName;
            Config.valueService = serviceValue;


            int posToCity = spinnerToCityNhan.getSelectedItemPosition();

            Log.d(TAG,"------------------- posToCity = " + posToCity);

            String toCityName = String.valueOf(mListCity.get(posToCity).getName());
            String toCityValue = String.valueOf(mListCity.get(posToCity).getAreacode());

            Log.d(TAG,"------------------- to City name nHan selected = " + toCityName);
            Log.d(TAG,"------------------- to City value nHan selected = " + toCityValue);

            Config.toCitySelectedNhan = toCityName;
            Config.valueToCity = toCityValue;


            int posDistrictTo = spinnerToDistrictNhan.getSelectedItemPosition();

            Log.d(TAG,"------------------- posDistrictTo = " + posDistrictTo);

            String toDistrictName = String.valueOf(mListDistrictTo.get(posDistrictTo).getName());
            String toDistrictValue = String.valueOf(mListDistrictTo.get(posDistrictTo).getValue());

            Log.d(TAG,"------------------- to District name nHan selected = " + toDistrictName);
            Log.d(TAG,"------------------- to District value nHan selected = " + toDistrictValue);

            Config.valueToDistrict = toDistrictValue;
            Config.toDistrictSelectedNhan = toDistrictName;



            // Log.d("HTTT", spinHTTT.getSelectedItem().toString());
//            if (myValidate()) {
            String bill = edtBillWhite.getText().toString() != null ? edtBillWhite.getText().toString() : "";

            if (bill.equals( "" )) {
                Message.makeToastWarning( this, getString( R.string.toast_error_null_id_bill ) );
            } else {

                if (!imagePath.equalsIgnoreCase( "" )) {
//                    if (Commons.hasConnection(this)) {
//                        callAPIImportWhiteBill();
//                    } else {
//                        Message.makeToastErrorConnect(ManagerBillWhiteActivity.this);
//                    }
                    sendImage();

                    callAPIImportWhiteBill();

                } else {
                  //  Message.makeToastError( this, this.getString( R.string.image_error ) );

                    sendImage();
                    callAPIImportWhiteBill();

                }
            }
//            }


        }// if id = btnSendWhite

        if (v.getId() == R.id.btnScanWhite) {
            scanBill();
        }
        if (v.getId() == R.id.btnPicBill) {

            Log.e("TAG","---------------- btn pic bill, manager bill white ");

            String bill = edtBillWhite.getText().toString();
            if (bill.equalsIgnoreCase( "" )) {
                Message.makeToastWarning( this, getString( R.string.toast_error_null_id_bill ) );
                //makeWarning("Bạn chưa nhập số vận đơn.");
            } else {
                String send = tvAcc.getText().toString().concat( "^" ).concat( bill );
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy( builder.build() );
                cameraUtil.getImageFromCamera( send );
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Log.d(TAG,"---------------- onItemSelected ");

        if (parent.getId() == R.id.spinToCityNhan) {
            Log.d(TAG,"---------------- calling setupSpinnerDistrict ");
            setupSpinnerDistrict(spinnerToDistrictNhan, mListCity.get(position).getId());
        }

        else if (parent.getId() == R.id.spinHTTTt) {
            //int posSpinner = spinHTTT.getSelectedItemPosition();
            //TextView value = (TextView) view.findViewById(R.id.tv_areacode);
            //String val = value.getText().toString();
            if (mListHTTT.get( position ).getId() == 11) {
                tvMakhSend.setVisibility( View.VISIBLE );
                edtMakhSend.setVisibility( View.VISIBLE );
            } else {
                tvMakhSend.setVisibility( View.GONE );
                edtMakhSend.setVisibility( View.GONE );
            }
        }
    }

    public void scanBill() {

        /*Intent intent = new Intent(this, ScanMSActivity.class);
        *//*Bundle b = new Bundle();
        b.putInt("cmw", Variables.BILLW);
        intent.putExtras(b);*//*
        startActivityForResult(intent,REQUEST_CODE);*/
        //startActivity(intent);

        ZXingScannerActivity.openScanner( this );
    }

    /*public void sendData() throws JSONException {

        String FeeCuocChinh = edtFeeCuocChinh.getText().toString() != null ? edtFeeCuocChinh
                .getText().toString() : "";
        String FeeNTX = edtFeeNTX.getText().toString() != null ? edtFeeNTX
                .getText().toString() : "";
        String FeeCOD = edtFeeCOD.getText().toString() != null ? edtFeeCOD
                .getText().toString() : "";
        String Sodienthoai = edtSodienthoai.getText().toString() != null ? edtSodienthoai
                .getText().toString() : "";
        String DCGui = edtDCGui.getText().toString() != null ? edtDCGui
                .getText().toString() : "";
        String FeeDongGoi = edtFeeDongGoi.getText().toString() != null ? edtFeeDongGoi
                .getText().toString() : "";
        String FeeNangHa = edtFeeNangHa.getText().toString() != null ? edtFeeNangHa
                .getText().toString() : "";
        String FeeBaoHiem = edtFeeBaoHiem.getText().toString() != null ? edtFeeBaoHiem
                .getText().toString() : "";
        String FeeKhac = edtFeeKhac.getText().toString() != null ? edtFeeKhac
                .getText().toString() : "";
        String bill = edtBillWhite.getText().toString() != null ? edtBillWhite
                .getText().toString() : "";
        String key = SCurrentUser.getCurrentUser(this).getPublickey();
        int posHTTT = spinHTTT.getSelectedItemPosition();

        String mkh = SCurrentUser.getCurrentUser(this).getValue_staff();
        if (mListHTTT.get(posHTTT).getId() == 11) {
            mkh = edtMakhSend.getText().toString() != null ? edtMakhSend
                    .getText().toString() : "";
        } else {
            mkh = mkh.substring(0, 2) + "0000";
        }

        *//*String keys[] = {
                ConstantURLs.UPDATE_WHITE_BILL, key,
                bill, mListHTTT.get(posHTTT).getId() + "", mkh,
                Variables.SK, Variables.SLKD, Variables.Service, Variables.TL,
                Variables.TLQD, Variables.Long, Variables.Large,
                Variables.Height, Variables.fCity, Variables.tCity,
                Variables.fDis, Variables.tDis, formatMoney(FeeDongGoi),
                formatMoney(FeeNangHa), formatMoney(FeeBaoHiem),
                formatMoney(FeeKhac), formatMoney(FeeCuocChinh),
                formatMoney(FeeNTX), formatMoney(FeeCOD), Sodienthoai, DCGui};*//*
        String keys[] = {
                ConstantURLs.IMPORT_WHITE_BILL,
                key,
                bill,
                mListHTTT.get(posHTTT).getId() + "",
                mkh,
                input.getPackageNo()+"",
                input.getItemQty()+"",
                input.getService(),
                input.getWeight()+"",
                input.getDimensionWeight()+"", input.getPackageLong(), input.getWide()+"",
                input.getHigh()+"",
                input.getSenderProvince(), //fCity
                input.getReceiverProvince(), //tCity
                input.getSenderDistrict(), //fDistrict
                input.getReceiverDistrict(), //tDistrict
                formatMoney(FeeDongGoi),
                formatMoney(FeeNangHa), formatMoney(FeeBaoHiem),
                formatMoney(FeeKhac), formatMoney(FeeCuocChinh),
                formatMoney(FeeNTX), formatMoney(FeeCOD), Sodienthoai, DCGui};
        Log.d("cuoc chinh", "cuoc chinh: " + formatMoney(FeeCuocChinh));
        new BillPinkTask().execute(keys);
    }*/

    private void callAPIImportWhiteBill() {

        ImportWhiteBillInput inputBill = new ImportWhiteBillInput( this );

     //   inputBill.setAndroidKey(SCurrentUser.getCurrentUser(ManagerBillWhiteActivity.this).getPublickey());

        inputBill.setBill(edtBillWhite.getText().toString().trim());

     /*   int posHTTT = spinHTTT.getSelectedItemPosition();

        String mkh = SCurrentUser.getCurrentUser( this ).getValue_staff();
        if (mListHTTT.get( posHTTT ).getId() == 11) {
            mkh = edtMakhSend.getText().toString() != null ? edtMakhSend.getText().toString() : "";
        } else {
            mkh = mkh.substring( 0, 2 ) + "0000";
        }*/

    //    inputBill.setSenderCode( mkh );

        inputBill.setSenderCode("");

        inputBill.setReceiverCode("");
        inputBill.setSender("");
        inputBill.setReceiver("");
        inputBill.setSenderAddress("");

        inputBill.setReceiverAddress(edtDCGui.getText().toString());
        inputBill.setSenderContact("");
        inputBill.setReceiverContact("");
//        inputBill.setReceiverContact( edtSodienthoai.getText().toString() );
//        inputBill.setSenderProvince( input.getSenderProvince() );
        inputBill.setSenderProvince("");
//        inputBill.setReceiverProvince( input.getReceiverProvince() );
        inputBill.setReceiverProvince(Config.valueToCity);
//        inputBill.setSenderDistrict( input.getSenderDistrict() );
        inputBill.setSenderDistrict("");
//        inputBill.setReceiverDistrict( input.getReceiverDistrict() );
        inputBill.setReceiverDistrict(Config.valueToDistrict);

//        inputBill.setIsDocument( "" );
//        inputBill.setIsPro( "" );
//        inputBill.setIsOther( "" );
//
        inputBill.setIsDocument("N");
        inputBill.setIsPro("N");
        inputBill.setIsOther("N");

        inputBill.setDescription("");
        inputBill.setThProductCode("");

//        inputBill.setThPaymentCode( mListHTTT.get( posHTTT ).getId() + "" );

        inputBill.setThPaymentCode("");

//        inputBill.setService( input.getService() );

        inputBill.setService(Config.valueService);
        inputBill.setPackageNo(0);
        inputBill.setWeight(0);
        inputBill.setDimensionWeight(0);
        inputBill.setLength(0);
        inputBill.setWide(0);
        inputBill.setHigh(0);
        inputBill.setItemQty(0);
        inputBill.setCodAmt(0);
        inputBill.setInsuranceFee(0);
        inputBill.setPackingFee(0);
        inputBill.setLiftingFee(0);
        inputBill.setDeliveryFee(0);
        inputBill.setOtherAmt(0);
        inputBill.setPostage(0);
        inputBill.setSuburbsFee(0);


   /*  inputBill.setPackageNo( input.getPackageNo() );
        inputBill.setWeight( input.getWeight() );
        inputBill.setDimensionWeight( input.getDimensionWeight() );
        inputBill.setLength( input.getPackageLong() );
        inputBill.setWide( input.getWide() );
        inputBill.setHigh( input.getHigh() );
        inputBill.setItemQty( input.getItemQty() );
        inputBill.setCodAmt( input.getCodAmt() );
        inputBill.setInsuranceFee( formatMoney( edtFeeBaoHiem.getText().toString() ) );
        inputBill.setPackingFee( formatMoney( edtFeeDongGoi.getText().toString() ) );
        inputBill.setLiftingFee( formatMoney( edtFeeNangHa.getText().toString() ) );
        inputBill.setDeliveryFee( 0 );
        inputBill.setOtherAmt( formatMoney( edtFeeKhac.getText().toString() ) );
        inputBill.setPostage( formatMoney( edtFeeCuocChinh.getText().toString() ) );
        inputBill.setSuburbsFee( formatMoney( edtFeeNTX.getText().toString() ) );*/

        String data = new Gson().toJson( inputBill );

        Log.d(TAG,"---------------------------------------------------");
        Log.d(TAG,"" + data);
        Log.d(TAG,"---------------------------------------------------");

        new ImportWhiteBillAPI( this, data ).execute();

    }

    @Override
    public void onSuccess() {
        Message.makeToastSuccess( ManagerBillWhiteActivity.this, getString( R.string.synchronized_success ) );
        onBackPressed();
    }

    public long formatMoney(String input) {
        if (input == null || input.isEmpty()) return 0;
        String del = "";
        char check[] = {' ', ','};

        for (int j = 0; j < check.length; j++) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt( i ) == check[j]) {
                    input = input.replaceAll( "\\" + input.charAt( i ) + "", del );
                }
            }
        }
        return Long.parseLong( input );
    }

    public boolean myValidate() {
        String bill = edtBillWhite.getText().toString() != null ? edtBillWhite.getText().toString() : "";
        // String nameKh = edtNameSend.getText().toString() != null ?
        // edtNameSend
        // .getText().toString() : "";
        if (bill.equals( "" )) {
            Message.makeToastWarning( this, getString( R.string.toast_error_null_id_bill ) );
            return false;
        } else if (spinHTTT.getSelectedItem().toString().equals( "11" )) {
            if (edtMakhSend.getText().toString().equals( "" )) {
                Message.makeToastWarning( this, getString( R.string.toast_error_null_id_customer ) );
                //myast.makeWarning("Bạn chưa nhập mã khách hàng.");
                return false;
            } else if (edtMakhSend.getText().toString().length() < 5) {
                Message.makeToastWarning( this, getString( R.string.toast_error_min_id_customer ) );
                //myast.makeWarning("Mã khách hàng tối thiểu 5 ký tự.");
                return false;
            } else if (edtMakhSend.getText().toString().length() == 5) {
                if (edtMakhSend.getText().toString().substring( 2, 5 ).equals( "000" )) {
                    Message.makeToastWarning( this, getString( R.string.toast_error_id_customer_stamp ) );
                    //myast.makeWarning("Đây là mã khách vãng lai.");
                    return false;
                }
            } else if (edtMakhSend.getText().toString().length() == 6) {
                if (edtMakhSend.getText().toString().substring( 2, 6 ).equals( "0000" )) {
                    Message.makeToastWarning( this, getString( R.string.toast_error_id_customer_stamp ) );
                    //myast.makeWarning("Đây là mã khách vãng lai.");
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK) {


            switch (requestCode) {
                case CameraUtil.RESULT_LOAD_CAMERA_IMAGE:
                    cameraUtil.performcamCrop();
                    break;
                case CameraUtil.RESULT_CROP_IMAGE:
                    imagePath = cameraUtil.setCroopedImage();
                    cameraUtil.deleteOrgPic();
                    layoutSaveImg.setVisibility( View.VISIBLE );
                    tvNameImg.setText( tvAcc.getText().toString().concat( "^" ).concat( edtBillWhite.getText().toString() ) + ".jpg" );
                    break;
            }


            String m = ZXingScannerActivity.getCodeAfterScan( requestCode, resultCode, data );

            if (m != null) {
                edtBillWhite.setEnabled( false );
                edtBillWhite.setText( m );
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.alpha_in, R.anim.alpha_out );
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case CameraUtil.REQUEST_ID_CAMERA_PERMISSIONS:
                cameraUtil.getImageFromCamera( tvAcc.getText().toString().concat( "^" ).concat( edtBillWhite.getText().toString() ) );
                break;
        }
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public void sendImage() {
        if (isOnline( this )) {
            Log.d( "NETWORK", "Net Fine" );
            Log.d( "NETWORK", "Net Fine" );
            connectFTP();
        } else {
            Log.d( "NETWORK", "Net Fail" );
        }
    }

    private void connectFTP() {

        Log.e(TAG,"------------------ bill white activity connectFTP() " );

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
        final String desFileName = "billwhite/" + tvAcc.getText().toString().concat( "^" ).concat( edtBillWhite.getText().toString() ) + ".jpg";


        Log.e("TAG","------------------------------- image path = " + imagePath );


        String des[] = {
                host,
                username,
                password,
                port,
                imagePath,
                desFileName,
                desDirectory
        };

        // final File fdelete = new File(srcFilePath);

    //    if (imagePath == null) return;

        if (TextUtils.isEmpty(imagePath) ){
            Log.e("TAG","------------------------------- image path is empty " );

        }else {
            Log.e("TAG","------------------------------- image path not empty " );
            new SendImageBill().execute( des );

        }
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
            Log.e(TAG,"------------------ bill white activity  doInBackground() " );

            // TODO Auto-generated method stub
            boolean status = false;
            Log.e(TAG,"------------------ calling ftpclient.ftpConnect " );

            Log.e(TAG,"---------------- pr[0] " + pr[0]); // host
            Log.e(TAG,"---------------- pr[1] " + pr[1]); // username
            Log.e(TAG,"---------------- pr[2] " + pr[2]); // password
            Log.e(TAG,"---------------- pr[3] " + pr[3]); // port

            Log.e(TAG,"---------------- pr[4] " + pr[4]); // imagepath
            Log.e(TAG,"---------------- pr[5] " + pr[5]); // desFileName
            Log.e(TAG,"---------------- pr[6] " + pr[6]); // desDirectory


            status = ftpclient.ftpConnect( pr[0], pr[1], pr[2], Integer.parseInt( pr[3] ) );

            Log.e(TAG,"------------------ status =  " + status );

            if (status == true) {
                boolean a = ftpclient.ftpUploadForBillWhite( pr[4], pr[5], pr[6], ManagerBillWhiteActivity.this );
                if (a) return true;
                else return false;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.e(TAG,"------------------ onPostExecute " );
            super.onPostExecute( result );

            if (Config.progressDialog != null && Config.progressDialog.isShowing()) {
                Config.progressDialog.dismiss();
            }

            Log.d( "", "Result ftpclient: " + result + "aaaaa" );
            if (result) {
                Toast.makeText( getApplicationContext(), "Gửi ảnh thành công", Toast.LENGTH_LONG ).show();
                layoutSaveImg.setVisibility( View.GONE );
                tvNameImg.setText( "" );
                imagePath = "";
                ftpclient.ftpDisconnect();
            } else {
                Toast.makeText( getApplicationContext(), "Không gửi được ảnh", Toast.LENGTH_LONG ).show();
                ftpclient.ftpDisconnect();
            }

        }

    }


}
// Nguyễn Ngọc Giao