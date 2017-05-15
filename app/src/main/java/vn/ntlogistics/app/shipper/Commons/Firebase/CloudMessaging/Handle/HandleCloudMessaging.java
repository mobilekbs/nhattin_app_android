package vn.ntlogistics.app.shipper.Commons.Firebase.CloudMessaging.Handle;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Zanty on 15/05/2017.
 */

public class HandleCloudMessaging {
    RemoteMessage remoteMessage;
    //SqliteManager db;
    int type;
    Context activity;

    public HandleCloudMessaging(RemoteMessage remoteMessage, Context activity) {
        this.remoteMessage = remoteMessage;
        this.activity = activity;
        type = Integer.parseInt(remoteMessage.getData().get("type"));
    }

    public void run() {
        try {
            /*switch (type){
                case 1: //Thông báo khuyến mãi
                    insertSQLNotification();
                    break;
                case 2: // Xóa shippingCode
                    deleteShippingCode();
                    break;
                case 3: //Thông báo đơn hàng
                    notificationOrder();
                    break;
                case 6: //Huỷ đơn hàng
                    cancelOrder();
                    break;
            }*/
        } catch (Exception e) {
            Log.d("HandleMessageFCM", "run ------------------------->");
            e.printStackTrace();

        }
    }

    /*private void notificationOrder() {
        //Reload list new order
        try {
            JSMyOrder dataMyOrder = new JSMyOrder(
                    activity,
                    Common.NUMBER_ITEM_LOAD,
                    0,
                    Common.STATUS_NEW_ORDER
            );
            String jsonMyOrder = new Gson().toJson(dataMyOrder);
            new MyOrderAPI(
                    OneShipApplication.getCurrentActivity(),
                    SMainActivity.getInstance().get().getChildFragment(1,0), //Get new order fragment
                    jsonMyOrder,
                    true,
                    1
            ).execute();
        } catch (Exception e) {
        }
        try {
            //call api order detail
            //TODO: hiện notification
            BaseNotification notify = new BaseNotification(
                    activity,
                    MainActivity.class
            );
            Intent intent = new Intent(
                    activity,
                    MainActivity.class);
            intent.putExtra("myOrder", remoteMessage.getData().get("myOrder"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notify.setIntent(intent);

            notify.setContent(
                    remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("body"),
                    R.drawable.logo_app
            );
            notify.build();
            notify.run();
        } catch (Exception e) {
        }
    }
    //Type 1 - Thong bao khuyen mai
    private void insertSQLNotification() {
        try {
            db = new SqliteManager(activity);
            //TODO: insert vào sql
            db.insertNotification(
                    remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("body"),
                    remoteMessage.getData().get("url")
            );
            //TODO: count Notification in Ste and add into icon app
            Utils.setBadgeIconApp(
                    activity,
                    db.countNotification()
            );
            //TODO: hiện notification
            BaseNotification notify = new BaseNotification(
                    activity,
                    WebViewActivity.class
            );
            Intent intent = new Intent(
                    activity,
                    WebViewActivity.class);
            intent.putExtra("title", remoteMessage.getData().get("title"));
            intent.putExtra("URL", remoteMessage.getData().get("url"));
            notify.setIntent(intent);
            notify.setContent(
                    remoteMessage.getData().get("title"),
                    remoteMessage.getData().get("body"),
                    R.drawable.logo_app
            );
            notify.build();
            notify.run();

            OneShipApplication.getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //TODO: set data notification
                        //NotificationFragment.instance.setupDataList();
                        ((MainActivity)activity).handleNotiInNotificationFragment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            Log.d("HandleMessageFCM", "insertSQLNotification --------->");
            e.printStackTrace();
        }
    }
    //Type 2 - Xoa shippingCode
    private void deleteShippingCode() {
        *//*NewOrderFragment.getInstanse().removeItemByShippingCode(
                remoteMessage.getData().get("shippingCode"),
                remoteMessage.getData().get("jobType")
        );*//*
        try {
            Bundle b = new Bundle();
            b.putString("shippingCode",remoteMessage.getData().get("shippingCode"));
            b.putString("jobType",remoteMessage.getData().get("jobType"));
            SMainActivity.getInstance().get().handleNotification(2,b);
        } catch (Exception e) {
            Log.d("HANDLE NOTIFICATION","DELETE___________________");
            e.printStackTrace();
        }
    }
    //Type 6 - Huy don hang
    private void cancelOrder() {
        //Reload list uncompleted order
        try {
            SMainActivity.getInstance().get().handleNotification(6,null);
        } catch (Exception e) {
            Log.d("HANDLE NOTIFICATION","CANCEL___________________");
            e.printStackTrace();
        }
        //TODO: hiện notification
        BaseNotification notify = new BaseNotification(
                activity,
                MainActivity.class
        );
        Intent intent = new Intent();
        notify.setIntent(intent);
        notify.setContent(
                remoteMessage.getData().get("title"),
                remoteMessage.getData().get("body"),
                R.drawable.logo_app
        );
        notify.build();
        notify.run();
    }*/
}
