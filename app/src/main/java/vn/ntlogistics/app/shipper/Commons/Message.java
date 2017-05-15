package vn.ntlogistics.app.shipper.Commons;

import android.content.Context;
import android.widget.Toast;

import vn.ntlogistics.app.shipper.R;


/**
 * Created by Zanty on 23/06/2016.
 */
public class Message {
    public static void showToast(Context context, String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public static void showToastConnect(Context context){
        Toast.makeText(context,
                context.getResources().getString(R.string.error_connect),
                Toast.LENGTH_SHORT).show();
    }

    public static void showToastNotPhone(Context context){
        Toast.makeText(context,
                context.getResources().getString(R.string.error_phone),
                Toast.LENGTH_SHORT).show();
    }
}
