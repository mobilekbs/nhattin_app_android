package vn.ntlogistics.app.shipper.Views.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.shipper.Commons.Commons;
import vn.ntlogistics.app.shipper.Commons.Singleton.SVehicle;
import vn.ntlogistics.app.shipper.Models.Outputs.Others.Vehicle;
import vn.ntlogistics.app.shipper.Models.Outputs.User.MyProfile;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.MyProfileVMs.ChangeInfoActivityViewModel;
import vn.ntlogistics.app.shipper.databinding.ActivityChangeInfoBinding;

public class ChangeInfoActivity extends BaseActivity {
    public static final String TAG = "ChangeInfoActivity";

    private ActivityChangeInfoBinding binding;
    private ChangeInfoActivityViewModel viewModel;

    int day, month, year;

    //EditText etName, etAddress, etPlateNo, etMail;
    //Spinner sp;
    List<String> mListVehicle;
    //RadioButton rbMale, rbFemale;
    //View btnUpdate, btnBack, btnBirthday, layoutMain;
    //TextView tvBirhday;

    MyProfile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_info);
        try {
            myProfile = (MyProfile) getIntent().getExtras().getSerializable("myProfile");
        } catch (Exception e) {
            myProfile = new MyProfile();
        }
        viewModel = new ChangeInfoActivityViewModel(this, myProfile);
        binding.setViewModel(viewModel);
        //setContentView(R.layout.activity_change_info);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        init();
    }

    public ActivityChangeInfoBinding getBinding(){
        return binding;
    }

    private void init() {
        try {
            myProfile = (MyProfile) getIntent().getExtras().getSerializable("myProfile");
        } catch (Exception e) {
            myProfile = new MyProfile();
        }
        //Ẩn bàn phím
        //layoutMain = findViewById(R.id.layoutMain);
        Commons.hideSoftKeyboard(this, binding.layoutMain);

        setupSpinner();
        getIntentInfo();

    }

    public void setupSpinner() {
        if (mListVehicle == null) {
            mListVehicle = new ArrayList<>();
            List<Vehicle> mListSVehicle = SVehicle.getListVehicle();
            for (int i = 0; i < mListSVehicle.size(); i++) {
                mListVehicle.add(mListSVehicle.get(i).getName());
            }
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, //set layout choose
                mListVehicle
        );
        //set layout for spinner
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        binding.sp.setAdapter(adapter1);
    }

    private void getIntentInfo() {
        if (myProfile != null) {
            int s = 0;
            try {
                s =Integer.parseInt(myProfile.getGender());
            } catch (Exception e) {
            }
            try {
                int v = Integer.parseInt(myProfile.getVehicleType());
                binding.sp.setSelection(v - 1);
            } catch (Exception e) {
            }
            if (s == 0) //0: male
                binding.rbMale.setChecked(true);
            else
                binding.rbFemale.setChecked(true);
            /*try {
                tvBirhday.setText(Utils.timeStampToDate(Long.parseLong(myProfile.getDateOfBirth())));
            } catch (NumberFormatException e) {
                tvBirhday.setText(myProfile.getDateOfBirth());
            }*/
            /*etName.setText(myProfile.getFullName());
            etAddress.setText(myProfile.getAddress());
            etPlateNo.setText(myProfile.getPlateNo());
            //etPhone.setText(SCurrentUser.getOurInstance().getPhoneNo());
            etMail.setText(myProfile.getEmail());*/
        }
    }


    /**
     * Create date picker dialog
     */
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(123);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 123) {
            int yearO, monthO, dayO;
            String dateTime = binding.tvBirthday.getText().toString();
            if (dateTime.length() < 1) {
                Calendar cal = Calendar.getInstance();
                dateTime = Commons.timeStampToDate(cal.getTimeInMillis());
            }
            dayO = Integer.parseInt(dateTime.split(Pattern.quote("/"))[0]);
            monthO = Integer.parseInt(dateTime.split(Pattern.quote("/"))[1]);
            yearO = Integer.parseInt(dateTime.split(Pattern.quote("/"))[2]);
            final DatePickerDialog dialogD = new DatePickerDialog(this,
                    myDateListener,
                    yearO,
                    monthO - 1,
                    dayO
            );
            dialogD.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.btnCancle), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.cancel();
                    }
                }
            });
            dialogD.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.btnOk), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        DatePicker datePicker = dialogD
                                .getDatePicker();
                        showDate(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                    }
                }
            });
            return dialogD;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            year = arg1;
            month = arg2 + 1;
            day = arg3;
            //showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        viewModel.setTextBirthday(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year).toString());
    }

    /**
     * End create date picker dialog
     */
}
