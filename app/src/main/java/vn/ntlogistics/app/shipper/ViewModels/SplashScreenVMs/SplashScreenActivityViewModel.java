package vn.ntlogistics.app.shipper.ViewModels.SplashScreenVMs;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import java.util.List;

import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Singleton.SApiKey;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.GetApiKeyAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.UpdateFCMTokenAPI;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.shipper.Views.Activities.LoginActivity;
import vn.ntlogistics.app.shipper.Views.Activities.MainActivity;
import vn.ntlogistics.app.shipper.Views.Activities.SplashScreenActivity;


/**
 * Created by minhtan2908 on 2/16/17.
 */

public class SplashScreenActivityViewModel extends ViewModel {
    public static final String          TAG = "SplashScreenViewModel";
    private SplashScreenActivity activity;

    private ObservableField<String>     textTvLoad;
    private ObservableInt               colorTvLoad;
    private ObservableInt               visiblePb;

    private String                      promotion;

    //TODO: Is flag, if it had apikey, flag = true
    boolean                             flag = false;
    CountDownTimer                      countDownTimer;
    GetApiKeyAPI getApiKeyAPI;
    //GetApiKeyAPI                        api;
    SqliteManager db;

    public SplashScreenActivityViewModel(SplashScreenActivity activity) {
        this.activity = activity;
        textTvLoad = new ObservableField<>(activity.getResources().getString(R.string.loading));
        //textTvLoad.set(activity.getResources().getString(R.string.loading));
        //colorTvLoad.set(R.color.colorWhite);
        //colorTvLoad = new ObservableInt(activity.getResources().getColor(R.color.colorWhite));
        colorTvLoad = new ObservableInt(ContextCompat.getColor(activity,R.color.colorWhite));
        //colorTvLoad.set(ContextCompat.getColor(activity,R.color.colorWhite));
        visiblePb = new ObservableInt(View.VISIBLE);
        //visiblePb.set(View.VISIBLE);
    }

    public void setPromotion(String promotion){
        this.promotion = promotion;
    }

    public void checkLogin() {
        //TODO: create sqlite
        db = new SqliteManager(activity);
        try {
            db.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (SCurrentUser.getCurrentUser(activity).getSessionToken() == null) {
                db.readSessionToken();
            }

        } catch (Exception e) {
        }
        init();
    }

    private void init() {
        //tvLoad = (TextView) findViewById(R.id.tvLoading);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        getApiKeyAPI = new GetApiKeyAPI(activity);

        /*api = new GetApiKeyAPI(activity);
        // Chạy hàm kết nối api
        connectAPI(api);*/

        countDownTimer = new CountDownTimer(30000,5000) {
            @Override
            public void onTick(long l) {
                if(!flag && (getApiKeyAPI.getStatus()== AsyncTask.Status.PENDING||
                        getApiKeyAPI.getStatus()== AsyncTask.Status.FINISHED)) {
                    if(Commons.checkGPS(activity)) {
                        getApiKeyAPI = new GetApiKeyAPI(activity);
                        getApiKeyAPI.execute();
                    }
                    Log.d("ConnectAPI","Connected API");
                }
                else
                    countDownTimer.cancel();
                Log.d("ConnectAPI","Connecting API");
            }

            @Override
            public void onFinish() {
                if(!flag)
                    countDownTimer.start();
                Log.d("ConnectAPI","Restart connection API");
            }
        };

        activity.networkChangeConnection();

    }

    public void loadMainScreen(boolean updateVersion) {
        try {
            if(updateVersion){
                flag = true;
            }
            else {
                if (SApiKey.getOurInstance().getApiKey().length() < 1 ||
                        SApiKey.getOurInstance().getApiSecretKey().length() < 1) {
                    setColorTvLoad(ContextCompat.getColor(activity,R.color.colorRed));
                    setTextTvLoad(activity.getResources().getString(R.string.error_connect));
                    flag = false;
                } else {
                    flag = true;
                    if (SCurrentUser.getCurrentUser(activity).getSessionToken() != null) {
                        new UpdateFCMTokenAPI(activity, db.getFCMToken()).execute();
                        Intent i = new Intent(activity, MainActivity.class);
                        activity.startActivity(i);
                        activity.finish();
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        Intent i = new Intent(activity, LoginActivity.class);
                        if(promotion != null)
                            i.putExtra("promotion", promotion);
                        activity.startActivity(i);
                        activity.finish();
                        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
            }
        } catch (Exception e) {
            setColorTvLoad(ContextCompat.getColor(activity,R.color.colorRed));
            setTextTvLoad(activity.getResources().getString(R.string.error_connect));
            flag = false;
        }
    }

    public void checkConnect(boolean isConnected){
        if (isConnected) {
            setVisiblePb(View.VISIBLE);
            //progressBar.setVisibility(View.VISIBLE);
            //tvLoad.setTextColor(getResources().getColor(R.color.colorWhite));
            setColorTvLoad(ContextCompat.getColor(activity,R.color.colorWhite));
            //tvLoad.setText(getResources().getString(R.string.loading));
            setTextTvLoad(activity.getResources().getString(R.string.loading));
            countDownTimer.start();
        } else {
            setVisiblePb(View.GONE);
            setColorTvLoad(ContextCompat.getColor(activity,R.color.colorRed));
            setTextTvLoad(activity.getResources().getString(R.string.not_internet));
            countDownTimer.cancel();
        }
    }

    public ObservableField<String> getTextTvLoad() {
        return textTvLoad;
    }

    public void setTextTvLoad(String textTvLoad) {
        this.textTvLoad.set(textTvLoad);
    }

    public ObservableInt getColorTvLoad() {
        return colorTvLoad;
    }

    public void setColorTvLoad(int colorTvLoad) {
        this.colorTvLoad.set(colorTvLoad);
    }

    public ObservableInt getVisiblePb() {
        return visiblePb;
    }

    public void setVisiblePb(int visiblePb) {
        this.visiblePb.set(visiblePb);
    }

    @Override
    public void loadSuccess(List<?> mList) {

    }
}
