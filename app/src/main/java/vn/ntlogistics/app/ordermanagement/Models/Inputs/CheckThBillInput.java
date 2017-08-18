package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

/**
 * Created by Zanty on 04/08/2017.
 */

public class CheckThBillInput extends BaseInput {
    private String bill;

    public CheckThBillInput(Context context) {
        super(context);
    }

    public CheckThBillInput(Context context, String bill) {
        super(context);
        this.bill = bill;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }
}
