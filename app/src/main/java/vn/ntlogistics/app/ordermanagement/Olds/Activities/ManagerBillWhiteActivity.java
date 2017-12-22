package vn.ntlogistics.app.ordermanagement.Olds.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.MyTextWatcher;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.BaseLocation;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.ImportWhiteBillAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ImportWhiteBillInput;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.PublicPriceInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Pricing.PublicPriceOutput;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ZXingScannerActivity;

public class ManagerBillWhiteActivity extends BaseActivity implements OnClickListener,
        OnItemSelectedListener {

    public static final int REQUEST_CODE = 100;

    TextView tvMakhSend, tvAcc;

    EditText edtDCGui, edtSodienthoai, edtFeeNTX, edtFeeCuocChinh, edtFeeCOD;
    EditText edtBillWhite, edtMakhSend;
    EditText edtFeeDongGoi, edtFeeNangHa, edtFeeBaoHiem, edtFeeKhac;

    Button btnScanW, btnSendW;
    Spinner spinHTTT;
    ArrayList<BaseLocation> mListHTTT;

    //TODO: Toolbar
    View menuReload;

    //TODO: Data Intent
    private PublicPriceOutput   price;
    private PublicPriceInput    input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_bill_white);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWhiteBill);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mListHTTT = new ArrayList<>();
        mListHTTT.add(new BaseLocation(10, "10-NGTTN"));
        mListHTTT.add(new BaseLocation(11, "11-NGTTS"));
        mListHTTT.add(new BaseLocation(20, "20-NNTTN"));
        /*lstHTTT.add("10-NGTTN");
		lstHTTT.add("11-NGTTS");
		lstHTTT.add("20-NNTTN");

		lstValue.add("10");
		lstValue.add("11");
		lstValue.add("20");

		lstID.add("1000000");
		lstID.add("1000000");
		lstID.add("1000000");*/

        tvMakhSend = (TextView) findViewById(R.id.tvMakhSendd);
        tvAcc = (TextView) findViewById(R.id.tvAccountWhite);

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
        tvAcc.setText(SCurrentUser.getCurrentUser(this).getValue_staff());
        edtBillWhite = (EditText) findViewById(R.id.edtBillWhitee);
        edtMakhSend = (EditText) findViewById(R.id.edtMaKhSendd);

        edtFeeDongGoi = (EditText) findViewById(R.id.edtFeeDGg);
        edtFeeDongGoi
                .addTextChangedListener(new MyTextWatcher(edtFeeDongGoi, 0));
        edtFeeNangHa = (EditText) findViewById(R.id.edtFeeNangHaa);
        edtFeeNangHa.addTextChangedListener(new MyTextWatcher(edtFeeNangHa, 0));
        edtFeeBaoHiem = (EditText) findViewById(R.id.edtFeeBaoHiemm);
        edtFeeBaoHiem
                .addTextChangedListener(new MyTextWatcher(edtFeeBaoHiem, 0));
        edtFeeKhac = (EditText) findViewById(R.id.edtFeeKhacc);
        edtFeeKhac.addTextChangedListener(new MyTextWatcher(edtFeeKhac, 0));

        edtFeeCuocChinh = (EditText) findViewById(R.id.edtfct);
        edtFeeCuocChinh.addTextChangedListener(new MyTextWatcher(
                edtFeeCuocChinh, 0));
        edtDCGui = (EditText) findViewById(R.id.edtDCcGui);
        edtSodienthoai = (EditText) findViewById(R.id.edtssddtt);
        edtFeeNTX = (EditText) findViewById(R.id.edtFeeNTXx);
        edtFeeNTX.addTextChangedListener(new MyTextWatcher(edtFeeNTX, 0));

        edtFeeCOD = (EditText) findViewById(R.id.edtFcod);
        edtFeeCOD.addTextChangedListener(new MyTextWatcher(edtFeeCOD, 0));

        btnScanW = (Button) findViewById(R.id.btnScanWhite);
        btnSendW = (Button) findViewById(R.id.btnSendWhite);
        menuReload = findViewById(R.id.menuReloadWhiteBill);

        spinHTTT = (Spinner) findViewById(R.id.spinHTTTt);

        btnScanW.setOnClickListener(this);
        btnSendW.setOnClickListener(this);
        menuReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myRF();
            }
        });

        fillSpin();
        getBill();

    }

    public void getBill() {
        try {
            Bundle b = getIntent().getExtras();
            try {
                price = (PublicPriceOutput) b.get("price");
                input = (PublicPriceInput) b.get("input");
            } catch (Exception e) {
            }
            if(price == null)
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

    public void fillSpin() {
        setupSpinner(spinHTTT, mListHTTT);
        spinHTTT.setSelection(1);
        spinHTTT.setOnItemSelectedListener(this);
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
        return super.onOptionsItemSelected(item);
    }

    public void myRF() {
        // TODO Auto-generated method stub
        edtBillWhite.setText("");
        edtBillWhite.setEnabled(true);
        //edtFeeBaoHiem.setText("");
        //edtFeeDongGoi.setText("");
        //edtFeeKhac.setText("");
        //edtFeeNangHa.setText("");
        spinHTTT.setSelection(1);
        edtMakhSend.setText("");

        //edtFeeCuocChinh.setText("");
        //edtFeeNTX.setText("");

        //edtFeeCOD.setText("");
        edtSodienthoai.setText("");
        //edtDCGui.setText("");

        edtFeeBaoHiem.setText(price.getInsuranceFeeShow());
        edtFeeDongGoi.setText(price.getPackingFeeShow());
        edtFeeKhac.setText(price.getOtherAmtShow());
        edtFeeNangHa.setText(price.getLiftingFeeShow());
        edtFeeCuocChinh.setText(price.getPublicPostageShow());
        edtFeeNTX.setText(price.getSuburbsFeeShow());
        edtFeeCOD.setText(price.getCodShow());
        edtDCGui.setText(price.getPackingFeeShow());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.btnSendWhite) {
            // Log.d("HTTT", spinHTTT.getSelectedItem().toString());
            if (myValidate()) {
                if(Commons.hasConnection(this)){
                    callAPIImportWhiteBill();
                }
                else {
                    Message.makeToastErrorConnect(ManagerBillWhiteActivity.this);
                }
            }
        }
        if (v.getId() == R.id.btnScanWhite) {
            scanBill();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub
        if (parent.getId() == R.id.spinHTTTt) {
            //int posSpinner = spinHTTT.getSelectedItemPosition();
            //TextView value = (TextView) view.findViewById(R.id.tv_areacode);
            //String val = value.getText().toString();
            if (mListHTTT.get(position).getId() == 11) {
                tvMakhSend.setVisibility(View.VISIBLE);
                edtMakhSend.setVisibility(View.VISIBLE);
            } else {
                tvMakhSend.setVisibility(View.GONE);
                edtMakhSend.setVisibility(View.GONE);
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

        ZXingScannerActivity.openScanner(this);
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

    private void callAPIImportWhiteBill(){
        ImportWhiteBillInput inputBill = new ImportWhiteBillInput(this);

        inputBill.setBill(edtBillWhite.getText().toString());

        int posHTTT = spinHTTT.getSelectedItemPosition();
        String mkh = SCurrentUser.getCurrentUser(this).getValue_staff();
        if (mListHTTT.get(posHTTT).getId() == 11) {
            mkh = edtMakhSend.getText().toString() != null ? edtMakhSend
                    .getText().toString() : "";
        } else {
            mkh = mkh.substring(0, 2) + "0000";
        }
        inputBill.setSenderCode(mkh);

        inputBill.setReceiverCode("");
        inputBill.setSender("");
        inputBill.setReceiver("");
        inputBill.setSenderAddress("");

        inputBill.setReceiverAddress(edtDCGui.getText().toString());
        inputBill.setSenderContact("");
        inputBill.setReceiverContact(edtSodienthoai.getText().toString());
        inputBill.setSenderProvince(input.getSenderProvince());
        inputBill.setReceiverProvince(input.getReceiverProvince());
        inputBill.setSenderDistrict(input.getSenderDistrict());
        inputBill.setReceiverDistrict(input.getReceiverDistrict());

        inputBill.setIsDocument("");
        inputBill.setIsPro("");
        inputBill.setIsOther("");
        inputBill.setDescription("");
        inputBill.setThProductCode("");

        inputBill.setThPaymentCode(mListHTTT.get(posHTTT).getId() + "");
        inputBill.setService(input.getService());
        inputBill.setPackageNo(input.getPackageNo());
        inputBill.setWeight(input.getWeight());
        inputBill.setDimensionWeight(input.getDimensionWeight());
        inputBill.setLength(input.getPackageLong());
        inputBill.setWide(input.getWide());
        inputBill.setHigh(input.getHigh());
        inputBill.setItemQty(input.getItemQty());
        inputBill.setCodAmt(input.getCodAmt());
        inputBill.setInsuranceFee(formatMoney(edtFeeBaoHiem.getText().toString()));
        inputBill.setPackingFee(formatMoney(edtFeeDongGoi.getText().toString()));
        inputBill.setLiftingFee(formatMoney(edtFeeNangHa.getText().toString()));
        inputBill.setDeliveryFee(0);
        inputBill.setOtherAmt(formatMoney(edtFeeKhac.getText().toString()));
        inputBill.setPostage(formatMoney(edtFeeCuocChinh.getText().toString()));
        inputBill.setSuburbsFee(formatMoney(edtFeeNTX.getText().toString()));

        String data = new Gson().toJson(inputBill);

        new ImportWhiteBillAPI(this, data).execute();

    }

    @Override
    public void onSuccess() {
        Message.makeToastSuccess(ManagerBillWhiteActivity.this,
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

    public boolean myValidate() {
        String bill = edtBillWhite.getText().toString() != null ? edtBillWhite
                .getText().toString() : "";
        // String nameKh = edtNameSend.getText().toString() != null ?
        // edtNameSend
        // .getText().toString() : "";
        if (bill.equals("")) {
            Message.makeToastWarning(this, getString(R.string.toast_error_null_id_bill));
            return false;
        } else if (spinHTTT.getSelectedItem().toString().equals("11")) {
            if (edtMakhSend.getText().toString().equals("")) {
                Message.makeToastWarning(this, getString(R.string.toast_error_null_id_customer));
                //myast.makeWarning("Bạn chưa nhập mã khách hàng.");
                return false;
            } else if (edtMakhSend.getText().toString().length() < 5) {
                Message.makeToastWarning(this, getString(R.string.toast_error_min_id_customer));
                //myast.makeWarning("Mã khách hàng tối thiểu 5 ký tự.");
                return false;
            } else if (edtMakhSend.getText().toString().length() == 5) {
                if (edtMakhSend.getText().toString().substring(2, 5)
                        .equals("000")) {
                    Message.makeToastWarning(this,
                            getString(R.string.toast_error_id_customer_stamp));
                    //myast.makeWarning("Đây là mã khách vãng lai.");
                    return false;
                }
            } else if (edtMakhSend.getText().toString().length() == 6) {
                if (edtMakhSend.getText().toString().substring(2, 6)
                        .equals("0000")) {
                    Message.makeToastWarning(this,
                            getString(R.string.toast_error_id_customer_stamp));
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
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            String m = ZXingScannerActivity.getCodeAfterScan(requestCode, resultCode, data);

            if (m != null) {
                edtBillWhite.setEnabled(false);
                edtBillWhite.setText(m);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
}
// Nguyễn Ngọc Giao