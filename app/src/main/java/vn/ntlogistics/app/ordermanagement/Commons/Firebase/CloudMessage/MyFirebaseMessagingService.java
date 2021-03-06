package vn.ntlogistics.app.ordermanagement.Commons.Firebase.CloudMessage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.Notification.BaseNotification;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.OrderManagementActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.SplashScreenActivity;
import vn.ntlogistics.app.ordermanagement.Views.Application.MainApplication;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseMessaging";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        Bundle b = intent.getExtras();
        if (b != null) {
            try {
                Log.e(TAG, "onMessageReceived"+b.toString());
//                if(b.getString("type").equalsIgnoreCase("1") || b.getString("type").equalsIgnoreCase("0"))
                    handleMessage(b);
            } catch (Exception e) {
                Log.e(TAG, "onMessageReceived: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);

//        Log.e("dataChat",remoteMessage.getData().toString());
//        try
//        {
//            Map<String, String> params = remoteMessage.getData();
//            JSONObject object = new JSONObject(params);
//            Log.e("JSON_OBJECT", object.toString());
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        try {
//            Log.e("FIRE_BASE", remoteMessage.getData().toString());
//            if(remoteMessage.getData().get("type").equalsIgnoreCase("2") || remoteMessage.getData().get("type").equalsIgnoreCase("3")) {
//                Map<String, String> params = remoteMessage.getData();
//                JSONObject object = new JSONObject(params);
//                notificationNewNoti(object);
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "onMessageReceived: " +e.getMessage());
//            e.printStackTrace();
//        }
    }

    private void handleMessage(Bundle data) {
        //notificationNewBill(remoteMessage);
        int type = Integer.parseInt(data.get("type").toString());
        switch (type) {
            case 0: // Nhận đơn hàng mới
                notificationNewBill(data);
                break;
            case 1: //Xóa đơn hàng mới
                notificationCancelBill(data);
                break;
            case 2:
                notificationNewNoti(data);
                break;
            case 3:
                notificationNewNoti(data);
                break;
        }
    }

    private void notificationNewBill(Bundle data) {
        /**
         * Call api get detial order, after insert into sqlite.
         */
        Bill result = new Bill();
        result.setEmsBpbillID(data.getString("emsBpbillID"));
        result.setBillID(data.getString("docode"));
        result.setSendDate(data.getString("senddate"));
        result.setSenderAddress(data.getString("sender_address"));
        result.setSenderProvinceID(data.getString("sender_province_id"));
        result.setSenderName(data.getString("sender"));
        result.setSenderNumberPhone(data.getString("sender_contact_tel"));
        result.setOtpCode(data.getString("otpcode"));
        result.setWeight(data.getString("weight"));
        result.setShipperAmount(data.getDouble("total_postage", 0));
        result.setCodAmount(data.getDouble("codamt", 0));
        result.setSenderNode(data.getString("note"));
        result.setStatus(Constants.STATUS_UNCOMPLETED + "");
        //Insert bill into Sqlite
        SSQLite.getInstance(this).insertOrUpdateSendBill(result);

        /**
         * Sau khi insert sqlite thì reload lại list đơn hàng mới.
         * Nếu activity hiện tại là OrderManagementActivity.
         */
        try {
            ((OrderManagementActivity) MainApplication.getCurrentActivity()).reload();
        } catch (Exception e) {
        }

        try {
            if(MainApplication.getCurrentActivity() != null) {
                //TODO: hiện notification
                BaseNotification notify = new BaseNotification(
                        this,
                        OrderManagementActivity.class,
                        R.raw.noti);
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
                        getString(R.string.notification_title),
                        getString(R.string.bill_code) + " " + result.getBillID(),
                        R.mipmap.logonhattin
                );
                notify.build();
                notify.run();
            }
        } catch (Exception e) {
        }
    }


    private void notificationNewNoti(Bundle data) {
        /**
         * Call api get detial order, after insert into sqlite.
         */
//        Bill result = new Bill();
//        result.setEmsBpbillID(data.getString("emsBpbillID"));
//        result.setBillID(data.getString("docode"));
//        result.setSendDate(data.getString("senddate"));
//        result.setSenderAddress(data.getString("sender_address"));
//        result.setSenderProvinceID(data.getString("sender_province_id"));
//        result.setSenderName(data.getString("sender"));
//        result.setSenderNumberPhone(data.getString("sender_contact_tel"));
//        result.setOtpCode(data.getString("otpcode"));
//        result.setWeight(data.getString("weight"));
//        result.setShipperAmount(data.getDouble("total_postage", 0));
//        result.setCodAmount(data.getDouble("codamt", 0));
//        result.setSenderNode(data.getString("note"));
//        result.setStatus(Constants.STATUS_UNCOMPLETED + "");
//        //Insert bill into Sqlite
//        SSQLite.getInstance(this).insertOrUpdateSendBill(result);

        /**
         * Sau khi insert sqlite thì reload lại list đơn hàng mới.
         * Nếu activity hiện tại là OrderManagementActivity.
         */
//        try {
//            ((OrderManagementActivity) MainApplication.getCurrentActivity()).reload();
//        } catch (Exception e) {
//        }

        try {
//            if(MainApplication.getCurrentActivity() != null) {

            String notificationMessage = "";

            if(data.getString("type").equalsIgnoreCase("2")) {

                notificationMessage = getString(R.string.notification_title_2);

                SSQLite.getInstance(this).insertOrUpdateCongoNoti(data.getString("type"), data.getString("message"), data.getString("from")
                        , data.getString("date"), data.getString("time"));
            } else if(data.getString("type").equalsIgnoreCase("3")) {

                notificationMessage = getString(R.string.notification_title_3);

                SSQLite.getInstance(this).insertOrUpdateThongNoti(data.getString("type"), data.getString("message"), data.getString("from")
                        , data.getString("date"), data.getString("time"));
            }

            //TODO: hiện notification
            BaseNotification notify = new BaseNotification(
                    this,
                    SplashScreenActivity.class,
                    R.raw.noti);
            Intent intent = new Intent(
                    this,
                    SplashScreenActivity.class);

            Log.e(TAG, data.getString("type")+" "+data.getString("date")+" "+data.getString("time")+" "+data.getString("from")
                    +" "+data.getString("message"));

            /**
             * Đi đến quản lý đơn hàng và reload list đơn hàng mới
             * khi click vào notification.
             */
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notify.setIntent(intent);

            notify.setContent(
                   notificationMessage,
                      data.getString("message"),
//                    getString(R.string.bill_code) + " " + data.getString("message"),
                    R.mipmap.logonhattin
            );
            notify.build();
            notify.run();
//            }
        } catch (Exception e) {
        }
    }



    private void notificationCancelBill(Bundle data) {
        /**
         * Dùng billID xóa bill trong sqlite.
         * Sau đó reload lại list bill nếu đang ở OrderManagementActivity
         */
        String billID = data.getString("billID");
        if (SSQLite.getInstance(this).deleteSenderBill(billID)) {
            try {
                ((OrderManagementActivity) MainApplication.getCurrentActivity()).reload();
            } catch (Exception e) {
            }
        }

        try {
            if(MainApplication.getCurrentActivity() != null) {
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
                        getString(R.string.notification_cancel_title),
                        getString(R.string.bill_code) + " " + billID,
                        R.mipmap.logonhattin
                );
                notify.build();
                notify.run();
            }
        } catch (Exception e) {
        }
    }

    /*private void handleMessage(RemoteMessage remoteMessage) {
        //notificationNewBill(remoteMessage);
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
        Log.e("FIRE_BASE", remoteMessage.getData().toString());
        *//**
     * Call api get detial order, after insert into sqlite.
     *//*
        Bill result = new Bill();
        result.setBillID(remoteMessage.getData().get("docode"));
        result.setSendDate(remoteMessage.getData().get("senddate"));
        result.setSenderAddress(remoteMessage.getData().get("sender_address"));
        result.setSenderProvinceID(remoteMessage.getData().get("sender_province_id"));
        result.setSenderName(remoteMessage.getData().get("sender"));
        result.setSenderNumberPhone(remoteMessage.getData().get("sender_contact_tel"));
        result.setOtpCode(remoteMessage.getData().get("otpcode"));
        result.setStatus(Constants.STATUS_UNCOMPLETED+"");
        //Insert bill into Sqlite
        SSqlite.getInstance(this).insertOrUpdateSendBill(result);

        *//**
     * Sau khi insert sqlite thì reload lại list đơn hàng mới.
     * Nếu activity hiện tại là OrderManagementActivity.
     *//*
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

            *//**
     * Đi đến quản lý đơn hàng và reload list đơn hàng mới
     * khi click vào notification.
     *//*
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notify.setIntent(intent);

            notify.setContent(
                    getString(R.string.notification_title),
                    getString(R.string.bill_code) + " " + result.getBillID(),
                    R.mipmap.logonhattin
            );
            notify.build();
            notify.run();
        } catch (Exception e) {
        }
    }


    private void notificationCancelBill(RemoteMessage remoteMessage) {
        *//**
     * Dùng billID xóa bill trong sqlite.
     * Sau đó reload lại list bill nếu đang ở OrderManagementActivity
     *//*
        String billID = remoteMessage.getData().get("billID");
        if(SSqlite.getInstance(this).deleteSenderBill(billID)){
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

            *//**
     * Reload list đơn hàng mới
     *//*
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notify.setIntent(intent);

            notify.setContent(
                    getString(R.string.notification_cancel_title),
                    getString(R.string.bill_code) + " " + billID,
                    R.mipmap.logonhattin
            );
            notify.build();
            notify.run();
        } catch (Exception e) {
        }
    }*/


}
