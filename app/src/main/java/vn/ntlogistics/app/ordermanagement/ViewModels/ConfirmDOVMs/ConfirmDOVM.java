package vn.ntlogistics.app.ordermanagement.ViewModels.ConfirmDOVMs;

import android.app.Activity;
import android.content.Intent;
import android.databinding.Bindable;
import android.os.Bundle;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.Gson;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSqlite;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.CheckThBillAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.ConfirmBPBillAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.ConfirmBPBillInput;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ConfirmDOActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ZXingScannerActivity;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityConfirmDoBinding;

/**
 * Created by Zanty on 24/07/2017.
 */

public class ConfirmDOVM extends ViewModel {
    private ConfirmDOActivity           activity;
    private ActivityConfirmDoBinding    binding;

    private ConfirmBPBillInput          item;
    private int                         position;

    private boolean                     isInvalid = false, isComfirm = false;
    private String                      billScan;

    public ConfirmDOVM(ConfirmDOActivity activity, ActivityConfirmDoBinding binding) {
        this.activity = activity;
        this.binding = binding;
        getDataIntent();

        if(item.getDoCode() != null){
            binding.etDOCode.setHint(activity.getString(R.string.sender_code_hint));
        }
    }

    private void getDataIntent(){
        Bundle b = activity.getIntent().getExtras();

        try {
            item = (ConfirmBPBillInput) b.getSerializable("item");
            position = b.getInt("position");
        } catch (Exception e) {
        }

        if(item == null)
            item = new ConfirmBPBillInput(activity);
    }

    public boolean myValidate() {
        String billDO = binding.etDOCode.getText().toString();
        String weight = binding.etWeight.getText().toString();
        String quantity = binding.etQuantity.getText().toString();
        String packNo = binding.etPackageNo.getText().toString();

        if (billDO.length() == 0 && item.getDoCode() == null) {
            Message.makeToastWarning(activity,
                    activity.getString(R.string.error_do_number_null));
            binding.etDOCode.requestFocus();
            return false;
        } else if (packNo.length() == 0 && item.getPackNo() == 0) {
                Message.makeToastWarning(activity,
                        activity.getString(R.string.error_number_package_null));
                binding.etPackageNo.requestFocus();
                return false;
        } else if (weight.length() == 0 && item.getWeight() == 0) {
            Message.makeToastWarning(activity,
                    activity.getString(R.string.error_weight_null));
            binding.etWeight.requestFocus();
            return false;
        } else if (quantity.length() == 0 && item.getItemQty() == 0) {
            Message.makeToastWarning(activity,
                    activity.getString(R.string.error_number_null));
            binding.etQuantity.requestFocus();
            return false;
        }
        return true;
    }

    private void refreshLayout(){
        binding.etDOCode.setText("");
        binding.etWeight.setText("");
        binding.etDimensionWeight.setText("");
        binding.etQuantity.setText("");
        binding.etPackageNo.setText("");
    }

    public void checkDOCodeInvalid(String bill, boolean isConfirm){
        /**
         * Kiểm tra khi item tồn tại
         */
        if(item.getDoCode() != null && item.getDoCode().length() > 0){
            if(bill != null && bill.length() > 0) {
                if(!bill.equalsIgnoreCase(item.getDoCode())){
                    isInvalid = false;
                    this.isComfirm = isConfirm;
                    callCheckThBillAPI(bill);
                }
                else {
                    isInvalid = true;
                    if(isConfirm){
                        callAPIConfirmBPBill();
                    }
                }
            }
        }
    }

    //region TODO: On Click ___________________

    public void onClickSend(View v){
        Commons.setEnabledButton(v);
        if (myValidate()) {

            if(item.getDoCode() == null || item.getDoCode().length() == 0){
                //item không tồn tại, confirm do code bình thường
                callAPIConfirmBPBill();
            }
            else if(isInvalid && billScan.equalsIgnoreCase(binding.etDOCode.getText().toString())){
                //item tồn tại và đã check do code thành công.
                callAPIConfirmBPBill();
            }
            else {
                // item tồn tại và chưa chắc do code or check rồi nhưng đã sửa thì check lại

                checkDOCodeInvalid(binding.etDOCode.getText().toString(), true);
            }
        }
    }

