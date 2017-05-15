package vn.ntlogistics.app.shipper.Views.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import vn.ntlogistics.app.shipper.Commons.ACRA.ACRAReportSender;

/**
 * Created by Zanty on 15/05/2017.
 */
@ReportsCrashes(formKey = "")
public class MainApplication extends Application {

    public void onCreate() {
        super.onCreate();
        ACRA.init(this);

        // instantiate the report sender with the email credentials.
        // these will be used to send the crash report
        ACRAReportSender reportSender = new ACRAReportSender();
        ACRA.getErrorReporter().setReportSender(reportSender);
    }

    private static Activity mCurrentActivity = null;
    public static Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity){
        mCurrentActivity = currentActivity;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
