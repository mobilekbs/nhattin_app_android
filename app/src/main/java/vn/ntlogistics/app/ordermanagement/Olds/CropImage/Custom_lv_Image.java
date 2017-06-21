package vn.ntlogistics.app.ordermanagement.Olds.CropImage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary.ItemBill;
import vn.ntlogistics.app.ordermanagement.R;

public class Custom_lv_Image extends ArrayAdapter<ItemBill> {

	ArrayList<ItemBill> arr = null;
	Activity context;
	int layout_id;

	public Custom_lv_Image(Activity context, int layout_id,
                           ArrayList<ItemBill> arr) {
		super(context, layout_id, arr);
		// TODO Auto-generated constructor stub
		this.arr = arr;
		this.context = context;
		this.layout_id = layout_id;

	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		v = inflater.inflate(layout_id, null);
		
		if (arr.size() > 0) {
			ItemBill myBill = arr.get(position);

			TextView tvImage = (TextView) v.findViewById(R.id.ctvBill);
			tvImage.setText(myBill.getBill().toString());

			ImageView img = (ImageView) v.findViewById(R.id.cImgBill);
			img.setRotation(90);
			img.setImageBitmap(myBill.getImg());
		}
		return v;

	}

}
