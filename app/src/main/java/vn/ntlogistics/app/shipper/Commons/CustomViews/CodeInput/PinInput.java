package vn.ntlogistics.app.shipper.Commons.CustomViews.CodeInput;

import android.app.Service;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import vn.ntlogistics.app.shipper.R;

/**
 * Created by Zanty on 16/05/2017.
 */

public class PinInput extends LinearLayout implements
        View.OnFocusChangeListener,
        View.OnKeyListener,
        TextWatcher {

    public interface OnInputListener {
        void finish(String result);
    }

    EditText et1, et2, et3, et4, etHidden;
    OnInputListener inputListener;

    public PinInput(Context context) {
        super(context);
        init(context, null);
    }

    public PinInput(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PinInput(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PinInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initControls();
        setPINListeners();
    }

    private void initControls() {
        //Sử dụng LayoutInflater để gán giao diện trong list.xml cho class này
        LayoutInflater li = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.layout_pin_input, this, true);

        et1 = (EditText) findViewById(R.id.et1_pin);
        et2 = (EditText) findViewById(R.id.et2_pin);
        et3 = (EditText) findViewById(R.id.et3_pin);
        et4 = (EditText) findViewById(R.id.et4_pin);
        etHidden = (EditText) findViewById(R.id.et_hidden_pin);
    }

    public void setInputListener(OnInputListener inputListener) {
        this.inputListener = inputListener;
    }

    public void restart() {
        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        etHidden.setText("");
        setFocusedPinBackground(et1);
        setDefaultPinBackground(et4);
        showSoftKeyboard(etHidden);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        switch (id) {
            case R.id.et1_pin:
                if (hasFocus) {
                    setFocus(etHidden);
                    showSoftKeyboard(etHidden);
                }
                break;

            case R.id.et2_pin:
                if (hasFocus) {
                    setFocus(etHidden);
                    showSoftKeyboard(etHidden);
                }
                break;

            case R.id.et3_pin:
                if (hasFocus) {
                    setFocus(etHidden);
                    showSoftKeyboard(etHidden);
                }
                break;

            case R.id.et4_pin:
                if (hasFocus) {
                    setFocus(etHidden);
                    showSoftKeyboard(etHidden);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //setDefaultPinBackground(et1);
        //setDefaultPinBackground(et2);
        //setDefaultPinBackground(et3);
        //setDefaultPinBackground(et4);
        //sInput += s.toString();
        if (s.length() == 0) {
            setFocusedPinBackground(et1);
            setDefaultPinBackground(et2);
            et1.setText("");
        } else if (s.length() == 1) {
            setDefaultPinBackground(et1);
            setDefaultPinBackground(et3);
            setFocusedPinBackground(et2);
            et1.setText(s.charAt(0) + "");
            et2.setText("");
            et3.setText("");
            et4.setText("");
        } else if (s.length() == 2) {
            setDefaultPinBackground(et2);
            setDefaultPinBackground(et4);
            setFocusedPinBackground(et3);
            et2.setText(s.charAt(1) + "");
            et3.setText("");
            et4.setText("");
        } else if (s.length() == 3) {
            setDefaultPinBackground(et3);
            setFocusedPinBackground(et4);
            et3.setText(s.charAt(2) + "");
            et4.setText("");

        } else if (s.length() == 4) {
            setFocusedPinBackground(et4);
            et4.setText(s.charAt(3) + "");
            if (inputListener != null)
                inputListener.finish(s.toString());
            hideSoftKeyboard(et4);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            switch (id) {
                case R.id.et_hidden_pin:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (etHidden.getText().length() == 4)
                            et4.setText("");
                        else if (etHidden.getText().length() == 3)
                            et3.setText("");
                        else if (etHidden.getText().length() == 2)
                            et2.setText("");
                        else if (etHidden.getText().length() == 1)
                            et1.setText("");

                        if (etHidden.length() > 0)
                            etHidden.setText(etHidden.getText().subSequence(0, etHidden.length() - 1));

                        return true;
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager)
                getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager)
                getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    /**
     * Sets default PIN background.
     *
     * @param editText edit text to change
     */
    private void setDefaultPinBackground(EditText editText) {

        setViewBackground(
                editText,
                ContextCompat.getDrawable(getContext(), R.drawable.textfield_default_holo_light));
    }

    /**
     * Sets focus on a specific EditText field.
     *
     * @param editText EditText to set focus on
     */
    public static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    /**
     * Sets focused PIN field background.
     *
     * @param editText edit text to change
     */
    private void setFocusedPinBackground(EditText editText) {
        setViewBackground(editText,
                ContextCompat.getDrawable(getContext(), R.drawable.textfield_focused_holo_light));
    }

    /**
     * Sets listeners for EditText fields.
     */
    private void setPINListeners() {
        etHidden.addTextChangedListener(this);

        et1.setOnFocusChangeListener(this);
        et2.setOnFocusChangeListener(this);
        et3.setOnFocusChangeListener(this);
        et4.setOnFocusChangeListener(this);

        et1.setOnKeyListener(this);
        et2.setOnKeyListener(this);
        et3.setOnKeyListener(this);
        et4.setOnKeyListener(this);
        etHidden.setOnKeyListener(this);
    }

    /**
     * Sets background of the view.
     * This method varies in implementation depending on Android SDK version.
     *
     * @param view       View to which set background
     * @param background Background to set to view
     */
    @SuppressWarnings("deprecation")
    public void setViewBackground(View view, Drawable background) {
        if (view == null || background == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int actualHeight = getHeight();

        Log.d("TAG", "proposed: " + proposedHeight + ", actual: " + actualHeight);

        if (actualHeight >= proposedHeight) {
            // Keyboard is shown
            if (etHidden.length() == 0)
                setFocusedPinBackground(et1);
            else
                setDefaultPinBackground(et1);

            showSoftKeyboard(etHidden);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
