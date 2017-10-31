package vn.ntlogistics.app.ordermanagement.Olds.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.MyTextWatcher;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.BaseLocation;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.City;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.District;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.UpdatePinkBillAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Olds.putDataToServer;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.UpdatePinkBillInput;
import vn.ntlogistics.app.ordermanagement.Olds.FTPFile.MyFTPClientFunctions;
import vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary.ItemBill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.SplashScreenActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ZXingScannerActivity;

public class SendBillActivity extends BaseActivity implements OnClickListener,
		OnItemSelectedListener {

	private static final int REQUEST_CODE = 99;
	private static final int REQUEST_CODE_TAKE_PICTURE = 198;
	private static final int REQUEST_CODE_SCAN = 197;
	private static String accHong;
	private static int posDis;
	private LinearLayout layoutSaveImg;

	private MyFTPClientFunctions ftpclient = null;

	private ImageView ivPhoto;
	private TextView tvhardmakh, tvAccountPink, tvNameImg;
	private EditText edtBill;
	private EditText edtMaKh, edtMoney, edtMoneyCod;
	private AutoCompleteTextView edtNameKh;
	private Button btnSend, btnScan, btnPicBill;
	private Spinner spinCity, spinDistrict;
	private CheckBox cbNNTTS;

	private ArrayList<City> mListCity;
	private ArrayList<District> mListDis;
	private ArrayList<String> list_giao = new ArrayList<String>();
	/*private ArrayList<String> list_value = new ArrayList<String>();
	private ArrayList<String> list_Name_District = new ArrayList<String>();
	*/
	public static int vt_city = 0;
	String P_areacode = "";
	String billCheck;
	// private MyDB db;
	boolean checkBox = false;
	int backFail;
	int oldPos;
	String loz = "";
	private static String saveBill;

	private String 			imagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_bill);
		overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSendBill);
		toolbar.setTitle(getString(R.string.enter_pink_bill));
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		ftpclient = new MyFTPClientFunctions();

		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);*/
		tvhardmakh = (TextView) findViewById(R.id.tvhardmakh);
		tvAccountPink = (TextView) findViewById(R.id.tvAccountPink);
		tvNameImg = (TextView) findViewById(R.id.tvNameImg);

		layoutSaveImg = (LinearLayout) findViewById(R.id.layoutSaveImg);

		mListCity = new ArrayList<>();
		mListDis = new ArrayList<>();

		accHong = SCurrentUser.getCurrentUser(this).getValue_staff();
		try {
			posDis = Integer.parseInt(SCurrentUser.getCurrentUser(this).getMyBank());
		} catch (NumberFormatException e) {
			posDis = 0;
		}

		tvAccountPink.setText(SCurrentUser.getCurrentUser(this).getValue_staff());

		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
		edtBill = (EditText) findViewById(R.id.edtBill);
		edtMaKh = (EditText) findViewById(R.id.edtMaKh);
		edtNameKh = (AutoCompleteTextView) findViewById(R.id.edtNameKh);
		autoComplete();
		edtMoney = (EditText) findViewById(R.id.edtMoney);
		edtMoney.addTextChangedListener(new MyTextWatcher(edtMoney, 0));
		edtMoneyCod = (EditText) findViewById(R.id.edtMoneyCod);
		edtMoneyCod.addTextChangedListener(new MyTextWatcher(edtMoneyCod, 0));

		btnSend = (Button) findViewById(R.id.btnSend);
		btnScan = (Button) findViewById(R.id.btnScan);
		btnPicBill = (Button) findViewById(R.id.btnPicBill);
		spinCity = (Spinner) findViewById(R.id.spinCity);
		spinDistrict = (Spinner) findViewById(R.id.spinDistrict);
		cbNNTTS = (CheckBox) findViewById(R.id.cbNNTTS);
		if (cbNNTTS.isChecked()) {
			tvhardmakh.setVisibility(View.VISIBLE);
			edtMaKh.setVisibility(View.VISIBLE);
			checkBox = true;
		}
		changeBox();

		btnScan.setOnClickListener(this);
		//btnPicBill.setOnClickListener(this);

		getData();
		getDataBillFail();
		btnSend.setOnClickListener(this);
		//
		pullDB_ToSpinner();

	}

	public void changeBox() {
		cbNNTTS.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					tvhardmakh.setVisibility(View.VISIBLE);
					edtMaKh.setVisibility(View.VISIBLE);
					checkBox = true;
				} else {
					tvhardmakh.setVisibility(View.GONE);
					edtMaKh.setVisibility(View.GONE);
					edtMaKh.setText("");
					checkBox = false;
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {
			switch (requestCode){
				case REQUEST_CODE:
					layoutSaveImg.setVisibility(View.VISIBLE);
					tvNameImg.setText(tvAccountPink.getText().toString().concat("^")
							.concat(saveBill).concat(".jpg"));
					Variables.LST_ItemBill.clear();
					break;
				case REQUEST_CODE_TAKE_PICTURE:
					layoutSaveImg.setVisibility(View.VISIBLE);
					tvNameImg.setText(tvAccountPink.getText().toString().concat("^")
							.concat(saveBill).concat(".jpg"));

					break;
			}
			try {
				String m = ZXingScannerActivity.getCodeAfterScan(requestCode, resultCode, data);
				if (m != null) {
					edtBill.setEnabled(false);
					edtBill.setText(m);
				}

			} catch (NullPointerException e) {
				e.getMessage();
			}
		}
	}

	public String formatMoney(String input) {
		String del = "";
		char check[] = { ' ', ',' };

		for (int j = 0; j < check.length; j++) {
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) == check[j]) {
					input = input.replaceAll("\\" + input.charAt(i) + "", del);
				}
			}
		}

		return input;
	}

	public void autoComplete() {

		Cursor c = SSqlite.getInstance(this).getAllDataFromTable(Variables.TBL_GUEST);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			list_giao.add(c.getString(
					c.getColumnIndex(Variables.KEY_GUEST_NAME)).toString());
			c.moveToNext();
		}

		edtNameKh.addTextChangedListener(new MyTextWatcher(edtNameKh, 1));
		edtNameKh.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list_giao));

	}

	public void getData() {
		/*try {
			int getFromScanFragment = getIntent().getExtras().getInt("fromMyCrop");
			if (getFromScanFragment == 9) {
				edtBill.setText(saveBill);
				Intent intent = new Intent(this, ScanActivity.class);
				intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, 4);
				startActivityForResult(intent, REQUEST_CODE);
			}
		} catch (NullPointerException e) {
			e.getMessage();
		}*/
	}

	public void getDataBillFail() {

		try {
			int dm = getIntent().getExtras().getInt("key_dm");
			String bill = getIntent().getExtras().getString("key_bill");
			String tkh = getIntent().getExtras().getString("key_tkh");
			String mkh = getIntent().getExtras().getString("key_mkh");
			String money = getIntent().getExtras().getString("key_money");
			String moneyCOD = getIntent().getExtras().getString("key_moneyCOD");
			String tinh = getIntent().getExtras().getString("key_tinh");
			String quan = getIntent().getExtras().getString("key_quan");
			Log.d("dm", dm + " " + tkh + " " + mkh + " " + bill + " "
					+ P_areacode.toString().trim().concat("0000"));
			billCheck = bill;
			String mnv = SCurrentUser.getCurrentUser(getApplicationContext()).getPublickey();
			/*------------------KEY_STAFF_ID----------------*//*
			Cursor cS = db.getAllDataFromTable(Variables.TBL_STAFF,
					Variables.KEY_STAFF_ID, "1=1");
			cS.moveToFirst();

			while (!cS.isAfterLast()) {
				mnv = cS.getString(cS.getColumnIndex(Variables.KEY_VALUE_STAFF));
				cS.moveToNext();
			}*/

			String cut = mnv.substring(0, 2);
			if (dm == 93) {
				backFail = 1;
				if (!mkh.equals(cut.toString().trim().concat("0000"))) {
					cbNNTTS.setChecked(true);
					Log.d("dung", "dung");
				} else {
					cbNNTTS.setChecked(false);
					Log.d("sai", "sai");
				}
				getDTfromLV(bill, tkh, money, moneyCOD, mkh);
			}

		} catch (NullPointerException e) {
			e.getMessage();
		}
	}

	public void getDTfromLV(String bill, String tkh, String money,
			String moneyCOD, String mkh) {
		edtBill.setText(bill);
		edtNameKh.setText(tkh);
		edtMoney.setText(money);
		edtMoneyCod.setText(moneyCOD);
		if (cbNNTTS.isChecked()) {
			edtMaKh.setText(mkh);
		} else {
			edtMaKh.setText("");
		}
		btnSend.setText("Sửa");

	}

	public boolean updateBillFail() {
		String bill = edtBill.getText().toString();
		String mkh = edtMaKh.getText().toString();
		String tkh = edtNameKh.getText().toString();
		String tinh = P_areacode.toString().trim();
		String quan = spinDistrict.getSelectedItem().toString();
		String money = edtMoney.getText().toString();
		String moneyCod = edtMoneyCod.getText().toString();
		String failmkh = tinh.concat("0000");
		String mkhChecked;
		if (mkh.equals("") || mkh == "") {
			mkhChecked = failmkh;
		} else {
			if (mkh.equals(failmkh) || mkh == failmkh) {
				Message.makeToastWarning(this,
						getString(R.string.toast_error_id_customer));
				//makeWarning("Mã khách hàng không hợp lệ.");
				return false;
			} else {
				mkhChecked = mkh;
			}
		}
		tkh = tkh.toUpperCase();
		if (money == "" || money.equals(""))
			money = "0";
		if (moneyCod == "" || moneyCod.equals(""))
			moneyCod = "0";
		String values[] = { bill, mkhChecked, tkh, tinh, quan, money, moneyCod };
		String fields[] = { Variables.KEY_BILL, Variables.KEY_MKH,
				Variables.KEY_TKH, Variables.KEY_TINH, Variables.KEY_QUAN,
				Variables.KEY_MONEY, Variables.KEY_MONEYCOD };
		Log.d("", "Bill: " + bill + ",Mã khách: " + mkhChecked + ",Tên: " + tkh
				+ ",Tỉnh: " + tinh + ",Quận: " + quan + ",Cước chính: " + money
				+ " " + ",Cod: " + moneyCod);
		boolean c = SSqlite.getInstance(this).updateData4Table(Variables.TBL_BILLFAIL,
				Variables.KEY_BILL, billCheck, fields, values);
		return true;
	}

	public void pullDB_ToSpinner() {

		//String mnv = SCurrentUser.getCurrentUser(this).getPublickey();
		/*------------------KEY_STAFF_ID----------------*/
		/*Cursor cS = db.getAllDataFromTable(Variables.TBL_STAFF,
				Variables.KEY_STAFF_ID, "1=1");
		cS.moveToFirst();

		while (!cS.isAfterLast()) {
			mnv = cS.getString(cS.getColumnIndex(Variables.KEY_VALUE_STAFF));
			cS.moveToNext();
		}*/

		//String cut = mnv.substring(0, 2);
		//P_areacode = cut;
		//Log.d("cut", cut);
		/*------------------Positon----------------*/
		/*String clause = " " + Variables.KEY_AREACODE + " = " + "'" + cut + "'";
		String id_positon = "";
		Cursor myCu = db.getAllDataFromTable(Variables.TBL_CITY,
				Variables.KEY_CITY_ID, clause);
		db.getPositionByAreaCode(cut)
		myCu.moveToFirst();
		while (!myCu.isAfterLast()) {
			id_positon = myCu.getString(myCu
					.getColumnIndex(Variables.KEY_ID_POSITON));
			myCu.moveToNext();
		}*/
		//int id_position = SSqlite.getInstance(this).getIdLocationInCity();
		/*Log.d("", "Id_position: " + id_position);

		*//*------------------Alldata----------------*//*
		String abc = " 1=1 order by " + Variables.KEY_AREACODE + " ASC";
		Cursor c = db.getAllDataFromTable(Variables.TBL_CITY,
				Variables.KEY_CITY_ID, abc);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			list_ID.add(c.getString(c.getColumnIndex(Variables.KEY_CITY_ID)));
			list_Name
					.add(c.getString(c.getColumnIndex(Variables.KEY_NAME_CITY)));
			list_areacode.add(c.getString(c
					.getColumnIndex(Variables.KEY_AREACODE)));
			c.moveToNext();
		}*/

		mListCity = SSqlite.getInstance(this).getListCity();
		String areaCode = SCurrentUser.getCurrentUser(this).getValue_staff().substring(0,2);
		int areaCodeCity = 50;
		try {
			areaCodeCity = Integer.parseInt(areaCode);
		} catch (NumberFormatException e) {
		}
		int positionCity = -1;
		for (City item : mListCity){
			positionCity++;
			if (item.getAreacode() == areaCodeCity)
				break;
		}

		setupSpinner(spinCity, mListCity);

		spinCity.setSelection(positionCity);
		spinCity.setOnItemSelectedListener(this);

	}

	public void pullDistrict(String id) {
		/*String Wclause = Variables.KEY_FK_CITY_ID + "=" + id;

		Cursor c = db.getAllDataFromTable(Variables.TBL_DISTRICT,
				Variables.KEY_DISTRICT_ID, Wclause);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			list_Name_District.add(c.getString(
					c.getColumnIndex(Variables.KEY_NAME_DISTRICT)).toString());
			list_value.add(c.getString(
					c.getColumnIndex(Variables.KEY_DISTRICT_VALUE)).toString());
			c.moveToNext();
		}
		CustomSpinnerDistrict CSD = new CustomSpinnerDistrict(this, list_value,
				list_Name_District);
		spinDistrict.setAdapter(CSD);*/
		mListDis = SSqlite.getInstance(this).getListDistrictByCidyID(id);
		setupSpinner(spinDistrict, mListDis);
		spinDistrict.setSelection(posDis);
		spinDistrict.setOnItemSelectedListener(this);

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_send_bill, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		/*if (id == R.id.btnback) {
			if (backFail != 1) {
				Intent intent = new Intent(this, Quanlynhap.class);
				Bundle a = new Bundle();
				a.putInt("keyloz", 4);
				intent.putExtras(a);
				startActivity(intent);
				finish();
			} else {
				Intent intent = new Intent(this, BillFail.class);
				startActivity(intent);
			}

		}*/
		if (id == R.id.btnrf) {
			myRF();
		}
		return super.onOptionsItemSelected(item);
	}

	public void myRF() {
		edtBill.setText("");
		edtBill.setEnabled(true);
		edtNameKh.setText("");
		edtMoney.setText("");
		edtMoneyCod.setText("");
		edtMaKh.setText("");
		tvNameImg.setText("");
		layoutSaveImg.setVisibility(View.GONE);
	}

	private void sendImage() {
		if (isOnline(this)) {
			Log.d("NETWORK", "Net Fine");
			connectFTP();
		} else {
			Log.d("NETWORK", "Net Fail");
		}
	}

	private void connectFTP() {
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
		final String desFileName = "billupload/"
				+ tvAccountPink.getText().toString().concat("^")
						.concat(edtBill.getText().toString()).concat(".jpg");
		String des[] = { host, username, password, port,
                imagePath, desFileName, desDirectory };
		// final File fdelete = new File(srcFilePath);

		new SendImageBill().execute(des);

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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnScan) {
			Log.d("Scan", "This is Scan Bill");
			ScanNew();
		}
		else if (v.getId() == R.id.btnPicBill) {
			String bill = edtBill.getText().toString();
			if (bill.equalsIgnoreCase("")) {
				Message.makeToastWarning(this,
						getString(R.string.toast_error_null_id_bill));
				//makeWarning("Bạn chưa nhập số vận đơn.");
			} else {
				saveBill = bill;
				String send = tvAccountPink.getText().toString().concat("^")
						.concat(bill);
				ItemBill itemBill = new ItemBill();
				itemBill.setBill(send.toString());
				Variables.LST_ItemBill.add(itemBill);
				//getImage();
				Intent take = Commons.takeAPicture(this, send);
				imagePath = take.getExtras().get("pathImage").toString();
				startActivityForResult(take, REQUEST_CODE_TAKE_PICTURE);
			}

		}
		else if (v.getId() == R.id.btnSend) {
			if (backFail == 1) {
				// Toast.makeText(this, "SỬA", Toast.LENGTH_SHORT).show();
				if (checkBill()) {
					if (updateBillFail()) {
						Intent intent = new Intent(this, BillFailActivity.class);
						startActivity(intent);
						finish();
					}
				}
			} else {
				if (checkBill()) {
					if(Commons.hasConnection(this)){
						callAPIUpdatePinkBill();
					}
					else {
						//Lưu vào Sqlite
						dataUnSend();
					}
					//NetAsync(v);
				}
			}
			// sendImage();
		}
	}

	public void sendAllData() throws JSONException {
		String bill = edtBill.getText().toString();
		String mkh = edtMaKh.getText().toString();
		String tkh = edtNameKh.getText().toString().trim();
		String tinh = mListCity.get(spinCity.getSelectedItemPosition()).getAreacode()+"";
		String quan = mListDis.get(spinDistrict.getSelectedItemPosition()).getValue()+"";
		String money = formatMoney(edtMoney.getText().toString());
		String moneyCod = formatMoney(edtMoneyCod.getText().toString());
		String failmkh = tinh.concat("0000");
		String mkhChecked;
		if (mkh.equals("") || mkh == "") {
			mkhChecked = failmkh;
		} else {
			if (mkh.equals(failmkh) || mkh == failmkh) {
				Message.makeToastWarning(this,
						getString(R.string.toast_error_id_customer));
				//makeWarning("Mã khách hàng không hợp lệ.");
				return;
			} else {
				mkhChecked = mkh;
			}
		}
		tkh = tkh.toUpperCase();

		if (money == "" || money.equals(""))
			money = "0";
		if (moneyCod == "" || moneyCod.equals(""))
			moneyCod = "0";

		String keys[] = { ConstantURLs.UPDATE_PINK_BILL,
				SCurrentUser.getCurrentUser(this).getPublickey(),
				bill, mkhChecked,
				tkh, tinh, quan, money, moneyCod };
		new BillPinkTask().execute(keys);
	}

	//TODO: Call API -------------------------------------------------

	private void callAPIUpdatePinkBill(){
		try {
			UpdatePinkBillInput input = new UpdatePinkBillInput(
                    this,
                    edtBill.getText().toString(),
                    edtMaKh.getText().toString(),
                    edtNameKh.getText().toString().trim(),
                    null,
                    formatMoney(edtMoney.getText().toString()),
                    formatMoney(edtMoneyCod.getText().toString()),
                    mListCity.get(spinCity.getSelectedItemPosition()).getAreacode()+"",
                    mListDis.get(spinDistrict.getSelectedItemPosition()).getValue()+""
            );

			String data = new Gson().toJson(input);

			new UpdatePinkBillAPI(this, data, false, input.getBill()).execute();
		} catch (Exception e) {
		}
	}

	//TODO: Call API -------------------------------------------------End/

	/**
	 * -------------------------Async Task Check Internet connection------------
	 **/
	private class NetCheck extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null)
					for (int i = 0; i < info.length; i++)
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean check) {
			// TODO Auto-generated method stub
			Log.d("", "INTERNET: " + check);
			if (check == true) {
				try {
					sendAllData();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			} else {
				dataUnSend();
			}
		}
	}

	public void NetAsync(View view) {
		new NetCheck().execute();
	}

	public void dataUnSend() {
		String bill = edtBill.getText().toString();
		String mkh = edtMaKh.getText().toString();
		String tkh = edtNameKh.getText().toString().trim();
		String tinh = mListCity.get(spinCity.getSelectedItemPosition()).getAreacode()+"";
		String quan = mListDis.get(spinDistrict.getSelectedItemPosition()).getValue()+"";
		String money = formatMoney(edtMoney.getText().toString());
		String moneyCod = formatMoney(edtMoneyCod.getText().toString());
		String failmkh = tinh.concat("0000");
		String isDO = "N";

		String mkhChecked;
		if (mkh.equals("") || mkh == "") {
			mkhChecked = failmkh;
		} else {
			if (mkh.equals(failmkh) || mkh == failmkh) {
				Message.makeToastWarning(this,
						getString(R.string.toast_error_id_customer));
				//makeWarning("Mã khách hàng không hợp lệ.");
				return;
			} else {
				mkhChecked = mkh;
			}
		}
		tkh = tkh.toUpperCase();
		/*Cursor c = db.getAllDataFromTable(Variables.TBL_STAFF,
				Variables.KEY_STAFF_ID, "1=1");
		c.moveToFirst();*/

		if (money == "" || money.equals(""))
			money = "0";
		if (moneyCod == "" || moneyCod.equals(""))
			moneyCod = "0";
		String values[] = { bill, mkhChecked, tkh, tinh, quan, money, moneyCod,
				isDO };
		String keys[] = { Variables.KEY_BILL, Variables.KEY_MKH,
				Variables.KEY_TKH, Variables.KEY_TINH, Variables.KEY_QUAN,
				Variables.KEY_MONEY, Variables.KEY_MONEYCOD, Variables.KEY_ISDO };

		Cursor cSend = SSqlite.getInstance(this).getAllDataFromTable(Variables.TBL_BILLFAIL);
		cSend.moveToFirst();
		ArrayList<String> listCheck = new ArrayList<String>();
		while (!cSend.isAfterLast()) {
			listCheck.add(cSend.getString(cSend
					.getColumnIndex(Variables.KEY_BILL)));
			cSend.moveToNext();
		}
		boolean check = true;
		for (int i = 0; i < listCheck.size(); i++) {
			if (listCheck.get(i).equals(bill) || listCheck.get(i) == bill)
				check = false;
		}
		long ks = 0;
		if (check == true) {
			ks = SSqlite.getInstance(this).insertdata(Variables.TBL_BILLFAIL, keys, values);
		}
		if (ks > 0) {
			Log.d("", "Insert thành công");
			Message.makeToastError(this,
					getString(R.string.toast_save_bill_off));
			/*makeFail("Không có kết nối Internet." + "\n"
					+ "Vận đơn của bạn đã được lưu lại, vui lòng gửi lại sau!");*/
			myRF();
			return;
		} else
			Log.d("", "Lỗi cmnr");
		// makeFail("Vận đơn đã được lưu");
		myRF();
	}

	/*-------------------------Async Task--------------------------------*/
	private class BillPinkTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... keys) {

			putDataToServer put = new putDataToServer();
			publishProgress(1);
			return put.putData(keys[0], keys[1], keys[2], keys[3], keys[4],
					keys[5], keys[6], keys[7], keys[8]);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			btnSend.setText("Đang gửi...");

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Log.d("", "Result:aa" + result + "aaaaa");
			btnSend.setText("Gửi");
			showMes(result);
		}

	}

	/*----------------------------------------------------------------------*/
	private class SendImageBill extends AsyncTask<String, Integer, Boolean> {
		@Override
		protected Boolean doInBackground(String... pr) {
			// TODO Auto-generated method stub
			boolean status = false;
			status = ftpclient.ftpConnect(pr[0], pr[1], pr[2],
					Integer.parseInt(pr[3]));
			if (status == true) {
				boolean a = ftpclient.ftpUpload(pr[4], pr[5], pr[6], SendBillActivity.this);
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
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("", "Result ftpclient: " + result + "aaaaa");
			if (result) {
				Toast.makeText(getApplicationContext(), "Gửi ảnh thành công",
						Toast.LENGTH_LONG).show();
				layoutSaveImg.setVisibility(View.GONE);
				tvNameImg.setText("");
				ftpclient.ftpDisconnect();
			} else {
				Toast.makeText(getApplicationContext(), "Không gửi được ảnh",
						Toast.LENGTH_LONG).show();
				ftpclient.ftpDisconnect();
			}

		}

	}

	public void showMes(String mes) {
		if (mes.equals("-1") || mes == "-1") {
			Message.makeToastError(this,
					getString(R.string.error_null_field));
			//makeFail("Bạn gửi thiếu thông tin các trường bắt buộc.");
			return;
		} else if (mes.equals("1") || mes == "1") {
			Message.makeToastError(this,
					getString(R.string.toast_error_key_api));
			//makeFail("Sai Key.");
			Variables.fuckAcc = 1;
			String oldKey = SCurrentUser.getCurrentUser(this).getPublickey();
			/*Cursor c = db.getAllDataFromTable(Variables.TBL_STAFF,
					Variables.KEY_STAFF_ID, "1=1");
			c.moveToFirst();
			while (!c.isAfterLast()) {
				oldKey = c
						.getString(c.getColumnIndex(Variables.KEY_PUBLIC_KEY));
				c.moveToNext();
			}*/
			SSqlite.getInstance(this).deleteUser();
			Commons.restartApp(this, SplashScreenActivity.class);
			/*boolean a = db.deleteDataFromTable(Variables.TBL_STAFF,
					Variables.KEY_PUBLIC_KEY, oldKey);*/
			return;
		} else if (mes.equals("2") || mes == "2") {
			Message.makeToastWarning(this,
					getString(R.string.toast_success_update_bill));
			//makeWarning("Vận đơn đã được cập nhật.");
			return;
		} else if (mes.equals("-2") || mes == "-2") {
			Message.makeToastWarning(this,
					getString(R.string.toast_error_bill_null));
			//makeWarning("Vận đơn không tồn tại.");
			return;
		} else if (mes.equals("-3") || mes == "-3" || mes.equals("-4")
				|| mes == "-4") {
			Message.makeToastWarning(this,
					getString(R.string.toast_success_update_bill_1));
			//makeWarning("Vận đơn đã được nhập liệu.");
			return;
		} else if (mes.equals("-5") || mes == "-5") {
			Message.makeToastError(this);
			//makeFail("Đã xảy ra lỗi hệ thống.");
			return;
		} else if (mes.equals("-6") || mes == "-6") {
			Message.makeToastError(this,
					getString(R.string.toast_error_input_id));
			//makeFail("Mã khách hàng không hợp lệ.");
			return;
		} else if (mes.equals("0") || mes == "0") {
			Message.makeToastSuccess(this,
					getString(R.string.toast_success_send_100));
			//makeSuccess("Gửi thành công. Bạn đã nhận được 100đ vào tài khoản");
			/*if (!tvNameImg.getText().toString().equalsIgnoreCase("")) {
				sendImage();
			}*/
			myRF();
			return;
		}
	}

	/*public void makeWarning(String text) {

		Context context = getApplicationContext();
		LayoutInflater inflater = getLayoutInflater();
		View custom_waring = inflater.inflate(R.layout.custom_toast_warning,
				null);
		TextView tv = (TextView) custom_waring.findViewById(R.id.tvwarning);
		tv.setText(text);
		Toast myToast = new Toast(context);
		myToast.setView(custom_waring);
		myToast.setGravity(Gravity.LEFT | Gravity.TOP, 0, 95);
		myToast.setDuration(Toast.LENGTH_SHORT);
		myToast.show();

	}

	public void makeFail(String text) {
		Context context = getApplicationContext();
		LayoutInflater inflater = getLayoutInflater();
		View custom_fail = inflater.inflate(R.layout.custom_toast_fail, null);
		TextView tv = (TextView) custom_fail.findViewById(R.id.tvfail);
		tv.setText(text);
		Toast myToast = new Toast(context);
		myToast.setView(custom_fail);
		myToast.setGravity(Gravity.LEFT | Gravity.TOP, 0, 95);
		myToast.setDuration(Toast.LENGTH_SHORT);
		myToast.show();

	}

	public void makeFailLong(String text) {
		Context context = getApplicationContext();
		LayoutInflater inflater = getLayoutInflater();
		View custom_fail = inflater.inflate(R.layout.custom_toast_fail, null);
		TextView tv = (TextView) custom_fail.findViewById(R.id.tvfail);
		tv.setText(text);
		Toast myToast = new Toast(context);
		myToast.setView(custom_fail);
		myToast.setGravity(Gravity.LEFT | Gravity.TOP, 0, 95);
		myToast.setDuration(Toast.LENGTH_LONG);
		myToast.show();
	}

	public void makeSuccess(String text) {
		Context context = getApplicationContext();
		LayoutInflater inflater = getLayoutInflater();
		View custom_success = inflater.inflate(R.layout.custom_toast_success,
				null);
		TextView tv = (TextView) custom_success.findViewById(R.id.tvsuccess);
		tv.setText(text);
		Toast myToast = new Toast(context);
		myToast.setView(custom_success);
		myToast.setGravity(Gravity.LEFT | Gravity.TOP, 0, 95);
		myToast.setDuration(Toast.LENGTH_LONG);
		myToast.show();
	}*/

	public void ScanNew() {
		/*Intent intent = new Intent(this, ScanMSActivity.class);
		Bundle b = new Bundle();
		b.putInt("cmnt", Variables.BILLNT);
		intent.putExtras(b);
		startActivityForResult(intent, REQUEST_CODE_SCAN);*/

		ZXingScannerActivity.openScanner(this);
	}

	public boolean checkBill() {
		if (edtBill.getText().toString() == ""
				|| edtBill.getText().toString().equals("")) {
			Message.makeToastWarning(this,
					getString(R.string.toast_error_null_id_bill));
			//makeWarning("Bạn chưa nhập số vận đơn.");
			return false;
		} else if (edtNameKh.getText().toString() == ""
				|| edtNameKh.getText().toString().equals("")) {
			Message.makeToastWarning(this,
					getString(R.string.toast_error_null_name_customer));
			//makeWarning("Bạn chưa nhập tên khách hàng.");
			return false;
		} else if (checkBox) {
			if (edtMaKh.getText().toString() == ""
					|| edtMaKh.getText().toString().equals("")) {
				Message.makeToastWarning(this,
						getString(R.string.toast_error_null_id_customer));
				return false;
			}

		}
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (parent.getId() == R.id.spinCity) {
			/*TextView areacode = (TextView) view.findViewById(R.id.tv_areacode);
			String areacode_SL = areacode.getText().toString();

			TextView c_city_id = (TextView) view.findViewById(R.id.tv_idCity);
			String id_city_SL = c_city_id.getText().toString();
			if (id_city_SL != null && id_city_SL != "") {
				P_areacode = areacode_SL;

				if (!areacode_SL.equals(accHong.substring(0, 2))) {
					posDis = 0;
				}

			}*/
			pullDistrict(mListCity.get(position).getId()+"");

		}
		if (parent.getId() == R.id.spinDistrict) {

			int a = spinDistrict.getSelectedItemPosition();
			String loz = String.valueOf(a);
			String[] fields = { Variables.KEY_MYBANK };
			String[] values = { loz };
			Log.d("", "LOZ: " + loz);
			Log.d("", "P_areacode: " + P_areacode);
			if (P_areacode.equals(accHong.substring(0, 2))) {
				boolean up = SSqlite.getInstance(this).updateData4Table(Variables.TBL_STAFF,
						Variables.KEY_VALUE_STAFF, accHong, fields, values);
			} else {
				posDis = 0;
			}
			/*TextView value = (TextView) view
					.findViewById(R.id.tv_value_district);
			String value_dis_SL = value.getText().toString();
			TextView name = (TextView) view.findViewById(R.id.tv_name_district);
			String name_dis_SL = name.getText().toString();*/

		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
	}
}
// Nguyễn Ngọc Giao
