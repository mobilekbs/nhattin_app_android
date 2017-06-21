package vn.ntlogistics.app.ordermanagement.Views.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.BillFail.BillFailSqlite;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.BillDOActivity;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.SendBillActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.databinding.ItemBillFailBinding;

/**
 * Created by Zanty on 02/06/2017.
 */

public class BillFailAdapter extends RecyclerView.Adapter<BillFailAdapter.DataObjectHolder> {

    Context context;
    List<BillFailSqlite> mList;
    private SqliteManager           db;

    public BillFailAdapter(Context context, List<BillFailSqlite> mList) {
        this.context = context;
        this.mList = mList;
        db = new SqliteManager(context);
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        ItemBillFailBinding binding;
        int iTag;
        public DataObjectHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            //iv = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public BillFailAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_fail,parent,false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final BillFailAdapter.DataObjectHolder holder, int position) {
        //holder.iv.setImageDrawable(context.getResources().getDrawable(mList.get(position)));
        holder.iTag = position;
        final BillFailSqlite item = mList.get(holder.iTag);

        setupLayout(holder.binding, item);

        holder.binding.layoutFAIL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMain(item);
            }
        });

        holder.binding.layoutFAIL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickMain(item, holder.iTag);
                return false;
            }
        });
    }

    private void setupLayout(ItemBillFailBinding binding, BillFailSqlite item) {
        binding.tvisDO.setText(item.getIsDO());
        if (item.getIsDO().equals("Y")) {
            binding.hardTvSVD.setText("Số DO:");
            binding.hardTvApollo.setText("Trọng lượng:");
            binding.hardTvTienthuho.setText("Số lượng:");
            binding.layoutFAIL.setBackgroundResource(R.drawable.lv_do_fail);
            binding.tvSVD.setText(item.getBill());
            binding.tvApollo.setText(item.getWeight());
            binding.tvTienthuho.setText(item.getItemQty());
        } else {

            binding.hardTvSVD.setText("Số vận đơn:");
            binding.hardTvApollo.setText("Tiền Apollo:");
            binding.hardTvTienthuho.setText("Tiền thu hộ:");
            binding.tvSVD.setText(item.getBill());
            binding.tvApollo.setText(Commons.DinhDangChuoiTien(item.getMoney()));
            binding.tvTienthuho.setText(Commons.DinhDangChuoiTien(item.getMoneyCod()));
        }
        if (item.getStatus() == null
                || item.getStatus().equals("")
                || item.getStatus().equals("0")) {
            binding.tvStatus.setText("");
        }
        else{
                /*
                 * Mã lỗi 200: thành công.
                 * 116 - DO Code không hợp lệ
                 * 117 - DO Code đã được sử dụng
                 * 141 - Vận đơn đã được cập nhật // old 2
                 * 142 - Lỗi thông tin // old -1
                 * 143 - Vận đơn không tồn tại // old -2
                 * 144 - Vận đơn đã được nhập liệu // old -3 -4
                 * 145 - Mã khách hàng không hợp lệ // old -6
                 */
            String status = "";
            switch (item.getStatus()){
                case "10": //Lỗi hệ thống
                    status = "Lỗi hệ thống";
                    break;
                case "116":
                    status = "DO Code không hợp lệ";
                    break;
                case "117":
                    status = "DO Code đã được sử dụng";
                    break;
                case "141":
                    status = "Vận đơn đã được cập nhật";
                    break;
                case "142":
                    status = "Lỗi thông tin";
                    break;
                case "143":
                    status = "Vận đơn không tồn tại";
                    break;
                case "144":
                    status = "Vận đơn đã được nhập liệu";
                    break;
                case "145":
                    status = "Mã khách hàng không hợp lệ";
                    break;
                default:
                    break;
            }
            binding.tvStatus.setText(status);
        }
    }

    private void onLongClickMain(final BillFailSqlite item, final int position) {
        AlertDialog.Builder b = new AlertDialog.Builder(context);
        b.setTitle("Xóa vận đơn");
        b.setMessage("Bạn chắc chắn muốn xóa bản ghi này?");
        b.setPositiveButton("Có", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                db.deleteDataFromTable(Variables.TBL_BILLFAIL,
                        Variables.KEY_BILL, item.getBill());
                mList.remove(position);
                notifyItemChanged(position);
            }
        });
        b.setNegativeButton("Không", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });
        b.create().show();
    }

    private void onClickMain(BillFailSqlite item){
        String dimensionWeight = "";

        if(item.getDimensionWeight() == null || item.getDimensionWeight().equals(""))
            dimensionWeight = "0";
        if (item.getIsDO().equals("N")) {
            Intent send = new Intent(context, SendBillActivity.class);
            Bundle sBundle = new Bundle();
            sBundle.putInt("key_dm", 93);
            sBundle.putString("key_bill", item.getBill());
            sBundle.putString("key_tkh", item.getCustomerName());
            sBundle.putString("key_mkh", item.getCustomerID());
            sBundle.putString("key_money", item.getMoney());
            sBundle.putString("key_moneyCOD", item.getMoneyCod());
            sBundle.putString("key_tinh", item.getCity());
            sBundle.putString("key_quan", item.getDistrict());
            send.putExtras(sBundle);
            ((Activity) context).startActivity(send);
            //finish();
        } else {
            Intent send = new Intent(context, BillDOActivity.class);
            Bundle sBundle = new Bundle();
            sBundle.putInt("key_loz", 69);
            sBundle.putString("key_billDO", item.getBill());
            sBundle.putString("key_TL", item.getWeight());
            sBundle.putString("key_SL", item.getItemQty());
            sBundle.putString("key_SK", item.getPackageNo());
            sBundle.putString("key_TLQD", dimensionWeight);
            send.putExtras(sBundle);
            ((Activity) context).startActivity(send);
            //finish();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
