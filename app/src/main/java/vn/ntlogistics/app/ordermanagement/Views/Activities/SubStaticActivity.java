package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.R;

public class SubStaticActivity extends BaseActivity {

    Button btnNang, btnCong, btnThong;

    public static void startIntentActivity(Context context){
        Intent i = new Intent(context, SubStaticActivity.class);
        context.startActivity(i);
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_static);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSendBill);
        toolbar.setTitle(getString(R.string.menu_title_5));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnNang  = (Button) findViewById(R.id.btnnang);
        btnCong  = (Button) findViewById(R.id.btncong);
        btnThong = (Button) findViewById(R.id.btnthong);

        btnNang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                ProductivityStatisticsActivity.startIntentActivity(SubStaticActivity.this);
            }
        });

        btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                Intent intent = new Intent(SubStaticActivity.this , CongoThongListActivity.class);
                intent.putExtra("type" , "2");
                startActivity(intent);
            }
        });

        btnThong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                Intent intent = new Intent(SubStaticActivity.this , CongoThongListActivity.class);
                intent.putExtra("type" , "3");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

}
