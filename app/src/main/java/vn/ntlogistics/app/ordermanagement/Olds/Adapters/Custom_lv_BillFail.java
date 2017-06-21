package vn.ntlogistics.app.ordermanagement.Olds.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.R;


public class Custom_lv_BillFail extends CursorAdapter {
	Context mContext;
	LayoutInflater mInflater;
	int mStatus;

	public Custom_lv_BillFail(Context context, Cursor cursor) {
		super(context, cursor);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		while (!cursor.isAfterLast()) {

			Log.d("",
					" "
							+ cursor.getString(cursor
									.getColumnIndex(Variables.KEY_BILL)));

			cursor.moveToNext();
		}

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View view = mInflater.inflate(R.layout.custom_lv_billfail,
				parent, false);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		String S_isDO = cursor.getString(cursor
				.getColumnIndex(Variables.KEY_ISDO));
		TextView hard_tvBill = (TextView) view.findViewById(R.id.hard_tvSVD);
		TextView hard_tvApollo = (TextView) view
				.findViewById(R.id.hard_tvApollo);
		TextView hard_tvCOD = (TextView) view
				.findViewById(R.id.hard_tvTienthuho);
		TextView tvbill = (TextView) view.findViewById(R.id.tvSVD);
		TextView tvApollo = (TextView) view.findViewById(R.id.tvApollo);
		TextView tvCod = (TextView) view.findViewById(R.id.tvTienthuho);
		TextView tvStatus = (TextView) view.findViewById(R.id.tvStatus);
		TextView isDO = (TextView) view.findViewById(R.id.tvisDO);
		String status = "";
		status = cursor.getString(cursor.getColumnIndex(Variables.KEY_STATUS)) != null ? cursor
				.getString(cursor.getColumnIndex(Variables.KEY_STATUS)) : "";
		LinearLayout ln = (LinearLayout) view.findViewById(R.id.layoutFAIL);
		if (S_isDO.equals("Y")) {
			hard_tvBill.setText("Số DO:");
			hard_tvApollo.setText("Trọng lượng:");
			hard_tvCOD.setText("Số lượng:");
			ln.setBackgroundResource(R.drawable.lv_do_fail);
			tvbill.setText(cursor.getString(cursor
					.getColumnIndex(Variables.KEY_BILL)));
			tvApollo.setText(cursor.getString(cursor
					.getColumnIndex(Variables.KEY_TL)));
			tvCod.setText(cursor.getString(cursor
					.getColumnIndex(Variables.KEY_SL)));
			isDO.setText(S_isDO);
			if (status.equals(null) || status == null) {
				tvStatus.setText("");
				status = "";
			} else if (status.equals("") || status == "" || status == "0"
					|| status.equals("0")) {
				tvStatus.setText("");
			} else if (status.equals("-1") || status == "-1") {
				tvStatus.setText("Lỗi hệ thống");
			} else if (status.equals("1") || status == "1") {
				tvStatus.setText("Sai Khóa");
			} else if (status.equals("2") || status == "2") {
				tvStatus.setText("Vận đơn không tồn tại");
			} else if (status.equals("-2") || status == "-2") {
				tvStatus.setText("Vận đơn không tồn tại");
			} else if (status.equals("3") || status == "3") {
				tvStatus.setText("Vận đơn đã được xác nhận trước đó");
			} else if (status.equals("5") || status == "5") {
				tvStatus.setText("Lỗi hệ thống");
			}
		} else {
			hard_tvBill.setText("Số vận đơn:");
			hard_tvApollo.setText("Tiền Apollo:");
			hard_tvCOD.setText("Tiền thu hộ:");
			tvbill.setText(cursor.getString(cursor
					.getColumnIndex(Variables.KEY_BILL)));
			tvApollo.setText(cursor.getString(cursor
					.getColumnIndex(Variables.KEY_MONEY)));
			tvCod.setText(cursor.getString(cursor
					.getColumnIndex(Variables.KEY_MONEYCOD)));
			isDO.setText(S_isDO);
			if (status.equals(null) || status == null) {
				tvStatus.setText("");
				status = "";
			} else if (status.equals("") || status == "" || status == "0"
					|| status.equals("0")) {
				tvStatus.setText("");
			} else if (status.equals("-1") || status == "-1") {
				tvStatus.setText("Thiếu thông tin bắt buộc");
			} else if (status.equals("1") || status == "1") {
				tvStatus.setText("Sai Khóa");
			} else if (status.equals("2") || status == "2") {
				tvStatus.setText("Vận đơn đã được cập nhật");
			} else if (status.equals("-2") || status == "-2") {
				tvStatus.setText("Vận đơn không tồn tại");
			} else if (status.equals("-3") || status == "-3"
					|| status.equals("-4") || status == "-4") {
				tvStatus.setText("Vận đơn đã được nhập liệu");
			} else if (status.equals("-5") || status == "-5") {
				tvStatus.setText("Lỗi hệ thống");
			}
		}

	}

}
