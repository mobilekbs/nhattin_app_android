package vn.ntlogistics.app.shipper.Commons.Singleton;


import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Models.Outputs.Others.Vehicle;

/**
 * Created by Zanty on 21/07/2016.
 */
public class SVehicle {
    private static List<Vehicle> mList;

    public SVehicle() {
    }

    public static List<Vehicle> getListVehicle() {
        if(mList==null)
            createListVehicle();
        return mList;
    }

    private static void createListVehicle() {
        mList = new ArrayList<>();
        mList.add(new Vehicle("1","Xe máy"));
        mList.add(new Vehicle("2","Xe 3 bánh"));
        mList.add(new Vehicle("3","Xe van"));
        mList.add(new Vehicle("4","Xe tải 500kg"));
        mList.add(new Vehicle("5","Xe tải 1000kg"));
        mList.add(new Vehicle("6","Xe tải 2500kg"));
        mList.add(new Vehicle("7","Xe tải 5000kg"));
        mList.add(new Vehicle("8","Xe tải 10 tons"));
        mList.add(new Vehicle("9","Xe tải 20 tons"));
        mList.add(new Vehicle("10","Xe container 40'"));
    }
}
