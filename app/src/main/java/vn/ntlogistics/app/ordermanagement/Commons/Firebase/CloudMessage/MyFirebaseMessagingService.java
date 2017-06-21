package vn.ntlogistics.app.ordermanagement.Commons.Firebase.CloudMessage;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vn.ntlogistics.app.ordermanagement.Commons.Notification.BaseNotification;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.OrderManagementActivity;
import vn.ntlogistics.app.ordermanagement.Views.Application.MainApplication;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    SqliteManager db = null;
    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(db == null){
            db = new SqliteManager(this);
        }
        try {
            handleMessage(remoteMessage);
        } catch (Exception e) {
        }
    }

    private void handleMessage(RemoteMessage remoteMessage) {
        int type = Integer.parseInt(remoteMessage.getData().get("type"));
        switch (type){
            case 0: // Nhận đơn hàng mới
                notificationNewBill(remoteMessage);
                break;
            case 1: //Xóa đơn hàng mới
                notificationCancelBill(remoteMessage);
                break;
        }
    }

    private void notificationNewBill(RemoteMessage remoteMessage) {
        /**
         * Call api get detial order, after insert into sqlite.
         */
        String billID = remoteMessage.getData().get("billID");
        //...

        /**
         * Sau khi insert sqlite thì reload lại list đơn hàng mới.
         * Nếu activity hiện tại là OrderManagementActivity.
         */
        try {
            ((OrderManagementActivity)MainApplication.getCurrentActivity()).reload();
        } catch (Exception e) {
        }

        try {
            //call api order detail
            //TODO: hiện notification
            BaseNotification notify = new BaseNotification(this, OrderManagementActivity.class);
            Intent intent = new Intent(
                    this,
                    OrderManagementActivity.class);

            /**
             * Đi đến quản lý đơn hàng và reload list đơn hàng mới
             * khi click vào notification.
             */
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notify.setIntent(intent);

            notify.setContent(
                    remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("body"),
                    R.mipmap.logonhattin
            );
            notify.build();
            notify.run();
        } catch (Exception e) {
        }
    }


    private void notificationCancelBill(RemoteMessage remoteMessage) {
        /**
         * Dùng billID xóa bill trong sqlite.
         * Sau đó reload lại list bill nếu đang ở OrderManagementActivity
         */
        String billID = remoteMessage.getData().get("billID");
        if(db.deleteSenderBill(billID)){
            try {
                ((OrderManagementActivity)MainApplication.getCurrentActivity()).reload();
            } catch (Exception e) {
            }
        }

        try {
            //TODO: hiện notification
            BaseNotification notify = new BaseNotification(this, OrderManagementActivity.class);
            Intent intent = new Intent(
                    this,
                    OrderManagementActivity.class);

            /**
             * Reload list đơn hàng mới
             */
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notify.setIntent(intent);

            notify.setContent(
                    remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("body"),
                    R.mipmap.logonhattin
            );
            notify.build();
            notify.run();
        } catch (Exception e) {
        }
    }


}
