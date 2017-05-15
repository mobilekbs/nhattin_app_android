package vn.ntlogistics.app.shipper.Commons.CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.ntlogistics.app.shipper.R;


/**
 * Created by Zanty on 12/07/2016.
 */
public class ContentGoodsLayout extends LinearLayout {
    String type, value, content;
    TextView tvType, tvValue, tvContent;
    boolean showContent = true;

    public ContentGoodsLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ContentGoodsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ContentGoodsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContentGoodsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initControls();


        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ContentGoodsLayout);
        try {

            try {
                type = array.getString(R.styleable.ContentGoodsLayout_tvType);
            } catch (Exception e) {
                type = "";
            }

            try {
                value = array.getString(R.styleable.ContentGoodsLayout_tvValue);
            } catch (Exception e) {
                value = "";
            }

            try {
                content = array.getString(R.styleable.ContentGoodsLayout_tvContent);
            } catch (Exception e) {
                content = "";
            }

            setTvType(type);
            setTvContent(content);
            setTvValue(value);

        } finally {
            array.recycle();
        }
    }

    private void initControls() {
        //Sử dụng LayoutInflater để gán giao diện trong list.xml cho class này
        LayoutInflater li = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.layout_content_goods, this, true);

        tvType = (TextView) findViewById(R.id.tvType);
        tvValue = (TextView) findViewById(R.id.tvValue);
        tvContent = (TextView) findViewById(R.id.tvContent);

        type = "";
        value = "";
        content = "";
    }

    public void setTvType(String type){
        tvType.setText(type);
    }
    public void setTvValue(String value){
        tvValue.setText(value);
    }
    public void setTvContent(String content){
        tvContent.setText(content);
    }
    public void showContent(){
        tvContent.setVisibility(VISIBLE);
    }
    public void hideContent(){
        tvContent.setVisibility(GONE);
    }

}
