package vn.ntlogistics.app.ordermanagement.Commons.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.BillFail.BillFailSqlite;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.City;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.District;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.Service;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login.User;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;


/**
 * Created by Zanty on 27/06/2016.
 */
public class SqliteManager extends SQLiteOpenHelper {
    public static final String TAG = "SqliteManager";
    private Context context;

    //TODO: Init table
    String tb_sender_bill = "CREATE TABLE  " + Variables.TBL_SENDER_BILL + "("
            + "_id" + " integer primary key autoincrement, "
            + Variables.SB_ID + " text not null,"
            + Variables.SB_SENDER_PHONE + " text not null,"
            + Variables.SB_SENDER_ADDRESS + " text not null,"
            + Variables.SB_SENDER_NAME + " text,"
            + Variables.SB_SENDER_NODE + " text,"
            + Variables.SB_RECEIVER_PHONE + " text,"
            + Variables.SB_RECEIVER_ADDRESS + " text,"
            + Variables.SB_RECEIVER_NAME + " text,"
            + Variables.SB_RECEIVER_NODE + " text,"
            + Variables.SB_LENGTH + " text,"
            + Variables.SB_WIDTH + " text,"
            + Variables.SB_HEIGHT + " text,"
            + Variables.SB_WEIGHT + " text,"
            + Variables.SB_COD + " text,"
            + Variables.SB_SERVICE + " text,"
            + Variables.SB_STATUS + " text,"
            + Variables.SB_SEND_DATE + " text,"
            + Variables.SB_PROVINCE_ID + " text,"
            + Variables.SB_OTP_CODE + " text"
            + ");";

    public SqliteManager(Context context) {
        super(context, Variables.DATABASE_NAME, null, Variables.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
            case 2:
                try {
                    /**
                     * Version DB = 3
                     * Bảng lưu lại thông tin đơn hàng mà được giao cho
                     * shipper đi lấy hàng về kho.
                     */
                    sqLiteDatabase.execSQL(tb_sender_bill);
                } catch (SQLException e) {
                }
                /**
                 * Sau khi xử lý xong thì update version db.
                 * Lưu ý: setVersion ở case cuối cùng.
                 */
                sqLiteDatabase.setVersion(Variables.DATABASE_VERSION);
                break;
            default:
                break;
        }
    }

