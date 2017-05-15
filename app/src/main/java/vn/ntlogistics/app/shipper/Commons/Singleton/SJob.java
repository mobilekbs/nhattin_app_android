package vn.ntlogistics.app.shipper.Commons.Singleton;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Models.Enums.EServiceShip;
import vn.ntlogistics.app.shipper.Models.Outputs.Others.Job;
import vn.ntlogistics.app.shipper.R;

/**
 * Created by Zanty on 09/07/2016.
 */
public class SJob {

    private static SJob instance;

    public static SJob getInstance(){
        if(instance == null)
            instance = new SJob();
        return instance;
    }

    public enum TypeJob{
        LAY_HANG,
        GIAO_HANG,
        GIAO_LAI,
        TRA_HANG_1_PHAN,
        TRA_HANG_TOAN_PHAN,
        XE_OM,
        LAI_OTO,
        MUA_HANG
    }


    private List<Job> mList = new ArrayList<>();
    private List<Job> mListCommon = new ArrayList<>();

    public SJob() {
    }

    public static String getJobNameById(Context context, int jonType){
        switch (jonType){
            case 1:
                return context.getResources().getString(R.string.job_take_order);
            case 2:
                return context.getResources().getString(R.string.job_delivery_order);
            case 3:
                return context.getResources().getString(R.string.job_handover_order);
            case 4:
                return context.getResources().getString(R.string.job_return_order);
            case 5:
                return context.getResources().getString(R.string.job_return_total_order);
            case 6:
                return context.getResources().getString(R.string.job_ship_moto);
            case 7:
                return context.getResources().getString(R.string.job_ship_car);
            case 8:
                return context.getResources().getString(R.string.job_ship_k);
            default:
                return null;
        }
    }

    /**
     * So sánh status đã hoàn thành công việc.
     * @param status 1 2 8 9 10 14 là những status hoàn thàng công việc.
     * @return true là hoàn thành | fasle là chưa hoàn thành.
     */
    public static boolean compareStatusComplete(int status){
        switch (status){
            case 1:
            case 2:
            case 8:
            case 9:
            case 14:
            case 19:
            case 21:
            case 23:
            case 24:
            case 25:
            case 28:
                return true;
            default:
                return false;
        }
    }

    public List<Job> getList(Context context){
        List<Job> mList = new ArrayList<>();
        mList.add(new Job(0,context.getString(R.string.job_status_0)));
        mList.add(new Job(1,context.getString(R.string.job_status_1)));
        mList.add(new Job(2,context.getString(R.string.job_status_2)));
        mList.add(new Job(3,context.getString(R.string.job_status_3)));
        mList.add(new Job(4,context.getString(R.string.job_status_4)));
        mList.add(new Job(5,context.getString(R.string.job_status_5)));
        mList.add(new Job(6,context.getString(R.string.job_status_6)));
        mList.add(new Job(7,context.getString(R.string.job_status_7)));
        mList.add(new Job(8,context.getString(R.string.job_status_8)));
        mList.add(new Job(9,context.getString(R.string.job_status_9)));
        mList.add(new Job(10,context.getString(R.string.job_status_10)));
        mList.add(new Job(13,context.getString(R.string.job_status_13)));
        mList.add(new Job(14,context.getString(R.string.job_status_14)));
        mList.add(new Job(15,context.getString(R.string.job_status_15)));
        mList.add(new Job(16,context.getString(R.string.job_status_16)));
        mList.add(new Job(17,context.getString(R.string.job_status_17)));
        mList.add(new Job(18,context.getString(R.string.job_status_18)));
        mList.add(new Job(19,context.getString(R.string.job_status_19)));
        mList.add(new Job(20,context.getString(R.string.job_status_20)));
        mList.add(new Job(21,context.getString(R.string.job_status_21)));
        mList.add(new Job(22,context.getString(R.string.job_status_22)));
        mList.add(new Job(23,context.getString(R.string.job_status_23)));
        mList.add(new Job(24,context.getString(R.string.job_status_24)));
        mList.add(new Job(25,context.getString(R.string.job_status_25)));
        mList.add(new Job(26,context.getString(R.string.job_status_26)));
        mList.add(new Job(27,context.getString(R.string.job_status_27)));
        mList.add(new Job(28,context.getString(R.string.job_status_28)));
        mList.add(new Job(29,context.getString(R.string.job_status_29)));
        mList.add(new Job(30,context.getString(R.string.job_status_30)));
        mList.add(new Job(31,context.getString(R.string.job_status_31)));
        mList.add(new Job(32,context.getString(R.string.job_status_32)));
        mList.add(new Job(33,context.getString(R.string.job_status_33)));
        mList.add(new Job(34,context.getString(R.string.job_status_34)));
        mList.add(new Job(35,context.getString(R.string.job_status_35)));
        mList.add(new Job(36,context.getString(R.string.job_status_36)));
        mList.add(new Job(37,context.getString(R.string.job_status_37)));
        mList.add(new Job(38,context.getString(R.string.job_status_38)));
        mList.add(new Job(39,context.getString(R.string.job_status_39)));
        mList.add(new Job(40,context.getString(R.string.job_status_40)));
        mList.add(new Job(41,context.getString(R.string.job_status_41)));
        mList.add(new Job(42,context.getString(R.string.job_status_42)));
        mList.add(new Job(43,context.getString(R.string.job_status_43)));
        mList.add(new Job(44,context.getString(R.string.job_status_44)));
        mList.add(new Job(45,context.getString(R.string.job_status_45)));
        return mList;
    }

