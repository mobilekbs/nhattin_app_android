package vn.ntlogistics.app.ordermanagement.Commons.CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.ntlogistics.app.ordermanagement.R;


/**
 * Created by Zanty on 22/06/2016.
 */
public class ReveicerFeeLayout extends LinearLayout {

    String freight, added, colecting, total;

    HeaderLayout header;
    TextView tvFreightFeeSender,tvAddedServiceFeeSender, tvColectingRevenueSender, tvTotal, tvVATReceiver, tvExtraFee, tvCODFee, tvAddressSender;

    public ReveicerFeeLayout(Context context) {
        super(context);
        init(context,null);
    }

    public ReveicerFeeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ReveicerFeeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReveicerFeeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initControls();

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ReveicerFeeLayout);
        try {

            try {
                freight = array.getString(R.styleable.ReveicerFeeLayout_tvFreight);
            }
            catch (Exception e){
                freight = "";
            }

            try {
                added = array.getString(R.styleable.ReveicerFeeLayout_tvAddedService);
            }catch (Exception e){
                added = "";
            }

            try {
                colecting = array.getString(R.styleable.ReveicerFeeLayout_tvColecting);
            }catch (Exception e){
                colecting = "";
            }

            try {
                total = array.getString(R.styleable.ReveicerFeeLayout_tvTotal);
            }catch (Exception e){
                total = "";
            }

        }finally {
            array.recycle();
        }
    }

    private void initControls() {
        //Sử dụng LayoutInflater để gán giao diện trong list.xml cho class này
        LayoutInflater li = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.layout_reveicer_fee, this, true);

        header = (HeaderLayout) findViewById(R.id.headerReceive);

        tvTotal = header.getTvConfirmCode();
        tvFreightFeeSender = (TextView) findViewById(R.id.tvFreightFeeSender);
        tvAddedServiceFeeSender = (TextView) findViewById(R.id.tvAddedServiceFeeSender);
        tvColectingRevenueSender = (TextView) findViewById(R.id.tvColectingRevenueSender);
        tvCODFee = (TextView) findViewById(R.id.tvCODFee);
        tvVATReceiver = (TextView) findViewById(R.id.tvVATReceiver);
        tvExtraFee = (TextView) findViewById(R.id.tvExtraFee);
        tvAddressSender = (TextView) findViewById(R.id.tvAddressSender);

        freight = "";
        added = "";
        colecting = "";
        total = "";
    }

    public void setAddress(String s){
        tvAddressSender.setText(s);
    }

    public void setFreightFee(String s){
        tvFreightFeeSender.setText(s);
    }

    public void setAddedServiceFee(String s){
        tvAddedServiceFeeSender.setText(s);
    }

    public void setColectingRevenue(String s){
        tvColectingRevenueSender.setText(s);
    }

    public void setTotalFee(String s){
        tvTotal.setText(s);
    }

    public void setVAT(String s){
        tvVATReceiver.setText(s);
    }

    public void setCOD(String s){
        tvCODFee.setText(s);
    }

    public void setExtraFee(String s){
        tvExtraFee.setText(s);
    }

}