    public String getDBPath(Context context){
        if(android.os.Build.VERSION.SDK_INT >= 17) {
            return context.getApplicationInfo().dataDir + "/databases/";
        } else {
            return "/data/data/" + context.getPackageName() + "/databases/";
        }
        // "/data/data/" + context.getPackageName() + "/databases/";
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = getDBPath(context) + Variables.DATABASE_NAME;
            File file = new File(myPath);
            if (file.exists() && !file.isDirectory())
                checkDB = SQLiteDatabase.openDatabase(
                        myPath,
                        null,
                        SQLiteDatabase.OPEN_READONLY);
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
                copyDataBase();

                /**
                 *  Sau khi copy db từ asset thì tạo thêm bảng sendbill
                 */
                this.getReadableDatabase();
                try {
                    //Đọc db
                    SQLiteDatabase db = SQLiteDatabase
                            .openOrCreateDatabase(
                                    getDBPath(context) + Variables.DATABASE_NAME,
                                    null);
                    //version 3
                    db.execSQL(tb_sender_bill);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Copy DB from asset
    public void copyDataBase() throws IOException {
        InputStream mInput = context.getAssets().open(Variables.DATABASE_NAME);
        String outFileName = getDBPath(context) + Variables.DATABASE_NAME;

        File fDB = new File(getDBPath(context));
        if(!fDB.exists())
            fDB.mkdirs();

        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        // Save mydbVersion
        /*SharedPreferences myShare = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = myShare.edit();
        editor.putInt(Variables.SP_KEY_DB_VER, Variables.DATABASE_VERSION);
        editor.commit();*/
        mOutput.close();
        mInput.close();
    }

    //TODO: My Staff -------------------
    public boolean inserOrUpdatetUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                values.put(Variables.KEY_PUBLIC_KEY, user.getPublickey());
                values.put(Variables.KEY_LOCAL_KEY, user.getLocalkey());
                values.put(Variables.KEY_VALUE_STAFF, user.getValue_staff());
                values.put(Variables.KEY_MYBANK, user.getMyBank());

                c = db.rawQuery(
                        "select * from "+ Variables.TBL_STAFF +" where "
                                + Variables.KEY_PUBLIC_KEY +"=?",
                        new String[]{user.getPublickey()}
                );
                if (c.moveToFirst()) {
                    db.update(Variables.TBL_STAFF,
                            values,
                            Variables.KEY_PUBLIC_KEY + "=?",
                            new String[]{user.getPublickey()});
                } else {
                    db.insert(Variables.TBL_STAFF, null, values);
                }
                SCurrentUser.delCurrentUser();
                c.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "inserOrUpdatetUser -------------------");
            return false;
        }
    }
    
    public void deleteUser(){
        DeleteDataOfTable(Variables.TBL_STAFF, null, null);
    }

    public User getUser(){
        User user = new User();
        Cursor c = getAllDataFromTable(Variables.TBL_STAFF);
        try {
            if (c.moveToFirst()) {
                do {
                    user.setIdStaff(c.getInt(0));
                    user.setPublickey(c.getString(c.getColumnIndex(Variables.KEY_PUBLIC_KEY)));
                    user.setLocalkey(c.getString(c.getColumnIndex(Variables.KEY_LOCAL_KEY)));
                    user.setValue_staff(c.getString(c.getColumnIndex(Variables.KEY_VALUE_STAFF)));
                    user.setMyBank(c.getString(c.getColumnIndex(Variables.KEY_MYBANK)));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Không thể getdata getUser");
        }
        return user;
    }

    public int getIdLocationInCity(){
        User user = getUser();
        if(user.getValue_staff() != null){
            String areaCode = user.getValue_staff().substring(0,2);
            City item = null;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = null;
            try {
                c = db.rawQuery(
                        "select * from "+ Variables.TBL_CITY +" where "
                                + Variables.KEY_AREACODE +"=?",
                        new String[]{areaCode}
                );
                if (c.moveToFirst()) {
                    do {
                        item = new City(
                                c.getInt(0),
                                c.getString(1),
                                c.getInt(2),
                                c.getInt(3)
                        );
                    } while (c.moveToNext());
                }
                if(item != null){
                    return item.getIdPosition();
                }
            } catch (Exception e) {
                Log.d(TAG, "Không thể getdata getUser");
            }
        }
        return 0;
    }

    //TODO: My Staff ------------------- End/

    //TODO: City -----------------------
    public ArrayList<City> getListCity(){
        ArrayList<City> mList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            /*c = db.rawQuery(
                    "select * from "+ Variables.TBL_CITY +" where "
                            + Variables.KEY_CITY_ID +"=? order by " + Variables.KEY_AREACODE + " ASC",
                    new String[]{"1=1"}
            );*/
            c = db.query(Variables.TBL_CITY,null,null,null,null,null,
                    Variables.KEY_AREACODE + " ASC");
            if (c.moveToFirst()) {
                do {
                    City item = new City(
                            c.getInt(0),
                            c.getString(1),
                            c.getInt(2),
                            c.getInt(3)
                    );
                    mList.add(item);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "getListCity ------------------------");
            e.printStackTrace();
        }
        return mList;
    }

    public int getPositionByAreaCode(String areaCode){
        int position = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            c = db.rawQuery(
                    "select * from "+ Variables.TBL_CITY +" where "
                            + Variables.KEY_AREACODE +"=?",
                    new String[]{areaCode}
            );
            if (c.moveToFirst()) {
                do {
                    position = c.getInt(c.getColumnIndex(Variables.KEY_ID_POSITON));
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "getPositionByAreaCode --------------------");
        }
        return position;
    }
    //TODO: City ----------------------- End/

    //TODO: District -------------------
    public ArrayList<District> getListDistrictByCidyID(String id){
        ArrayList<District> mList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            /*c = db.rawQuery(
                    "select * from "+ Variables.TBL_DISTRICT +" where "
                            + Variables.KEY_FK_CITY_ID +"=? ",
                    new String[]{id}
            );*/
            c = db.query(Variables.TBL_DISTRICT, null,
                    Variables.KEY_FK_CITY_ID + "=?", new String[]{id},
                    null, null, Variables.KEY_DISTRICT_VALUE + " ASC");
            if (c.moveToFirst()) {
                do {
                    District item = new District(
                            c.getInt(0),
                            c.getInt(1),
                            c.getString(2),
                            c.getInt(3)
                    );
                    mList.add(item);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "getListDistrictByCidyID ----------------");
            e.printStackTrace();
        }
        return mList;
    }

    //TODO: District ------------------- End/

    //TODO: Service -------------------
    public ArrayList<Service> getListService(){
        ArrayList<Service> mList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            c = getAllDataFromTable(Variables.TBL_SERVICE);
            if (c.moveToFirst()) {
                do {
                    Service item = new Service(
                            c.getInt(0),
                            c.getInt(1),
                            c.getString(2)
                    );
                    mList.add(item);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Không thể getdata getListService");
        }
        return mList;
    }
    //TODO: Service ------------------- End/

    //TODO: Bill Fail -------------------

    public ArrayList<BillFailSqlite> getListBillFail(){
        ArrayList<BillFailSqlite> mList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            c = db.query(Variables.TBL_BILLFAIL,null,null,null,null,null,null);
            if (c.moveToFirst()) {
                do {
                    BillFailSqlite item = new BillFailSqlite(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getString(9),
                            c.getString(10),
                            c.getString(11),
                            c.getString(12),
                            c.getString(13)
                    );
                    mList.add(item);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "getListCity ------------------------");
            e.printStackTrace();
        }
        return mList;
    }

    //TODO: Bill Fail -------------------End/

    //TODO: Send Bill ____________________________________________

    public boolean insertOrUpdateSendBill(Bill bill){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                values.put(Variables.SB_ID, bill.getBillID());
                values.put(Variables.SB_SENDER_PHONE, bill.getSenderNumberPhone());
                values.put(Variables.SB_SENDER_ADDRESS, bill.getSenderAddress());
                values.put(Variables.SB_SENDER_NAME, bill.getSenderName());
                values.put(Variables.SB_SENDER_NODE, bill.getSenderNode());
                values.put(Variables.SB_RECEIVER_PHONE, bill.getReceiverNumberPhone());
                values.put(Variables.SB_RECEIVER_ADDRESS, bill.getReceiverAddress());
                values.put(Variables.SB_RECEIVER_NAME, bill.getReceiverName());
                values.put(Variables.SB_RECEIVER_NODE, bill.getReceiverNode());
                values.put(Variables.SB_LENGTH, bill.getLength());
                values.put(Variables.SB_WIDTH, bill.getWidth());
                values.put(Variables.SB_HEIGHT, bill.getHeight());
                values.put(Variables.SB_WEIGHT, bill.getWeight());
                values.put(Variables.SB_COD, bill.getCod());
                values.put(Variables.SB_SERVICE, bill.getService());
                values.put(Variables.SB_STATUS, bill.getStatus());

                //Kiểm tra xem billID đã có trong bảng chưa.
                c = db.rawQuery(
                        "select * from "+ Variables.TBL_SENDER_BILL +" where "
                                + Variables.SB_ID +"=?",
                        new String[]{bill.getBillID()}
                );
                if (c.moveToFirst()) { //Đã có trong bảng thì update
                    db.update(Variables.TBL_SENDER_BILL,
                            values,
                            Variables.SB_ID + "=?",
                            new String[]{bill.getBillID()});
                } else { //Chưa có trong bảng thì insert
                    db.insert(Variables.TBL_SENDER_BILL, null, values);
                }
                c.close();
                return true;
            } catch (Exception e) {
                Log.d(TAG, "insertOrUpdateSendBill ------------------- 1");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "insertOrUpdateSendBill ------------------- 2");
            return false;
        }
    }

    public boolean updateStatusSendBill(Bill bill){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                boolean result = false;
                values.put(Variables.SB_STATUS, bill.getStatus());

                //Kiểm tra xem billID đã có trong bảng chưa.
                c = db.rawQuery(
                        "select * from "+ Variables.TBL_SENDER_BILL +" where "
                                + Variables.SB_ID +"=?",
                        new String[]{bill.getBillID()}
                );
                if (c.moveToFirst()) { //Đã có trong bảng thì update
                    db.update(Variables.TBL_SENDER_BILL,
                            values,
                            Variables.SB_ID + "=?",
                            new String[]{bill.getBillID()});
                    result = true;
                }
                c.close();
                return result;
            } catch (Exception e) {
                Log.d(TAG, "insertOrUpdateSendBill ------------------- 1");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "insertOrUpdateSendBill ------------------- 2");
            return false;
        }
    }

    public ArrayList<Bill> getListSenderBillByStatus(String status){
        ArrayList<Bill> mList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            c = db.query(Variables.TBL_SENDER_BILL,
                    null, Variables.SB_STATUS+"=?",
                    new String[]{status}, null, null, null);

            //c = getAllDataFromTable(Variables.TBL_SENDER_BILL);
            if (c.moveToFirst()) {
                do {
                    Bill item = new Bill(
                            c.getString(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getString(9),
                            c.getString(10),
                            c.getString(11),
                            c.getString(12),
                            c.getString(13),
                            c.getString(14),
                            c.getString(15),
                            c.getString(16),
                            c.getString(17),
                            c.getString(18)
                    );
                    mList.add(item);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Không thể getdata getListSenderBillByStatus");
            e.printStackTrace();
        }

        return mList;
    }

    public boolean deleteSenderBill(String billID){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(Variables.TBL_SENDER_BILL, Variables.SB_ID+"=?", new String[]{billID});
            return true;
        } catch (Exception e) {
            Log.e(TAG, "deleteSenderBill");
            e.printStackTrace();
            return false;
        }
    }

    //TODO: Send Bill ____________________________________________End/



    //TODO: Notification
    /*public void insertNotification(String title, String content, String url) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NOTIFY_TITLE, title);
            values.put(NOTIFY_CONTENT, content);
            values.put(NOTIFY_URL, url);
            values.put(NOTIFY_SEEN, 0);
            db.insert(TB_NOTIFICATION, null, values);
        } catch (Exception e) {
            Log.d(TAG, "Không thể insert Notification");
        }
    }

    public void updateNotification(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTIFY_SEEN, 1);
        try {
            db.update(TB_NOTIFICATION, values, "_id=?", new String[]{id});
        } catch (Exception e) {
            Log.d(TAG, "Không thể update Notification");
        }
    }

    public int countNotification(){
        int count = 0;
        Cursor c = getAllDataFromTable(TB_NOTIFICATION);
        try {
            if (c.moveToFirst()) {
                do {
                    count++;
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Không thể get Notification");
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
        Cursor c = getAllDataFromTable(TB_NOTIFICATION);
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
            Log.d(TAG, "Không thể getdata Notification");
        }
        return mList;
    }*/

    /*public void insertOrUpdateLogin(String userID, String ssToken, String phone) {
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
    }*/

    // Xóa dữ liệu một bảng
    public void DeleteDataOfTable(String tableName, String columnName, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (columnName != null && value != null)
                db.delete(tableName, columnName + "=?", new String[]{String.valueOf(value)});
            else {
                db.delete(tableName, null, null);
                //Bảng sqlite_sequence xét id tự tăng, xóa để về lại 0
                db.delete(TAG, "name=?", new String[]{tableName});
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public Cursor getAllDataFromTable(String tableName) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(tableName, null, null, null, null, null, null);
        } catch (Exception e) {
            Log.d(TAG, "getAllDataFromTable ------------------------");
            e.printStackTrace();
        }
        return c;
    }

    public boolean updateData4Table(String table, String keyID, String valueID,
                                    String[] field, String[] values) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        for (int i = 0; i < values.length; i++) {
            content.put(field[i], values[i]);
        }
        return db.update(table, content, keyID + " = '" + valueID + "'", null) > 0;
    }

    public long insertdata(String tblname, String[] keys, String[] values) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        for (int i = 0; i < values.length; i++) {
            content.put(keys[i], values[i]);
        }
        return db.insert(tblname, null, content);

    }

    public boolean deleteDataFromTable(String table, String field, String values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, field + " = " + "'" + values + "'", null) > 0;
    }

    public Cursor getAllDataFromTable(String tblname, String _id,
                                      String condition) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select " + _id + " _id,* from " + tblname + " where "
                + condition;
        return db.rawQuery(sql, null);
    }

    /*public List<Lonlat> readAllLocation() {
        List<Lonlat> mList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c = getAllDataFromTable(TB_LOCATION);
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
            Cursor c = getAllDataFromTable(TB_LOGIN);
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
    }*/
}
