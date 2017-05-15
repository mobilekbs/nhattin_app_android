package vn.ntlogistics.app.shipper.Commons;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.ntlogistics.app.shipper.BuildConfig;
import vn.ntlogistics.app.shipper.R;

/**
 * Created by Zanty on 15/05/2017.
 */

public class Commons {
    public static final String TAG = "Commons";

    // Định dạng số kiểu 100.000.000
    public static String DinhDangChuoiTien(String chuoiSo) {
        try {
            return String.format("%,.0f", Double.parseDouble(chuoiSo));
        } catch (Exception e) {
            return "";
        }
    }

    // Định dạng số kiểu 100.000.000
    public static String DinhDangChuoiTien(Double chuoiSo) {
        try {
            return String.format("%,.0f", chuoiSo);
        } catch (Exception e) {
            return "";
        }
    }

    // Định dạng số kiểu 100.000.000,234234
    public String DinhDangChuoiSoLuong(String chuoiSo) {
        try {
            return String.format("%,.1f", Double.parseDouble(chuoiSo));
        } catch (Exception e) {
            Log.d(TAG, "DinhDangChuoiSoLuong: " + e);
            return "";
        }
    }

    // Mã hóa mật khẩu sang md5
    public static String convertToMd5(final String md5) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        try {

            final java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            final byte[] array = md.digest(md5.getBytes("UTF-8"));
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (final java.security.NoSuchAlgorithmException e) {
        }
        return sb.toString();
    }

    // Ẩn bàn phím khi click outside edittext
    /*public void hideSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
    }*/

    /**
     * Ẩn bàn phím ảo khi ấn ra ngoài
     * @param context
     * @param view truyền vào layout chứa edittext
     */
    public static void hideSoftKeyboard(final Context context, View view) {

        try {
            // Set up touch listener for non-text box views to hide keyboard.
            if (!(view instanceof EditText)) {

                view.setOnTouchListener(new View.OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent event) {
                        InputMethodManager inputMethodManager = (InputMethodManager) context
                                .getSystemService(Activity.INPUT_METHOD_SERVICE);
                        try {
                            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }

                });
            }

            // If a layout container, iterate over children and seed recursion.
            if (view instanceof ViewGroup) {

                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                    View innerView = ((ViewGroup) view).getChildAt(i);

                    hideSoftKeyboard(context,innerView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // END //Ẩn bàn phím


    // Convert dp to pixel
    public static int Convert_DP_TO_PIXEL(Context context, int dp) {

        try {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                    context.getResources().getDisplayMetrics());
        } catch (Exception e) {
            Log.d(TAG, "Convert_DP_TO_PIXEL: " + e);
            return 0;
        }

    }
    // END convert

    public void XoaFileTrongThuMuc(String duongDanThuMuc, String duoiFile) {
        try {
            File[] folderList = LayListFileTrongThuMuc(duongDanThuMuc, duoiFile);

            for (int i = 0; i < folderList.length; i++) {
                File file = new File(folderList[i].getPath());
                file.delete();
            }
        } catch (Exception e) {
            Log.d(TAG, "XoaFileTrongThuMuc: " + e);
        }
    }

    public File[] LayListFileTrongThuMuc(String duongDanThuMuc, final String duoiFile) {
        //Sử dụng
        //Lấy list file
        //File[] folderList = thanhNam.LayListFileTrongThuMuc(duongDanThuMucGoc, ".3gpp");

        //Lấy list thư mục con
        //File[] folderList = thanhNam.LayListFileTrongThuMuc(duongDanThuMucGoc, null);

        File[] folderList = null;
        try {
            File folder = new File(duongDanThuMuc);

            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return (name.endsWith(duoiFile));
                }
            };

            if (duoiFile != null)
                folderList = folder.listFiles(filter);
            else
                folderList = folder.listFiles();

        } catch (Exception e) {
            Log.d(TAG, "LayListFileTrongThuMuc: " + e);
        }
        return folderList;
    }

    public void log(String s) {
        try {
            Log.d(TAG, s);
        } catch (Exception e) {

        }
    }

