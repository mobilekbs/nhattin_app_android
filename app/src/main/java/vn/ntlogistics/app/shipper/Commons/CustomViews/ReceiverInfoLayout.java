package vn.ntlogistics.app.shipper.Commons.CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.R;


/**
 * Created by Zanty on 22/06/2016.
 */
public class ReceiverInfoLayout extends LinearLayout {

    ReceiverInfoLayout layout;

    public interface SetOnClickReceiver {
        void onClick(String value);
    }

    boolean api = true;

    String name, address, phone, time, code;

    TextView  tvConfirmCode, tvTrust, tvUntrust, tvUReceiver, tvTReceiver;
    View btnTrust, btnUntrust, btnCall, lnTrustUntrustReceiver;
    HeaderLayout header;

    View lnLocation, lnName, lnCOD, lnNote, lnPhone, lnTimeReceiver;
    TextView tvAddress, tvPhone, tvName, tvNote, tvCod, tvTimeReceiver;

    public ReceiverInfoLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ReceiverInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ReceiverInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReceiverInfoLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (layout == null)
            layout = this;

        initControls();

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ReceiverInfoLayout);
        try {

            try {
                name = array.getString(R.styleable.ReceiverInfoLayout_tvName);
            } catch (Exception e) {
                name = "";
            }

            try {
                time = array.getString(R.styleable.ReceiverInfoLayout_tvTimeReveice);
            } catch (Exception e) {
                time = "";
            }

            try {
                address = array.getString(R.styleable.ReceiverInfoLayout_tvAddress);
            } catch (Exception e) {
                address = "";
            }

            try {
                phone = array.getString(R.styleable.ReceiverInfoLayout_tvPhone);
            } catch (Exception e) {
                phone = "";
            }

            try {
                code = array.getString(R.styleable.ReceiverInfoLayout_tvConfirmCode);
            } catch (Exception e) {
                code = "";
            }

            setName(name);
            setAddress(address);
            setPhone(phone);
            setConfirmCode(code);

        } finally {
            array.recycle();
        }
    }

    private void initControls() {
        //Sử dụng LayoutInflater để gán giao diện trong list.xml cho class này
        LayoutInflater li = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.layout_reveicer_info, this, true);

        header = (HeaderLayout) findViewById(R.id.headerReceive);

        tvConfirmCode = header.getTvConfirmCode();

        tvTrust = (TextView) findViewById(R.id.tvTrust);
        tvUntrust = (TextView) findViewById(R.id.tvUntrust);
        tvUReceiver = (TextView) findViewById(R.id.tvUReceiver);
        tvTReceiver = (TextView) findViewById(R.id.tvTReceiver);

        lnTrustUntrustReceiver = findViewById(R.id.lnTrustUntrustReceiver);

        lnLocation = findViewById(R.id.lnLocation);
        lnName = findViewById(R.id.lnName);
        lnCOD = findViewById(R.id.lnCodReceiver);
        lnNote = findViewById(R.id.lnNote);
        lnPhone = findViewById(R.id.lnPhone);
        lnTimeReceiver = findViewById(R.id.lnTimeReceiver);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvName = (TextView) findViewById(R.id.tvName);
        tvNote = (TextView) findViewById(R.id.tvNote);
        tvCod = (TextView) findViewById(R.id.tvCodReceiver);
        tvTimeReceiver = (TextView) findViewById(R.id.tvTimeReceiver);

        btnTrust = findViewById(R.id.btnTrustReceive);
        btnUntrust = findViewById(R.id.btnUnTrustReceive);
        btnCall = findViewById(R.id.btnCall);

        name = "";
        address = "";
        phone = "";
        time = "";
    }

    public void setupStopPlace(int number, int resID){
        header.setupHeaderStopPlace(number,resID);
    }

    public void setTitleHeader(String s){
        header.setTitle(s);
    }

    public void setOnClickTrust(final SetOnClickReceiver onClickReceiver) {
        btnTrust.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(api) {
                    api = false;
                    String s = tvTrust.getText().toString();
                    /**
                     * So sánh, nếu tvTSend là chữ TRUST thì sẽ chuyển thành TRUSTED và ngược lại
                     */
                    if (s.equalsIgnoreCase(getResources().getString(R.string.trust))) {
                        onClickReceiver.onClick("trust");
                    } else {
                        onClickReceiver.onClick("trusted");
                    }
                }
            }
        });
    }

    public void setOnClickUnTrust(final SetOnClickReceiver onClickReceiver) {
        btnUntrust.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(api) {
                    api = false;
                    String s = tvUntrust.getText().toString();
                    /**
                     * So sánh, nếu tvTSend là chữ TRUST thì sẽ chuyển thành TRUSTED và ngược lại
                     */
                    if (s.equalsIgnoreCase(getResources().getString(R.string.untrust))) {
                        onClickReceiver.onClick("untrust");
                    } else {
                        onClickReceiver.onClick("untrusted");
                    }
                }
            }
        });
    }

    public void setOnClickCall(final SetOnClickReceiver onClickTrust) {
        btnCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickTrust.onClick(tvPhone.getText().toString());
            }
        });
    }

    public void setButtonTrust(){
        api = true;
        String s = tvTrust.getText().toString();
        if (s.equalsIgnoreCase(getResources().getString(R.string.trust))) {
            tvTrust.setText(getResources().getString(R.string.trusted));
            //Cộng lên 1 số
            int count = Integer.parseInt(tvTReceiver.getText().toString()) + 1;
            tvTReceiver.setText(count + "");
            btnUntrust.setEnabled(false);
            btnUntrust.setBackgroundColor(getResources().getColor(R.color.colorGrayGray));

        } else {
            tvTrust.setText(getResources().getString(R.string.trust));
            btnUntrust.setEnabled(true);
            //Trừ đi 1 số
            int count = Integer.parseInt(tvTReceiver.getText().toString()) - 1;
            tvTReceiver.setText(count + "");
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            btnUntrust.setBackgroundResource(outValue.resourceId);
        }
    }

    public void setEnableButton(int i){
        if(i==0) { //Untrsuted
            tvUntrust.setText(getResources().getString(R.string.Untrusted));
            btnUntrust.setEnabled(true);
            btnTrust.setEnabled(false);
            btnTrust.setBackgroundColor(getResources().getColor(R.color.colorGrayGray));
        }
        else {
            tvTrust.setText(getResources().getString(R.string.trusted));
            btnTrust.setEnabled(true);
            btnUntrust.setEnabled(false);
            btnUntrust.setBackgroundColor(getResources().getColor(R.color.colorGrayGray));
        }
    }

    public void setButtonUntrust(){
        api = true;
        String s = tvUntrust.getText().toString();
        if (s.equalsIgnoreCase(getResources().getString(R.string.untrust))) {
            tvUntrust.setText(getResources().getString(R.string.Untrusted));
            //Cộng lên 1 số
            int count = Integer.parseInt(tvUReceiver.getText().toString()) + 1;
            tvUReceiver.setText(count + "");
            btnTrust.setEnabled(false);
            btnTrust.setBackgroundColor(getResources().getColor(R.color.colorGrayGray));

        } else {
            tvUntrust.setText(getResources().getString(R.string.untrust));
            btnTrust.setEnabled(true);
            //Trừ đi 1 số
            int count = Integer.parseInt(tvUReceiver.getText().toString()) - 1;
            tvUReceiver.setText(count + "");
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            btnTrust.setBackgroundResource(outValue.resourceId);
        }
    }

    public View getBtnTrust() {
        return btnTrust;
    }

    public View getBtnUnTrust() {
        return btnUntrust;
    }

    public View getBtnCall() {
        return btnCall;
    }

    public void trustIsChecked() {
        String checkS = tvUntrust.getText().toString();
        String checkD = tvTrust.getText().toString();
        if(checkS.equalsIgnoreCase(getResources().getString(R.string.untrust))) {
            if (checkD.equalsIgnoreCase(getResources().getString(R.string.trust))) {
                tvTrust.setText(getResources().getString(R.string.trusted));
            }
            else {
                tvTrust.setText(getResources().getString(R.string.trust));
            }
        }
    }

    public void untrustIsChecked() {
        String checkS = tvTrust.getText().toString();
        String checkD = tvUntrust.getText().toString();
        if(checkS.equalsIgnoreCase(getResources().getString(R.string.trust))) {
            if (checkD.equalsIgnoreCase(getResources().getString(R.string.untrust))) {
                tvUntrust.setText(getResources().getString(R.string.Untrusted));
            }
            else {
                tvUntrust.setText(getResources().getString(R.string.untrust));
            }
        }
    }

    public void setNumberTrust(String s){
        tvTReceiver.setText(s);
    }

    public void setNumberUntrust(String s){
        tvUReceiver.setText(s);
    }

    public void setShowTrustUntrust(int i){
        lnTrustUntrustReceiver.setVisibility(i);
    }

    public void setTrust(String i) {
        tvTrust.setText(i);
    }

    public void setUnTrust(String i) {
        tvUntrust.setText(i);
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setAddress(String address) {
        tvAddress.setText(address);
    }

    public void setPhone(String phone) {
        tvPhone.setText(phone);
    }

    public void setConfirmCode(String code) {
        tvConfirmCode.setText(code);
    }

    public void setTvPhone(String s){
        if(s != null)
            if(s.length()>0)
                tvPhone.setText(s);
            else
                lnPhone.setVisibility(GONE);
        else
            lnPhone.setVisibility(GONE);
    }

    public void setTvName(String s){
        if(s != null) {
            if(s.length() > 0)
                tvName.setText(s);
            else
                lnName.setVisibility(GONE);
        }
        else
            lnName.setVisibility(GONE);
    }

    public void setTvNote(String s){
        if(s != null) {
            if(s.length() > 0)
                tvNote.setText(s);
            else
                lnNote.setVisibility(GONE);
        }
        else
            lnNote.setVisibility(GONE);
    }
    public void setTvCod(String s){
        if(s!=null) {
            try {
                double ss = Double.parseDouble(s);
                if (ss == 0)
                    lnCOD.setVisibility(GONE);
                else {
                    tvCod.setText(Commons.DinhDangChuoiTien(s) + getResources().getString(R.string.unit));
                }
            } catch (Exception e) {
            }
        }
        else
            tvCod.setText(0 + getResources().getString(R.string.unit));
    }
    public void setTvAddress(String s){
        if(s==null)
            lnLocation.setVisibility(GONE);
        else {
            tvAddress.setText(s);
        }
    }
    public void setTvTime(String s){
        if(s == null)
            lnTimeReceiver.setVisibility(GONE);
        else {
            if(s.length() > 0)
                tvTimeReceiver.setText(s);
            else
                lnTimeReceiver.setVisibility(GONE);
        }
    }
}
