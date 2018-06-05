package vn.ntlogistics.app.ordermanagement.Commons;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.ntlogistics.app.config.Config;

/**
 * Created by kindlebit on 8/14/2017.
 */

public class CameraUtil {

    public static final int RESULT_LOAD_CAMERA_IMAGE = 401, RESULT_CROP_IMAGE = 402;
    private String selected="nothing", cropped_img_path="", gallery_image_path="", APPID="";
    private Context context;
    private Activity activity;
    public static final int REQUEST_ID_CAMERA_PERMISSIONS = 400;
    private static String[] permissions= new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public boolean onceDone = false;

    public CameraUtil(Context context){
        this.context=context;
        activity=(Activity)context;
        APPID = context.getPackageName();
    }

    public static ArrayList<String> checkPermissions(Context context) {
        int result;
        ArrayList<String> listPermissionsNeeded = new ArrayList<String>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(context,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        return listPermissionsNeeded;
    }

public static String filename = "";
    public void getImageFromCamera(String filegetname) {
        filename = filegetname;
        if (Build.VERSION.SDK_INT > 22) {
            ArrayList<String> arrayListPermissions = checkPermissions(context);
            if(arrayListPermissions.size() > 0){
                ActivityCompat.requestPermissions(activity, arrayListPermissions.toArray(new String[arrayListPermissions.size()]),REQUEST_ID_CAMERA_PERMISSIONS);
            } else {
//                camIntent();
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File out = new File(getTempCamFileName());
                Uri uri = Uri.fromFile(out);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                activity.startActivityForResult(i, RESULT_LOAD_CAMERA_IMAGE);
            }
        } else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File out = new File(getTempCamFileName());
            Uri uri = Uri.fromFile(out);
            i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(i, RESULT_LOAD_CAMERA_IMAGE);
        }
    }


    private static String getTempCamFileName() {
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "Nhattin");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }
        String uriSting = (myDirectory.getAbsolutePath() + "/" + filename + "_orig.jpg");
        return uriSting;

    }
    private String getCroppedFilename() {
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "Nhattin");
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }

        if (!this.onceDone) {
            getCurrentTime();
            this.onceDone = true;
            Log.e("TAG", "*********************************************  onceDone = true ");
        }

        String uriSting = (myDirectory.getAbsolutePath() + "/" + filename + "^bill^" + Config. billNumber + "^" + Config.fileTimeStamp + ".jpg");
        return uriSting;

    }

    private static String getRealPathFromURI(Uri contentUri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = ((Activity) context).managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String getPathKitKat(final Uri uri, Context context) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            //  else if ("file".equ       return uri.getPath();
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public void camIntent(){
        Log.e("path","path"+APPID);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File out = new File(getTempCamFileName());
        Uri uri = FileProvider.getUriForFile(context,APPID + ".provider", out);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(i, RESULT_LOAD_CAMERA_IMAGE);
    }


    public void performcamCrop() {
        String selectedImage = getTempCamFileName();
        performCrop(selectedImage);
    }

    private void performCrop(String path){
        try {
            Uri uri_out,uri_in;
            File in = new File(path);
            File out = new File(getCroppedFilename());
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
//            if(Build.VERSION.SDK_INT > 23){
//
//                uri_out= FileProvider.getUriForFile(context,APPID + ".provider", out);
//                uri_in = FileProvider.getUriForFile(context,APPID + ".provider", in);
//                context.grantUriPermission("com.google.android.apps.photos", uri_out, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            }else{
                uri_out= Uri.fromFile(out);
                uri_in = Uri.fromFile(in);
//            }
            cropIntent.setDataAndType(uri_in, "image/*");
            cropIntent.putExtra("crop", "true");
/*            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
            cropIntent.putExtra("return-data", true);*/

            //indicate aspect of desired crop
           /* cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);*/
            //retrieve data on return
            cropIntent.putExtra("return-data", false);

            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri_out);

//            cropIntent.putExtra("setWallpaper", false);
           // cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

            activity.startActivityForResult(cropIntent, RESULT_CROP_IMAGE);

        } catch (Exception e) {
            Log.e("CropExp", "//" + e.toString());
        }
    }

    public void performGalleryImgCrop(Intent data){
        File imageFile = new File(getRealPathFromURI(data.getData(), context));
        gallery_image_path = imageFile.toString();
        performCrop(gallery_image_path);

    }

    public void kitkatGalleryImgCrop(Intent data){
        Uri selectedImageUri = data.getData();
        gallery_image_path = getPathKitKat(selectedImageUri, context);
        performCrop(gallery_image_path);
    }

    public String setCroopedImage(){
        cropped_img_path=  getCroppedFilename();
        return cropped_img_path;
    }

    public void deleteOrgPic(){
        File filecheck = new File(getTempCamFileName());
        if(filecheck.exists())
            filecheck.delete();
    }

    public static void getCurrentTime() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        Config.fileTimeStamp = dateFormat.format(cal.getTime());

        //date output format
       // return dateFormat.format(cal.getTime());
    }

}
