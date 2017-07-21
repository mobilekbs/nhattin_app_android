package vn.ntlogistics.app.ordermanagement.Olds.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.ConfirmBPBillAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ConfirmBPBillInput;
import vn.ntlogistics.app.ordermanagement.R;

public class BillDOActivity extends BaseActivity implements OnClickListener {
	public static final String TAG = "BillDOActivity";

	public static final int					REQUEST_CODE_SCAN = 201;

	EditText edtBill_DO, edtTL_DO, edtSL_DO, edtTLQD_DO, edtSokien_DO;
	Button btnScan_DO, btnSend_DO;
	String billCheck;
	int backFail;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_do);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBillDO);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setDisplayShowTitleEnabled(false);
		edtBill_DO = (EditText) findViewById(R.id.edtBill_DO);
		edtTL_DO = (EditText) findViewById(R.id.edtTL_DO);
		edtSL_DO = (EditText) findViewById(R.id.edtSL_DO);
		edtTLQD_DO = (EditText) findViewById(R.id.edtTLQD_DO);
		edtSokien_DO = (EditText) findViewById(R.id.edtSokien_DO);

		btnScan_DO = (Button) findViewById(R.id.btnScan_DO);
		btnSend_DO = (Button) findViewById(R.id.btnSend_DO);

		btnScan_DO.setOnClickListener(this);
		btnSend_DO.setOnClickListener(this);

		getData();
		getBillDoFail();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_bill_do, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*// if (id == R.id.to_billdofail) {
		// Intent intent = new Intent(this, BillFail.class);
		// startActivity(intent);
		// finish();
		// }
		if (id == R.id.btnbackDO) {
			if (backFail != 1) {
				Intent intent = new Intent(this, Quanlynhap.class);
				Bundle a = new Bundle();
				a.putInt("keyloz", 3);
				intent.putExtras(a);
				startActivity(intent);
				finish();
			} else {
				Intent intent = new Intent(this, BillFail.class);
				startActivity(intent);
				finish();
			}
		}*/
		if (id == R.id.btnrfDO) {
			myRF();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnScan_DO) {
			ScanDO();
		}
		if (v.getId() == R.id.btnSend_DO) {
			if (backFail == 1 && myValidate()) {
				updateBillFail();
				/*Intent intent = new Intent(this, BillFailActivity.class);
				startActivity(intent);*/
				finish();
			} else {
				if (myValidate()) {
					if(Commons.hasConnection(this))
						callAPIConfirmBPBill();
					else{
						Message.makeToastError(this,
								getString(R.string.toast_save_bill_off));
						dataUnSend();
						myRF();
					}
				}
			}
		}
	}

	public void ScanDO() {
		Intent intent = new Intent(this, ScanMSActivity.class);
		/*Bundle b = new Bundle();
		b.putInt("cmdo", Variables.BILLDO);
		intent.putExtras(b);*/
		startActivityForResult(intent, REQUEST_CODE_SCAN);
	}

	public void getData() {
		try {
			String m = getIntent().getExtras().getString("a").toString();
			if (!m.equals("") || m != "") {
				edtBill_DO.setEnabled(false);
				edtBill_DO.setText(m);
			}
		} catch (NullPointerException e) {
			e.getMessage();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == REQUEST_CODE_SCAN){
				Bundle b = data.getExtras();
				String m = null;
				try {
					m = b.getString("symbol").toString();
				} catch (Exception e) {
				}
				if (m != null) {
					edtBill_DO.setEnabled(false);
					edtBill_DO.setText(m);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void getBillDoFail() {
		try {
			int loz = getIntent().getExtras().getInt("key_loz");

			String billDO = getIntent().getExtras().getString("key_billDO");
			String TL = getIntent().getExtras().getString("key_TL");
			String SL = getIntent().getExtras().getString("key_SL");
			String SK = getIntent().getExtras().getString("key_SK");
			String TLQD = getIntent().getExtras().getString("key_TLQD");
			Log.d("dm2", loz + " " + billDO + " " + TL + " " + SL + "  " + TLQD
					+ "   " + SK);
			billCheck = billDO;
			if (loz == 69) {
				backFail = 1;
				getDTfromLV(billDO, TL, SL, TLQD, SK);
			}

		} catch (NullPointerException e) {
			e.getMessage();
		}
	}

	private void getDTfromLV(String billDO, String tL, String sL, String tLQD,
			String SK) {
		// TODO Auto-generated method stub
		edtBill_DO.setText(billDO);
		edtTL_DO.setText(tL);
		edtSL_DO.setText(sL);
		edtTLQD_DO.setText(tLQD);
		edtSokien_DO.setText(SK);
		btnSend_DO.setText("Sửa");
	}

	public void updateBillFail() {

		String billDO = edtBill_DO.getText().toString();
		String TL = edtTL_DO.getText().toString();
		String SL = edtSL_DO.getText().toString();
		String TLQD = edtTLQD_DO.getText().toString().trim();
		String SK = edtSokien_DO.getText().toString();

		String values[] = { billDO, TL, SL, TLQD, SK };
		String fields[] = { Variables.KEY_BILL, Variables.KEY_TL,
				Variables.KEY_SL, Variables.KEY_TLQD, Variables.KEY_SOKIENDO };
		Log.d("", "Bill: " + billDO);
		SSqlite.getInstance(this).updateData4Table(Variables.TBL_BILLFAIL,
				Variables.KEY_BILL, billCheck, fields, values);
		// if (c) {
		// Toast.makeText(this, "Cập nhật thành công.", Toast.LENGTH_SHORT)
		// .show();
		// } else {
		// Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT)
		// .show();
		// }

	}

	public boolean myValidate() {
		String billDO = edtBill_DO.getText().toString();
		String TL = edtTL_DO.getText().toString();
		String SL = edtSL_DO.getText().toString();
		String SK = edtSokien_DO.getText().toString();

		if (billDO.equals("") || billDO == "") {
			Message.makeToastWarning(this,
					getString(R.string.error_do_number_null));
//			myast.makeWarning("Bạn chưa nhập số DO.");
			return false;
		} else if (TL.equals("") || TL == "") {
			Message.makeToastWarning(this,
					getString(R.string.error_weight_null));
//			myast.makeWarning("Bạn chưa nhập trọng lượng.");
			return false;
		} else if (SL.equals("") || SL == "") {
			Message.makeToastWarning(this,
					getString(R.string.error_number_null));
			return false;
		} else if (SK.equals("") || SK == "") {
			Message.makeToastWarning(this,
					getString(R.string.error_number_package_null));
			//myast.makeWarning("Bạn chưa nhập số kiện.");
			return false;
		}
		return true;
	}

	public void callAPIConfirmBPBill(){
		ConfirmBPBillInput input = new ConfirmBPBillInput(this);
		input.setDoCode(edtBill_DO.getText().toString());

		long dimensionWeight = 0;
		try {
			dimensionWeight = Long.parseLong(edtTLQD_DO.getText().toString());
		} catch (NumberFormatException e) {
		}
		input.setDimensionWeight(dimensionWeight);

		long weight = 0;
		try {
			weight = Long.parseLong(edtTL_DO.getText().toString());
		} catch (NumberFormatException e) {
		}
		input.setWeight(weight);

		long itemQty = 0;
		try {
			itemQty = Long.parseLong(edtSL_DO.getText().toString());
		} catch (NumberFormatException e) {
		}
		input.setItemQty(itemQty);

		short packNo = 0;
		try {
			packNo = Short.parseShort(edtSokien_DO.getText().toString());
		} catch (NumberFormatException e) {
		}
		input.setPackNo(packNo);

		input.setIsactive("Y");

		String data = new Gson().toJson(input);
		Log.d(TAG, "input = " + data);
		new ConfirmBPBillAPI(this, data, null, false).execute();
	}

	@Override
	public void onSuccess() {
		myRF();
	}

	public void myRF() {
		edtBill_DO.setEnabled(true);
		edtBill_DO.setText("");
		edtSL_DO.setText("");
		edtTL_DO.setText("");
		edtTLQD_DO.setText("");
		edtSokien_DO.setText("");

	}

	public void dataUnSend() {
		String billDO = edtBill_DO.getText().toString();
		String SK = edtSokien_DO.getText().toString();
		String SL = edtSL_DO.getText().toString();
		String TL = edtTL_DO.getText().toString();
		String TLQD = edtTLQD_DO.getText().toString();
		String isDO = "Y";
		if (TLQD == null || TLQD == "" || TLQD.equals(""))
			TLQD = "0";

		String values[] = { billDO, SL, TL, TLQD, isDO, SK };
		String keys[] = { Variables.KEY_BILL, Variables.KEY_SL,
				Variables.KEY_TL, Variables.KEY_TLQD, Variables.KEY_ISDO,
				Variables.KEY_SOKIENDO };
		Cursor cSend = SSqlite.getInstance(this).getAllDataFromTable(Variables.TBL_BILLFAIL,
				Variables.KEY_BILL_ID, "1=1");
		cSend.moveToFirst();
		ArrayList<String> listCheck = new ArrayList<String>();
		while (!cSend.isAfterLast()) {
			listCheck.add(cSend.getString(cSend
					.getColumnIndex(Variables.KEY_BILL)));
			cSend.moveToNext();
		}
		boolean check = true;
		for (int i = 0; i < listCheck.size(); i++) {
			if (listCheck.get(i).equals(billDO) || listCheck.get(i) == billDO)
				check = false;
		}
		long ks = 0;
		if (check == true) {
			ks = SSqlite.getInstance(this).insertdata(Variables.TBL_BILLFAIL, keys, values);
		}
		if (ks > 0) {
			Log.d("", "Insert thành công");
		} else
			Log.d("", "Lỗi cmnr");
	}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
}
