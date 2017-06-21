package vn.ntlogistics.app.ordermanagement.Commons;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by Zanty on 19/05/2017.
 */

public class Message {
    public static void makeToastTop(Context context, String message, int idRes, int idResBG){
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View custom_success = inflater.inflate(R.layout.custom_toast_top,
                null);
        TextView tv = (TextView) custom_success.findViewById(R.id.tvMessageToastTop);
        tv.setText(message);

        ImageView iv = (ImageView) custom_success.findViewById(R.id.ivToast);
        iv.setImageDrawable(ContextCompat.getDrawable(context, idRes));

        View view = custom_success.findViewById(R.id.loMainToastTop);
        view.setBackgroundColor(ContextCompat.getColor(context, idResBG));

        Toast myToast = new Toast(context);
        myToast.setView(custom_success);
        myToast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL,0,0);
        myToast.setDuration(Toast.LENGTH_SHORT);
        myToast.show();
    }

    public static void makeToastSuccess(Context context){
        makeToastTop(context,
                context.getString(R.string.success),
                R.drawable.pull_message_success,
                R.color.colorSuccess
        );
    }
    public static void makeToastSuccess(Context context, String message){
        makeToastTop(context,
                message,
                R.drawable.pull_message_success,
                R.color.colorSuccess
        );
    }
    public static void makeToastWarning(Context context, String massage){
        makeToastTop(context,
                massage,
                R.drawable.pull_message_warning,
                R.color.colorOrange
        );
    }
    public static void makeToastErrorSystem(Context context){
        makeToastTop(context,
                context.getString(R.string.error_system),
                R.drawable.pull_message_error,
                R.color.colorRed
        );
    }
    public static void makeToastError(Context context){
        makeToastTop(context,
                context.getString(R.string.toast_error),
                R.drawable.pull_message_error,
                R.color.colorRed
        );
    }
    public static void makeToastError(Context context, String massage){
        makeToastTop(context,
                massage,
                R.drawable.pull_message_error,
                R.color.colorRed
        );
    }
    public static void makeToastErrorConnect(Context context){
        makeToastTop(context,
                context.getString(R.string.not_connect),
                R.drawable.pull_message_error,
                R.color.colorRed
        );
    }
}
