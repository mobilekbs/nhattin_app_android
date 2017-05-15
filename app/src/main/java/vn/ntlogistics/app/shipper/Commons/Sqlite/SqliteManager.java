package vn.ntlogistics.app.shipper.Commons.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.shipper.Models.Outputs.Location.Lonlat;
import vn.ntlogistics.app.shipper.Models.Outputs.Login.ApiKey;
import vn.ntlogistics.app.shipper.Models.Outputs.Login.CurrentUser;
import vn.ntlogistics.app.shipper.Models.Outputs.Login.Notify;


/**
 * Created by Zanty on 27/06/2016.
 */
public class SqliteManager extends SQLiteOpenHelper {
    Context context;
    //public static SqliteManager instance;
    //private String DB_PATH = "/data/data/" + Common.packageName + "/databases/";
    public static String DB_NAME = "db_OneShipPro.db";
    public static int DB_NUMBER = 3; //05.01.2017 - ShipK
    //TODO: Table location
    public static final String TB_LOCATION = "tb_location";
    private final String LONGITUDE = "longitude";
    private final String LATITUDE = "latitude";
    private final String TIME = "time";
    //TODO: Table login
    public final String TB_LOGIN = "tb_login";
    private String SS_TOKEN = "sessionToken";
    private final String USER_ID = "userId";
    private final String LOGIN_PHONE = "phoneNo";
    //TODO: Table notification
    public final String TB_NOTIFICATION = "tb_notification";
    public final String NOTIFY_TITLE = "title";
    public final String NOTIFY_CONTENT = "content";
    public final String NOTIFY_URL = "url";
    public final String NOTIFY_SEEN = "seen";
    //TODO: Table phone reminder
    public final String TB_PHONE = "tb_phone_reminder";
    public final String PHONE = "phoneNo";
    //TODO: Table Apikey
    public final String TB_API_KEY = "tb_api_key";
    public final String API_KEY = "apiKey";
    public final String API_SECRET_KEY = "apiSecretKey";
    //TODO: Table Fcm Token
    public final String TB_FCM_TOKEN = "tb_fcm_token";
    public final String FCM_TOKEN = "fcmToken";

    //TODO: Table Order Detail ShipK
    public final String TB_DETAIL_SHIPK = "tb_detail_shipk";
    public final String DS_SHIPPING_CODE = "shipping_code";
    public final String DS_SUPPLIER_ID = "supplier_id";
    public final String DS_PRODUCT_ID = "product_id";

    String tb_detail_shipk = "CREATE TABLE  " + TB_DETAIL_SHIPK + "("
            + "_id" + " integer primary key autoincrement, "
            + DS_SHIPPING_CODE + " text not null,"
            + DS_SUPPLIER_ID + " text not null,"
            + DS_PRODUCT_ID + " text not null"
            + ");";

    String tb_login = "CREATE TABLE  " + TB_LOGIN + "("
            + "_id" + " integer primary key autoincrement, "
            + USER_ID + " text not null,"
            + SS_TOKEN + " text not null,"
            + LOGIN_PHONE + " text not null"
            + ");";
    String tb_location = "CREATE TABLE " + TB_LOCATION + "("
            + "_id" + " integer primary key autoincrement, "
            + LONGITUDE + " text not null,"
            + LATITUDE + " text not null,"
            + TIME + " text"
            + ");";
    String tb_notification = "CREATE TABLE " + TB_NOTIFICATION + "("
            + "_id" + " integer primary key autoincrement, "
            + NOTIFY_TITLE + " text not null,"
            + NOTIFY_CONTENT + " text not null,"
            + NOTIFY_URL + " text,"
            + NOTIFY_SEEN + " INTEGER"
            + ");";
    String tb_phone = "CREATE TABLE " + TB_PHONE + "("
            + "_id" + " integer primary key autoincrement, "
            + PHONE + " text not null"
            + ");";
    String tb_api_key = "CREATE TABLE  " + TB_API_KEY + "("
            + "_id" + " integer primary key autoincrement, "
            + API_KEY + " text not null,"
            + API_SECRET_KEY + " text not null"
            + ");";
    String tb_fcm_token = "CREATE TABLE  " + TB_FCM_TOKEN + "("
            + "_id" + " integer primary key autoincrement, "
            + FCM_TOKEN + " text not null"
            + ");";
    public SqliteManager(Context context) {
        super(context, DB_NAME, null, DB_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
            case 2: //update shipK
                try {
                    sqLiteDatabase.execSQL(tb_detail_shipk);
                } catch (Exception e) {
                }
                sqLiteDatabase.setVersion(DB_NUMBER);
                break;
            default:
                break;
        }
    }

