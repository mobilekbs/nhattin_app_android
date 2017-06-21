package vn.ntlogistics.app.ordermanagement.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.BaseLocation;
import vn.ntlogistics.app.ordermanagement.R;

public class CustomSpinnerAdapter<T extends BaseLocation> extends BaseAdapter {

	ArrayList<T> mList;
	Context context;

	public CustomSpinnerAdapter(Context context, ArrayList<T> mList) {
		this.context = context;
		this.mList = mList;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getApplicationContext().getSystemService(
							Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_custom_spinner, parent,
					false);
		}
		try {
			TextView tv = (TextView) convertView.findViewById(R.id.tvTitleItemSpinner);
			tv.setText(mList.get(position).getName());

			View ivChecked = convertView.findViewById(R.id.ivCheckedItemSpinner);
			if(mList.get(position).isChecked())
				ivChecked.setVisibility(View.VISIBLE);
			else
				ivChecked.setVisibility(View.GONE);
		} catch (Exception e) {
		}
		return convertView;
	}

}