    public String getNameByIdStatus(Context context, int id){
        if(mListCommon == null || mListCommon.size() <= 0){
            mListCommon = getList(context);
        }
        for (Job j : mListCommon){
            if(j.getId() == id)
                return j.getName();
        }
        return null;
    }

    /**
     * So sánh xem công việc nào của dịch vụ nào.
     * Job type 6 7 là của 1 ship U.
     * Nhận biết đổi màu item trong MyOrder.
     * @param jobType
     * @return 1: 1SHIPU | 2: Book
     */
    public static EServiceShip compareServiceShip(int jobType){
        switch (jobType){
            case 6:
            case 7:
                return EServiceShip.SHIP_U;
            case 8:
                return EServiceShip.Ship_K;
            default:
                return EServiceShip.SHIP_CARGO;
        }
    }

    public List<Job> getListStatusJob(Context context, TypeJob type){
        switch (type){
            case LAY_HANG:
                createTakeOrder(context);
                break;
            case GIAO_HANG:
            case GIAO_LAI:
                createDeliveryOrder(context);
                break;
            case TRA_HANG_1_PHAN:
            case TRA_HANG_TOAN_PHAN:
                createReturnALlOrder(context);
                break;
            case XE_OM:
            case LAI_OTO:
                createShipU(context);
            case MUA_HANG:
                createShipK(context);
                break;
        }
        return mList;
    }

    public static int getIdStartByJobType(int id){
        switch (id){
            case 1: //lay hang
                return 37; //bat dau lay hang
            case 2: //giao hang
                return 38; //bat dau giao hang
            case 3: //giao lai
                return 38;
            case 4: //tra hang 1 phan
                return 39;
            case 5: //tra hang toan phan
                return 39;
            case 6: //xe om
                return 41;
            case 7: //lay xe
                return 41;
            default:
                return -1;
        }
    }

    public static int getIdSuccessByJobType(int id){
        switch (id){
            case 1: //lay hang
                return 1; //bat dau lay hang
            case 2: //giao hang
                return 8; //bat dau giao hang
            case 3: //giao lai
                return 8;
            case 4: //tra hang 1 phan
                return 14;
            case 5: //tra hang toan phan
                return 14;
            case 6: //xe om
                return 43;
            case 7: //lay xe
                return 43;
            case 8: //mua hang
                return 45;
            default:
                return -1;
        }
    }

    /**
     * Kiểm tra xem status đó có phải là đơn hàng mới không
     * @param id
     * @return
     */
    public static boolean getStatusNewOrder(int id){
        switch (id){
            case 0:
            case 18:
            case 20:
            case 22:
            case 40:
                return true;
            default:
                return false;
        }
    }

