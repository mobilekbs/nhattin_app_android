package vn.ntlogistics.app.ordermanagement.Views.Fragments.InputManagement;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.List;

import vn.ntlogistics.app.ordermanagement.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.ordermanagement.Commons.Sqlite.Variables;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail.Bill;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.BillDOActivity;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.SendBillActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.Views.Activities.PricingActivity;
import vn.ntlogistics.app.ordermanagement.databinding.FragmentPinkBillBinding;

public class PinkBillFragment extends BaseFragment implements OnClickListener {

    private FragmentPinkBillBinding     binding;
    private View                        view;

    private int                         flag;
    SqliteManager db;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pink_bill, container, false);
        view = binding.getRoot();
        db = new SqliteManager(getContext());

        //TODO: Get data intent
        Bundle b = getActivity().getIntent().getExtras();

        flag = b.getInt("flag");

        if (flag == 0) {
            binding.btnPinkBill.setVisibility(View.GONE);
        }
        else  {
            binding.btnWhiteBill.setVisibility(View.GONE);
            binding.btnDOBill.setVisibility(View.GONE);
        }

        binding.btnWhiteBill.setOnClickListener(this);
        binding.btnPinkBill.setOnClickListener(this);
        binding.btnDOBill.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.btnPinkBill:
                if (existsUser()) {
                    Intent intent = new Intent(getActivity(), SendBillActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btnWhiteBill:
                if (existsUser()) {
                    Intent a = new Intent(getActivity(), PricingActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("menu", Variables.THISISMENU);
                    a.putExtras(b);
                    startActivity(a);
                    //getActivity().finish();
                }
                break;
            case R.id.btnDOBill:
                if (existsUser()) {
                    Intent i = new Intent(getActivity(), BillDOActivity.class);
                    startActivity(i);
                    //getActivity().finish();
                }
                break;
        }
        /*if (v.getId() == R.id.btnIr) {
            if (existsUser()) {
                Intent intent = new Intent(getActivity(), SendBillActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        }
        if (v.getId() == R.id.btnIw) {
            if (existsUser()) {
                Intent a = new Intent(getActivity(), PricingActivity.class);
                Bundle b = new Bundle();
                b.putInt("menu", Variables.THISISMENU);
                a.putExtras(b);
                startActivity(a);
                //getActivity().finish();
            }
        }
        if (v.getId() == R.id.btnIDO) {
            if (existsUser()) {
                Intent i = new Intent(getActivity(), BillDOActivity.class);
                startActivity(i);
                //getActivity().finish();
            }
        }*/

    }

    public boolean existsUser() {
        if (SCurrentUser.getCurrentUser(getContext()).getPublickey() == null) {
            Message.makeToastError(getContext(),
                    getString(R.string.error_block_account));
            return false;
        } else
            return true;
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Bill> mList) {

    }
}
