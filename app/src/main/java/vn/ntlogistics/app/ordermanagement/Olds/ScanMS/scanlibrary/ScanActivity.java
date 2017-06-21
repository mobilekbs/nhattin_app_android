package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by jhansi on 28/03/15.
 */

public class ScanActivity extends Activity implements IScanner {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_layout);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		init();
	}

	private void init() {
		PickImageFragment fragment = new PickImageFragment();
		
		Bundle bundle = new Bundle();
		bundle.putInt(ScanConstants.OPEN_INTENT_PREFERENCE,
				getPreferenceContent());
		fragment.setArguments(bundle);
		android.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.content, fragment);
		fragmentTransaction.hide(fragment);
		fragmentTransaction.commit();
	}

	protected int getPreferenceContent() {
		return getIntent().getIntExtra(ScanConstants.OPEN_INTENT_PREFERENCE, 0);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		exitApp();
	}

	private void exitApp() {
		Intent it = new Intent();
		it.setComponent(new ComponentName("com.appautocrop",
				"com.scan.ScanMS"));
		Bundle a = new Bundle();
		a.putInt("fromMyCrop", 9);
		it.putExtras(a);
		startActivity(it);
		finish();
	}

	@Override
	public void onBitmapSelect(Uri uri) {
		ScanFragment fragment = new ScanFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(ScanConstants.SELECTED_BITMAP, uri);
		fragment.setArguments(bundle);
		android.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.content, fragment);
		fragmentTransaction.addToBackStack(ScanFragment.class.toString());
		fragmentTransaction.commit();
	}

	@Override
	public void onScanFinish(Uri uri) {
		ResultFragment fragment = new ResultFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(ScanConstants.SCANNED_RESULT, uri);
		fragment.setArguments(bundle);
		android.app.FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.content, fragment);
		fragmentTransaction.addToBackStack(ResultFragment.class.toString());
		fragmentTransaction.commit();
	}

	public native Bitmap getScannedBitmap(Bitmap bitmap, float x1, float y1,
			float x2, float y2, float x3, float y3, float x4, float y4);

	public native Bitmap getGrayBitmap(Bitmap bitmap);

	public native Bitmap getMagicColorBitmap(Bitmap bitmap);

	public native Bitmap getBWBitmap(Bitmap bitmap);

	public native float[] getPoints(Bitmap bitmap);

	static {
		System.loadLibrary("opencv_java");
		//System.loadLibrary("Scanner");
	}


}