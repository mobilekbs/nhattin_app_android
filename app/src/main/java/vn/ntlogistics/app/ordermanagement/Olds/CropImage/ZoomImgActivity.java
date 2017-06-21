package vn.ntlogistics.app.ordermanagement.Olds.CropImage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.R;

public class ZoomImgActivity extends Activity {
	ImageView imgBtn;
	View	btnBackZoom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom_img);
		imgBtn = (ImageView) findViewById(R.id.btnOutZoom);
		btnBackZoom = findViewById(R.id.btnBackZoom);
		try {
			int key = getIntent().getExtras().getInt("key");
			imgBtn.setImageBitmap(Variables.LST_ItemBill.get(key).getImg());

		} catch (NullPointerException a) {
			a.getMessage();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.zoom_img, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.btnbackbyZoom) {
			Intent it = new Intent(this, MyCropImage.class);
			Bundle b = new Bundle();
			b.putInt("thisismenu", 100);
			it.putExtras(b);
			startActivity(it);
			finish();
		}*/
		return super.onOptionsItemSelected(item);
	}
}
