package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.InputManagementVMs.InputManagementVM;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityInputManagementBinding;

public class InputManagementActivity extends BaseActivity {
	private ActivityInputManagementBinding		binding;
	private InputManagementVM					viewModel;

	public static void startIntentActivity(Context context, int flag){
		Bundle b = new Bundle();
		b.putInt("flag", flag);

		Intent i = new Intent(context, InputManagementActivity.class);
		i.putExtras(b);
		context.startActivity(i);
		((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_input_management);
		viewModel = new InputManagementVM(this, binding);
		binding.setViewModel(viewModel);

		initToolbar();

		/*viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
		viewPager.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				actionBar.setSelectedNavigationItem(arg0);
				mScrollView.requestDisallowInterceptTouchEvent(true);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// mScrollView.getParent().requestDisallowInterceptTouchEvent(true);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});*/

		/*actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		tab1 = actionBar.newTab();
		tab1.setTabListener(this);
		tab2 = actionBar.newTab();
		tab2.setTabListener(this);
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);*/
		//editTab();
	}

	private void initToolbar() {
		setSupportActionBar(binding.toolbarInputManagement);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		binding.toolbarInputManagement.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	public void editTab() {
		/*keyloz = getIntent().getExtras().getInt("keyloz");
		Variables a = new Variables();
		Log.d("", "key: " + keyloz);
		if (keyloz == 3) {
			tab1.setText("Nhận");
			tab2.setText("Trả");
			Variables.find=3;
		}
		if (keyloz == 4) {
			tab1.setText("Trả");
			tab2.setText("Nhận");
			Variables.find=4;
		}*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.quanlynhap, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}
}
