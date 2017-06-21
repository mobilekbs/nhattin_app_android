package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.ImageViewPager.ScreenSlidePagerAdapter;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityViewImageBinding;


public class ViewImageActivity extends BaseActivity {
    public static final String          TAG = "ViewImageActivity";

    private ActivityViewImageBinding    binding;

    ScreenSlidePagerAdapter             adapter;
    List<String>                        mListURLs;
    private int                         position;
    private String                      orderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_image);
        init();
    }

    private void init() {

        try {
            mListURLs = getIntent().getStringArrayListExtra("ImageURLs");
            position = (int) getIntent().getExtras().get("position");
        } catch (Exception e) {
            finish();
        }
        try {
            orderName = (String) getIntent().getExtras().get("OrderName");
        } catch (Exception e) {
            orderName = "";
        }

        binding.tvTitle.setText(orderName);
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
                finish();
            }
        });
        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
            }
        });
        setupViewPager();
    }

    private void setupViewPager() {
        adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),mListURLs);
        binding.viewImages.setAdapter(adapter);
        binding.viewImages.setCurrentItem(position);
        binding.viewImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    final int childCount = binding.viewImages.getChildCount();
                    for (int i = 0; i < childCount; i++)
                        binding.viewImages.getChildAt(i).setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }
            }
        });
    }
}
