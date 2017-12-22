package vn.ntlogistics.app.ordermanagement.Olds.Activities;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.BillFail.BillFailSqlite;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.ConfirmBPBillAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.UpdatePinkBillAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Olds.putDataToServer;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ConfirmBPBillInput;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.UpdatePinkBillInput;
import vn.ntlogistics.app.ordermanagement.Olds.Adapters.Custom_lv_BillFail;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.BillFailAdapter;


public class BillFailActivity extends BaseActivity implements OnClickListener {
    Button sendAll;
    //ListView lvBillFail;
    public int count = 0;
    public int countsend;
    public int oldcountsend;


    //TODO: Init List BillFail
    private TextView                            tvNullList;
    private RecyclerView                        rvBillFail;
    private BillFailAdapter                     adapter;
    private ArrayList<BillFailSqlite>           mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_fail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBillFail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Cursor c = SSQLite.getInstance(this).getAllDataFromTable(Variables.TBL_BILLFAIL);
        countsend = c.getCount();
        oldcountsend = countsend;

        initRecyclerView();

        sendAll = (Button) findViewById(R.id.btnSendAll);
        /*lvBillFail = (ListView) findViewById(lvBillFail);
        lvBillFail.setOnItemClickListener(this);
        lvBillFail.setOnItemLongClickListener(this);*/
        sendAll.setOnClickListener(this);
        //showBill();
    }

    private void initRecyclerView() {
        tvNullList = (TextView) findViewById(R.id.tvNullList);
        rvBillFail = (RecyclerView) findViewById(R.id.rvBillFail);
        rvBillFail.setLayoutManager(new LinearLayoutManager(this));

        mList = SSQLite.getInstance(this).getListBillFail();
        adapter = new BillFailAdapter(this, mList);

        if(mList.size() == 0){
            tvNullList.setVisibility(View.VISIBLE);
        }
        else
            tvNullList.setVisibility(View.GONE);

        rvBillFail.setAdapter(adapter);
    }

    public void reloadRecyclerView(){
        if (adapter != null) {
            mList.clear();
            mList.addAll(SSQLite.getInstance(this).getListBillFail());
            adapter.notifyDataSetChanged();
            if(mList.size() == 0){
                tvNullList.setVisibility(View.VISIBLE);
            }
            else
                tvNullList.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.bill_fail, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.btnSendAll) {
            //NetAsync(v);
            if(Commons.hasConnection(this)){
                sendData();
            }
            else {
                Message.makeToastErrorConnect(BillFailActivity.this);
                //makeFail("Không có kết nối Internet.");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            reloadRecyclerView();
        } catch (Exception e) {
        }
    }

    /*public void showBill() {
        Cursor c = db.getAllDataFromTable(Variables.TBL_BILLFAIL);
        c.moveToFirst();

        if (c.getCount() > 0) {
            Custom_lv_BillFail adt = new Custom_lv_BillFail(this, c);
            lvBillFail.setAdapter(adt);

        } else {
            Message.makeToastSuccess(this,
                    getString(R.string.toast_get_bill_off_null));
            //makeSuccess("Bạn không có vận đơn nào chưa hoàn thành.");
        }

    }*/

    public void sendData() {
        /*String bill = "";
        String mkh = "";
        String tkh = "";
        String tinh = "";
        String quan = "";
        String money = "";
        String moneyCod = "";
        String isDO = "";
        String SK = "";
        String TL = "";
        String SL = "";
        String TLQD = "";
        //Cursor ckey = db.getAllDataFromTable(Variables.TBL_STAFF);
        //ckey.moveToFirst();
        String key = SCurrentUser.getCurrentUser(this).getPublickey();

        Cursor c = db.getAllDataFromTable(Variables.TBL_BILLFAIL);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            isDO = c.getString(c.getColumnIndex(Variables.KEY_ISDO));
            bill = c.getString(c.getColumnIndex(Variables.KEY_BILL)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_BILL)) : "";
            mkh = c.getString(c.getColumnIndex(Variables.KEY_MKH)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_MKH)) : "";
            tkh = c.getString(c.getColumnIndex(Variables.KEY_TKH)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_TKH)) : "";
            tinh = c.getString(c.getColumnIndex(Variables.KEY_TINH)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_TINH)) : "";
            quan = c.getString(c.getColumnIndex(Variables.KEY_QUAN)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_QUAN)) : "";
            money = c.getString(c.getColumnIndex(Variables.KEY_MONEY)) != null ? formatMoney(c
                    .getString(c.getColumnIndex(Variables.KEY_MONEY))) : "";
            moneyCod = c.getString(c.getColumnIndex(Variables.KEY_MONEYCOD)) != null ? formatMoney(c
                    .getString(c.getColumnIndex(Variables.KEY_MONEYCOD))) : "";
            TL = c.getString(c.getColumnIndex(Variables.KEY_TL)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_TL)) : "";
            SL = c.getString(c.getColumnIndex(Variables.KEY_SL)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_SL)) : "";
            SK = c.getString(c.getColumnIndex(Variables.KEY_SOKIENDO)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_SOKIENDO)) : "";
            TLQD = c.getString(c.getColumnIndex(Variables.KEY_TLQD)) != null ? c
                    .getString(c.getColumnIndex(Variables.KEY_TLQD)) : "";

            if (isDO.equals("N")) {
                Log.d("UNSEND", "Money: " + money + ", MoneyCOD: " + moneyCod);
                String keys[] = {isDO, ConstantURLs.UPDATE_PINK_BILL, key,
                        bill,
                        mkh, tkh, tinh, quan, money, moneyCod};
                new BillPinkTask().execute(keys);
            } else {
                String keys[] = {isDO, ConstantURLs.CONFIRM_DO, key, bill, TL, SL,
                        TLQD, SK};
                new BillPinkTask().execute(keys);
            }
            c.moveToNext();

        }*/
        for (int i = 0; i < mList.size(); i++){
            if(mList.get(i).getIsDO().equals("N")){
                //call update pink bill
                callAPIUpdatePinkBill(mList.get(i));
            }
            else {
                callAPIConfirmBPBill(mList.get(i));
            }
        }
    }

    //TODO: Call APIs -------------------------------------------

    public void callAPIConfirmBPBill(BillFailSqlite item){
        ConfirmBPBillInput input = new ConfirmBPBillInput(this);
        input.setDoCode(item.getBill());

        long dimensionWeight = 0;
        try {
            dimensionWeight = Long.parseLong(item.getDimensionWeight());
        } catch (NumberFormatException e) {
        }
        input.setDimensionWeight(dimensionWeight);

        long weight = 0;
        try {
            weight = Long.parseLong(item.getWeight());
        } catch (NumberFormatException e) {
        }
        input.setWeight(weight);

        long itemQty = 0;
        try {
            itemQty = Long.parseLong(item.getItemQty());
        } catch (NumberFormatException e) {
        }
        input.setItemQty(itemQty);

        short packNo = 0;
        try {
            packNo = Short.parseShort(item.getPackageNo());
        } catch (NumberFormatException e) {
        }
        input.setPackNo(packNo);

        input.setIsactive("Y");

        String data = new Gson().toJson(input);
        new ConfirmBPBillAPI(this, data, item.getBill(), true).execute();
    }

    public void callAPIUpdatePinkBill(BillFailSqlite item){
        UpdatePinkBillInput input = new UpdatePinkBillInput(
                this,
                item.getBill(),
                item.getCustomerID(),
                item.getCustomerName(),
                null,
                item.getMoney(),
                item.getMoneyCod(),
                item.getCity(),
                item.getDistrict());
        String data = new Gson().toJson(input);
        new UpdatePinkBillAPI(this, data, true, item.getBill()).execute();
    }

    @Override
    public void onSuccess() {
        reloadRecyclerView();
    }

    //TODO: Call APIs -------------------------------------------End/

    /**
     * ----------SEND-------------
     **/
    private class BillPinkTask extends AsyncTask<String, Integer, String> {
        int a;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            a = countsend--;
        }

        @Override
        protected String doInBackground(String... keys) {
            putDataToServer put = new putDataToServer();
            publishProgress(1);
            if (keys[0].equals("N"))
                return put.putDataDel(keys[1], keys[2], keys[3], keys[4],
                        keys[5], keys[6], keys[7], keys[8], keys[9]);
            else
                return put.putDO(keys[1], keys[2], keys[3], keys[4], keys[5],
                        keys[6], keys[7], true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            sendAll.setText("Đang gửi lại...");

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            String resultFail = result;
            String bill = result;
            result = result.substring(0, 1);
            if (result.equals("-") || result == "-") {
                resultFail = resultFail.substring(0, 2);
                bill = bill.substring(2, bill.length());
            } else {
                resultFail = resultFail.substring(0, 1);
                bill = bill.substring(1, bill.length());
            }
            Log.d("RESULT", "resultFail: " + resultFail);
            Log.d("RESULT", "result: " + result);
            // bill = bill.substring(1, bill.length());
            Log.d("BILL", "bill: " + bill);

            if (result.equals("0") || result == "0") {
                SSQLite.getInstance(BillFailActivity.this).deleteDataFromTable(Variables.TBL_BILLFAIL,
                        Variables.KEY_BILL, bill);
                Cursor c = SSQLite.getInstance(BillFailActivity.this).getAllDataFromTable(Variables.TBL_BILLFAIL);
                c.moveToFirst();

                Custom_lv_BillFail adt1 = new Custom_lv_BillFail(
                        getApplicationContext(), c);
                //lvBillFail.setAdapter(adt1);
                adt1.notifyDataSetChanged();
            } else {
                count++;
                String[] field = {Variables.KEY_STATUS};
                String[] values = {resultFail.toString()};
                boolean m = SSQLite.getInstance(BillFailActivity.this).updateData4Table(Variables.TBL_BILLFAIL,
                        Variables.KEY_BILL, bill.toString(), field, values);
                if (m)
                    Log.d("", "update thành công");
                else
                    Log.d("", "update thất bại");
                Cursor c = SSQLite.getInstance(BillFailActivity.this).getAllDataFromTable(Variables.TBL_BILLFAIL);
                c.moveToFirst();
                if (c.getCount() > 0) {
                    Custom_lv_BillFail adt2 = new Custom_lv_BillFail(
                            getApplicationContext(), c);
                    //lvBillFail.setAdapter(adt2);
                    adt2.notifyDataSetChanged();
                }

            }
            Log.d("countSEND con lai", a + "");
            if (a != 1)
                sendAll.setText("Đang gửi lại...");
            else {

                sendAll.setText("Gửi lại");
                if (count > 0) {
                    Message.makeToastWarning(getApplicationContext(),
                            "Bạn còn " + count + " vận đơn chưa gửi được");
                    //makeWarning("Bạn còn " + count + " vận đơn chưa gửi được");

                } else
                    Message.makeToastSuccess(getApplicationContext(),
                            getString(R.string.toast_bill_off_send));
                    //makeSuccess("Bạn đã gửi hết vận đơn.");
                countsend = count;
                count = 0;
            }
        }

    }

    /**
     * ---------------------------
     **/
    /*public void makeFail(String text) {
        Context context = getApplicationContext();
        LayoutInflater inflater = getLayoutInflater();
        View custom_fail = inflater.inflate(R.layout.custom_toast_fail, null);
        TextView tv = (TextView) custom_fail.findViewById(R.id.tvfail);
        tv.setText(text);
        Toast myToast = new Toast(context);
        myToast.setView(custom_fail);
        myToast.setGravity(Gravity.LEFT | Gravity.TOP, 0, 95);
        myToast.setDuration(Toast.LENGTH_SHORT);
        myToast.show();

    }

    public void makeWarning(String text) {

        Context context = getApplicationContext();
        LayoutInflater inflater = getLayoutInflater();
        View custom_waring = inflater.inflate(R.layout.custom_toast_warning,
                null);
        TextView tv = (TextView) custom_waring.findViewById(R.id.tvwarning);
        tv.setText(text);
        Toast myToast = new Toast(context);
        myToast.setView(custom_waring);
        myToast.setGravity(Gravity.LEFT | Gravity.TOP, 0, 95);
        myToast.setDuration(Toast.LENGTH_SHORT);
        myToast.show();

    }

    public void makeSuccess(String text) {
        Context context = getApplicationContext();
        LayoutInflater inflater = getLayoutInflater();
        View custom_success = inflater.inflate(R.layout.custom_toast_success,
                null);
        TextView tv = (TextView) custom_success.findViewById(R.id.tvsuccess);
        tv.setText(text);
        Toast myToast = new Toast(context);
        myToast.setView(custom_success);
        myToast.setGravity(Gravity.LEFT | Gravity.TOP, 0, 95);
        myToast.setDuration(Toast.LENGTH_SHORT);
        myToast.show();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // if (id == R.id.toMain) {
        // Intent intent = new Intent(this, Main.class);
        // startActivity(intent);
        // finish();
        // }
//        if (id == R.id.btnback) {
//            Intent i = new Intent(this, MyMenu.class);
//            startActivity(i);
//            finish();
//        }
        return super.onOptionsItemSelected(item);
    }

    private class NetCheck extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean check) {
            // TODO Auto-generated method stub
            Log.d("", "INTERNET: " + check);
            if (check == true) {
                sendData();
            } else {
                Message.makeToastErrorConnect(getApplicationContext());
                //makeFail("Không có kết nối Internet.");
            }
        }
    }

    public void NetAsync(View view) {
        new NetCheck().execute();
    }


    public String formatMoney(String input) {
        String del = "";
        char check[] = {' ', ','};

        for (int j = 0; j < check.length; j++) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == check[j]) {
                    input = input.replaceAll("\\" + input.charAt(i) + "", del);
                }
            }
        }

        return input;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
}