    public static long convertDataToMillisecond(String data){
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            date = sdf.parse(data);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String timeStampToDate(long timeStamp) {
        try {
            Date netDate = (new Date(timeStamp));
            return new SimpleDateFormat("dd/MM/yyyy").format(netDate);
        } catch (Exception ex) {
            return "";
        }
    }
    public static String timeStampToDateNotYear(long timeStamp) {
        try {
            Date netDate = (new Date(timeStamp));
            return new SimpleDateFormat("dd/MM").format(netDate);
        } catch (Exception ex) {
            return "";
        }
    }
    public static String timeToDateFirebase(long timeStamp) {
        try {
            Date netDate = (new Date(timeStamp));
            return new SimpleDateFormat("yyyyMMdd").format(netDate);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getHourFromMiloseconds(long time){
        try {
            Date netDate = (new Date(time));
            return new SimpleDateFormat("HH").format(netDate);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String timeStampToTime(long timeStamp) {
        try {
            Date netDate = (new Date(timeStamp));
            return new SimpleDateFormat("HH:mm").format(netDate);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String slipteStringDate(String date) {
        String[] timeDate = date.split(" ");
        return timeDate[0];
    }

    public static final long ONE_SECOND = 1000;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long ONE_DAY = ONE_HOUR * 24;
    public static final long MONTHS = ONE_DAY * 30;
    public static final long YEARS = MONTHS * 12;

    public static String millisToLongDHMS(Context context, String uploadTime) {
        Calendar c = Calendar.getInstance();
        Long duration = c.getTimeInMillis() - Long.parseLong(uploadTime);
        StringBuffer res = new StringBuffer();
        long temp = 0;
        if (duration >= ONE_SECOND) {
            temp = duration / YEARS;
            if (temp > 0) {
                if (temp > 1)
                    res.append(temp).append(" " + context.getResources().getString(R.string.years));
                else
                    res.append(temp).append(" " + " " + context.getResources().getString(R.string.year));

                return res.toString();
            }

            temp = duration / MONTHS;
            if (temp > 0) {
                if (temp > 1)
                    res.append(temp).append(" " + context.getResources().getString(R.string.months));
                else
                    res.append(temp).append(" " + context.getResources().getString(R.string.month));

                return res.toString();
            }


            temp = duration / ONE_DAY;
            if (temp > 0) {
                if (temp > 1)
                    res.append(temp).append(" " + context.getResources().getString(R.string.days));
                else
                    res.append(temp).append(" " + context.getResources().getString(R.string.day));

                return res.toString();
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                if (temp > 1)
                    res.append(temp).append(" " + context.getResources().getString(R.string.hours));
                else
                    res.append(temp).append(" " + context.getResources().getString(R.string.hour));

                return res.toString();

            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                if (temp > 1)
                    res.append(temp).append(" " + context.getResources().getString(R.string.minutes));
                else
                    res.append(temp).append(" " + context.getResources().getString(R.string.minute));

                return res.toString();
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                if (temp > 1)
                    res.append(temp).append(" " + context.getResources().getString(R.string.seconds));
                else
                    res.append(temp).append(" " + context.getResources().getString(R.string.second));

                return res.toString();
            }
            return res.toString();
        } else {
            return "0 second";
        }
    }

    public static byte[] convertFileToByte(String path) {
        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return b;
    }

    public static byte[] convertBitmapToByte(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return b;
    }

    public static Bitmap resize(Bitmap b, int alpha) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, 1000, 1000), Matrix.ScaleToFit.CENTER);
        m.postRotate(alpha);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
    }

    public static boolean checkVersionAndroid() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return true;
        } else
            return false;
    }

    //Rotate image when get image uri form taking and gallery.
    public static Bitmap rotateImagePath(String photoPath, Bitmap bb) {
        int orientation = 1;
        int alpha = 0;
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(photoPath);
            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        } catch (Exception e) {
        }
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                alpha = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                alpha = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                alpha = 270;
                break;
            default:
                break;
        }
        /*try {
            ei.setAttribute(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED + ""
            );
            alpha = 0;
        } catch (Exception e) {
        }*/
        return Commons.resize(bb, alpha);
    }

