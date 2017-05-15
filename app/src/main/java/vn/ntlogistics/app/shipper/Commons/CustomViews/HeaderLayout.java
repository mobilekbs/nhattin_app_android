package vn.ntlogistics.app.shipper.Commons.CustomViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.ntlogistics.app.shipper.Models.Enums.EServiceShip;
import vn.ntlogistics.app.shipper.R;

/**
 * Created by Zanty on 21/06/2016.
 */
public class HeaderLayout extends LinearLayout {

    private boolean showTime;
    private boolean showConfirmCode;
    private boolean showCheckbox;
    private int textTypeTiltle;
    private String title, time, day, code, titleConfirm;

    //init Controls
    private TextView tvTitle, tvDay, tvTime, tvConfirmCode, tvTitleConfirm;
    private CheckBox cb;
    private ImageView ivIconClock;
    private LinearLayout lnTime, lnConfirmCode,lnBody;
    //Header StopPlace
    private ImageView ivIcon;
    private TextView tvNumber;
    private RelativeLayout loMain, loStopPlace;

    public HeaderLayout(Context context) {
        super(context);
        init(context, null);
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initControls();

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HeaderLayout);
        try {
            showCheckbox = array.getBoolean(R.styleable.HeaderLayout_showCheckbox,true);
            setShowCheckbox(showCheckbox);

            showTime = array.getBoolean(R.styleable.HeaderLayout_showTime,false);
            setShowTime(showTime);

            showConfirmCode = array.getBoolean(R.styleable.HeaderLayout_showConfirmCode,false);
            setShowConfirmCode(showConfirmCode);

            try {
                title = array.getString(R.styleable.HeaderLayout_tvTitle);
            }
            catch (Exception e){
                time = "";
            }

            try {
                code = array.getString(R.styleable.HeaderLayout_tvCode);
            }catch (Exception e){
                code = "";
            }

            try {
                titleConfirm = array.getString(R.styleable.HeaderLayout_tvTitleConfirm);
            }catch (Exception e){
                code = "";
            }

            try {
                time = array.getString(R.styleable.HeaderLayout_tvTime);
                day = array.getString(R.styleable.HeaderLayout_tvDay);
            }catch (Exception e){
                title = "";
                day ="";
            }

            try {
                textTypeTiltle = array.getInt(R.styleable.HeaderLayout_android_textStyle, Typeface.NORMAL);
            }
            catch (Exception e){
            }
            setTextTypeTiltle(textTypeTiltle);
            setTitle(title);
            setDay(day);
            setTime(time);
            setConfirmCode(code);
            setTitleConfirm(titleConfirm);
        }finally {
            array.recycle();
        }
    }

    private void initControls(){
        //Sử dụng LayoutInflater để gán giao diện trong list.xml cho class này
        LayoutInflater li = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.header_layout, this, true);

        tvTitleConfirm = (TextView) findViewById(R.id.tvTitleConfirm);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvConfirmCode = (TextView) findViewById(R.id.tvConfirmCode);
        lnTime = (LinearLayout) findViewById(R.id.lnTime);
        lnConfirmCode = (LinearLayout) findViewById(R.id.lnConfirmCode);
        lnBody = (LinearLayout) findViewById(R.id.lnBody);
        cb = (CheckBox) findViewById(R.id.checkBox);
        ivIconClock = (ImageView) findViewById(R.id.ivIconClock);

        //TODO: StopPlace
        loMain = (RelativeLayout) findViewById(R.id.loMain);
        loStopPlace = (RelativeLayout) findViewById(R.id.loStopPlace);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        tvNumber = (TextView) findViewById(R.id.tvNumber);

        title = "";
        time = "";
        day = "";
        code ="";
        titleConfirm = "";
    }

    /*//TODO: tức thời
    public void setComboFastOrder(boolean b){
        if(b){
            lnBody.setBackgroundColor(getResources().getColor(R.color.colorRed));
            tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
            ivIconClock.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_clock_white));
        }
        else {
            lnBody.setBackgroundColor(getResources().getColor(R.color.colorBG));
            tvTitle.setTextColor(getResources().getColor(R.color.colorBlack));
            tvDay.setTextColor(getResources().getColor(R.color.colorBlack));
            tvTime.setTextColor(getResources().getColor(R.color.colorBlack));
            ivIconClock.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_clock_black));
        }
    }*/

    public void setHeaderService(EServiceShip service, int fast){
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {
                ContextCompat.getColor(getContext(),R.color.colorWhite),
                ContextCompat.getColor(getContext(),R.color.colorWhite)
        };
        switch (service){
            case SHIP_CARGO: //Book
                //tvDay.setVisibility(GONE);
                //ivIconClock.setVisibility(GONE);
                /*if(fast == Common.TYPE_SHIP_CARGO) { //Thuong = 0

                }
                else*/
                if(fast == 1){ //Nhanh - Hoa toc
                    CompoundButtonCompat.setButtonTintList(cb, new ColorStateList(states, colors));
                    
                    lnBody.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    ivIconClock.setImageDrawable(ContextCompat.getDrawable(getContext(),R.mipmap.ic_alarm_clock_white));
                }
                else if(fast == 2){ //HUB
                    CompoundButtonCompat.setButtonTintList(cb, new ColorStateList(states, colors));
                    lnBody.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorHeaderHub));
                    tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    ivIconClock.setImageDrawable(ContextCompat.getDrawable(getContext(),R.mipmap.ic_alarm_clock_white));
                }
                else {
                    int colorsCargo[] = {
                            ContextCompat.getColor(getContext(),R.color.colorAccent),
                            ContextCompat.getColor(getContext(),R.color.colorAccent)};
                    CompoundButtonCompat.setButtonTintList(cb, new ColorStateList(states, colorsCargo));
                    lnBody.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBG));
                    tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                    ivIconClock.setImageDrawable(ContextCompat.getDrawable(getContext(),R.mipmap.ic_alarm_clock_black));
                }
                break;
            case SHIP_U: //Ship U
                //tvDay.setVisibility(VISIBLE);
                //ivIconClock.setVisibility(VISIBLE);
                CompoundButtonCompat.setButtonTintList(cb, new ColorStateList(states, colors));
                lnBody.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                ivIconClock.setImageDrawable(ContextCompat.getDrawable(getContext(),R.mipmap.ic_alarm_clock_white));
                break;
            case Ship_K:
                CompoundButtonCompat.setButtonTintList(cb, new ColorStateList(states, colors));
                lnBody.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorHeaderShipK));
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                ivIconClock.setImageDrawable(ContextCompat.getDrawable(getContext(),R.mipmap.ic_alarm_clock_white));
                break;
            default:
                int colorsCargo[] = {
                        ContextCompat.getColor(getContext(),R.color.colorAccent),
                        ContextCompat.getColor(getContext(),R.color.colorAccent)};
                CompoundButtonCompat.setButtonTintList(cb, new ColorStateList(states, colorsCargo));
                lnBody.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBG));
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                tvDay.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
                ivIconClock.setImageDrawable(ContextCompat.getDrawable(getContext(),R.mipmap.ic_alarm_clock_black));

                break;
        }
    }

    public void setupHeaderStopPlace(int number, int resId){
        if(number == 0){
            tvNumber.setVisibility(GONE);
        }
        else {
            tvNumber.setVisibility(VISIBLE);
            tvNumber.setText(number+"");
        }
        ivIcon.setImageResource(resId);
        loStopPlace.setVisibility(VISIBLE);
        loMain.setVisibility(GONE);
    }

    public TextView getTvConfirmCode(){
        return tvConfirmCode;
    }
    public TextView getTvTitle(){
        return tvTitle;
    }
    public TextView getTvTime(){
        return tvTime;
    }
    public TextView getTvDay(){
        return tvDay;
    }

    public void setLnConfirmCode(boolean show){
        if(show)
            lnConfirmCode.setVisibility(VISIBLE);
        else
            lnConfirmCode.setVisibility(GONE);
    }

    public void setTextTypeTiltle(int textTypeTiltle){
        tvTitle.setTypeface(null,textTypeTiltle);
    }

    public void setShowTime(boolean show){
        if(show){
            lnTime.setVisibility(VISIBLE);
        }
        else {
            lnTime.setVisibility(GONE);
        }
    }

    public void setShowConfirmCode(boolean show){
        if(show){
            lnConfirmCode.setVisibility(VISIBLE);
        }
        else {
            lnConfirmCode.setVisibility(GONE);
        }
    }

    public void setShowCheckbox(boolean show){
        if(show){
            cb.setVisibility(VISIBLE);
        }
        else {
            cb.setVisibility(GONE);
        }
    }
    public void setTitleConfirm(String title){
        tvTitleConfirm.setText(title);
    }
    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setTime(String time){
        tvTime.setText(time);
    }

    public void setDay(String day){
        tvDay.setText(day);
    }

    public void setConfirmCode(String code){
        tvConfirmCode.setText(code);
    }

    public String getTitle(){
        return title;
    }
    public String getTime(){
        return time;
    }
    public String getDay(){
        return day;
    }
    public String getCode(){
        return code;
    }

    public void setCheckbox(boolean check){
        cb.setChecked(check);
    }

    public boolean isCheckbox(){
        return cb.isChecked();
    }

    public CheckBox getCheckBox(){
        return cb;
    }

    public boolean isShowCheckbox() {
        return showCheckbox;
    }

    public boolean isShowConfirmCode() {
        return showConfirmCode;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public ImageView getIvIconClock(){
        return ivIconClock;
    }
}
