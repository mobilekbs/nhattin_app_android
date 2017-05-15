package vn.ntlogistics.app.shipper.Models.ConnectAPIs.Connect;

import android.content.Context;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.shipper.Models.ConstantURLs;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.OrderDetail;

/**
 * Created by (TB0) on 25/06/2016.
 */
public class OrderDetailAPI extends BaseConnectAPI {
    OrderDetail mOrder;
    Order order;
    int status, position;
    boolean reload;
    BaseFragment fragment;

    public OrderDetailAPI(Context context, String data, Order order, int status, int position, boolean reload) {
        super(context, ConstantURLs.URL_ORDER_DETAIL, data, false, Method.POST);
        this.order = order;
        this.status = status;
        this.position = position;
        this.reload = reload;
    }

    public OrderDetailAPI(Context context, String data, Order order, int status, int position, boolean reload, BaseFragment fragment) {
        super(context, ConstantURLs.URL_ORDER_DETAIL, data, false, Method.POST);
        this.order = order;
        this.status = status;
        this.position = position;
        this.reload = reload;
        this.fragment = fragment;
    }

    @Override
    public void onPost(JsonObject result) {
        /*if (result.get("errorMessage").isJsonNull()) {
            JsonArray array = result.get("data").getAsJsonArray();
            if (array.size() > 0) {
                mOrder = new Gson().fromJson(result, OrderDetail.class);
                //SOrderDetail.setOurInstance(mOrder);
                if(fragment != null){ //accept order
                    ((NewOrderFragment) fragment).callAcceptOrderAPI(
                            order,mOrder,position,order.getOrderType());
                }
                else if (!reload) {
                    Bundle b = new Bundle();
                    b.putSerializable("orderDetail", mOrder);
                    b.putSerializable("order",order);
                    Intent i;
                    if(order.getOrderType() ==2){
                        i = new Intent(context, OrderDetailHubActivity.class);
                    }
                    else {
                        i = new Intent(context, SC020102Activity.class);
                    }
                    i.putExtra("statusOrder", status);
                    i.putExtra("idItem", position);
                    i.putExtra("categoryTitle", order.getCategoryTitle());
                    i.putExtras(b);
                    ((Activity)context).startActivity(i);
                    ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    //Reload activity
                    Intent i = ((Activity)context).getIntent();
                    Bundle b = new Bundle();
                    b.putSerializable("orderDetail", mOrder);
                    b.putSerializable("order",order);
                    i.putExtra("statusOrder", status);
                    i.putExtra("idItem", position);
                    i.putExtra("categoryTitle", order.getCategoryTitle());
                    i.putExtras(b);

                    ((Activity)context).finish();
                    ((Activity)context).startActivity(i);
                }
            } else {
                if (reload) {
                    //Reload activity
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else
                    Message.showToast(context,
                            context.getResources().getString(R.string.toast_order_null));
            }
        } else {
            Message.showToast(context,
                    result.get("errorMessage").getAsString());
        }*/
    }

    /**
     * Hàm chạy dù json trả về null
     */
    @Override
    public void onPostMain(String result) {
        //SC020102Activity.oneClick = true;
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

}
