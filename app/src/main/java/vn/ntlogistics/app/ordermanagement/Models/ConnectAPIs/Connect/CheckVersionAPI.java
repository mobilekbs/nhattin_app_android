package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CheckVersionInput;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CommonInput;
import vn.ntlogistics.app.ordermanagement.R;

/**
 * Created by Zanty on 19/08/2017.
 */

public class CheckVersionAPI extends BaseConnectAPI {

    public static void execute(Context context){
        new CheckVersionAPI(context).execute();
    }

    public CheckVersionAPI(Context context) {
        super(context, ConstantURLs.CHECK_VERSION, null, true, Method.POST);
        CommonInput<CheckVersionInput> input = new CommonInput<>(new CheckVersionInput(context));
        this.data = input.toJson();
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void doInBG() {

    }

    @Override
    public void onPost(JsonObject result) {
        try {
            /**
             * true: version của app là mới nhất
             * false: version đã cũ
             */
            boolean isNewest = result.get("data").getAsBoolean();
            if(!isNewest){
                getDialogUpdateApp();
            }
        } catch (Exception e) {
        }
    }

    public void getDialogUpdateApp(){
        CustomDialog dialog = new CustomDialog((Activity) context);
        dialog.setShow(true);
        dialog.setShow1Button(true,context.getString(R.string.update));
        dialog.setTitleMessage(context.getString(R.string.dialog_message_update_version));
        dialog.setTextTitle(context.getString(R.string.note_dialog));
        dialog.setOnClickButton(new CustomDialog.SetOnClickDialog() {
            @Override
            public void onClickOk() {
                final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                try {
                    context.startActivity(
                            new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + appPackageName)
                            )
                    );
                } catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(
                            new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id="
                                            + appPackageName)
                            )
                    );
                }
            }

            @Override
            public void onClickCancel() {

            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                ((Activity)context).finish();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onFailed(int errorCode, String errorMessage) {

    }

    @Override
    public void onError() {

    }
}
