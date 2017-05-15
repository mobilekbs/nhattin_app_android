package vn.ntlogistics.app.shipper.Models.Outputs.Others;

import java.io.Serializable;

import vn.ntlogistics.app.shipper.Models.Outputs.Order.BLDetail;

/**
 * Created by minhtan2908 on 12/3/16.
 */

public class FinishOrder implements Serializable {
    private BLDetail blDetail;
    private boolean check;
    private int position;

    public FinishOrder() {
    }

    public FinishOrder(BLDetail blDetail, boolean check, int position) {
        this.blDetail = blDetail;
        this.check = check;
        this.position = position;
    }

    public FinishOrder(BLDetail blDetail) {
        this.blDetail = blDetail;
        this.check = false;
    }

    public BLDetail getBlDetail() {
        return blDetail;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBlDetail(BLDetail blDetail) {
        this.blDetail = blDetail;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
