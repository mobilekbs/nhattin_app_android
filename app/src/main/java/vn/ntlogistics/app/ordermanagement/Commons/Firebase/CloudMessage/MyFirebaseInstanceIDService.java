package vn.ntlogistics.app.ordermanagement.Commons.Firebase.CloudMessage;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;

import vn.ntlogistics.app.ordermanagement.Commons.SharedPreference.MySharedPreference;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.UpdateFCMTokenAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CommonInput;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.UpdateFCMTokenInput;
import vn.ntlogistics.app.ordermanagement.Views.Application.MainApplication;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public MyFirebaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log.d("FCM", "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        /**
         * Insert fcm token vào shared preference.
         * Vì firebase chỉ cấp 1 lần duy nhất cho đến khi token được thay đổi.
         * Nên lưu lại cho trường hợp chưa login,
         * khi login vào thì update ngay token vừa được lưu
         */
        MySharedPreference.setFCMToken(this, refreshedToken);
        /**
         * Nếu đã tồn tại user thì update fcm token mới
         */
        if(SCurrentUser.getCurrentUser(this) != null &&
                SCurrentUser.getCurrentUser(this).getPublickey() != null &&
                SCurrentUser.getCurrentUser(this).getPublickey().length() > 0){
            CommonInput<UpdateFCMTokenInput> input = new CommonInput<>();
            input.setData(new UpdateFCMTokenInput(MainApplication.getCurrentActivity(), refreshedToken));
            String data = new Gson().toJson(input);
            new UpdateFCMTokenAPI(this, data).execute();
        }
    }
}
