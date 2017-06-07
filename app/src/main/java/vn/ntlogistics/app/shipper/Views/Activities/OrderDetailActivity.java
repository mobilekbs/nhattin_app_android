package vn.ntlogistics.app.shipper.Views.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Singleton.SJob;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.AcceptOrderAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.DenyOrdersAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.GetStatusOfBillAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.OrderDetailAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.RefuseOrderAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.RemoveFromListAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.UpdateOrderStatusAPI;
import vn.ntlogistics.app.shipper.Models.Enums.EServiceShip;
import vn.ntlogistics.app.shipper.Models.Inputs.JSADROrder;
import vn.ntlogistics.app.shipper.Models.Inputs.JSOrderDetail;
import vn.ntlogistics.app.shipper.Models.Inputs.JSStatusOfBill;
import vn.ntlogistics.app.shipper.Models.Inputs.JSUpdateStatus;
import vn.ntlogistics.app.shipper.Models.Inputs.ListFee;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.BLDetail;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.OrderDetail;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Adapters.ViewPagerAdapter;
import vn.ntlogistics.app.shipper.Views.Fragments.OrderDetail.OrderInfoFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.OrderDetail.ServiceFeeFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.OrderDetail.ServiceInfoFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.OrderDetail.StatusOrderFragment;

//TODO: Chi tiết đơn hàng
public class OrderDetailActivity extends BaseActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    ViewPagerAdapter adapter;
    //TODO: toolbar
    private AppCompatTextView tvTitleToolbar;
    private Toolbar toolbar;
    public ImageView ivHideShowList;
    String[] titles;
    ArrayList<String> listBLCode;
    Thread loadViewPager;
    String shippingCode, dateTime;
    int jobtype = -1;
    //TODO: init button
    View btnReceive, btnDismiss, btnUpdate, btnRefuse;
    public int statusOrder;

    TextView tvBtnUpdate, tvDismiss;
    List<BaseFragment> mListFragment;

    //TODO: list BL dùng để show update
    List<String> mListBLUpdate;

    CustomDialog dialog;
    ProgressBar progressBar;

    OrderDetail orderDetail;
    Order order;
    //View fbStart;
    LinearLayout ln;
    float minX = 0, maxX, minY, maxY;

    BaseAsystask asystaskLoadDetail;

    //TODO: Chứa position được chọn trong RecyclerView để xóa khi nhận hoặc hủy
    public static int idItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detail);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

        initToolbar();
        init();
        initViewPager();
        setupControls();
        //mMapView = (MapView) findViewById(R.id.mapNewOrder);
        //mapsNewOrder = new LayoutMapsNewOrder(this,savedInstanceState,mMapView);

        asystaskLoadDetail = new BaseAsystask() {

            @Override
            public void onPre() {
                if (mViewPager.getVisibility() == View.GONE)
                    mViewPager.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void doInBG() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupViewPager();
                    }
                });
            }

            @Override
            public void onPost() {
                tabLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        };
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                asystaskLoadDetail.execute();
            }
        }, 300);
    }

    private void init() {
        /**
         * ivHideShowList là icon trên toolbar
         * Dùng để hiển thị nội dung trong Mua Hàng theo 2 dạng: hình chữ or chữ
         * Nó chỉ hiển thị khi ở trong Mua Hàng
         */
        ivHideShowList = (ImageView) findViewById(R.id.ivHideShowList);

        initButton();
    }

    private void initButton() {
        btnReceive = findViewById(R.id.btnReceiveBL);
        btnDismiss = findViewById(R.id.btnDismissBL);
        btnUpdate = findViewById(R.id.btnUpdateBL);
        btnRefuse = findViewById(R.id.btnRefuseBL);
        tvBtnUpdate = (TextView) findViewById(R.id.tvBtnUpdate);
        tvDismiss = (TextView) findViewById(R.id.tvDismiss);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //fbStart = findViewById(R.id.fbStart);
        ln = (LinearLayout) findViewById(R.id.lnButton);

        setClickButton();
    }

    private void setupControls() {
        //Lấy BLCode tử List BLDetail
        mListBLUpdate = new ArrayList<>();
        if (orderDetail != null) {
            List<BLDetail> mList = orderDetail.getData();
            if (mList != null)
                for (int i = 0; i < mList.size(); i++) {
                    mListBLUpdate.add(mList.get(i).getConsigneeAddress());
                }
        }

        /*if (statusOrder == Common.STATUS_NEW_ORDER) {
            btnUpdate.setVisibility(View.GONE);
            btnRefuse.setVisibility(View.GONE);
            btnDismiss.setVisibility(View.VISIBLE);
            btnReceive.setVisibility(View.VISIBLE);
        } else*/
        if (statusOrder == Constants.STATUS_UNCOMPLETED) {
            btnDismiss.setVisibility(View.GONE);
            btnReceive.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            tvBtnUpdate.setText(getResources().getString(R.string.update_dh));
            if (SCurrentUser.getCurrentUser(this).getUserID().equalsIgnoreCase("167")) {
                tvDismiss.setText(getResources().getString(R.string.refuse));
                btnRefuse.setVisibility(View.VISIBLE);
            } else {
                btnRefuse.setVisibility(View.GONE);
            }
            /**
             * Nếu 1 BL trong Order đã hoàn thành or đã update thì sẽ không thể từ chối
             */
            if (listBLCode.size() > mListBLUpdate.size()) {
                btnRefuse.setBackgroundResource(R.drawable.bg_button_hide);
                btnRefuse.setEnabled(false);
            }
        } else if (statusOrder == Constants.STATUS_COMPLETED) {
            btnDismiss.setVisibility(View.GONE);
            btnReceive.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnRefuse.setVisibility(View.VISIBLE);
            tvBtnUpdate.setText(getResources().getString(R.string.edit_dh));
            tvDismiss.setText(getResources().getString(R.string.delete));
        } else {
            btnDismiss.setVisibility(View.GONE);
            btnReceive.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
            btnRefuse.setVisibility(View.VISIBLE);
            //tvBtnUpdate.setText(getResources().getString(R.string.edit_dh));
            tvDismiss.setText(getResources().getString(R.string.delete));
        }
    }

    /**
     * Kiểm tra xem các BL trong Order có BL nào đã update Status chưa.
     *
     * @param lstBL
     * @return true khi có BL đã update, false khi không có BL nào update.
     */
    private boolean checkUpdatedStatus(List<BLDetail> lstBL) {
        for (int i = 0; i < lstBL.size(); i++) {
            if (!SJob.getStatusNewOrder(lstBL.get(i).getStatusID()))
                return true;
        }
        return false;
    }

    private void setClickButton() {
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
                callAcceptOrderAPI();
            }
        });
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
                dialog = new CustomDialog(OrderDetailActivity.this);
                dialog.setTextTitle(getResources().getString(R.string.note_dialog));
                dialog.setTitleMessage(getResources().getString(R.string.dialog_not_receive_button));
                dialog.setShow(true);
                dialog.setTextButton(getResources().getString(R.string.yes), getResources().getString(R.string.no));
                dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                    @Override
                    public void onClickOk() {
                        callDenyOrderAPI();
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
                if (mListBLUpdate.size() > 1) {
                    CharSequence[] listBL = new CharSequence[mListBLUpdate.size() + 1];
                    for (int i = 0; i < mListBLUpdate.size() + 1; i++) {
                        if (i == 0) {
                            listBL[0] = getResources().getString(R.string.dialog_choose_all);
                        } else {
                            listBL[i] = mListBLUpdate.get(i - 1);
                        }
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                    builder.setTitle(getResources().getString(R.string.dialog_choose_blcode));
                    builder.setItems(listBL, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            callGetStatusOfBillAPI(which);
                        }
                    });
                    builder.show();
                } else {
                    callGetStatusOfBillAPI(1);
                }

            }
        });

        btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
                long time = Long.parseLong(dateTime) - System.currentTimeMillis();
                int hours = (int) ((time / (1000 * 60 * 60)) % 24);

                dialog = new CustomDialog(OrderDetailActivity.this);
                dialog.setTextTitle(getResources().getString(R.string.note_dialog));
                if (statusOrder == Constants.STATUS_UNCOMPLETED) {
                    if (hours < 1) {
                        dialog.setTitleMessage(getResources().getString(R.string.dialog_refuse_1));
                    } else
                        dialog.setTitleMessage(getResources().getString(R.string.dialog_refuse_2));
                } else {
                    dialog.setTitleMessage(getResources().getString(R.string.dialog_completed));
                }
                dialog.setShow(true);
                dialog.setTextButton(getResources().getString(R.string.yes), getResources().getString(R.string.no));
                dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                    @Override
                    public void onClickOk() {
                        if (statusOrder == Constants.STATUS_UNCOMPLETED)
                            callRefuseOrderAPI();
                        else
                            callRemoveFromListAPI();
                        dialog.dismiss();
                    }

                    @Override
                    public void onClickCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    void initToolbar() {
        Bundle b = getIntent().getExtras();

        try {
            orderDetail = (OrderDetail) b.getSerializable("orderDetail");
        } catch (Exception e) {
        }

        try {
            order = (Order) b.getSerializable("order");
            shippingCode = order.getShippingCode();
            dateTime = order.getAssignDatetime();
            listBLCode = (ArrayList<String>) order.getLstBLCode();
            jobtype = order.getJobType();
            idItem = (int) b.get("idItem");
        } catch (Exception e) {

        }

        try {
            statusOrder = Integer.parseInt(getIntent().getExtras().get("statusOrder").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitleToolbar = (AppCompatTextView) findViewById(R.id.tvTitleToolbar);

        tvTitleToolbar.setText(shippingCode);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void initViewPager() {
        titles = new String[]{};
        /**
         * Nếu jobType thuộc SHIP U thì sẽ không cho hiển thị tab Hàng Hoá/Dịch vụ
         */
        if (SJob.compareServiceShip(jobtype) != EServiceShip.SHIP_U) {
            titles = new String[]{
                    getResources().getString(R.string.orderdetail_tab1),
                    getResources().getString(R.string.orderdetail_tab2),
                    getResources().getString(R.string.orderdetail_tab3),
                    getResources().getString(R.string.orderdetail_tab4)
            };
        } else {
            titles = new String[]{
                    getResources().getString(R.string.orderdetail_tab1),
                    getResources().getString(R.string.orderdetail_tab3),
                    getResources().getString(R.string.orderdetail_tab4)
            };
        }

        mListFragment = new ArrayList<>();
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, mListFragment, titles);
        mViewPager = (ViewPager) findViewById(R.id.container);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                adapter.getFragmentAdapter(position).changeFragment();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /**Số lượng page load sẵn, nhỏ nhất là 2
         * Cho lúc chuyển tab mượt
         **/
        //mViewPager.setOffscreenPageLimit(4);
    }

    private void setupViewPager() {
        final List<BaseFragment> list = new ArrayList<>();
        OrderInfoFragment delevery = new OrderInfoFragment();
        ServiceInfoFragment info = new ServiceInfoFragment();
        ServiceFeeFragment cost = new ServiceFeeFragment();
        StatusOrderFragment status = new StatusOrderFragment();
        list.add(delevery);
        list.add(info);
        list.add(cost);
        list.add(status);
        mViewPager.setOffscreenPageLimit(4);
        mListFragment.addAll(list);
        adapter.notifyDataSetChanged();
    }

    //Tu choi
    private void callRefuseOrderAPI() {
        JSADROrder data = new JSADROrder(this);
        data.setData(listBLCode);

        List<String> mListShippingCode = new ArrayList<>();
        mListShippingCode.add(shippingCode);
        data.setShippingCode(mListShippingCode);

        List<BLDetail> mListBLDetail = orderDetail.getData();
        List<ListFee> mListFee = new ArrayList<>();
        for (int i = 0; i < mListBLDetail.size(); i++) {
            double amount = 0;
            if (mListBLDetail.get(i).getConsigneeShippingFee() == 0)
                amount = mListBLDetail.get(i).getSenderShippingFee();
            else
                amount = mListBLDetail.get(i).getConsigneeShippingFee();
            mListFee.add(new ListFee(mListBLDetail.get(i).getBlCode(), amount));
        }
        data.setLstFee(mListFee);
        String json = new Gson().toJson(data);
        new RefuseOrderAPI(this, json, null).execute();
    }

    //Chap nhan
    private void callAcceptOrderAPI() {
        JSADROrder data = new JSADROrder(this);
        data.setData(listBLCode);

        List<String> mListShippingCode = new ArrayList<>();
        mListShippingCode.add(shippingCode);
        data.setShippingCode(mListShippingCode);

        List<BLDetail> mListBLDetail = orderDetail.getData();
        List<ListFee> mListFee = new ArrayList<>();
        for (int i = 0; i < mListBLDetail.size(); i++) {
            double amount = 0;
            if (mListBLDetail.get(i).getConsigneeShippingFee() == 0)
                amount = mListBLDetail.get(i).getSenderShippingFee();
            else
                amount = mListBLDetail.get(i).getConsigneeShippingFee();
            mListFee.add(new ListFee(mListBLDetail.get(i).getBlCode(), amount));
        }

        data.setLstFee(mListFee);
        String json = new Gson().toJson(data);
        new AcceptOrderAPI(this, json, null, order.getOrderType()).execute();
    }

    //Huy don hang
    private void callRemoveFromListAPI() {
        JSADROrder data = new JSADROrder(this);
        data.setData(listBLCode);
        String json = new Gson().toJson(data);
        new RemoveFromListAPI(this, json, null, 1).execute();
    }

    private void callDenyOrderAPI() {
        JSADROrder data = new JSADROrder(this);
        data.setData(listBLCode);
        String json = new Gson().toJson(data);
        new DenyOrdersAPI(this, json, null).execute();
    }

    private void callGetStatusOfBillAPI(int position) {
        try {
            int mPosition = 0;
            String blCode;
            if (position == 0) {
                mPosition = -1;
                blCode = listBLCode.get(0);
            } else {
                mPosition = position - 1;
                blCode = listBLCode.get(mPosition);
            }
            JSStatusOfBill data = new JSStatusOfBill(
                    this,
                    blCode,
                    jobtype
            );
            String json = new Gson().toJson(data);
            new GetStatusOfBillAPI(
                    this, json, shippingCode, listBLCode, jobtype,
                    mPosition, orderDetail.getData()
            ).execute();
        } catch (Exception e) {
            Log.d("SC020102Activity", "callGetStatusOfBillAPI ------------");
            e.printStackTrace();
        }
    }

    private void callUpdateStatusOrderAPI() {

        JSUpdateStatus data = new JSUpdateStatus(this);

        List<String> mListShippingCode = new ArrayList<>();

        mListShippingCode.add(shippingCode);

        data.setData(listBLCode);
        data.setStatusId(SJob.getIdStartByJobType(jobtype) + "");
        data.setShippingCode(mListShippingCode);
        data.setJobType(jobtype);
        String json = new Gson().toJson(data);

        new UpdateOrderStatusAPI(
                this, json, null,
                SJob.getIdStartByJobType(jobtype), shippingCode
        ).execute();
    }

    /*public void updateStatusSuccess(boolean b) {
        if (b) {
            if (SCurrentUser.getCurrentUser(this).getUserID().equalsIgnoreCase("167")) {
                btnRefuse.setBackgroundResource(R.drawable.bg_button_hide);
                btnRefuse.setEnabled(false);
            }
            Message.showToast(this, getString(R.string.dialog_start_success));
            fbStart.setVisibility(View.GONE);
        }
    }*/

    @Override
    public void updateStatusSuccess(int statusID) {
        if (statusID > -1) {
            /*if (SCurrentUser.getCurrentUser(this).getUserID().equalsIgnoreCase("167")) {
                btnRefuse.setBackgroundResource(R.drawable.bg_button_hide);
                btnRefuse.setEnabled(false);
            }*/
            Message.showToast(this, getString(R.string.dialog_start_success));
            //fbStart.setVisibility(View.GONE);
        }
    }

    //TODO: Init sup controls
    private AlertDialog getAlertDialog() {
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.note_dialog));
        builder.setMessage(getResources().getString(R.string.dialog_new_order));

        /*builder.setPositiveButton(getString(R.string.btnOk),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/
        builder.setNegativeButton(getString(R.string.btnOk),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    /**
     * Sau khi nhận đơn hàng xong
     */
    public void receiveSuccess() {
        /*lnMapNewOrder.setVisibility(View.GONE);
        //Load layout
        //asystaskLoadDetail.execute();
        JSOrderDetail js = new JSOrderDetail(this);
        js.setShippingCode(shippingCode);
        js.setStatusId(Common.STATUS_UNCOMPLETED + "");
        js.setJobType(jobtype + "");
        String json = new Gson().toJson(js);
        new OrderDetailAPI(
                this,
                json,
                order,
                Common.STATUS_UNCOMPLETED,
                idItem,
                true
        ).execute();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            loadViewPager.interrupt();
            loadViewPager = null;
        } catch (Exception e) {
        }
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            loadViewPager.interrupt();
            loadViewPager = null;
        } catch (Exception e) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //mMapView.onPause();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //mMapView.onResume();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            loadViewPager.interrupt();
            loadViewPager = null;
        } catch (Exception e) {
        }
        try {
            //mMapView.onDestroy();
        } catch (Exception e) {
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        try {
            //mMapView.onLowMemory();
        } catch (Exception e) {
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == RESULT_OK)
                if (requestCode == 220) {
                    JSOrderDetail js = new JSOrderDetail(this);
                    js.setShippingCode(shippingCode);
                    js.setStatusId(statusOrder + "");
                    js.setJobType(jobtype + "");
                    String json = new Gson().toJson(js);
                    new OrderDetailAPI(
                            this,
                            json,
                            order,
                            statusOrder,
                            -1,
                            true
                    ).execute();

                    /*int s = Integer.parseInt(data.getExtras().get("status").toString());
                    *//**
                     * Status == 1 thì tức là update status thành công,
                     * cho hiện nữa nút của đã hoàn thành
                     * Còn nếu == 2 tức là edit status thành công, và không đủ điều kiện
                     * thành công Order đó nên sẽ trở lại đơn hàng chưa hoàn thành.
                     * Và hiện giao diện chưa hoàn thành.
                     *//*
                    if (s == 1) {
                        statusOrder = Common.STATUS_COMPLETED;
                        btnDismiss.setVisibility(View.GONE);
                        btnReceive.setVisibility(View.GONE);
                        btnUpdate.setVisibility(View.VISIBLE);
                        btnRefuse.setVisibility(View.VISIBLE);
                        tvBtnUpdate.setText(getResources().getString(R.string.edit_dh));
                        tvDismiss.setText(getResources().getString(R.string.delete));
                    }
                    else if(s == 2){
                        statusOrder = Common.STATUS_UNCOMPLETED;
                        btnDismiss.setVisibility(View.GONE);
                        btnReceive.setVisibility(View.GONE);
                        btnUpdate.setVisibility(View.VISIBLE);
                        btnRefuse.setVisibility(View.VISIBLE);
                        tvBtnUpdate.setText(getResources().getString(R.string.update_dh));
                        tvDismiss.setText(getResources().getString(R.string.refuse));
                    }*/
                }
        } catch (Exception e) {
        }
    }
}
