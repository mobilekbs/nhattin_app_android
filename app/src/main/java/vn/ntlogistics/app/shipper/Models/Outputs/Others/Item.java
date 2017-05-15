package vn.ntlogistics.app.shipper.Models.Outputs.Others;


import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;

/**
 * Created by Zanty on 09/06/2016.
 */
public class Item {
    private Order order;
    private boolean check;
    private float distance;

    public Item() {
    }

    public Item(Order order, boolean check) {
        this.order = order;
        this.check = check;
    }

    public Item(Order order, boolean check, float distance) {
        this.order = order;
        this.check = check;
        this.distance = distance;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
