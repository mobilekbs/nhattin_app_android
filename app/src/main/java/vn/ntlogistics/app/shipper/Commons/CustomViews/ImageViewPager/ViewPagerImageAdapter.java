package vn.ntlogistics.app.shipper.Commons.CustomViews.ImageViewPager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Activities.ViewImageActivity;

/**
 * Created by Zanty on 23/06/2016.
 */
public class ViewPagerImageAdapter extends PagerAdapter {
    ImageView imageView;
    private Context context;
    private List<String> urls;
    DisplayMetrics metrics;
    int deviceWidth;
    String name;
    boolean zoom;

    public ViewPagerImageAdapter(Context context, List<String> urls) {
        this.context = context;
        if(urls == null)
            this.urls = new ArrayList<>();
        else
            this.urls = urls;
        metrics = context.getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
    }

    public ViewPagerImageAdapter(Context context, List<String> urls, String name, boolean zoom) {
        this.context = context;
        this.name = name;
        this.zoom = zoom;
        if(urls == null)
            this.urls = new ArrayList<>();
        else
            this.urls = urls;
        metrics = context.getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_page_image,container,false);
        imageView = (ImageView) view.findViewById(R.id.img_pager_item);
        Glide.with(context)
                .load(urls.get(position))
                .asBitmap()
                .override(deviceWidth, 0)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewImageActivity.class);
                i.putStringArrayListExtra("ImageURLs",(ArrayList<String>)urls);
                i.putExtra("position",position);
                i.putExtra("OrderName","Đơn Hàng 123");
                context.startActivity(i);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
