package vn.ntlogistics.app.ordermanagement.Olds.Activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login.User;
import vn.ntlogistics.app.ordermanagement.R;

public class ChangePassActivity extends BaseActivity implements OnClickListener {
	TextView tvshowMoney;
	EditText edtOldPass, edtNewPass, edtConfirmNewPass;
	Button btnChangeKo, btnChangeNow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pass);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChangePass);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		tvshowMoney = (TextView) findViewById(R.id.tvshowMoney);
		edtNewPass = (EditText) findViewById(R.id.edtNewPass);
		edtOldPass = (EditText) findViewById(R.id.edtOldPass);
		edtConfirmNewPass = (EditText) findViewById(R.id.edtConfirmNewPass);
		btnChangeKo = (Button) findViewById(R.id.btnChangeKo);
		btnChangeNow = (Button) findViewById(R.id.btnChangeNow);
		btnChangeKo.setOnClickListener(this);
		btnChangeNow.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_send_bill, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		/*if (id == R.id.backmn) {
			Intent i = new Intent(this, MyMenu.class);
			startActivity(i);
			finish();
		}*/
		if (id == R.id.btnrf) {
			edtConfirmNewPass.setText("");
			edtNewPass.setText("");
			edtOldPass.setText("");

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnChangeKo) {
			showhang();
		}
		if (v.getId() == R.id.btnChangeNow) {
			if (checkNull()) {
				if (checkPass())
					updatePass();
			}
		}
	}

	public void showhang() {
		btnChangeKo.setVisibility(View.GONE);
		edtOldPass.setVisibility(View.VISIBLE);
		edtNewPass.setVisibility(View.VISIBLE);
		edtConfirmNewPass.setVisibility(View.VISIBLE);
		btnChangeNow.setVisibility(View.VISIBLE);
	}

	public boolean checkPass() {
		String localKey = SCurrentUser.getCurrentUser(this).getLocalkey();
		String opass = edtOldPass.getText().toString();
		String pnew = edtNewPass.getText().toString();
		String cnew = edtConfirmNewPass.getText().toString();

		if (!localKey.equals(opass)) {
			Message.makeToastWarning(this,
					getString(R.string.error_old_password_not_correct));
			//myast.makeWarning("Mật khẩu cũ không đúng.");
			return false;
		}
		if (!pnew.equals(cnew)) {
			Message.makeToastWarning(this,
					getString(R.string.val_pass_equals));
			//myast.makeWarning("Mật khẩu xác nhận không đúng.");
			return false;
		}

		if (localKey.equals(pnew)) {
			Message.makeToastWarning(this,
					getString(R.string.val_pass_equals));
			//myast.makeWarning("Mật khẩu mới phải khác mật khẩu cũ.");
			return false;
		}

		return true;
	}

	public void updatePass() {
		//Cursor c = db.getAllDataFromTable(Variables.TBL_STAFF);
		/*String localKey = SCurrentUser.getCurrentUser(this).getLocalkey();
		*//*c.moveToFirst();
		if (!c.isAfterLast()) {
			localKey = c.getString(c.getColumnIndex(Variables.KEY_LOCAL_KEY));
			c.moveToNext();
		}*//*
		*//*String keyID = Variables.KEY_LOCAL_KEY;
		String valueID = localKey;*//*
		String field[] = { Variables.KEY_LOCAL_KEY };
		String values[] = { edtNewPass.getText().toString() };

		boolean a = db.updateData4Table(Variables.TBL_STAFF,
				Variables.KEY_LOCAL_KEY, localKey,
				field, values);*/
		/*Intent i = new Intent(this, MainActivity.class);
		startActivity(i);*/
		User user = SCurrentUser.getCurrentUser(this);
        user.setLocalkey(edtNewPass.getText().toString());
		SSqlite.getInstance(this).inserOrUpdatetUser(user);
		finish();
		Message.makeToastSuccess(this, getString(R.string.toast_success_update));
		//myast.makeSuccess("Cập nhật thành công.");

	}

	public void myRF() {
		edtConfirmNewPass.setText("");
		edtNewPass.setText("");
		edtOldPass.setText("");
	}

	public boolean checkNull() {
		/*String oldpass = edtOldPass.getText().toString();
		String newpass = edtNewPass.getText().toString();
		String cnewpass = edtConfirmNewPass.getText().toString();*/
        if(checkNull(edtOldPass, getString(R.string.error_null_field)) &&
                checkNull(edtNewPass, getString(R.string.val_pass_4_char)) &&
                checkNull(edtConfirmNewPass, getString(R.string.val_pass_4_char)) &&
                checkEquals(edtNewPass, edtConfirmNewPass, getString(R.string.val_pass_equals))
                ){
            return true;
        }
        else
            return false;
		/*if (oldpass.equals("") || newpass.equals("") || cnewpass.equals("")) {
            Message.makeToastWarning(this,
                    getString(R.string.error_null_field));
			//myast.makeWarning("Bạn chưa nhập đủ thông tin.");
			return false;
		}
		if (newpass.length() < 4) {
			myast.makeWarning("Mật khẩu mới phải tối thiểu 4 ký tự.");
			return false;
		}
		return true;*/
	}

	private boolean checkNull(EditText et, String message){
        if(et.getText().toString().length() == 0){
            et.setError(message);
            return false;
        }
        else {
            et.setError(null);
            return true;
        }
    }

    private boolean checkEquals(EditText et1, EditText et2, String message){
        if(et1.getText().toString().equals(et2.getText().toString())){
            et2.setError(null);
            return true;
        }
        else {
            et2.setError(message);
            return false;
        }
    }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
	}
}
