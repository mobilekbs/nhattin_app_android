package vn.ntlogistics.app.shipper.Views.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog.InitDialogCheck;
import vn.ntlogistics.app.shipper.Commons.Image.CircleTransform;
import vn.ntlogistics.app.shipper.Commons.Location.Services.MyLocationService;
import vn.ntlogistics.app.shipper.Commons.RequestCode;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Singleton.SMainActivity;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.OrderDetailAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.UpdateUserStatusAPI;
import vn.ntlogistics.app.shipper.Models.Inputs.JSOrderDetail;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.Models.Outputs.User.MyProfile;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.MainVMs.MainActivityViewModel;
import vn.ntlogistics.app.shipper.Views.Adapters.FragmentMainAdapter;
import vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu.MyOrderFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu.NotificationFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu.ReferralCodeFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.Main.MyProfileFragment;
import vn.ntlogistics.app.shipper.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String      TAG = "MainActivityViewModel";

    private ActivityMainBinding     binding;
    private MainActivityViewModel   viewModel;



    //TODO: Twice press back finish
    boolean doubleBackToExitPressedOnce = false;

    //TODO: register
    int register = 0;

    FragmentMainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        viewModel = new MainActivityViewModel(this,binding);
        binding.setViewModel(viewModel);
        binding.menuNavMain.setViewModel(viewModel);
        binding.appBarMain.setViewModel(viewModel);
        InitDialogCheck.checkInternetGPS(this);

        init();

        SMainActivity.updateActivity(this);
    }

    void init() {

        //initSwitchStatus();
        initToolbar();
        initDrawerMenu();


        try {
            register = (int) getIntent().getExtras().get("Register");
        } catch (Exception e) {
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setupFragment();
            }
        },100);

        //TODO: Start service
        startService(new Intent(MainActivity.this, MyLocationService.class));
        //TODO: Khi bật ứng dụng lên thì mặc định sẽ là trạng thái online
        new UpdateUserStatusAPI(this, Constants.STATUS_WORKING_USER+"", 1,viewModel).execute();
    }

    public void setBadgeNotification(int i) {
        if (i > 0) {
            binding.menuNavMain.tvNote.setVisibility(View.VISIBLE);
            binding.menuNavMain.tvNote.setText(i + "");
            binding.appBarMain.badgeView.setText(i + "");
            if(binding.appBarMain.contentMain.viewPager.getCurrentItem()==4)
                binding.appBarMain.badgeView.setVisibility(View.VISIBLE);
            else
                binding.appBarMain.badgeView.setVisibility(View.GONE);
        } else {
            binding.menuNavMain.tvNote.setVisibility(View.GONE);
            binding.appBarMain.badgeView.setVisibility(View.GONE);
        }
        Commons.setBadgeIconApp(this, i);
    }

    public void setHeaderMenu(final MyProfile myProfile) {
        if (myProfile.getAvatarPhoto() != null)
            Glide.with(this)
            .load(myProfile.getAvatarPhoto())
            .asBitmap()
            .transform(new CircleTransform(this))
            .centerCrop()
            .error(R.mipmap.ic_avatar_white)
            .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    binding.navHeaderMain.ivAvatar.setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    File imgFile = new File(myProfile.getAvatarPhoto());
                    Uri uri = Uri.fromFile(imgFile);
                    Glide.with(getApplicationContext())
                            .load(uri)
                            .asBitmap()
                            .transform(new CircleTransform(getApplicationContext()))
                            .centerCrop()
                            .error(R.mipmap.ic_avatar_white)
                            .into(binding.navHeaderMain.ivAvatar);
                }
            });
        else
            Glide.with(this)
                    //TODO: URL Image
                    .load(R.mipmap.ic_avatar_white)
                    .into(binding.navHeaderMain.ivAvatar);
        binding.navHeaderMain.tvNameNav.setText(myProfile.getFullName());
        binding.navHeaderMain.tvPhoneNav.setText(SCurrentUser.getCurrentUser(this).getPhoneNo());
    }

    public void callOrderDetailAPI(Order order) {
        JSOrderDetail data = new JSOrderDetail(
                this,
                order.getShippingCode(),
                Constants.STATUS_NEW_ORDER + "",
                order.getJobType() + ""
        );
        String json = new Gson().toJson(data);
        new OrderDetailAPI(
                this,
                json,
                order,
                Constants.STATUS_NEW_ORDER,
                -1,
                false
        ).execute();
    }


    /**
     * Update user status success
     * Call from UpdateUserStatusAPI
     */
    /*public void updateUserStatusSuccess(boolean b) {
        ((MyOrderFragment)adapter.getFragment(1)).updateUserStatusSuccess(b);
        binding.appBarMain.swStatus.setChecked(b);
        //isCheckedStatus = b;
        //TODO: Cho chạy service lấy vị trí khi user đang làm việc
        if (b) {
            if (!Utils.isMyServiceRunning(MainActivity.this, MyLocationService.class))
                startService(new Intent(MainActivity.this, MyLocationService.class));
        } else {
            if (Utils.isMyServiceRunning(MainActivity.this, MyLocationService.class))
                stopService(new Intent(MainActivity.this, MyLocationService.class));
        }
    }*/

    void initToolbar() {
        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void initDrawerMenu() {
        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setupFragment() {
        adapter = new FragmentMainAdapter(
                getSupportFragmentManager(),
                this);

        adapter.addFragment(new MyProfileFragment(), getResources().getString(R.string.title_taixe));
        adapter.addFragment(new MyOrderFragment(), getResources().getString(R.string.title_donhang));
        //adapter.addFragment(new WalletFragment(), getResources().getString(R.string.title_money));
        adapter.addFragment(new ReferralCodeFragment(), getResources().getString(R.string.title_refer_code));
        adapter.addFragment(new NotificationFragment(), getResources().getString(R.string.title_notify));

        binding.appBarMain.contentMain.viewPager.setAdapter(adapter);
        viewModel.setAdapter(adapter);
        if (register == 1) {
            try {
                viewModel.initDialogRegister();
            } catch (Exception e) {
            }
            viewModel.setChoose(0);
        } else {
            viewModel.setChoose(1);
        }
        //TODO: Số lượng page load sẵn, nhỏ nhất là 3
        binding.appBarMain.contentMain.viewPager.setOffscreenPageLimit(5);

        binding.appBarMain.contentMain.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d("VIEWPAGER",position+" - " + positionOffset + " - " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                    case 2: // Wallet Info
                    case 4:
                        try {
                            adapter.getFragment(position).changeFragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void handleNotiInNotificationFragment() {
        ((NotificationFragment) adapter.getFragment(4)).setupDataList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce)
                moveTaskToBack(true);
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getResources().getString(R.string.twice_back), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 250) {
                    try {
                        String myOrder = String.valueOf(data.getExtras().get("myOrder"));
                        Order order = new Gson().fromJson(myOrder, Order.class);
                        callOrderDetailAPI(order);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(requestCode == RequestCode.CHANGE_INFO) { //change info user
                    try {
                        MyProfile myProfile = (MyProfile) data.getExtras().getSerializable("myProfile");
                        ((MyProfileFragment)adapter.getFragment(0)).changeInfoUser(myProfile);
                    } catch (Exception e) {
                    }
                }
                else {
                    ((MyProfileFragment) adapter.getFragment(0)).getResultFragment(requestCode, data);
                }
                /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    MyProfileFragment.instance.setImageCropper(result.getUri());
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy intent gửi về từ Notification
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /**
         * Hàm xử lý khi nhấn vào notifacation sẽ chạy getOrder  */
        try {
            /*String myOrder = String.valueOf(intent.getExtras().get("myOrder"));
            Order order = new Gson().fromJson(myOrder, Order.class);
            callOrderDetailAPI(order);*/
            //viewModel.setCurrentFragment(1);
            viewModel.setChoose(1);
            ((MyOrderFragment)adapter.getFragment(1)).setCurrentFragment(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //Fix bug khi thoát ra ở 1 tab k fai mặc định thì khi Resume, background chọn luôn mặc định
            viewModel.setChoose(binding.appBarMain.contentMain.viewPager.getCurrentItem());
        } catch (Exception e) {
        }

        try {
            //NewOrderFragment.getInstanse().isOnline(swStatus.isChecked());
            ((MyOrderFragment)adapter.getFragment(1)).updateUserStatusSuccess(
                    binding.appBarMain.swStatus.isChecked()
            );
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        SMainActivity.clear();
        //instance = null;
        /*// Intent yêu cầu gửi đến Service.
        Intent serviceIntent = new Intent(this, DestroyService.class);
        // Chạy dịch vụ.
        startService(serviceIntent);*/
        /*if(!Utils.isMyServiceRunning(MainActivity.this,DestroyIntentService.class))
            startService(new Intent(MainActivity.this, DestroyIntentService.class));*/
        //new UpdateUserStatusAPI(this,31+"",1).execute();

        if (Commons.isMyServiceRunning(MainActivity.this, MyLocationService.class))
            stopService(new Intent(MainActivity.this, MyLocationService.class));
        super.onDestroy();
    }

    @Override
    public void handleNotification(int action, Bundle b) {
        switch (action) {
            case 6: //Huy don hang
                adapter.getFragment(1).handleNotification(action, null);
                break;
            case 2: //Xoa don hang
                adapter.getFragment(1).handleNotification(action, b);
                break;
        }
    }

    public void reloadFromNotification(List<Order> mList){
        /*((NewOrderFragment)((MyOrderFragment) adapter.getFragment(1))
                .getFragmentAdapter(0)).reloadFromNotification(mList);*/
    }

    public BaseFragment getChildFragment(int main, int child){
        if(main == 1){
            if(child == -1){
                return adapter.getFragment(main);
            }
            else
                return ((MyOrderFragment)adapter.getFragment(main)).getFragmentAdapter(child);
        }
        else
            return adapter.getFragment(main);
    }
}
