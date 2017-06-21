package vn.ntlogistics.app.ordermanagement.Views.Fragments.InputManagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.R;


public class WhiteBillFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return (LinearLayout) inflater.inflate(R.layout.fragment_white_bill,
				container, false);
	}

	@Override
	public void showControls(boolean check, int action) {

	}

	@Override
	public void loadSuccess(List<Bill> mList) {

	}
}
