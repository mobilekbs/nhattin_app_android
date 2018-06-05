package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.ReportErrorAPI;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.SplashScreenActivity;


/**
 * Created by Zanty on 21/06/2016.
 */
public abstract class BaseConnectAPI extends AsyncTask<Void, Void, String> {
    public static final String          TAG = "BaseConnectAPI";

    public Context                      context;
    public String                       url, data;
    ProgressDialog                      pg_dialog;
    public boolean                      refresh = false, isReport = false;
    GetJsonFromUrl getJsonFromUrl;
    Method method;
    //SqliteManager db;

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
                switch (errorCode){
                    case 200: //Thành công
                        /*JsonObject result = null;
                        try {
                            result = rootObject.get("data").getAsJsonObject();
                        } catch (Exception e) {
                        }*/
                        onPost(rootObject);
                        break;
                    case 47: //Android Key không hợp lệ
                        Message.makeToastError(context, context.getString(R.string.toast_error_key_api));
                        //SqliteManager db = new SqliteManager(context);
                        SSQLite.getInstance(context).deleteUser();
                        SCurrentUser.delCurrentUser();
                        Commons.restartApp(context, SplashScreenActivity.class);
                        break;
                    default:
                        onFailed(errorCode, rootObject.get("errorMessage").getAsString());
                        break;
                }
                if (errorCode != 200)
                    callReportErrorAPI(aVoid);
            } else {
                Log.d(TAG, "Result = null");
                onError();
                callReportErrorAPI(aVoid);
            }
        } catch (Exception e) {
            onError();
            e.printStackTrace();
            callReportErrorAPI(aVoid);
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
        Log.e("BaseConnectAPI","-------- doInBackground ");

        String response = null;
        try {
            getJsonFromUrl = new GetJsonFromUrl(url, data);
            switch (method) {
                case GET:
                    Log.e("BaseConnectAPI","--------  case GET ");

                    response = getJsonFromUrl.getResponseStringGET();

                    Log.e("BaseConnectAPI","--------  response " + response);

                    break;
                case POST:
                    Log.e("BaseConnectAPI","--------  case POST ");

                    response = getJsonFromUrl.getResponseStringPOST();
                    Log.e("BaseConnectAPI","--------  response " + response);
                    break;
                case DELETE:
                    Log.e("BaseConnectAPI","--------  case DELETE ");

                    response = getJsonFromUrl.getResponseStringDELETE();
                    Log.e("BaseConnectAPI","--------  response " + response);
                    break;
                case PUT:
                    Log.e("BaseConnectAPI","--------  case PUT ");
                    Log.e("BaseConnectAPI","--------  response " + response);

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

    /**
     * Sau khi onPost nếu check errorCode != 200 thì chạy vào hàm này
     */
    public void onFailed(int errorCode, String errorMessage){
        try {
            Message.makeToastError(context, errorMessage);
        } catch (Exception e) {
        }
    }

    public void onError(){
        try {
            Message.makeToastErrorConnect(context);
        } catch (Exception e) {
        }
    }

    public void callReportErrorAPI(String output){
        if (!isReport && Commons.hasConnection(context))
            new ReportErrorAPI(context, url, data, output +"").execute();
    }

    public void onPostMain(String result) {}

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

    public void initDialogWithTitle(String message, boolean cancel) {
        refresh = true;
        pg_dialog = new ProgressDialog(context);
        pg_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pg_dialog.setMessage(message);
        pg_dialog.setIndeterminate(false);
        pg_dialog.setCancelable(cancel);
        pg_dialog.setCanceledOnTouchOutside(false);
        pg_dialog.show();
    }

    /*public void getDialogUpdateApp(String errorMessage){
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
    }*/
}
