package vn.ntlogistics.app.ordermanagement.Commons.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.BillFail.BillFailSqlite;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.City;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.District;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.Service;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Login.User;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ConfirmBPBillInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;

import static vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables.KEY_AREACODE;


/**
 * Created by Zanty on 27/06/2016.
 */
public class SQLiteManager {

    protected static final String TAG = "SqliteManager";

    private Context context;
    private SQLiteDatabase db;
    private BaseSQLite mDbHelper;

    public SQLiteManager(Context context) {
        this.context = context;
        mDbHelper = new BaseSQLite(context);
        createDatabase();
    }

    public SQLiteManager createDatabase() throws SQLException {
        try {
            mDbHelper.createDatabase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public SQLiteManager open() throws SQLException {
        try {
            mDbHelper.openDatabase();
            mDbHelper.close();
            db = mDbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    //TODO: My Staff -------------------
    public boolean inserOrUpdatetUser(User user) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                values.put(Variables.KEY_STAFF_ID, user.getIdStaff());
                values.put(Variables.KEY_PUBLIC_KEY, user.getPublickey());
                values.put(Variables.KEY_LOCAL_KEY, user.getLocalkey());
                values.put(Variables.KEY_VALUE_STAFF, user.getValue_staff());
                values.put(Variables.KEY_MYBANK, user.getMyBank());

                c = db.rawQuery(
                        "select * from "+ Variables.TBL_STAFF +" where "
                                + Variables.KEY_PUBLIC_KEY + "=?",
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
            ArrayList<City> mListCity = SSQLite.getInstance(context).getListCity();
            int areaCodeCity = 50;
            try {
                areaCodeCity = Integer.parseInt(areaCode);
            } catch (NumberFormatException e) {
            }
            int positionCity = -1;
            for (City city : mListCity){
                positionCity++;
                if (city.getAreacode() == areaCodeCity)
                    break;
            }
            return positionCity;
        }
        return 0;
    }

    //TODO: My Staff ------------------- End/

    //TODO: City -----------------------
    public ArrayList<City> getListCity(){
        ArrayList<City> mList = new ArrayList<>();
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            /*c = db.rawQuery(
                    "select * from "+ Variables.TBL_CITY +" where "
                            + Variables.KEY_CITY_ID +"=? order by " + Variables.KEY_AREACODE + " ASC",
                    new String[]{"1=1"}
            );*/
            c = db.query(Variables.TBL_CITY,null,
                    Variables.KEY_AREACODE + "!=?",
                    new String[]{"0"},null,null,
                    Variables.KEY_CITY_ID + " ASC");
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
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            c = db.rawQuery(
                    "select * from "+ Variables.TBL_CITY +" where "
                            + KEY_AREACODE +"=?",
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

        //SQLiteDatabase db = this.getWritableDatabase();
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
        //SQLiteDatabase db = this.getWritableDatabase();
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
        //SQLiteDatabase db = this.getWritableDatabase();
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
            //SQLiteDatabase db = this.getWritableDatabase();
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
                values.put(Variables.SB_COD, bill.getCodAmount());
                values.put(Variables.SB_SHIP, bill.getShipperAmount());
                values.put(Variables.SB_SERVICE, bill.getService());
                values.put(Variables.SB_STATUS, bill.getStatus());
                values.put(Variables.SB_SEND_DATE, bill.getSendDate());
                values.put(Variables.SB_PROVINCE_ID, bill.getSenderProvinceID());
                values.put(Variables.SB_OTP_CODE, bill.getOtpCode());
                values.put(Variables.SB_BPBILL_ID, bill.getEmsBpbillID());

                //Kiểm tra xem billID đã có trong bảng chưa.
                c = db.rawQuery(
                        "select * from "+ Variables.TBL_SENDER_BILL +" where "
                                + Variables.SB_ID + "=?",
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

    public boolean updateStatusSendBill(String billId, int status){
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                boolean result = false;
                values.put(Variables.SB_STATUS, status);

                //Kiểm tra xem billID đã có trong bảng chưa.
                c = db.rawQuery(
                        "select * from "+ Variables.TBL_SENDER_BILL +" where "
                                + Variables.SB_ID +"=?",
                        new String[]{billId}
                );
                if (c.moveToFirst()) { //Đã có trong bảng thì update
                    db.update(Variables.TBL_SENDER_BILL,
                            values,
                            Variables.SB_ID + "=?",
                            new String[]{billId});
                    result = true;
                }
                c.close();
                return result;
            } catch (Exception e) {
                Log.d(TAG, "updateStatusSendBill ------------------- 1");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "updateStatusSendBill ------------------- 2");
            return false;
        }
    }

    public boolean updateStatusSendBill(Bill bill){
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
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

        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            c = db.query(Variables.TBL_SENDER_BILL,
                    null, Variables.SB_STATUS+"=?",
                    new String[]{status}, null, null, null);

            //c = getAllDataFromTable(Variables.TBL_SENDER_BILL);
            if (c.moveToFirst()) {
                do {
                    Log.e("updatebi","updatebi"+c.getString(1)+" "+c.getString(21));
                    Bill item = new Bill(
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
                            c.getString(18),
                            c.getString(19),
                            c.getString(20),
                            c.getString(21)
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
            //SQLiteDatabase db = this.getWritableDatabase();
            db.delete(Variables.TBL_SENDER_BILL, Variables.SB_ID+"=?", new String[]{billID});
            return true;
        } catch (Exception e) {
            Log.e(TAG, "deleteSenderBill");
            e.printStackTrace();
            return false;
        }
    }

    //TODO: Send Bill ____________________________________________End/

    //region TODO: Confirm DO_____________________________________
    public boolean insertOrUpdateConfirmDO(ConfirmBPBillInput data){
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                values.put(Variables.KEY_BILL, data.getDoCode());
                values.put(Variables.KEY_SL, data.getItemQty());
                values.put(Variables.KEY_TL, data.getWeight());
                values.put(Variables.KEY_TLQD, data.getDimensionWeight());
                values.put(Variables.KEY_SOKIENDO, data.getPackNo());
                values.put(Variables.KEY_ISDO, "Y");

                //Kiểm tra xem billID đã có trong bảng chưa.
                c = db.rawQuery(
                        "select * from "+ Variables.TBL_BILLFAIL +" where "
                                + Variables.KEY_BILL +"=?",
                        new String[]{data.getDoCode()}
                );
                if (c.moveToFirst()) { //Đã có trong bảng thì update
                    db.update(Variables.TBL_BILLFAIL,
                            values,
                            Variables.KEY_BILL + "=?",
                            new String[]{data.getDoCode()});
                } else { //Chưa có trong bảng thì insert
                    db.insert(Variables.TBL_BILLFAIL, null, values);
                }
                c.close();
                return true;
            } catch (Exception e) {
                Log.d(TAG, "insertOrUpdateConfirmDO ------------------- 1");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "insertOrUpdateConfirmDO ------------------- 2");
            return false;
        }
    }

    public boolean deleteConfirmBill(String DOCode){
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            db.delete(Variables.TBL_BILLFAIL, Variables.KEY_BILL+"=?", new String[]{DOCode});
            return true;
        } catch (Exception e) {
            Log.e(TAG, "deleteSenderBill");
            e.printStackTrace();
            return false;
        }
    }
    //endregion TODO: Confirm DO__________________________________End/


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
        //SQLiteDatabase db = this.getWritableDatabase();
        try {
            if (columnName != null && value != null)
                db.delete(tableName, columnName + "=?", new String[]{String.valueOf(value)});
            else {
                db.delete(tableName, null, null);
                //Bảng sqlite_sequence xét id tự tăng, xóa để về lại 0
              //  db.delete(TAG, "name=?", new String[]{tableName});

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy dữ liệu từ sql trả về cursor
    public Cursor GetDataFromTable(String tableName, String arrColumn[], String whereClause,
                                   String arrGiaTriWhereClause[], String groupBy, String having, String orderBy) {
        //SQLiteDatabase db = this.getReadableDatabase();
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

        //SQLiteDatabase db = this.getReadableDatabase();
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
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        for (int i = 0; i < values.length; i++) {
            content.put(field[i], values[i]);
        }
        return db.update(table, content, keyID + " = '" + valueID + "'", null) > 0;
    }

    public long insertdata(String tblname, String[] keys, String[] values) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        for (int i = 0; i < values.length; i++) {
            content.put(keys[i], values[i]);
        }
        return db.insert(tblname, null, content);

    }

    public boolean deleteDataFromTable(String table, String field, String values) {
        //SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table, field + " = " + "'" + values + "'", null) > 0;
    }

    public Cursor getAllDataFromTable(String tblname, String _id,
                                      String condition) {
        //SQLiteDatabase db = this.getWritableDatabase();
        String sql = "select " + _id + " _id,* from " + tblname + " where "
                + condition;
        return db.rawQuery(sql, null);
    }


    public boolean insertOrUpdateCongoNoti(String type, String msg, String from, String date, String time){
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                values.put(Variables.CONGO_TYPE, type);
                values.put(Variables.CONGO_MSG,  msg);
                values.put(Variables.CONGO_FROM, from);
                values.put(Variables.CONGO_DATE, date);
                values.put(Variables.CONGO_TIME, time);

                db.insert(Variables.TBL_CONGO, null, values);

                c.close();
                return true;
            } catch (Exception e) {
                Log.d(TAG, "insertCongo ------------------- 1");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "insertCongo ------------------- 2");
            return false;
        }
    }

    public boolean insertOrUpdateThongNoti(String type, String msg, String from, String date, String time){
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor c = null;
            try {
                values.put(Variables.THONG_TYPE, type);
                values.put(Variables.THONG_MSG,  msg);
                values.put(Variables.THONG_FROM, from);
                values.put(Variables.THONG_DATE, date);
                values.put(Variables.THONG_TIME, time);

                db.insert(Variables.TBL_THONG, null, values);

                c.close();
                return true;
            } catch (Exception e) {
                Log.d(TAG, "insertThong ------------------- 1");
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "insertThong ------------------- 2");
            return false;
        }
    }

    public ArrayList<HashMap<String, String>> getListCongThong(String type){
        ArrayList<HashMap<String, String>> mList = new ArrayList<>();
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        try {
            if(type.equalsIgnoreCase("2")) {
                c = getAllDataFromTable(Variables.TBL_CONGO);
            } else if(type.equalsIgnoreCase("3")) {
                c = getAllDataFromTable(Variables.TBL_THONG);
            }
            if (c.moveToFirst()) {
                Log.e("columns","columns"+c.getColumnName(0));
                do {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("type", c.getString(1));
                    hashMap.put("msg", c.getString(2));
                    hashMap.put("from", c.getString(3));
                    hashMap.put("date", c.getString(4));
                    hashMap.put("time", c.getString(5));

                    Log.e("columns","columns"+c.getString(0));

                    mList.add(hashMap);

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Không thể getdata getListService");
        }
        return mList;
    }


    public boolean deleteCongThong(int id, String type){
        try {
            int deletid = (id+1);
            //SQLiteDatabase db = this.getWritableDatabase();
            Log.e("valueclick","valueclick"+id+" "+type);
            if(type.equalsIgnoreCase("2")) {
                db.delete(Variables.TBL_CONGO, "_id=?", new String[]{String.valueOf(deletid)});
            } else  if(type.equalsIgnoreCase("3")) {
                db.delete(Variables.TBL_THONG, "_id=?", new String[]{String.valueOf(deletid)});
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "deleteSenderBill");
            e.printStackTrace();
            return false;
        }
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

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
