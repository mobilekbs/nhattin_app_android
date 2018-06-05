package vn.ntlogistics.app.ordermanagement.ViewModels.MyOrderVMs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import vn.ntlogistics.app.config.Config;
import vn.ntlogistics.app.ordermanagement.Commons.Animations.MyAnimation;
import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Constants;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.CreateBillResponseAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CreateBillResponseInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.UpdateBillActivity;
import vn.ntlogistics.app.ordermanagement.Views.Activities.ZXingScannerActivity;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityUpdateBillBinding;

/**
 * Created by Zanty on 01/08/2017.
 */

public class UpdateBillVM extends ViewModel {
    private UpdateBillActivity          activity;
    private ActivityUpdateBillBinding   binding;

    private Bill                        item;
    private String                      billID = null;
    //private ICallbackAPI                callback;
    /**
     * 1 - Nhận hàng
     *   - Hiện 3 trạng thái 1 2 3 và không có scan
     * 2 - Trả hàng
     *   - Hiện 3 trạng thái 2 3 4 và có hiện scan
     */
    private int                             flag;
    private int                             position;

    public UpdateBillVM(UpdateBillActivity activity, ActivityUpdateBillBinding binding) {
        this.activity = activity;
        this.binding = binding;
        getDataIntent();
        setupLayout();
    }

    private void getDataIntent() {
        Bundle b = activity.getIntent().getExtras();

        if(b != null){
            try {
                flag = b.getInt("flag");
            } catch (Exception e) {
            }
            try {
                item = (Bill) b.getSerializable("item");
                position = b.getInt("position");
//                billID = item.getEmsBpbillID();
                billID = item.getBillID();
            } catch (Exception e) {
            }
        }
    }

    private void setupLayout() {

        Commons.hideSoftKeyboard(activity, binding.loMainUpdate);

        if (flag == 2){ //Nhận hàng
            binding.loScanCodeUpdate.setVisibility(View.VISIBLE);
            binding.rb04Update.setVisibility(View.VISIBLE);
            binding.rb01Update.setVisibility(View.GONE);
            binding.tvMessageUpdate.setVisibility(View.GONE);
            binding.tvTitleUpdate.setText(activity.getString(R.string.error_report));
            binding.btnOkUpdate.setText(activity.getString(R.string.send));
        }

        binding.btnScanUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                /*Intent intent = new Intent(activity, ScanMSActivity.class);
                activity.startActivityForResult(intent, Constants.REQUEST_CODE_SCAN);*/

                ZXingScannerActivity.openScanner(activity);
            }
        });

        //todo----

        binding.btnOkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

       //         Toast.makeText( activity, "code in UpdateBillVM.java  line 101", Toast.LENGTH_SHORT ).show();

                int statusId = 0;//từ chối đi lấy đơn hàng

                switch (binding.rgMainUpdate.getCheckedRadioButtonId()){
                    case R.id.rb01Update:
                        statusId = 1;
                        break;
                    case R.id.rb02Update:
                        statusId = 2;
                        break;
                    case R.id.rb03Update:
                        statusId = 3;
                        break;
                    case R.id.rb04Update:
                        statusId = 4;
                        break;
                    case R.id.rb99Update:
                        statusId = 99;
                        break;
                }
            //    billID="F118794161";
                Log.e("updatebill","updatebill  " + statusId + " "+billID+"   s"+binding.etDOCode.getText().toString() + "  # " + binding.etReasonUpdate.getText().toString());

                if(statusId == 0) {
                    Commons.showToast(activity, activity.getString(R.string.error_select_status_update));
                }
                else if(binding.etDOCode.getText().toString().length() == 0)  {

                    Log.e("TAG","--------------------------- binding.etDOCode.getText() ");
                    Log.e("TAG","--------------------------- bill Id = " + billID);


//                    if(billID == null) {
//                        && binding.etDOCode.getText().toString().length() == 0){

                    if (Config.caseQyanLy && billID != null ){
                        callCreateBillResponse(statusId);

                    }else {
                        Commons.showToast(activity, activity.getString(R.string.error_null_bill_id));
                    }

                }

                else {
//                    billID = binding.etDOCode.getText().toString();
                    callCreateBillResponse(statusId);
                }
            }
        });
        binding.btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        binding.btnDeleteTextUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.etDOCode.setText("");
                binding.etDOCode.setEnabled(true);
                showScan(true);
            }
        });

        binding.etDOCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 0){
                    showScan(true);
                }
                else {
                    showScan(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.rb99Update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.etReasonUpdate.setVisibility(View.VISIBLE);
                }
                else {
                    binding.etReasonUpdate.setVisibility(View.GONE);
                }
            }
        });
    }

    public void showScan(boolean show){
        MyAnimation.setVisibilityAnimationRight(binding.btnScanUpdate, show);
        MyAnimation.setVisibilityAnimationRight(binding.btnDeleteTextUpdate, !show);
    }

    //region TODO: Call API_________________________
    private void callCreateBillResponse(int responseStatus){

        Log.e("UpdateBillVM","---------- binding.etDOCode = " +  binding.etDOCode.getText().toString());
        Log.e("UpdateBillVM","---------- binding.etReasonUpdate = " + binding.etReasonUpdate.getText().toString());

        CreateBillResponseInput data;

        if(Config.caseTraBaoCaoSuCo){

             data = new CreateBillResponseInput(
                    activity,
                    binding.etDOCode.getText().toString(),
                    SCurrentUser.getCurrentUser(activity).getIdStaff()+"",
                    String.format("%02d", flag),
                    String.format("%02d", responseStatus),
                     binding.etReasonUpdate.getText().toString()
                   );
        }else {

            Log.e("UpdateBillVM","---------- else i know ");

            data = new CreateBillResponseInput(
                    activity,
                    billID,
                    SCurrentUser.getCurrentUser(activity).getIdStaff()+"",
                    String.format("%02d", flag),
                    String.format("%02d", responseStatus),
                    binding.etReasonUpdate.getText().toString());

        }//else

        new CreateBillResponseAPI(activity, data, this).execute();

    }//callCreateBillResponse

    @Override
    public void onSuccess() {
        try {
            SSQLite.getInstance(activity).updateStatusSendBill(item.getBillID(), Constants.STATUS_CANCEL);
        } catch (Exception e) {
        }
        Intent intent = new Intent();
        Bundle b = new Bundle();
        b.putInt("position", position);
        intent.putExtras(b);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.onBackPressed();
    }

    //endregion TODO: Call API______________________End/

}
