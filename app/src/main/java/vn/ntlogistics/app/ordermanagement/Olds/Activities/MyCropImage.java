package vn.ntlogistics.app.ordermanagement.Olds.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Olds.CropImage.Custom_lv_Image;
import vn.ntlogistics.app.ordermanagement.Olds.CropImage.ZoomImgActivity;
import vn.ntlogistics.app.ordermanagement.R;


public class MyCropImage extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	Button btnCrop;
	ListView lstImg;
	Button btnSendImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_crop_image);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMyCropImage);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		lstImg = (ListView) findViewById(R.id.lstImg);
		btnCrop = (Button) findViewById(R.id.btnCROP);
		btnSendImg = (Button) findViewById(R.id.btnSendImg);
		btnSendImg.setOnClickListener(this);
		btnCrop.setOnClickListener(this);
		lstImg.setOnItemClickListener(this);
		getData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.my_crop_image, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.btnCropBack) {
			Intent it = new Intent(this, MyMenu.class);
			startActivity(it);
			finish();
		}*/
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnCROP) {
			CropBill();
		}

	}

	public void CropBill() {
		Intent it = new Intent(this, ScanMSActivity.class);
		Bundle a = new Bundle();
		a.putInt("cmcrop", 9);
		it.putExtras(a);
		startActivity(it);
		finish();
	}

	public void getData() {
		try {
			int thisismenu = getIntent().getExtras().getInt("thisismenu");
			int cropxongcmnr = getIntent().getExtras().getInt("cropxongcmnr");
			if (thisismenu == Variables.THISISMENU) {
				if (Variables.LST_ItemBill.size() > 0) {
					Custom_lv_Image c_img = new Custom_lv_Image(this,
							R.layout.custom_lv_image, Variables.LST_ItemBill);
					lstImg.setAdapter(c_img);
				} else {
					return;
				}
			}
			if (cropxongcmnr == Variables.CROPDONE) {
				// imCropped.setVisibility(View.VISIBLE);
				// imCropped.setImageBitmap(Variables.IMAGECROPPED);
				// tvBillCrop.setText(Variables.KEYBILLCROP);
				for (int i = 0; i < Variables.LST_ItemBill.size(); i++) {
					Log.d("", "BILL " + i + ": "
							+ Variables.LST_ItemBill.get(i).getBill()
							+ ", IMAGE " + i + ": "
							+ Variables.LST_ItemBill.get(i).getImg());

				}
				Custom_lv_Image c_img = new Custom_lv_Image(this,
						R.layout.custom_lv_image, Variables.LST_ItemBill);
				lstImg.setAdapter(c_img);
			}
		} catch (NullPointerException e) {
			e.getMessage();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		Intent it = new Intent(this, ZoomImgActivity.class);
		Bundle b = new Bundle();
		b.putInt("key", position);
		it.putExtras(b);
		startActivity(it);
		finish();

	}

}
