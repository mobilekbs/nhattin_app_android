package vn.ntlogistics.app.ordermanagement.Views.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.ordermanagement.Commons.Enums.EServiceShip;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SJob;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.BLDetail;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.BillDetail;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Adapters.ViewPagerAdapter;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.OrderDetail.OrderInfoFragment;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.OrderDetail.ServiceFeeFragment;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.OrderDetail.ServiceInfoFragment;
import vn.ntlogistics.app.ordermanagement.Views.Fragments.OrderDetail.StatusOrderFragment;

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
    View btnEdit, btnCompleted;
    public int statusOrder;

    List<BaseFragment> mListFragment;

    //TODO: list BL dùng để show update
    List<String> mListBLUpdate;

    CustomDialog dialog;
    ProgressBar progressBar;

    //OrderKDetail orderKDetail;
    BillDetail billDetail;
    Bill bill;

    float minX = 0, maxX, minY, maxY;

    BaseAsystask asystaskLoadDetail;

    //TODO: Chứa position được chọn trong RecyclerView để xóa khi nhận hoặc hủy
    public static int idItem = -1;

    public static void startIntentActivity(Context context,
                                           BillDetail billDetail,
                                           Bill bill,
                                           int statusOrder,
                                           int idItem){
        Bundle b = new Bundle();
        b.putSerializable("orderDetail", billDetail);
        b.putSerializable("order", bill);
        b.putInt("statusOrder", statusOrder);
        b.putInt("idItem", idItem);

        Intent i = new Intent(context, OrderDetailActivity.class);
        i.putExtras(b);

        context.startActivity(i);
        ((Activity) context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }

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
        /**
         * Khi vào đơn hàng mới thì sẽ hiện Layout Maps New Order
         */
        /*if (statusOrder == Common.STATUS_NEW_ORDER) {
            getAlertDialog().show();
            progressBar.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            //lnMapNewOrder.setVisibility(View.VISIBLE);
            //mapsNewOrder.loadMap();
        }
        else{

        }*/

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                asystask.execute();
            }
        }, 50);*/
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
        btnEdit = findViewById(R.id.btnEdit);
        btnCompleted = findViewById(R.id.btnCompleted);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        setClickButton();
    }

    private void setupControls() {
        //Lấy BLCode tử List BLDetail
        mListBLUpdate = new ArrayList<>();
        if(billDetail != null) {
            List<BLDetail> mList = billDetail.getData();
            if (mList != null)
                for (int i = 0; i < mList.size(); i++) {
                    mListBLUpdate.add(mList.get(i).getConsigneeAddress());
                }
        }

        if (statusOrder == Constants.STATUS_UNCOMPLETED) {
            btnCompleted.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        } else {
            btnCompleted.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        }
    }

    private void setClickButton() {
        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
                callCompletedOrderAPI();
                /*dialog = new CustomDialog(OrderDetailActivity.this);
                dialog.setTextTitle(getResources().getString(R.string.note_dialog));
                dialog.setTitleMessage(
                        getString(R.string.message_dialog_update_now)
                );
                dialog.setShow(true);
                dialog.setTextButton(getResources().getString(R.string.yes), getResources().getString(R.string.no));
                dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                    @Override
                    public void onClickOk() {

                        dialog.dismiss();
                    }

                    @Override
                    public void onClickCancel() {
                        dialog.dismiss();
                    }
                });
                dialog.show();*/
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Commons.setEnabledButton(view);
                //Sửa thông tin đơn hàng

            }
        });
    }

    void initToolbar() {
        Bundle b = getIntent().getExtras();
        /*try {
            shippingCode = b.getString("shippingCode");
            dateTime = b.getString("dateTime");
            listBLCode = getIntent().getStringArrayListExtra("listBLCode");
            jobtype = Integer.parseInt(b.get("Jobtype").toString());
            idItem = (int) b.get("idItem");
        } catch (Exception e) {
        }*/

        try {
            billDetail = (BillDetail) b.getSerializable("orderDetail");
        } catch (Exception e) {
        }

        try {
            bill = (Bill) b.getSerializable("order");
            shippingCode = bill.getBillID();
            dateTime = bill.getAssignDatetime();
            //listBLCode = (ArrayList<String>) bill.getLstBLCode();
            jobtype = bill.getJobType();
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
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                OrderDetailActivity.this,
                mListFragment, titles);
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

    //Hoan thanh don hang
    private void callCompletedOrderAPI() {

    }
/*
    public void updateStatusSuccess(boolean b) {
        if (b) {
            if (SCurrentUser.getCurrentUser(this).getUserID().equalsIgnoreCase("167")) {
                btnRefuse.setBackgroundResource(R.drawable.bg_button_hide);
                btnRefuse.setEnabled(false);
            }
            Message.showToast(this, getString(R.string.dialog_start_success));
            fbStart.setVisibility(View.GONE);
        }
    }*/


    //TODO: Init sup controls
    /*private AlertDialog getAlertDialog() {
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.note_dialog));
        builder.setMessage(getResources().getString(R.string.dialog_new_order));

        *//*builder.setPositiveButton(getString(R.string.btnOk),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*//*
        builder.setNegativeButton(getString(R.string.btnOk),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }*/

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
            if (resultCode == RESULT_OK){}
                /*if (requestCode == 220) {
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

                    *//*int s = Integer.parseInt(data.getExtras().get("status").toString());
                    *//**//**
                     * Status == 1 thì tức là update status thành công,
                     * cho hiện nữa nút của đã hoàn thành
                     * Còn nếu == 2 tức là edit status thành công, và không đủ điều kiện
                     * thành công Order đó nên sẽ trở lại đơn hàng chưa hoàn thành.
                     * Và hiện giao diện chưa hoàn thành.
                     *//**//*
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
                    }*//*
                }*/
        } catch (Exception e) {
        }
    }
}
