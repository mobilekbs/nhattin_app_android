package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import vn.ntlogistics.app.ordermanagement.R;

public class DetailCongoThongActivity extends AppCompatActivity {
    TextView txtDate, txtMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_congo_thong);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(getIntent().getStringExtra("type").equalsIgnoreCase("2")) {
            toolbar.setTitle(getString(R.string.sub_static_cong)+" Detail");
        } else if(getIntent().getStringExtra("type").equalsIgnoreCase("3")) {
            toolbar.setTitle(getString(R.string.sub_static_thong)+" Detail");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtDate  = (TextView) findViewById(R.id.tvdate);
        txtMsg   = (TextView) findViewById(R.id.tvmsg);

        txtDate.setText(getIntent().getStringExtra("date")+" "+getIntent().getStringExtra("time"));
        txtMsg.setText(getIntent().getStringExtra("msg"));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

}
