package vn.ntlogistics.app.shipper.ViewModels.MainVMs;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.shipper.Commons.Location.Services.MyLocationService;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.SignoutAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.UpdateUserStatusAPI;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;
import vn.ntlogistics.app.shipper.Views.Activities.WebViewActivity;
import vn.ntlogistics.app.shipper.Views.Adapters.FragmentMainAdapter;
import vn.ntlogistics.app.shipper.Views.Fragments.ContentMenu.MyOrderFragment;
import vn.ntlogistics.app.shipper.Views.Fragments.Main.MyProfileFragment;
import vn.ntlogistics.app.shipper.databinding.ActivityMainBinding;

/**
 * Created by minhtan2908 on 1/18/17.
 */

public class MainActivityViewModel extends ViewModel {
    public static final String          TAG = "MainActivityViewModel";

    private MainActivity activity;
    private ActivityMainBinding binding;

    FragmentMainAdapter                 adapter;
    CustomDialog                        dialog;

    List<View>                          mListView; //List view menu
    //SetOnClickNavMenu navMenu;
    boolean isCheckedStatus = true;

    public MainActivityViewModel(MainActivity activity, ActivityMainBinding binding) {
        this.activity = activity;
        this.binding = binding;
        init();
    }

    public void setAdapter(FragmentMainAdapter adapter){
        this.adapter = adapter;
    }

    private void init(){
        mListView = new ArrayList<>();
        mListView.add(binding.menuNavMain.bgMenuMyPage);
        mListView.add(binding.menuNavMain.bgMenuMyOrder);
        //mListView.add(binding.menuNavMain.bgMenuMoney);
        mListView.add(binding.menuNavMain.bgMenuCode);
        mListView.add(binding.menuNavMain.bgMenuNotify);
    }

