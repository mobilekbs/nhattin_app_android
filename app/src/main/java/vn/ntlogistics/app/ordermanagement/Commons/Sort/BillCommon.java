package vn.ntlogistics.app.ordermanagement.Commons.Sort;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SJob;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Job;


/**
 * Created by Zanty on 25/07/2016.
 */
public class BillCommon {

    /**
     * Sắp xếp Order theo job.
     * Được gọi ở các hàm NewOrder, Uncompleted, Completed, CancelOrder
     * @param mList
     * @param job
     * @return
     */
    public static List<Bill> sortOrderByJob(List<Bill> mList, int job){
        List<Bill> mListResponce = new ArrayList<>();

        for(int i = 0; i < mList.size(); i++){
            int jobtype = mList.get(i).getJobType();
            if(jobtype == job)
                mListResponce.add(mList.get(i));
        }

        return mListResponce;
    }

    /**
     * Hàm lấy tên của công việc theo Id.
     * @param context
     * @param job là id công việc cần tìm.
     * @return về String tên công việc.
     */
    public static String getJobNameByJobType(Context context,int job){
        String jobName = null;
        List<Job> mListJob = new SJob().createListJobMain(context);
        for (int i = 0; i<mListJob.size(); i++){
            if(mListJob.get(i).getId()==job){
                jobName = mListJob.get(i).getName();
            }
        }
        return jobName;
    }

    /*public static List<Order> sortOrderByLocation1(Context context, List<Order> mList){
        *//**
         * Lấy vị trí hiện tại của thiết bị.
         *//*
        GetCurrentLocation getLocation = new GetCurrentLocation(context);
        Location location = new Location("");
        location.setLatitude(getLocation.getLatitude());
        location.setLongitude(getLocation.getLongitude());

        *//**
         * @param listDistance dùng để chứa khoảng cách giữa điểm hiện tại và điểm đầu
         *                     của đơn hàng đó.
         *//*
        List<Float> listDistance = new ArrayList<>();
        for (int i = 0; i< mList.size(); i++){
            //Tìm latlng theo địa chỉ của đơn hàng
            LatLng latLng = LocationCommon.searchLatLngByAddress(
                    (Activity) context,
                    mList.get(i).getOrder().getSourceAddress()
            );
            Location location1 = null;
            if(latLng != null) {
                location1 = new Location("");
                location1.setLatitude(latLng.latitude);
                location1.setLongitude(latLng.longitude);
            }
            //Lấy khoảng cách giữa 2 điểm
            listDistance.add(location.distanceTo(location1));
            Log.d("LOCATION",location.distanceTo(location1)+"");
        }
        *//**
         * Sắp xếp theo khoảng cách giữa vị trí hiện tại của mình và điểm lấy hàng.
         *//*
        for (int i = 0; i < listDistance.size()-1; i++){
            for (int j = i+1; j <listDistance.size(); j++) {
                if (listDistance.get(i) != null && listDistance.get(j) != null) {
                    if(listDistance.get(i) > listDistance.get(j)){
                        float temp = listDistance.get(i);
                        listDistance.set(i, listDistance.get(j));
                        listDistance.set(j, temp);
                        //Thay đổi list Order
                        Order tempOrder = mList.get(i);
                        mList.set(i, mList.get(j));
                        mList.set(j, tempOrder);
                    }
                } else if(listDistance.get(i) == null && listDistance.get(j) != null){
                    listDistance.set(i, listDistance.get(j));
                    listDistance.set(j, null);
                    //Thay đổi list Order
                    mList.set(i, mList.get(j));
                    mList.set(j, null);
                }
            }
        }

        return mList;
    }*/



}
