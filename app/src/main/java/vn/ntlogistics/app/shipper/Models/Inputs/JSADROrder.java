package vn.ntlogistics.app.shipper.Models.Inputs;

import android.content.Context;

import java.util.List;

/**
 * Created by Zanty on 21/07/2016.
 */
public class JSADROrder extends JSBase {
    private List<String> data; //lst BL
    private List<String> shippingCode;
    private List<ListFee> lstFee;


    public JSADROrder(Context context) {
        super(context);
    }

    public List<ListFee> getLstFee() {
        return lstFee;
    }

    public void setLstFee(List<ListFee> lstFee) {
        this.lstFee = lstFee;
    }

    public List<String> getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(List<String> shippingCode) {
        this.shippingCode = shippingCode;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }


}
