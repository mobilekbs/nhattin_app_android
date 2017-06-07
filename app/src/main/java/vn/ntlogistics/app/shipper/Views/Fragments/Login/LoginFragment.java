package vn.ntlogistics.app.shipper.Views.Fragments.Login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseFragment;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Sqlite.SqliteManager;
import vn.ntlogistics.app.shipper.Models.Outputs.Order.Order;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.LoginVMs.LoginFragmentViewModel;
import vn.ntlogistics.app.shipper.databinding.FragmentLoginBinding;

/**
 * Created by NamNgo on 10/04/2016.
 * Đăng nhập
 */
public class LoginFragment extends BaseFragment {
    public static String                TAG = "LoginFragment";

    private FragmentLoginBinding binding;
    private LoginFragmentViewModel viewModel;
    private View                        view;

    //TODO: Read list phone reminder
    List<String>                        mListPhone = new ArrayList<>();
    SqliteManager db;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.fragment_login, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        viewModel = new LoginFragmentViewModel(this);
        binding.setViewModel(viewModel);

        view = binding.getRoot();
        db = new SqliteManager(getContext());
        init();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void init() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        /**
         * Truyền layout chứa EditText để khi nhấn ra ngoài sẽ ẩn editText đó đi
         */
        Commons.hideSoftKeyboard(getContext(),binding.rltLogin);

        setupAutoComplete();
    }

    private void setupAutoComplete() {
        mListPhone = db.getListPhoneFromData();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.item_autocomplete,
                mListPhone);
        binding.etPhoneLogin.setThreshold(2); //ký tự tối thiểu để hiện
        binding.etPhoneLogin.setAdapter(adapter);
    }

    @Override
    public void showControls(boolean check, int action) {

    }

    @Override
    public void loadSuccess(List<Order> mList) {
        if(viewModel != null)
            viewModel.loadSuccess(mList);
    }
}