    public void onClick(View view) {
        int position = 0;
        switch (view.getId()) {
            case R.id.menu_my_page:
                position = 0;
                break;
            case R.id.menu_my_order:
                position = 1;
                break;
            /*case R.id.menu_money:
                position = 2;
                break;*/
            case R.id.menu_code:
                position = 2;
                break;
            case R.id.menu_notify:
                position = 3;
                break;
            case R.id.menu_hotline:
                TextView tvPhone = (TextView) view.findViewById(R.id.tvPhoneHotline);
                String uri = "tel:" + tvPhone.getText().toString().trim();
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(uri));
                activity.startActivity(i);
                break;
            case R.id.menu_setting:
                position = -1;
                Toast.makeText(activity, "Click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_about_us:
                position = -1;
                openWebView(ConstantURLs.URL_WEB_ABOUT_US,activity.getResources().getString(R.string.menu_about_us));
                break;
            case R.id.menu_toturial:
                position = -1;
                initDialog();
                break;
            case R.id.menu_other_info:
                position = -1;
                initDialog();
                break;
            case R.id.menu_contract:
                position = -1;
                initDialog();
                break;
            case R.id.menu_out:
                position = -1;
                setupDialogSignout();
                break;

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        try {
            if (position >= 0) {
                if(binding.appBarMain.contentMain.viewPager.getCurrentItem()==0){
                    if(MyProfileFragment.check)
                        checkChangeMyProfile(position);
                    else {
                        setChoose(position);
                    }
                }
                else {
                    setChoose(position);
                }
                if(binding.appBarMain.contentMain.viewPager.getCurrentItem()==4) {
                    if(!binding.appBarMain.badgeView.getText().toString().isEmpty())
                        binding.appBarMain.badgeView.setVisibility(View.VISIBLE);
                    else
                        binding.appBarMain.badgeView.setVisibility(View.GONE);
                }
                else
                    binding.appBarMain.badgeView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        setupDialogSwitch(isChecked);
    }

    /**
     * Update user status success
     * Call from UpdateUserStatusAPI
     */
    public void updateUserStatusSuccess(boolean b) {
        ((MyOrderFragment)adapter.getFragment(1)).updateUserStatusSuccess(b);
        binding.appBarMain.swStatus.setChecked(b);
        isCheckedStatus = b;
        //TODO: Cho chạy service lấy vị trí khi user đang làm việc
        if (b) {
            if (!Commons.isMyServiceRunning(activity, MyLocationService.class))
                activity.startService(new Intent(activity, MyLocationService.class));
        } else {
            if (Commons.isMyServiceRunning(activity, MyLocationService.class))
                activity.stopService(new Intent(activity, MyLocationService.class));
        }
    }

    private void setupDialogSwitch(final boolean b) {
        dialog = new CustomDialog(activity);
        dialog.setTextTitle(activity.getResources().getString(R.string.note_dialog));
        if (b)
            dialog.setTitleMessage(activity.getResources().getString(R.string.dialog_toggle_on));
        else
            dialog.setTitleMessage(activity.getResources().getString(R.string.dialog_toggle_off));
        dialog.setShow(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTextButton(
                activity.getResources().getString(R.string.yes),
                activity.getResources().getString(R.string.no)
        );
        dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
            @Override
            public void onClickOk() {
                callUpdateUserStatusAPI(b);
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                binding.appBarMain.swStatus.setChecked(!b);
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                binding.appBarMain.swStatus.setChecked(!b);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Hàm gọi api
     *
     * @param b -> true: đang làm việc(status 30) | false: nghỉ ngơi(status 31)
     */
    private void callUpdateUserStatusAPI(boolean b) {
        int i = Constants.STATUS_WORKING_USER; // STATUS_WORKING_USER = 30 là đang làm việc
        if (!b) {
            i = Constants.STATUS_RESTED_USER;
        }
        new UpdateUserStatusAPI(activity, i + "", 0, this).execute();

    }

    @Override
    public void loadSuccess(List<?> mList) {

    }

    private void setupDialogSignout() {
        dialog = new CustomDialog(activity);
        dialog.setTextTitle(activity.getResources().getString(R.string.note_dialog));
        dialog.setTitleMessage(activity.getResources().getString(R.string.dialog_signout));
        dialog.setShow(true);
        dialog.setTextButton(activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
        dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
            @Override
            public void onClickOk() {
                //Call API
                new SignoutAPI(activity).execute();
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openWebView(String url, String title){
        Intent i = new Intent(activity, WebViewActivity.class);
        i.putExtra("URL", url);
        i.putExtra("title",title);
        activity.startActivity(i);
    }

    private void initDialog(){
        dialog = new CustomDialog(activity);
        dialog.setTextTitle(activity.getResources().getString(R.string.note_dialog));
        dialog.setTitleMessage(activity.getResources().getString(R.string.dialog_not_info));
        dialog.setShow(true);
        dialog.setShow1Button(true, activity.getResources().getString(R.string.turn_off));
        //dialog.setTextButton(activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
        dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
            @Override
            public void onClickOk() {
                //Call API
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void setChoose(int position) {
        binding.appBarMain.titleToolbar.setText(adapter.getPageTitle(position));
        binding.appBarMain.contentMain.viewPager.setCurrentItem(position);
        for (int i = 0; i < mListView.size(); i++) {
            if (i == position) {
                mListView.get(i).setBackgroundColor(ContextCompat.getColor(activity,R.color.colorAccent));
            } else {
                //mListView.get(i).setBackgroundResource(android.R.color.transparent);
                TypedValue outValue = new TypedValue();
                activity.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                mListView.get(i).setBackgroundResource(outValue.resourceId);
                /*int[] attrs = new int[]{R.attr.selectableItemBackground};
                TypedArray typedArray = activity.obtainStyledAttributes(attrs);
                int backgroundResource = typedArray.getResourceId(0, 0);
                mListView.get(i).setBackgroundResource(backgroundResource);
                mListView.get(i).setClickable(true);
                typedArray.recycle();*/
            }
        }
    }

    /**
     * Kiểm tra xem có thay đổi thông tin cá nhân không.
     * Nếu thay đổi thì sẽ hiện dialog.
     */
    public void checkChangeMyProfile(final int position) {
        if (MyProfileFragment.check) {
            final CustomDialog dialog = new CustomDialog(activity);
            dialog.setTitleMessage(activity.getResources().getString(R.string.dialog_check_change));
            dialog.setShow(true);
            dialog.setTextTitle(activity.getResources().getString(R.string.note_dialog));
            dialog.setTextButton(activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
            dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
                @Override
                public void onClickOk() {
                    ((MyProfileFragment)adapter.getFragment(0)).getViewModel().onClickOkDialog();
                    setChoose(position);
                    dialog.dismiss();
                }

                @Override
                public void onClickCancel() {
                    ((MyProfileFragment)adapter.getFragment(0)).getViewModel().onClickCancelDialog();
                    setChoose(position);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public void initDialogRegister(){
        final CustomDialog dialog = new CustomDialog(activity);
        dialog.setTextTitle(activity.getResources().getString(R.string.note_dialog));
        dialog.setTitleMessage(activity.getResources().getString(R.string.noti_register));
        dialog.setShow(true);
        dialog.setShow1Button(true, activity.getResources().getString(R.string.turn_off));
        //dialog.setTextButton(activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
        dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
            @Override
            public void onClickOk() {
                //Call API
                dialog.dismiss();
            }

            @Override
            public void onClickCancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public BaseFragment getFragmentAdapter(int position){
        return adapter.getFragment(position);
    }
}
