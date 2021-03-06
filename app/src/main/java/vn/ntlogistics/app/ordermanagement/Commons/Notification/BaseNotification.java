package vn.ntlogistics.app.ordermanagement.Commons.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import java.util.Random;

import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by Zanty on 02/08/2016.
 */
public class BaseNotification {
    Context context;
    PendingIntent pIntent;
    String title, content;
    int smallIcon;
    NotificationManager notificationManager;
    Notification noti;
    Intent intent;
    public static Uri uriSound = null;

    public static Uri getUriSound() {
        if (uriSound == null)
            uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        return uriSound;
    }

    public static void setUriSound(Uri uriSound) {
        BaseNotification.uriSound = uriSound;
    }

    public BaseNotification(Context context, Class<?> mClass) {
        this.context = context;
        this.intent = new Intent(context, mClass);
//        stackBuilder = TaskStackBuilder.create(context);
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(mClass);
//        // Adds the Intent that starts the Activity to the top of the stack

    }
    public BaseNotification(Context context, Class<?> mClass, int resSound) {
        this.context = context;
        this.uriSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + resSound);
        this.intent = new Intent(context, mClass);
//        stackBuilder = TaskStackBuilder.create(context);
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(mClass);
//        // Adds the Intent that starts the Activity to the top of the stack

    }

    public void setContent(String title, String content, int smallIcon) {
        this.title = title;
        this.content = content;
        this.smallIcon = smallIcon;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
        //stackBuilder.addNextIntent(intent);
    }

    public void build() {
        pIntent = PendingIntent.getActivity(
                context,
                250,
                intent,
                0
        );
        // Prepare intent which is triggered if the
        // notification is selected
        BitmapDrawable bitmapDrawable = (BitmapDrawable)
                ContextCompat.getDrawable(context, R.mipmap.logonhattin);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        // Build notification
        // Actions are just fake
        noti = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(smallIcon)
                .setLargeIcon(Bitmap.createScaledBitmap(bitmap, 128, 128, false))
                .setContentIntent(pIntent)
                .setTicker(context.getString(R.string.app_name))
                .setDefaults(Notification.DEFAULT_SOUND)
//                .setSound(getUriSound())
                //.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                //.setDefaults(NotificationCompat.DEFAULT_VIBRATE | Notification.FLAG_SHOW_LIGHTS)
                .setLights(0xff00ff00, 300, 100)
                .build();

        //noti.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.noti);;
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;

    }

    public void run() {
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m, noti);
    }
}
