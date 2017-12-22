package vn.ntlogistics.app.ordermanagement.ViewModels.SplashScreenVMs;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.CountDownTimer;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
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

        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                checkLogin();
            }
        };

        countDownTimer.start();

    }

    public void checkLogin() {
        /*try {
            SSQLite.getInstance(activity).createDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            User user = SCurrentUser.getCurrentUser(activity);
            int flag = 0; // Active confirm code

            /**
             * Nếu nhỏ hơn 999 thì đó đang ở version 3.
             * Vì lên v4 khi kích hoạt khóa sẽ thêm trường partnerId và partnerValue
             * Sẽ update lại giá trị trong sqlite.
             * Các user login trước ver4 sẽ phải kích hoạt lại.
             */
            if (user.getIdStaff() < 999){
                user = new User();
                SCurrentUser.delCurrentUser();
            }

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
