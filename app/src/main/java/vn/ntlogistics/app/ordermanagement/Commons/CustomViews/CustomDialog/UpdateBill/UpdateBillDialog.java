package vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.UpdateBill;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.CustomViews.CustomDialog.Interfaces.ICallbackAPI;
import vn.ntlogistics.app.ordermanagement.Commons.Interfaces.ICallback;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.CreateBillResponseInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.ScanMSActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.databinding.DialogUpdateStatusBillBinding;

/**
 * Created by Zanty on 11/07/2017.
 */

public class UpdateBillDialog extends Dialog {
    private DialogUpdateStatusBillBinding   binding;
    private Bill                            item;
    private String                          billID = null;
    private ICallbackAPI                    callback;
    private ICallback                       callbackScan;

    private Context mContext;


    /**
     * 1 - Nhận hàng
     *   - Hiện 3 trạng thái 1 2 3 và không có scan
     * 2 - Trả hàng
     *   - Hiện 3 trạng thái 2 3 4 và có hiện scan
     */
    private int                             flag;

    public UpdateBillDialog(@NonNull Context context,
                            int flag,
                            Bill item,
                            ICallbackAPI callback) {
        super(context);

        this.mContext = context;

        this.item = item;
        this.callback = callback;
        this.flag = flag;
        callbackScan = new ICallback() {
            @Override
            public void onSuccess(Object result) {
                /*try {
                    binding.etDOCode.setText(result.toString());
                } catch (Exception e) {
                }*/
            }
        };
        if(item != null)
            this.billID = item.getBillID();
    }

    public UpdateBillDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected UpdateBillDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                R.layout.dialog_update_status_bill, null, false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(binding.getRoot());

        if (flag == 2){ //Nhận hàng
            binding.loScanCodeUpdate.setVisibility(View.VISIBLE);
            binding.rb04Update.setVisibility(View.VISIBLE);
            binding.rb01Update.setVisibility(View.GONE);
            binding.tvMessageUpdate.setVisibility(View.GONE);
            binding.tvTitleUpdate.setText(getContext().getString(R.string.error_report));
            binding.btnOkUpdate.setText(getContext().getString(R.string.send));
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }

        binding.btnScanUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commons.setEnabledButton(v);
                Bundle b = new Bundle();
                b.putInt("test",1);
                b.putSerializable("iCallback", callbackScan);
                Intent intent = new Intent(getContext(), ScanMSActivity.class);
                intent.putExtras(b);
                getContext().startActivity(intent);
                getOwnerActivity().startActivityForResult(intent, 0);
            }
        });

        binding.btnOkUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int statusId = 0;//từ chối đi lấy đơn hàng

                Toast.makeText( mContext , "code in UpdateBillDialog.java  line 124", Toast.LENGTH_SHORT ).show();

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
                }

                if(statusId == 0) {
                    Commons.showToast(getContext(), getContext().getString(R.string.error_select_status_update));
                }
                else if(billID == null && binding.etDOCode.getText().toString().length() == 0){

                    Commons.showToast(getContext(), getContext().getString(R.string.error_null_bill_id));
                }
                else {
                    if(billID == null)
                        billID = binding.etDOCode.getText().toString(); //todo: todo nothing, just adding mark here ;)                    callCreateBillResponse(statusId);
                }
            }
        });
        binding.btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    //region TODO: Call API_________________________
    private void callCreateBillResponse(int responseStatus){
        CreateBillResponseInput data = new CreateBillResponseInput(
                getContext(),
                billID,
                SCurrentUser.getCurrentUser(getContext()).getIdStaff()+"",
                flag+"",
                responseStatus+"",
                null);
        //new CreateBillResponseAPI(getContext(), data, this).execute();
    }

    /*@Override
    public void onSuccess() {
        SSqlite.getInstance(getContext()).updateStatusSendBill(billID, Constants.STATUS_CANCEL);
        if(callback != null)
            callback.onSuccess();
        dismiss();
    }

    @Override
    public void onFailed() {
        if(callback != null)
            callback.onFailed();
    }*/

    //endregion TODO: Call API______________________End/


}