    public String getDBPath(Context context){
        return "/data/data/" + context.getPackageName() + "/databases/";
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = getDBPath(context) + DB_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory())
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database chua ton tai
        }

        if (checkDB != null)
            checkDB.close();

        return checkDB != null ? true : false;
    }

    // ----------Kiểm tra có data chưa, chưa có thì tạo
    public void createDataBase() throws IOException {
        try {
            boolean dbExist = checkDataBase(); // kiem tra db
            if (dbExist) {
            } else {
                this.getReadableDatabase();
                try {
                    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getDBPath(context) + DB_NAME, null);
                    db.execSQL(tb_location);
                    db.execSQL(tb_login);
                    db.execSQL(tb_notification);
                    db.execSQL(tb_phone);
                    db.execSQL(tb_api_key);
                    db.execSQL(tb_fcm_token);
                    //ver3
                    db.execSQL(tb_detail_shipk);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: Table FCM Token
    public void insertFCMToken(String fcmToken){
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FCM_TOKEN,fcmToken);
            Cursor c = null;
            try {
                c = db.rawQuery(
                        "select * from "+TB_FCM_TOKEN+" where "+FCM_TOKEN+"=?",
                        new String[]{fcmToken}
                );
                if (c.moveToFirst()) {
                    //Nếu đã có số dt trong db thì k làm j cả
                } else {
                    db.delete(TB_FCM_TOKEN, null, null);
                    db.delete("sqlite_sequence", "name=?", new String[]{TB_FCM_TOKEN});
                    db.insert(TB_FCM_TOKEN, null, values);
                }
                c.close();
            } catch (Exception e) {
                db.insert(TB_FCM_TOKEN, null, values);
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SQLITE_MANAGER", "Không thể insert FCM Token");
        }
    }
    public String getFCMToken(){
        String token = null;
        Cursor c = GetAllDataFromTable(TB_FCM_TOKEN);
        try {
            if (c.moveToFirst()) {
                do {
                    token = c.getString(1);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d("SQLITE_MANAGER", "Không thể getdata api key");
            e.printStackTrace();
        }
        return token;
    }

    //TODO: Table Api Key
    public void insertApiKey(ApiKey apiKey){
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(API_KEY,apiKey.getApiKey());
            values.put(API_SECRET_KEY,apiKey.getApiSecretKey());

            DeleteDataOfTable(TB_API_KEY,null,null);
            db.insert(TB_API_KEY, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SQLITE_MANAGER", "Không thể insert api key");
        }
    }

    public ApiKey getApiKey(){
        ApiKey api = new ApiKey();
        Cursor c = GetAllDataFromTable(TB_API_KEY);
        try {
            if (c.moveToFirst()) {
                do {
                    api.setApiKey(c.getString(1));
                    api.setApiSecretKey(c.getString(2));
                } while (c.moveToNext());
            }
        } catch (final Exception e) {
            Log.d("SQLITE_MANAGER", "Không thể get api key");
            e.printStackTrace();
        }
        return api;
    }

    public void deleteApiKey() {
        DeleteDataOfTable(TB_API_KEY,null,null);
    }

    //TODO: Phone Reminder
    public void insertPhoneReminder(String phone) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                c = db.rawQuery(
                        "select * from "+TB_PHONE+" where "+PHONE+"=?",
                        new String[]{phone}
                );
                if (c.moveToFirst()) {
                    //Nếu đã có số dt trong db thì k làm j cả
                } else {
                    values.put(PHONE,phone);
                    db.insert(TB_PHONE, null, values);
                }
                c.close();
            } catch (Exception e) {
                db.insert(TB_PHONE, null, values);
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SQLITE_MANAGER", "Không thể insert phone reminder");
        }
    }

    public List<String> getListPhoneFromData() {
        List<String> mList = new ArrayList<>();
        Cursor c = GetAllDataFromTable(TB_PHONE);
        try {
            if (c.moveToFirst()) {
                do {
                    mList.add(c.getString(1));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d("SQLITE_MANAGER", "Không thể getdata Notification");
        }
        return mList;
    }

    //TODO: Notification
    public void insertNotification(String title, String content, String url) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NOTIFY_TITLE, title);
            values.put(NOTIFY_CONTENT, content);
            values.put(NOTIFY_URL, url);
            values.put(NOTIFY_SEEN, 0);
            db.insert(TB_NOTIFICATION, null, values);
        } catch (Exception e) {
            Log.d("SQLITE_MANAGER", "Không thể insert Notification");
        }
    }

    public void updateNotification(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTIFY_SEEN, 1);
        try {
            db.update(TB_NOTIFICATION, values, "_id=?", new String[]{id});
        } catch (Exception e) {
            Log.d("SQLITE_MANAGER", "Không thể update Notification");
        }
    }

    public int countNotification(){
        int count = 0;
        Cursor c = GetAllDataFromTable(TB_NOTIFICATION);
        try {
            if (c.moveToFirst()) {
                do {
                    count++;
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d("SQLITE_MANAGER", "Không thể get Notification");
        }
        return count;
    }

    public void deleteNotification(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_NOTIFICATION, "_id=?", new String[]{id});
    }

    public void deleteNotification() {
        DeleteDataOfTable(TB_NOTIFICATION,null,null);
    }

    public List<Notify> getListNotificationFromData() {
        List<Notify> mList = new ArrayList<>();
        Cursor c = GetAllDataFromTable(TB_NOTIFICATION);
        try {
            if (c.moveToFirst()) {
                do {
                    Notify notify = new Notify();
                    notify.setId(c.getString(0));
                    notify.setTitle(c.getString(1));
                    notify.setContent(c.getString(2));
                    notify.setUrl(c.getString(3));
                    if (Integer.parseInt(c.getString(4)) == 1) {
                        notify.setSeen(true);
                    } else
                        notify.setSeen(false);
                    mList.add(notify);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d("SQLITE_MANAGER", "Không thể getdata Notification");
        }
        return mList;
    }

    public void insertOrUpdateLogin(String userID, String ssToken, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = null;
        values.put(USER_ID, userID);
        values.put(SS_TOKEN, ssToken);
        values.put(LOGIN_PHONE, phone);
        try {
//            c = db.query(TB_LOGIN, null, USER_ID+"=? OR "+USER_ID+"=? OR ", new String[]{SCurrentUser.getOurInstance().getUserID()}, null, null, null);
            c = db.rawQuery(
                    "select * from tb_login where userId=?",
                    new String[]{userID}
            );
            if (c.moveToFirst()) {
                String _id = c.getString(1);
                db1.update(TB_LOGIN, values, USER_ID + "=?", new String[]{_id});
            } else {
                db1.insert(TB_LOGIN, null, values);
            }
            c.close();
        } catch (Exception e) {
            db1.insert(TB_LOGIN, null, values);
            c.close();
        }
    }

    public void insertLocation(String lon, String lat, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = null;
        try {
            values.put(LONGITUDE, lon);
            values.put(LATITUDE, lat);
            values.put(TIME, time);
            db.insert(TB_LOCATION, null, values);
            c.close();
        } catch (Exception e) {
        }
    }

    // Xóa dữ liệu một bảng
    public void DeleteDataOfTable(String tableName, String columnName, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (columnName != null && value != null)
                db.delete(tableName, columnName + "=?", new String[]{String.valueOf(value)});
            else {
                db.delete(tableName, null, null);
                //Bảng sqlite_sequence xét id tự tăng, xóa để về lại 0
                db.delete("sqlite_sequence", "name=?", new String[]{tableName});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSessionToken() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TB_LOGIN, null, null);
            db.delete("sqlite_sequence", "name=?", new String[]{TB_LOGIN});
        } catch (Exception e) {
        }
    }

    // Lấy dữ liệu từ sql trả về cursor
    public Cursor GetDataFromTable(String tableName, String arrColumn[], String whereClause,
                                   String arrGiaTriWhereClause[], String groupBy, String having, String orderBy) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(tableName, // Tên bảng
                    arrColumn, // Cột lấy
                    whereClause, // Mệnh đề where
                    arrGiaTriWhereClause, groupBy, having, orderBy);
        } catch (Exception e) {
        }
        return c;
    }

    // Lấy tất cả dữ liệu 1 bảng từ sql trả về cursor
    public Cursor GetAllDataFromTable(String tableName) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(tableName, null, null, null, null, null, null);
        } catch (Exception e) {
        }
        return c;
    }

    public List<Lonlat> readAllLocation() {
        List<Lonlat> mList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c = GetAllDataFromTable(TB_LOCATION);
            if (c.moveToFirst()) {
                do {
                    mList.add(new Lonlat(
                            Double.parseDouble(c.getString(1)),
                            Double.parseDouble(c.getString(2)),
                            Long.parseLong(c.getString(3))
                    ));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
        }

        return mList;
    }

    public void readSessionToken() {
        try {
            Cursor c = GetAllDataFromTable(TB_LOGIN);
            if (c.moveToFirst()) {
                do {
                    if (c.getString(2) != null) {
                        CurrentUser user = new CurrentUser();
                        user.setUserID(c.getString(1));
                        user.setSessionToken(c.getString(2));
                        user.setPhoneNo(c.getString(3));
                        SCurrentUser.setCurrentUser(context,user);
                    }
                } while (c.moveToNext());
            }
        } catch (Exception e) {
        }
    }
}
