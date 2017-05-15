package vn.ntlogistics.app.shipper.Commons.CustomViews;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.ntlogistics.app.shipper.R;


/**
 * Created by minhtan2908 on 10/26/16.
 */

public class LayoutItemStopPlaceHub extends LinearLayout {

    ImageView ivStopPlaceItem, ivStopPlaceItem1;
    View vItemHub1;
    TextView tvAddressItem, tvNumberHub;

    public LayoutItemStopPlaceHub(Context context) {
        super(context);
        init();
    }

    public LayoutItemStopPlaceHub(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LayoutItemStopPlaceHub(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LayoutItemStopPlaceHub(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater li = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.item_stop_place_hub, this, true);

        ivStopPlaceItem = (ImageView) findViewById(R.id.ivStopPlaceItem);
        ivStopPlaceItem1 = (ImageView) findViewById(R.id.ivStopPlaceItem1);
        vItemHub1 = findViewById(R.id.vItemHub1);
        tvAddressItem = (TextView) findViewById(R.id.tvAddressItem);
        tvNumberHub = (TextView) findViewById(R.id.tvNumberHub);
    }

    public void setImageHub(int idRes) {
        ivStopPlaceItem.setImageResource(idRes);
        ivStopPlaceItem1.setImageResource(idRes);
    }

    public void setTextAddress(String s) {
        if (s != null)
            tvAddressItem.setText(s);
    }

    public void setHideViewHub1(boolean b){
        if(b){
            vItemHub1.setVisibility(GONE);
        }
        else
            vItemHub1.setVisibility(VISIBLE);
    }

    public void setTextNumber(String i) {
        tvNumberHub.setText(i);
    }
}
