package vn.ntlogistics.app.ordermanagement.Olds.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.R;


public class Custom_lv_BillFaillDO extends CursorAdapter {

	Context mContext;
	LayoutInflater mInflater;

	public Custom_lv_BillFaillDO(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View view = mInflater.inflate(R.layout.custom_lv_billdofail,
				parent, false);
		return view;
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		// TODO Auto-generated method stub
		
		
		TextView tvSDO = (TextView) v.findViewById(R.id.tvSDO);
		tvSDO.setText(c.getString(c.getColumnIndex(Variables.KEY_BILL_DO)));
		TextView tvTLDO = (TextView) v.findViewById(R.id.tvTLDO);
		tvTLDO.setText(c.getString(c.getColumnIndex(Variables.KEY_TL)));
		TextView tvSLDO = (TextView) v.findViewById(R.id.tvSLDO);
		tvSLDO.setText(c.getString(c.getColumnIndex(Variables.KEY_SL)));
		TextView tvStatusDO = (TextView) v.findViewById(R.id.tvStatusDO);
		String status = "";
		status = c.getString(c.getColumnIndex(Variables.KEY_STATUS_DO)) != null ? c
				.getString(c.getColumnIndex(Variables.KEY_STATUS_DO)) : "";
		if(tvSDO.getText().toString().length()==1){
			LinearLayout ln =(LinearLayout)v.findViewById(R.id.layoutDOFail);
			ln.setBackgroundResource(R.drawable.lv_do_fail);
		}
				
				
		if (status.equals(null) || status == null) {
			tvStatusDO.setText("");
			status = "";
		} else if (status.equals("") || status == "" || status == "0"
				|| status.equals("0")) {
			tvStatusDO.setText("");
		} else if (status.equals("-1") || status == "-1") {
			tvStatusDO.setText("Lỗi hệ thống");
		} else if (status.equals("1") || status == "1") {
			tvStatusDO.setText("Sai Khóa");
		} else if (status.equals("2") || status == "2") {
			tvStatusDO.setText("Vận đơn không tồn tại");
		} else if (status.equals("-2") || status == "-2") {
			tvStatusDO.setText("Vận đơn không tồn tại");
		} else if (status.equals("3") || status == "3") {
			tvStatusDO.setText("Vận đơn đã được xác nhận trước đó");
		} else if (status.equals("5") || status == "5") {
			tvStatusDO.setText("Lỗi hệ thống");
		}

	}

}
