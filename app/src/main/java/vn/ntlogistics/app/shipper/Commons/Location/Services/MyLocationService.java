package vn.ntlogistics.app.shipper.Commons.Location.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyLocationService extends Service {
    /*CurrentLocation currentLocation; //Lấy vị trí hiện tại
    WriteLocation               writeLocation; //Lưu vị trí hiện tại lên server Firebase
    Lonlat lonlatUser; //Lưu vị trí của user, 2s sẽ update mới
    CountDownTimer              mTimer; //Bộ đếm thời gian
    List<Lonlat>                mList;
    SqliteManager sqliteManager;*/

    public MyLocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*sqliteManager = new SqliteManager(this);
        mList = new ArrayList<>();
        lonlatUser = new Lonlat();
        writeLocation = new WriteLocation();

        mTimer = new CountDownTimer(Constants.ALLTIME_GET_LOCATION, Constants.TIME_GET_LOCATION) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                try {
                    //currentLocation = new GetCurrentLocation(MyLocationService.this);
                    currentLocation = new CurrentLocation(MyLocationService.this);
                    if (!Utils.hasConnection(getApplicationContext())) {
                        if(currentLocation.getLongitude() != 0 && currentLocation.getLatitude() != 0) {
                            sqliteManager.insertLocation(
                                    String.valueOf(currentLocation.getLongitude()),
                                    String.valueOf(currentLocation.getLatitude()),
                                    Calendar.getInstance().getTimeInMillis()+""
                            );
                        }
                    } else {
                        mList.clear();
                        try {
                            //Đọc dữ liệu từ bảng location ra
                            mList.addAll(sqliteManager.readAllLocation());
                            //Xóa dữ liệu đã đọc
                            sqliteManager.DeleteDataOfTable(SqliteManager.TB_LOCATION,null,null);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (mList.size() == 0) {
                            if (SCurrentUser.getCurrentUser(getApplicationContext()) != null && currentLocation.getLongitude() != 0 && currentLocation.getLatitude() != 0) {
                                lonlatUser.setLatitude(String.valueOf(currentLocation.getLatitude()));
                                lonlatUser.setLongitude(String.valueOf(currentLocation.getLongitude()));
                                lonlatUser.setTime(Calendar.getInstance().getTimeInMillis());
                                writeLocation.pushLocationIntoUser(
                                        SCurrentUser.getCurrentUser(getApplicationContext()).getUserID(),
                                        lonlatUser
                                );
                            }
                        }
                        else {
                            Log.d("MyLocationService","Push location to firebase");
                            if (SCurrentUser.getCurrentUser(getApplicationContext()) != null) {
                                for (int i = 0; i < mList.size(); i++) {
                                    lonlatUser.setLatitude(mList.get(i).getLatitude());
                                    lonlatUser.setLongitude(mList.get(i).getLongitude());
                                    lonlatUser.setTime(mList.get(i).getTime());
                                    writeLocation.pushLocationIntoUser(
                                            SCurrentUser.getCurrentUser(getApplicationContext()).getUserID(),
                                            lonlatUser
                                    );
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mTimer.start();
            }
        };*/
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Service này là loại không ràng buộc (Un bounded)
        // Vì vậy method này ko bao giờ được gọi.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //mTimer.start();
        /**
         *
         * START_STICKY nói với các hệ điều hành để tạo lại các dịch vụ
         * sau khi đã có đủ bộ nhớ và gọi onStartCommand() một lần nữa với một Intent null
         *
         * START_NOT_STICKY nói với các hệ điều hành
         * để không bận tâm tái tạo các dịch vụ một lần nữa.
         *
         * START_REDELIVER_INTENT giá trị thứ ba mà nói với các hệ điều hành
         * để tạo lại các dịch vụ và truyền một Intent tương tự cho onStartCommand().
         *
         * */
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mTimer.cancel();
    }
}