    public void createTakeOrder(Context context){
        mList.clear();
        mList.add(new Job(1,context.getString(R.string.job_status_1)));
        mList.add(new Job(2,context.getString(R.string.job_status_2)));
        mList.add(new Job(3,context.getString(R.string.job_status_3)));
        mList.add(new Job(4,context.getString(R.string.job_status_4)));
        mList.add(new Job(5,context.getString(R.string.job_status_5)));
        mList.add(new Job(6,context.getString(R.string.job_status_6)));
        mList.add(new Job(7,context.getString(R.string.job_status_7)));
    }

    public void createDeliveryOrder(Context context){
        mList.clear();
        mList.add(new Job(8,context.getString(R.string.job_status_8)));
        mList.add(new Job(9,context.getString(R.string.job_status_9)));
        mList.add(new Job(10,context.getString(R.string.job_status_10)));
        mList.add(new Job(3,context.getString(R.string.job_status_3)));
        mList.add(new Job(4,context.getString(R.string.job_status_4)));
        mList.add(new Job(13,context.getString(R.string.job_status_13)));
        mList.add(new Job(7,context.getString(R.string.job_status_7)));
    }
/*
    public void createHandoverOrder(){
        mList.clear();
        mList.add(new Job(8,"Giao thành công"));
        mList.add(new Job(9,"Khách từ chối nhận hàng"));
        mList.add(new Job(10,"Khách yêu cầu huỷ"));
        mList.add(new Job(4,"Không liên lạc được"));
        mList.add(new Job(5,"Địa chỉ không có thật"));
        mList.add(new Job(6,"Giao hàng trễ"));
        mList.add(new Job(7,"Lý do khác"));
    }

    public void createReturn1Order(){
        mList.clear();
        mList.add(new Job(1,"Trả hàng thành công"));
        mList.add(new Job(2,"Hàng mất mát"));
        mList.add(new Job(3,"Hàng hư hại"));
        mList.add(new Job(4,"Trả hàng trễ"));
        mList.add(new Job(5,"Lý do khác"));
    }*/

    public void createReturnALlOrder(Context context){
        mList.clear();
        mList.add(new Job(14,context.getString(R.string.job_status_14)));
        mList.add(new Job(15,context.getString(R.string.job_status_15)));
        mList.add(new Job(16,context.getString(R.string.job_status_16)));
        mList.add(new Job(17,context.getString(R.string.job_status_17)));
        mList.add(new Job(7,context.getString(R.string.job_status_7)));
    }

    public void createShipU(Context context){
        mList.clear();
        mList.add(new Job(43,context.getString(R.string.job_status_43)));
        mList.add(new Job(5,context.getString(R.string.job_status_5)));
        mList.add(new Job(3,context.getString(R.string.job_status_3)));
        mList.add(new Job(4,context.getString(R.string.job_status_4)));
        mList.add(new Job(10,context.getString(R.string.job_status_10)));
        mList.add(new Job(7,context.getString(R.string.job_status_7)));
    }
    public void createShipK(Context context){
        mList.clear();
        mList.add(new Job(45,context.getString(R.string.job_status_45)));
        mList.add(new Job(10,context.getString(R.string.job_status_10)));
    }

    public List<Job> createListJobMain(Context context){
        List<Job> mListJob = new ArrayList<>();
        mListJob.add(new Job(1,context.getResources().getString(R.string.job_take_order)));
        mListJob.add(new Job(2,context.getResources().getString(R.string.job_delivery_order)));
        mListJob.add(new Job(3,context.getResources().getString(R.string.job_handover_order)));
        mListJob.add(new Job(4,context.getResources().getString(R.string.job_return_order)));
        mListJob.add(new Job(5,context.getResources().getString(R.string.job_return_total_order)));
        mListJob.add(new Job(6,context.getResources().getString(R.string.job_ship_moto)));
        mListJob.add(new Job(7,context.getResources().getString(R.string.job_ship_car)));
        mListJob.add(new Job(8,context.getResources().getString(R.string.job_ship_k)));
        return mListJob;
    }
}
