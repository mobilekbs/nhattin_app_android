package vn.ntlogistics.app.ordermanagement.Commons.Sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Zanty on 08/12/2017.
 */

public class BaseSQLite extends SQLiteOpenHelper {

    public static final String TAG = "SqliteManager";
    private Context context;
    private SQLiteDatabase db;

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
            + Variables.SB_SHIP + " text,"
            + Variables.SB_SERVICE + " text,"
            + Variables.SB_STATUS + " text,"
            + Variables.SB_SEND_DATE + " text,"
            + Variables.SB_PROVINCE_ID + " text,"
            + Variables.SB_OTP_CODE + " text,"
            + Variables.SB_BPBILL_ID + " text"
            + ");";

    public BaseSQLite(Context context) {
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
    }

    /*private boolean checkDatabase() {
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
    }*/

    // Check xem DB Exists hay không
    public boolean checkDatabase() {
        String myPath = getDBPath(context) + Variables.DATABASE_NAME;
        File dbFile = new File(myPath);
        return dbFile.exists();
    }

    // ----------Kiểm tra có data chưa, chưa có thì tạo
    public void createDatabase() throws IOException {
        try {
            boolean dbExist = checkDatabase(); // kiem tra db
            if (dbExist) {
            } else {
                this.getWritableDatabase();
                this.close();
                try {
                    copyDataBase();
                    Log.d("Success", "DB Created");
                } catch (IOException mIOException) {
                    throw new Error("Fail");
                }
                //copyDataBase();

                /**
                 *  Sau khi copy db từ asset thì tạo thêm bảng sendbill
                 */
                this.getReadableDatabase();
                try {
                    //Đọc db
                    /*db = SQLiteDatabase
                            .openOrCreateDatabase(
                                    getDBPath(context) + Variables.DATABASE_NAME,
                                    null);*/
                    openDatabase();
                    //version 3
                    db.execSQL(tb_sender_bill);
                    close();
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

        /*File fDB = new File(getDBPath(context));
        if(!fDB.exists())
            fDB.mkdirs();*/

        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    // Open DB
    public boolean openDatabase() throws SQLException {
        try {
            String myPath = getDBPath(context) + Variables.DATABASE_NAME;
            if(checkDatabase())
                db = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.CREATE_IF_NECESSARY);
            return db != null;
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    // Close DB
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }
}
