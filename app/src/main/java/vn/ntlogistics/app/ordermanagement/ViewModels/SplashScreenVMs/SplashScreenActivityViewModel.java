package vn.ntlogistics.app.ordermanagement.ViewModels.SplashScreenVMs;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.CountDownTimer;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login.User;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.LoginActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.SplashScreenActivity;


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

    public SplashScreenActivityViewModel(SplashScreenActivity activity) {
        this.activity = activity;
    }

    public void checkLogin() {
        try {
            SSqlite.getInstance(activity).createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            User user = SCurrentUser.getCurrentUser(activity);
            int flag = 0; // Active confirm code
            if (user.getPublickey() != null) {
                if(user.getLocalkey() != null) { //Have password
                    flag = 2; // Enter password
                }
                else
                    flag = 1; // Create password
            }

            LoginActivity.startIntentActivity(activity, flag);

        } catch (Exception e) {
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
}
