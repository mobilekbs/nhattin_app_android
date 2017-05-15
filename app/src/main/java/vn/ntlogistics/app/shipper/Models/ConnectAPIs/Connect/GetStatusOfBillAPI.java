package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.BLDetail;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.StatusOrder;

/**
 * Created by Zanty on 31/07/2016.
 */
public class GetStatusOfBillAPI extends BaseConnectAPI {
    StatusOrder statusOrder;
    ArrayList<String> listBLCode;
    String shippingCode, orderKCode;
    List<BLDetail> mListBLDetail;
    Bundle bundle;
    /**
     * Xem hàm gọi là update hay edit
     * Nếu true là gọi từ update, false là gọi từ edit
     */
    //boolean update = false;
    int jobtype;
    /**
     * Vị trí của phần tử trong listBLCode mà ta muốn update
     * Nếu position = -1 thì là update tất cả BL trong Order
     */
    int position;
    public GetStatusOfBillAPI(Context context, String data, String shippingCode, ArrayList<String> listBLCode, int jobtype, int position, List<BLDetail> mListBLDetail) {
        super(context, ConstantURLs.URL_GET_STATUS_OF_BILL, data, false, Method.POST);
        statusOrder = new StatusOrder();
        this.shippingCode=shippingCode;
        this.listBLCode=listBLCode;
        this.jobtype=jobtype;
        this.position = position;
        //this.update = update;
        this.mListBLDetail = mListBLDetail;
        this.bundle = new Bundle();
    }
    public GetStatusOfBillAPI(Context context, String data, String shippingCode,ArrayList<String> listBLCode, int jobtype, String orderKCode) {
        super(context, ConstantURLs.URL_GET_STATUS_OF_BILL, data, false, Method.POST);
        statusOrder = new StatusOrder();
        this.shippingCode = shippingCode;
        this.listBLCode = listBLCode;
        this.orderKCode = orderKCode;
        this.jobtype = jobtype;
        this.bundle = new Bundle();
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void doInBG() {

    }

    @Override
    public void onPost(JsonObject result) {
        /*if (result.get("errorMessage").isJsonNull()) {
            statusOrder = new Gson().fromJson(
                    result,
                    StatusOrder.class
            );
            bundle.putSerializable("blDetail", (ArrayList<BLDetail>) mListBLDetail);
            bundle.putSerializable("statusOrder", statusOrder);
            *//*if(jobtype == Common.JOB_SHIP_K) {
                bundle.putSerializable("listBLCode", listBLCode);
                bundle.putSerializable("orderKCode", orderKCode);
            }*//*

            Intent i = new Intent(context, SC020201Activity.class);
            i.putExtra("Jobtype", jobtype);
            i.putExtra("position", position);
            i.putExtra("shippingCode",shippingCode);
            i.putExtras(bundle);
            ((Activity)context).startActivityForResult(i, 220);
            ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else {
            bundle.putSerializable("blDetail", (ArrayList<BLDetail>) mListBLDetail);
            *//*if(jobtype == Common.JOB_SHIP_K) {
                bundle.putSerializable("listBLCode", listBLCode);
                bundle.putSerializable("orderKCode", orderKCode);
            }*//*
            Intent i = new Intent(context, SC020201Activity.class);

            i.putExtra("Jobtype", jobtype);
            i.putExtra("position", position);
            i.putExtra("shippingCode",shippingCode);
            i.putExtras(bundle);
            ((Activity)context).startActivityForResult(i, 220);
        }*/
    }
}
