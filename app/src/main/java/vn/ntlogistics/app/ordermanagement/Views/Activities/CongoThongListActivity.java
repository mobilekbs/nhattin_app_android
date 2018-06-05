package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ItemCongoThongAdapter;

public class CongoThongListActivity extends AppCompatActivity {
    RecyclerView rvCongoThong;
    ArrayList<HashMap<String, String>> arrayList;
    ProgressBar progressBar;
    ItemCongoThongAdapter itemCongoThongAdapter;
    String type = "";
    public static CongoThongListActivity congThongListActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congo_thong_list);

        congThongListActivity = this;

        arrayList    =  new ArrayList<>();
        type         = getIntent().getStringExtra("type");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvCongoThong    = (RecyclerView) findViewById(R.id.rvcongothong);
        progressBar     = (ProgressBar) findViewById(R.id.pbLoading);

        rvCongoThong.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        if(type.equalsIgnoreCase("2")) {
            toolbar.setTitle(getString(R.string.sub_static_cong));

            arrayList =  SSQLite.getInstance(this).getListCongThong(type);
        } else if(type.equalsIgnoreCase("3")) {
            toolbar.setTitle(getString(R.string.sub_static_thong));

            arrayList =  SSQLite.getInstance(this).getListCongThong(type);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                rvCongoThong.setVisibility(View.VISIBLE);
                itemCongoThongAdapter = new ItemCongoThongAdapter(arrayList, type);
                rvCongoThong.setLayoutManager(new LinearLayoutManager(CongoThongListActivity.this, LinearLayoutManager.VERTICAL, false));
                rvCongoThong.setAdapter(itemCongoThongAdapter);
            }
        }, 2000);
    }

    public void openDetail(String type, String from, String msg, String date, String time){
        Intent intent = new Intent(CongoThongListActivity.this, DetailCongoThongActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("from", from);
        intent.putExtra("msg", msg);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    public void deleteItem(int delepos, String deletype){
        Log.e("valueclickdele","valueclickdele"+delepos+" "+deletype);
        SSQLite.getInstance(this).deleteCongThong(delepos,type);
        arrayList.remove(delepos);
        itemCongoThongAdapter.notifyDataSetChanged();
    }

}