    public void onClikScan(View v){
        Commons.setEnabledButton(v);
        /*Intent intent = new Intent(activity, ScanMSActivity.class);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE_SCAN);*/

        ZXingScannerActivity.openScanner(activity);
    }

    //endregion TODO: On Click ________________End/

    //region TODO: Call API_________________________
    public void callAPIConfirmBPBill(){
        ConfirmBPBillInput input = null;
        try {
            input = item.clone();
        } catch (CloneNotSupportedException e) {
            input = new ConfirmBPBillInput(activity);
        }
        if(input.getDoCode() == null) {
            input.setDoCode(binding.etDOCode.getText().toString());
        }
        else {
            input.setBill(billScan);
        }
        double dimensionWeight = 0;
        try {
            dimensionWeight = Double.parseDouble(binding.etDimensionWeight.getText().toString());
        } catch (NumberFormatException e) {
        }
        if(input.getDimensionWeight() != dimensionWeight)
            input.setDimensionWeight(dimensionWeight);

        double weight = 0;
        try {
            String strWeight = binding.etWeight.getText().toString();
            weight = Double.parseDouble(strWeight);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if(input.getWeight() != weight)
            input.setWeight(weight);

        long itemQty = 0;
        try {
            itemQty = Long.parseLong(binding.etQuantity.getText().toString());
        } catch (NumberFormatException e) {
        }
        if(input.getItemQty() != itemQty)
            input.setItemQty(itemQty);

        short packNo = 0;
        try {
            packNo = Short.parseShort(binding.etPackageNo.getText().toString());
        } catch (NumberFormatException e) {
        }
        if(input.getPackNo() != packNo)
            input.setPackNo(packNo);

        input.setIsactive("Y");
        if(Commons.hasConnection(activity)) {
            String data = new Gson().toJson(input);
            //Log.e("ConfirmBPBillAPI", data);
            /**
             * @param flag
             *          - 0 = confirm_do
             *          - 1 = confirm_ib_bill
             */
            new ConfirmBPBillAPI(activity, data, input.getBill()).execute();
        }
        else if(input.getBill() == null || input.getBill().length() == 0){
            Message.makeToastError(activity,
                    activity.getString(R.string.toast_save_bill_off));
            SSqlite.getInstance(activity).insertOrUpdateConfirmDO(input);
        }

    }

    @Override
    public void onSuccess() {
        if(item.getDoCode() != null && item.getDoCode().length() > 0){
            SSqlite.getInstance(activity).updateStatusSendBill(
                    item.getDoCode(), Constants.STATUS_COMPLETED);
            Intent i = new Intent();
            Bundle b = new Bundle();
            b.putInt("position", position);
            i.putExtras(b);
            activity.setResult(Activity.RESULT_OK, i);
            activity.finish();
            activity.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        }
        else {
            SSqlite.getInstance(activity).deleteConfirmBill(item.getDoCode());
            activity.onBackPressed();
        }
    }

    /**
     * Gọi API kiểm trả mã phiếu gửi hàng.
     * @param bill
     * isConfirm
     *          - true : Gọi từ send button, check xong là gọi confirm luôn.
     *          - false: Gọi khi scan, không làm gì tiếp theo
     */
    public void callCheckThBillAPI(String bill){
        billScan = bill;
        new CheckThBillAPI(activity, bill, this).execute();
    }

    @Override
    public void onSuccess(Object result) {
        if(result != null){
            try {
                isInvalid = (boolean) result;
                if(isInvalid && isComfirm){
                    isComfirm = false;
                    callAPIConfirmBPBill();
                }
                else {
                    Message.makeToastError(activity, activity.getString(R.string.toast_error_correct_id));
                }
            } catch (Exception e) {
            }
        }
    }

    //endregion TODO: Call API______________________End/

    @Bindable
    public ConfirmBPBillInput getItem() {
        return item;
    }

    public void setItem(ConfirmBPBillInput item) {
        this.item = item;
        notifyPropertyChanged(BR.item);
    }
}
