package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 20/06/2017.
 */

public class UpdateFCMTokenInput extends BaseInput {
    private String fcmToken;

    public UpdateFCMTokenInput(Context context) {
        super(context);
    }

    public UpdateFCMTokenInput(Context context, String fcmToken) {
        super(context);
        this.fcmToken = fcmToken;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
