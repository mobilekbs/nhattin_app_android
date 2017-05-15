package vn.ntlogistics.app.shipper.Views.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Models.Enums.ELogin;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Adapters.ViewPagerAdapter;
import vn.ntlogistics.app.shipper.Views.Fragments.Login.LoginFragment;
import vn.ntlogistics.app.shipper.databinding.ActivityLoginBinding;

//TODO: Đăng nhập
public class LoginActivity extends BaseActivity {
    public static String                TAG = "LoginActivity";

    //TODO: Data Binding
    ActivityLoginBinding binding;
    //LoginActivityViewModel            viewModel;

    //TODO: View Pager
    private ViewPagerAdapter adapter;
    private List<BaseFragment>          mListFragment;
    private String[]                    titles;

    //TODO: firebase
    //GoogleApiClient                     mGoogleApiClient;
    String                              promotion = null;

    //boolean                             invites = false;

    static LoginActivity activity;
    public static Activity getActivityLogin(){
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        //viewModel = new LoginActivityViewModel(this);
        //binding.set
        //Initialize the facebook SDK
        //FacebookSdk.sdkInitialize(LoginActivity.this);
        //AppEventsLogger.activateApp(LoginActivity.this);
        //setContentView(R.layout.activity_login);

        //flContainer = (FrameLayout) findViewById(R.id.container);
        //TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        setupAdapter();
        /*try {
            promotion = getIntent().getExtras().getString("promotion");
            if(promotion != null){
                loadFragment(ELogin.REGISTER);
                //((RegisterFragment)adapter.getFragmentAdapter(1)).setPromotionCode(promotion);
            }
        } catch (Exception e) {
        }*/
        // Create an auto-managed GoogleApiClient with access to App Invites.
        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppInvite.API)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    }
                })
                .build();
        try {
            firebaseInvite();
        } catch (Exception e) {
        }*/

        if(activity == null || activity != this)
            activity = this;
    }

    /*//TODO: Handle Invites Firebase -------------------------
    private void firebaseInvite() {

        // Check for App Invite invitations and launch deep-link activity if possible.
        // Requires that an Activity is registered in AndroidManifest.xml to handle
        // deep-link URLs.
        boolean autoLaunchDeepLink = true;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(AppInviteInvitationResult result) {
                                //Log.d("SplashScreenActivity", "getInvitation:onResult:" + result.getStatus());
                                if (result.getStatus().isSuccess()) {
                                    // Extract information from the intent
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);
                                    if(!invites) {
                                        try {
                                            handleDynamicLink(deepLink);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            Log.d(TAG,"firebaseInvite ------------------");
                                        }
                                    }
                                }
                            }
                        });
    }

    private void handleDynamicLink(String deepLink) {
        try {
            promotion = deepLink.split(Pattern.quote("="))[1];
        } catch (Exception e) {
        }
        //Message.showToast(this,promotion);
        if(promotion != null) {
            loadFragment(ELogin.REGISTER);
            ((RegisterFragment)adapter.getFragmentAdapter(1)).setPromotionCode(promotion);
        }
    }*/

    //TODO: End handle Invites Firebase ------------------------/

    private void setupAdapter() {
        mListFragment = new ArrayList<>();
        mListFragment.add(new LoginFragment());
        //mListFragment.add(new RegisterFragment());
        //mListFragment.add(new AddInfoFragment());
        titles = new String[]{
                getString(R.string.alert_title_login),
                getString(R.string.register),
                getString(R.string.add_information)
        };
        adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, mListFragment, titles);
        binding.nonSwipeVP.setAdapter(adapter);
        binding.nonSwipeVP.setOffscreenPageLimit(3);
    }


    public void loadFragment(ELogin login) {
        try {
            switch (login){
                case LOGIN:
                    binding.nonSwipeVP.setCurrentItem(0);
                    break;
                case REGISTER:
                    binding.nonSwipeVP.setCurrentItem(1);
                    break;
                case ADD_INFO:
                    binding.nonSwipeVP.setCurrentItem(2);
                    break;
                default:
                    binding.nonSwipeVP.setCurrentItem(0);
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG,"loadFragment -------------");
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if(binding.nonSwipeVP.getCurrentItem() == 0) {
                super.onBackPressed();
            }
            else {
                binding.nonSwipeVP.setCurrentItem(
                        binding.nonSwipeVP.getCurrentItem() - 1
                );
            }
        } catch (Exception e) {
            Log.d(TAG,"onBackPressed ----------"+ e.toString());
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            adapter.getFragmentAdapter(2).onActivityResult(requestCode,resultCode,data);
        } catch (Exception e) {
            e.printStackTrace();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
