package vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseAsystask;
import vn.ntlogistics.app.shipper.Commons.Constants;
import vn.ntlogistics.app.shipper.Commons.CustomViews.CustomDialog.CustomDialog;
import vn.ntlogistics.app.shipper.Commons.GmailSender.GmailSender;
import vn.ntlogistics.app.shipper.Commons.Message;
import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect.GetApiKeyAPI;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.Views.Activities.LoginActivity;
import vn.ntlogistics.app.shipper.Views.Activities.SplashScreenActivity;


/**
 * Created by Zanty on 21/06/2016.
 */
public abstract class BaseConnectAPI extends AsyncTask<Void, Void, String> {
    public Context context;
    public String url, data, errorMessage = null;
    ProgressDialog pg_dialog;
    public boolean refresh = false;
    GetJsonFromUrl getJsonFromUrl;
    Method method;
    SqliteManager db;

    public BaseConnectAPI(Context context, String url, String data, boolean refresh, Method method) {
        this.context = context;
        this.url = url;
        this.data = data;
        this.refresh = refresh;
        this.method = method;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!refresh) {
            initDialog();
        }
        onPre();
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        try {
            if (aVoid != null) {
                JsonObject rootObject = new JsonParser().parse(aVoid).getAsJsonObject();
                int errorCode = rootObject.get("errorCode").getAsInt();
                /**
                 * Nếu errorCode == 50 thì là do sessionToken hết hạn
                 * Nếu errorCode == 40 thì là do sessionToken không tồn tại
                 * Ta chuyển về màn hình Splash Screen và xóa hết thông tin đăng nhập.
                 *
                 * Nếu errorCode == 10 thì là do lỗi hệ thống
                 * Nếu errorCode == 20 thì là api key không tồn tại
                 */

                switch (errorCode){
                    case 0:
                        onPost(rootObject);
                        break;
                    case 50: //Session token không hợp lệ
                        SCurrentUser.delCurrentUser(context);
                        //Xóa sessionToken trong database
                        new SqliteManager(context).deleteSessionToken();
                        Intent i = new Intent(context, SplashScreenActivity.class);
                        ((Activity)context).startActivity(i);
                        ((Activity)context).finish();
                        ((Activity)context).overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                        break;
                    case 40: //sessionToken không tồn tại
                        SCurrentUser.delCurrentUser(context);
                        db = new SqliteManager(context);
                        db.deleteSessionToken();
                        Intent ii = new Intent(context, LoginActivity.class);
                        ((Activity)context).startActivity(ii);
                        ((Activity)context).finish();
                        ((Activity)context).overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
                        break;
                    case 10: //Lỗi hệ thống
                        Message.showToast(context,  rootObject.get("errorMessage").getAsString());
                        Log.d("BaseConnectAPI",rootObject.get("errorMessage").getAsString());
                        break;
                    case 20: //Api key không tồn tại
                        final GmailSender gmail = new GmailSender();
                        new BaseAsystask() {
                            @Override
                            public void onPre() {

                            }

                            @Override
                            public void doInBG() {
                                try {
                                    gmail.sendMail(
                                            "Apikey không hợp lệ",
                                            url + " - " + data,
                                            Constants.USER_GMAIL,
                                            Constants.CC_GMAIL
                                    );
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onPost() {

                            }
                        }.execute();
                        new GetApiKeyAPI(context).execute();
                        break;
                    case 24: //Yêu cầu update phiên bản mới
                        getDialogUpdateApp(rootObject.get("errorMessage").getAsString());
                        updateApp();
                        break;
                    default:
                        onError();
                        Message.showToast(context, rootObject.get("errorMessage").getAsString());
                        break;
                }
            } else {
                onError();
                Message.showToastConnect(context);
            }
        } catch (Exception e) {
            onError();
            e.printStackTrace();
        }
        onPostMain(aVoid);
        dismissDialog();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        onUpdate();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String response = null;
        try {
            getJsonFromUrl = new GetJsonFromUrl(url, data);
            switch (method) {
                case GET:
                    response = getJsonFromUrl.getResponseStringGET();
                    break;
                case POST:
                    response = getJsonFromUrl.getResponseStringPOST();
                    break;
                case DELETE:
                    response = getJsonFromUrl.getResponseStringDELETE();
                    break;
                case PUT:
                    //response = getJsonFromUrl.get();
                    break;
            }
            doInBG();
        } catch (Exception e) {
            response = null;
        }
        return response;
    }

    public abstract void onPre();

    public abstract void onUpdate();

    public abstract void doInBG();

    public abstract void onPost(JsonObject result);
    public void onError(){}

    public void onPostMain(String result) {}

    public void updateApp() {}

    public void initDialog() {
        pg_dialog = new ProgressDialog(context) {
            @Override
            public void onBackPressed() {
                // TODO Auto-generated method stub
                pg_dialog.dismiss();
            }
        };
        pg_dialog.show();
        pg_dialog.setIndeterminate(false);
        pg_dialog.setCancelable(false);
        pg_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pg_dialog.setContentView(R.layout.layout_progressdialog);
    }

    public void dismissDialog() {
        if (pg_dialog != null && pg_dialog.isShowing())
            pg_dialog.dismiss();
    }

    public void getDialogUpdateApp(String errorMessage){
        CustomDialog dialog = new CustomDialog((Activity) context);
        dialog.setShow(true);
        dialog.setShow1Button(true,context.getString(R.string.update_dh));
        dialog.setTitleMessage(errorMessage);
        dialog.setTextTitle(context.getString(R.string.title_notify));
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
}
