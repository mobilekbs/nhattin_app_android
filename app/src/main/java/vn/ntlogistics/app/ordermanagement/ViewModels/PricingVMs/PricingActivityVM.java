package vn.ntlogistics.app.ordermanagement.ViewModels.PricingVMs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Commons.Message;
import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SSQLite;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.BaseLocation;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.City;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.District;
import vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location.Service;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect.PricingAPI;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.PublicPriceInput;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;
import vn.ntlogistics.app.ordermanagement.Views.Activities.PricingActivity;
import vn.ntlogistics.app.ordermanagement.databinding.ActivityPricingBinding;

/**
 * Created by Zanty on 19/05/2017.
 */

public class PricingActivityVM extends ViewModel implements AdapterView.OnItemSelectedListener {
    private ActivityPricingBinding binding;
    private PricingActivity activity;

    //Data Spinner
    private ArrayList<Service> mListService;
    private ArrayList<City> mListCity;
    private ArrayList<District> mListDistrictForm, mListDistrictTo;

    public PricingActivityVM(ActivityPricingBinding binding, PricingActivity activity) {
        this.binding = binding;
        this.activity = activity;
        mListService = new ArrayList<>();
        mListCity = new ArrayList<>();
        mListDistrictForm = new ArrayList<>();
        mListDistrictTo = new ArrayList<>();
        Commons.hideSoftKeyboard(activity, binding.lnMainPricing);
        initSpinner();
    }

    private void initSpinner() {
        initSpinnerService();
        initSpinnerCity();
    }

    private void initSpinnerService() {
        mListService = SSQLite.getInstance(activity).getListService();
        setupSpinner(binding.spinService, mListService);
        binding.spinService.setSelection(0);
    }

    private void initSpinnerCity() {
        mListCity = SSQLite.getInstance(activity).getListCity();
        setupSpinner(binding.spinFromCity, mListCity);
        setupSpinner(binding.spinToCity, mListCity);

        binding.spinFromCity.setOnItemSelectedListener(this);
        binding.spinToCity.setOnItemSelectedListener(this);

        int idPosition = SSQLite.getInstance(activity).getIdLocationInCity();
        binding.spinFromCity.setSelection(idPosition);
        binding.spinToCity.setSelection(idPosition);

    }

    private void setupSpinnerDistrict(Spinner spinner, int id) {
        if (spinner.getId() == binding.spinFromDis.getId()) {
            mListDistrictForm.clear();
            mListDistrictForm.addAll(SSQLite.getInstance(activity).getListDistrictByCidyID(id + ""));
            setupSpinner(
                    spinner,
                    mListDistrictForm
            );
        } else if (spinner.getId() == binding.spinToDis.getId()) {
            mListDistrictTo.clear();
            mListDistrictTo.addAll(SSQLite.getInstance(activity).getListDistrictByCidyID(id + ""));
            setupSpinner(
                    spinner,
                    mListDistrictTo
            );
        }
    }

    private <T extends BaseLocation> void setupSpinner(Spinner spinner, final ArrayList<T> mList) {
        //CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(activity, mList);
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(
                activity,
                android.R.layout.simple_spinner_item
                , mList) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setText(mList.get(position).getName());
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setText(mList.get(position).getName());
                tv.setTextColor(ContextCompat.getColor(
                        tv.getContext(), R.color.colorAccentSub1
                ));
                return view;
                //return super.getView(position, convertView, parent);
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == binding.spinFromCity.getId()) {
            setupSpinnerDistrict(binding.spinFromDis,
                    mListCity.get(position).getId());
        } else if (parent.getId() == binding.spinToCity.getId()) {
            setupSpinnerDistrict(binding.spinToDis,
                    mListCity.get(position).getId());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void onClickTest(View v){

    }

    public void onClickPricing(View view) {
        Commons.setEnabledButton(view);
        if (Commons.hasConnection(activity)) {
            if (validate()) {
                callAPIPricing();
            }
        } else {
            Message.makeToastErrorConnect(activity);
        }
    }

    private boolean validate() {
        if (checkNullEdittext(binding.edtcTL))
            return true;
        else
            return false;
    }

    private boolean checkNullEdittext(EditText et) {
        if (et.getText().toString().length() == 0) {
            et.setError(activity.getString(R.string.error_null_field));
            et.requestFocus();
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
            return false;
        } else {
            et.setError(null);
            return true;
        }
    }

    private void callAPIPricing() {
        PublicPriceInput input = new PublicPriceInput(activity);
        //TODO: Add City
        int posFromCity = binding.spinFromCity.getSelectedItemPosition();
        int posToCity = binding.spinToCity.getSelectedItemPosition();
        input.setSenderProvince(mListCity.get(posFromCity).getAreacode() + "");
        input.setReceiverProvince(mListCity.get(posToCity).getAreacode() + "");

        //TODO: Add District
        int posFromDis = binding.spinFromDis.getSelectedItemPosition();
        int posToDis = binding.spinToDis.getSelectedItemPosition();
        input.setSenderDistrict(mListDistrictForm.get(posFromDis).getValue() + "");
        input.setReceiverDistrict(mListDistrictTo.get(posToDis).getValue() + "");

        //TODO: Add Service
        int posService = binding.spinService.getSelectedItemPosition();
        input.setService(mListService.get(posService).getValue() + "");

        //TODO: Add Others
        try {
            input.setPackageNo(Integer.parseInt(binding.edtSoKien.getText().toString()));
        } catch (NumberFormatException e) {
        }
        try {
            input.setWeight(Double.parseDouble(binding.edtcTL.getText().toString()));
        } catch (NumberFormatException e) {
        }
        try {
            input.setDimensionWeight(Double.parseDouble(binding.edtcTLQD.getText().toString()));
        } catch (NumberFormatException e) {
        }
        try {
            input.setPackageLong(Long.parseLong(binding.edtLong.getText().toString()));
        } catch (NumberFormatException e) {
        }
        try {
            input.setWide(Double.parseDouble(binding.edtLarge.getText().toString()));
        } catch (NumberFormatException e) {
        }
        try {
            input.setHigh(Double.parseDouble(binding.edtHeight.getText().toString()));
        } catch (NumberFormatException e) {
        }
        try {
            input.setItemQty(Integer.parseInt(binding.edtSLKD.getText().toString()));
        } catch (NumberFormatException e) {
        }
        input.setCodAmt(0);

        //String json = new Gson().toJson(input);
        new PricingAPI(activity, input, this).execute();
    }

    @Override
    public void onSuccess(String action, boolean b) {
        switch (action) {
            case PricingAPI.WHITE_BILL:
                /*Intent intent = new Intent(activity,
                        ManagerBillWhiteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("buoi", Variables.THISISPRICE);
                intent.putExtras(bundle);
                activity.startActivity(intent);*/
                //activity.finish();
                break;
            case PricingAPI.REPRICING:
                repricing();
                break;
        }
    }

    public void repricing() {
        binding.edtcTL.setText("");
        binding.edtcTLQD.setText("");
        binding.edtHeight.setText("");
        binding.edtLarge.setText("");
        binding.edtLong.setText("");
        binding.edtSoKien.setText("");
        binding.edtSLKD.setText("");
        binding.edtSLKD.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