    //TODO: get Android ID
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    //TODO: get IMEI number
    public static String getIMEINumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    //TODO: check has connection internet
    public static boolean hasConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }

    //Check GPS
    public static boolean checkGPS(Context c) {
        LocationManager locationManager = (LocationManager) c.getSystemService(c.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    public static Intent chooseImage(Context context) {
        String pathImage = "";
        Intent choose = null;

        //Kiem tra permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                        (Activity) context,
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        1);
            }
        }

        try {
            Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
            pick.setType("image/*");
            Intent pho = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String fileName = System.currentTimeMillis() + ".jpg";

            /**
             * Kiểm tra xem có cho đọc ghi vào bộ nhớ máy không.
             * Nếu cho thì get đường dẫn ra và lưu.
             * Nếu không thì lưu vào thẻ nhớ
             *
             */
            if(isExternalStorageWritable()){
                String path = Environment.getExternalStorageDirectory() + "/1Ship/Images";
                File folderMain = new File(path);
                //File folderMain = new File(Environment.getExternalStorageDirectory(),"/1Ship/Images");
                if (!folderMain.exists())
                    folderMain.mkdirs();
                pathImage = path + "/" + fileName;
                pho.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(pathImage)));
            }
            choose = Intent.createChooser(pick, context.getResources().getString(R.string.choose_image));
            choose.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pho});
            choose.putExtra("pathImage", pathImage);
        } catch (Exception e) {
        }
        return choose;
    }

    /**
     * Checks if external storage is available for read and write
     * Kiểm tra nếu lưu trữ bên ngoài sẵn sàng cho đọc và ghi
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     *  Checks if external storage is available to at least read
     *  Kiểm trả lưu trữ bên ngoài sẵn sàng cho đọc
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static void setEnabledButton(final View v) {
        v.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setEnabled(true);
            }
        }, 1000);
    }



    public static String convertPathToBase64(String path) {
        String base64 = null;
        try {
            byte[] data = Commons.convertFileToByte(path);
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            Log.d("createJSRegister", "Can not convert path to base64");
        }
        return base64;
    }

    public static byte[] resizeMaintainAspectRatio(int maxWidth, int maxHeight, Bitmap bitmap) {
        Bitmap bitmapSup = null;
        if (maxHeight > 0 && maxWidth > 0) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioBitmap < 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            bitmapSup = Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true);
        } else {
        }
        try {
            return convertBitmapToByte(bitmapSup);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Nếu chuỗi path truyền vào không parser thành File được thì đó là chuỗi base64 rồi.
     * Nên sẽ trả về chuỗi đó.
     * @param path
     * @return
     */
    public static String convertPathToBase64WithResize(String path) {
        String base64 = null;
        try {
            File imgFile = new File(path);

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            base64 = Base64.encodeToString(
                    Commons.resizeMaintainAspectRatio(
                            Constants.RESIZE_WIDTH,
                            Constants.RESIZE_HEIGHT, myBitmap),
                    Base64.NO_WRAP
            );
        } catch (Exception e) {
            return path;
        }
        return base64;
    }

    public static String convertBitmapToBase64WithResize(Bitmap bitmap) {
        String base64 = null;
        try {
            base64 = Base64.encodeToString(
                    Commons.resizeMaintainAspectRatio(
                            Constants.RESIZE_WIDTH,
                            Constants.RESIZE_HEIGHT, bitmap),
                    Base64.NO_WRAP
            );
        } catch (Exception e) {
        }
        return base64;
    }

    /**
     * Kiểm tra email đúng format không
     * @param email
     * @return
     */
    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Kiểm tra xem 1 service class có đang chạy không
     */
    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gán số vào icon app ở background
     * @param context
     * @param count
     */
    public static void setBadgeIconApp(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    /**
     * Kiểm tra
     * @param name
     * @return
     */
    public static boolean isText(String name) {
        /*char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;*/
        return name.matches("[a-zA-Z0-9-. @]+");
    }

    public static int getVersionCode(Context context){
        int appVersion = 0;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(appVersion == 0)
            appVersion = BuildConfig.VERSION_CODE;
        return appVersion;
    }


    public static String getVersionName(Context context){
        String appVersion = null;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            appVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(appVersion == null)
            appVersion = BuildConfig.VERSION_NAME;
        return appVersion;
    }
}
