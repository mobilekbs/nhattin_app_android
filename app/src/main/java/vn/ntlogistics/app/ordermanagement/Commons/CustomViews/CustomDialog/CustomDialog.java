package vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

import vn.ntlogistics.app.ordermanagement.R;


/**
 * Created by (TB0) on 01/07/2016.
 */
public class CustomDialog extends Dialog {
    Activity activity;
    TextView tv_title, tv_message;
    TextView bt_ok, btnCancel;
    private RecyclerView rvNotiDialog;
    SetOnClickDialog setOnClickDialog;
    String title = "";
    String message = "";

    String tvBtnOk = "";
    String tvBtnCancle = "";

    boolean showGPS = true;
    boolean show = false;
    boolean show1Button = false;

    //TODO: Init recycler view
    private List<String> mListNote;
    private View lnNoteDialog;

    public interface SetOnClickDialog {
        void onClickOk();
        void onClickCancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custom_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        tv_title = (TextView) findViewById(R.id.tvTextTitle);
        tv_message = (TextView) findViewById(R.id.tvmessage);
        bt_ok = (TextView)findViewById(R.id.btnok);
        btnCancel = (TextView)findViewById(R.id.btnCancel);
        rvNotiDialog = (RecyclerView) findViewById(R.id.rvNotiDialog);
        lnNoteDialog = findViewById(R.id.lnNoteDialog);

        tv_title.setText(title);
        tv_message.setText(message);

        if(mListNote != null) {
            lnNoteDialog.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
            rvNotiDialog.setLayoutManager(layoutManager);
            DividerItemDecoration decoration = new DividerItemDecoration(
                    activity, layoutManager.getOrientation());
            rvNotiDialog.addItemDecoration(decoration);
            rvNotiDialog.setItemAnimator(new DefaultItemAnimator());
            //NoteDialogAdapter adapter = new NoteDialogAdapter(activity, mListNote);
            //rvNotiDialog.setAdapter(adapter);
        }

        if(!show)
            if (showGPS) {
                btnCancel.setVisibility(View.GONE);
                bt_ok.setText(activity.getResources().getString(R.string.setting_dialog));
            } else {
                btnCancel.setVisibility(View.GONE);
                bt_ok.setText(activity.getResources().getString(R.string.try_again));
            }
        else {
            if(!show1Button) {
                bt_ok.setText(tvBtnOk);
                btnCancel.setText(tvBtnCancle);
            }
            else {
                btnCancel.setVisibility(View.GONE);
                bt_ok.setText(tvBtnCancle);
            }
        }
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickDialog.onClickOk();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnClickDialog.onClickCancel();
            }
        });
    }

    public void setNoteDialog(List<String> mListNote){
        if(mListNote != null && mListNote.size() > 0) {
            this.mListNote = mListNote;
        }
        else {
            this.mListNote = null;
        }
    }

    public CustomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void setTextButton(String btnOk, String btnCancle){
        tvBtnOk = btnOk;
        tvBtnCancle = btnCancle;
    }

    public void setTextTitle(String title) {
        this.title = title;

    }

    public void setShow(boolean is){
        show = is;
    }

    public void setGPS(boolean is){
        showGPS = is;
    }

    public void setTitleMessage(String message) {
            this.message = message;
    }

    public void setOnClickButton(SetOnClickDialog setOnClickDialog) {
        this.setOnClickDialog = setOnClickDialog;
    }

    public void setShow1Button(boolean b,String s){
        show1Button = b;
        tvBtnCancle = s;
    }

}

