package vn.ntlogistics.app.shipper.Views.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import vn.ntlogistics.app.shipper.Commons.AbstractClass.BaseActivity;
import vn.ntlogistics.app.shipper.Models.Inputs.JSRegister;
import vn.ntlogistics.app.shipper.R;
import vn.ntlogistics.app.shipper.ViewModels.ConfirmVMs.ConfirmActivityViewModel;
import vn.ntlogistics.app.shipper.databinding.ActivityConfirmCodeBinding;


//TODO: Xác nhận số đt
public class ConfirmCodeActivity extends BaseActivity {
    public static String TAG = "ConfirmCodeActivity";

    //TODO: Data Binding
    ActivityConfirmCodeBinding binding;
    ConfirmActivityViewModel viewModel;

    //EditText etConfirmCode;
    //Button btActived;
    //TODO: read confirm code via SMS
    //BroadcastReceiver broadcastReceiver;

    //TODO: đếm số lần gửi lại
    public static int countSendOtp = 0;
    //View loResend, btnExit;

    //ProgressBar pbConfirmCode;

    //TextView tvConfirmCode;

    int status = 0; //TODO: 0: SignIn - 1: Register
    JSRegister reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_code);
        //setContentView(R.layout.activity_confirm_code);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        try{
            status = (int) getIntent().getExtras().get("status");
            reg = (JSRegister) getIntent().getExtras().get("JSRegister");
        }
        catch (Exception e){
        }
        viewModel = new ConfirmActivityViewModel(this,status,reg, binding);
        binding.setViewModel(viewModel);
        //Prevent dismiss dialog
        setFinishOnTouchOutside(false);
        init();
        //control();
    }

    public void init() {



        //etConfirmCode = (EditText) findViewById(etConfirmCode);
        //btActived = (Button) findViewById(R.id.btActived);
        //loResend = findViewById(R.id.loResend);
        //btnExit = findViewById(R.id.btnExit);
        //listeningConfirmCode();
        setupProgressBarConfirmCode();
    }

    private void setupProgressBarConfirmCode() {
        //tvConfirmCode = (TextView) findViewById(R.id.tvConfirmCode);
        //pbConfirmCode = (ProgressBar) findViewById(pbConfirmCode);
        binding.pbConfirmCode.setProgress(0);
    }

    /*public void control() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        btActived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setEnabledButton(v);
                if (status == 1){ //Sign In
                    if (etConfirmCode.getText().length() < 1)
                        Message.showToast(ConfirmCodeActivity.this, getResources().getString(R.string.toast_otp_valid));
                    else
                        new SignInAPI(
                                ConfirmCodeActivity.this,
                                etConfirmCode.getText().toString()
                        ).execute();
                }
                else if(status == 0){ //Register
                    registerNewAccount();
                }
            }

        });
    }*/

    /*public void listeningConfirmCode() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
        //Bộ lọc lắng nghe sự thay đổi kết nối
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        //Đăng lý Broadcast Receiver
        //registerReceiver(broadcastReceiver, filter);
    }*/

    /*private void registerNewAccount() {
        //TODO: Create json form object class JSRegister, using toJson form Gson() parse to String
        User user = SCurrentUser.getCurrentUser(ConfirmCodeActivity.this);
        reg.setApiKey(SApiKey.getOurInstance().getApiKey());
        reg.setApiSecretKey(SApiKey.getOurInstance().getApiSecretKey());
        reg.setFullName(user.getFullName());
        reg.setPhoneNo(user.getPhoneNo());
        reg.setRefCode(user.getRefCode());
        try {
            if (SCurrentUser.getCurrentUser(ConfirmCodeActivity.this).getEmail() != null)
                reg.setEmail(SCurrentUser.getCurrentUser(ConfirmCodeActivity.this).getEmail());
        }
        catch (Exception e){
        }
        if(reg.getVehiclePhoto1()!=null)
            reg.setVehiclePhoto1(Utils.convertPathToBase64WithResize(reg.getVehiclePhoto1()));
        if(reg.getVehiclePhoto2()!=null)
            reg.setVehiclePhoto2(Utils.convertPathToBase64WithResize(reg.getVehiclePhoto2()));
        if(reg.getCmndPhoto1()!=null)
            reg.setCmndPhoto1(Utils.convertPathToBase64WithResize(reg.getCmndPhoto1()));
        if(reg.getCmndPhoto2()!=null)
            reg.setCmndPhoto2(Utils.convertPathToBase64WithResize(reg.getCmndPhoto2()));
        if(reg.getLicensePhoto1()!=null)
            reg.setLicensePhoto1(Utils.convertPathToBase64WithResize(reg.getLicensePhoto1()));
        if(reg.getLicensePhoto2()!=null)
            reg.setLicensePhoto2(Utils.convertPathToBase64WithResize(reg.getLicensePhoto2()));
        if(reg.getFacePhoto1()!=null)
            reg.setFacePhoto1(Utils.convertPathToBase64WithResize(reg.getFacePhoto1()));
        if(reg.getFacePhoto2()!=null)
            reg.setFacePhoto2(Utils.convertPathToBase64WithResize(reg.getFacePhoto2()));
        *//*reg.setVehicleType();
        reg.setPlateNo();
        reg.setVehiclePhoto1();
        reg.setVehiclePhoto2();
        reg.setFacePhoto1();
        reg.setFacePhoto2();
        reg.setCmndPhoto1();
        reg.setCmndPhoto2();
        reg.setLicensePhoto1();
        reg.setLicensePhoto2();*//*
        try {
            reg.setAvatarPhoto(SCurrentUser.getCurrentUser(ConfirmCodeActivity.this).getUrlPhoto());
        } catch (Exception e) {
            e.printStackTrace();
        }
        reg.setOtp(etConfirmCode.getText().toString());

        String json = new Gson().toJson(reg);
        new RegisterAPI(this,json).execute();
    }*/

    /*private void processReceive(Context context, Intent intent) {
        try {
            String body = "";
            Bundle bundle = intent.getExtras();
            //bundle trả về tập các tin nhắn gửi về cùng lúc
            Object[] objArr = (Object[]) bundle.get("pdus");
            SmsMessage[] smsMsg = new SmsMessage[objArr.length];
            //duyệt vòng lặp để đọc từng tin nhắn
            for (int i = 0; i < objArr.length; i++) {
                //lệnh chuyển đổi về tin nhắn createFromPdu
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    smsMsg[i] = SmsMessage.createFromPdu((byte[]) objArr[i], format);
                } else {
                    smsMsg[i] = SmsMessage.createFromPdu((byte[]) objArr[i]);
                }
                //body = smsMsg[i].getMessageBody();
                //lấy số điện thoại tin nhắn
                String address = smsMsg[i].getDisplayOriginatingAddress();
                if (address.equalsIgnoreCase(Common.OUR_PHONE_NUMBER)) {
                    //lấy nội dung tin nhắn
                    body = smsMsg[i].getMessageBody();
                }
            }
            etConfirmCode.setText(body.split(Pattern.quote(": "))[1].toString());
            //new PhoneNoAuthorizationAPI(ConfirmCodeActivity.this, etConfirmCode.getText().toString()).execute();
        } catch (Exception e) {
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Hủy đăng ký
        //unregisterReceiver(broadcastReceiver);
    }

}
