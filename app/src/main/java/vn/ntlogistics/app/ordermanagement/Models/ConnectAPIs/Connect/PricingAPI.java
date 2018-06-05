package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Connect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.StringTokenizer;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.BaseConnectAPI;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect.Method;
import vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.ConstantURLs;
import vn.ntlogistics.app.ordermanagement.Models.Inputs.PublicPriceInput;
import vn.ntlogistics.app.ordermanagement.Models.Outputs.Pricing.PublicPriceOutput;
import vn.ntlogistics.app.ordermanagement.Olds.Activities.ManagerBillWhiteActivity;
import vn.ntlogistics.app.ordermanagement.R;
import vn.ntlogistics.app.ordermanagement.ViewModels.Base.ViewModel;

/**
 * Created by Zanty on 20/05/2017.
 */

public class PricingAPI extends BaseConnectAPI {
    public static final String WHITE_BILL = "WHITE_BILL";
    public static final String REPRICING = "REPRICING";
    private PublicPriceInput input;
    ViewModel viewModel;

    public PricingAPI(Context context, PublicPriceInput data, ViewModel viewModel) {
        super(context, ConstantURLs.PUBLIC_PRICE, null, false, Method.POST);
        this.data = new Gson().toJson(data);
        this.input = data;
        this.viewModel = viewModel;
        initDialogWithTitle(context.getString(R.string.pricing_loading),false);
        Log.e("PricingAPI", this.data);
    }

    @Override
    public void onPre() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void doInBG() {
        Log.e("PricingAPI","-------- doInBG ");

    }

    @Override
    public void onPost(JsonObject rootObject) {
        Log.e("PricingAPI","-------- onPost ");

        //JsonObject rootObject = new JsonParser().parse(result).getAsJsonObject();
        PublicPriceOutput output = new Gson()
                .fromJson(rootObject.get("data").getAsJsonObject(), PublicPriceOutput.class);
        if(output == null)
            output = new PublicPriceOutput();

            /*Variables.POWfeePublicPostage = !Commons.roundingNumber(output.getPublicPostage())
                    .equals("0") ? Commons.roundingNumber(
                            output.getPublicPostage()) : "0";
            Variables.POWfeeChinh = !getDecimalFormat(
                    Commons.roundingNumber(output.getPublicPostage())).equals("0") ?
                    getDecimalFormat(Commons.roundingNumber(output.getPublicPostage())) : "0";
            Variables.POWfeeNTX = !getDecimalFormat(
                    Commons.roundingNumber(output.getSuburbsFee())).equals("0") ?
                    getDecimalFormat(Commons.roundingNumber(output.getSuburbsFee())) : "0";*/

        showResult(output);
        /*if(responceCode == 0) {

        }
        else if(responceCode == 1){
            Message.makeToastError(context, context.getString(R.string.toast_error_key_api));
            SqliteManager db = new SqliteManager(context);
            db.deleteUser();
            SCurrentUser.delCurrentUser();
            Commons.restartApp(context, SplashScreenActivity.class);
        }
        else {
            Message.makeToastError(context);
        }*/
    }

    public void showResult(final PublicPriceOutput output) {
        String PublicPrice = Commons.roundingNumber(output.getPublicPostage());
        String SuburbsFee = Commons.roundingNumber(output.getSuburbsFee());
        String InsuranceFee = Commons.roundingNumber(output.getInsuranceFee());
        String DeliveryFee = Commons.roundingNumber(output.getDeliveryFee());
        String PackingFee = Commons.roundingNumber(output.getPackingFee());
        String CountingFee = Commons.roundingNumber(output.getCountingFee());
        String LiftingFee = Commons.roundingNumber(output.getLiftingFee());
        String OtherAmt = Commons.roundingNumber(output.getOtherAmt());
        String Cod = Commons.roundingNumber(output.getCod()+"");
        /*Double a = Double.parseDouble(Commons.roundingNumber(output.getPublicPostage()));
        Double b = Double.parseDouble(Commons.roundingNumber(output.getSuburbsFee()));
        Double c = Double.parseDouble(Commons.roundingNumber(output.getInsuranceFee()));
        Double d = Double.parseDouble(Commons.roundingNumber(output.getDeliveryFee()));
        Double e = Double.parseDouble(Commons.roundingNumber(output.getPackingFee()));
        Double f = Double.parseDouble(Commons.roundingNumber(output.getCountingFee()));
        Double g = Double.parseDouble(Commons.roundingNumber(output.getLiftingFee()));
        Double h = Double.parseDouble(Commons.roundingNumber(output.getOtherAmt()));
        Double i = Double.parseDouble(Commons.roundingNumber(output.getCod()));*/

        /*Double mmm = output.getTotlePrice();
        Log.d("", "Tổng: " + mmm);

        BigDecimal tyu = BigDecimal.valueOf(mmm).setScale(0);
        String mm = tyu.toPlainString();*/

        String vungxa = !SuburbsFee.equals("0") ? "Phụ phí vùng xa : " : "";
        String baohiem = !InsuranceFee.equals("0") ? "Phí bảo hiểm      : "
                : "";
        String baophat = !DeliveryFee.equals("0") ? "Phí báo phát       : "
                : "";
        String donggoi = !PackingFee.equals("0") ? "Phí đóng gói       : " : "";
        String kiemdem = !CountingFee.equals("0") ? "Phí kiểm đếm     : " : "";
        String nangha = !LiftingFee.equals("0") ? "Phí nâng hạ        : " : "";
        String khac = !OtherAmt.equals("0") ? "Phí khác              : " : "";
        String cod = !Cod.equals("0") ? "Phí thu hộ           : " : "";

        String tienvungxa = !SuburbsFee.equals("0") ?
                getDecimalFormat(SuburbsFee) : "";
        String tienbaohiem = !InsuranceFee.equals("0") ? getDecimalFormat(InsuranceFee)
                : "";
        String tienbaophat = !DeliveryFee.equals("0") ? getDecimalFormat(DeliveryFee)
                : "";
        String tiendonggoi = !PackingFee.equals("0") ? getDecimalFormat(PackingFee)
                : "";
        String tienkiemdem = !CountingFee.equals("0") ? getDecimalFormat(CountingFee)
                : "";
        String tiennangha = !LiftingFee.equals("0") ? getDecimalFormat(LiftingFee)
                : "";
        String tienkhac = !OtherAmt.equals("0") ? getDecimalFormat(OtherAmt)
                : "";
        String tiencod = !Cod.equals("0") ? getDecimalFormat(Cod) : "";

        AlertDialog.Builder noti = new AlertDialog.Builder(context);
        noti.setTitle("Bảng giá");
        noti.setMessage("Tổng tiền             : " + output.getTotalPriceShow()
                + "\n" + "\n" + "Cước chính         : "
                + getDecimalFormat(PublicPrice) + "\n" + vungxa + tienvungxa
                + "\n" + baohiem + tienbaohiem + "\n" + baophat + tienbaophat
                + "\n" + donggoi + tiendonggoi + "\n" + kiemdem + tienkiemdem
                + "\n" + nangha + tiennangha + "\n" + khac + tienkhac + "\n"
                + cod + tiencod + "\n");
        noti.setNegativeButton("Nhập liên trắng",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        viewModel.onSuccess(WHITE_BILL,true);
                        Intent intent = new Intent(context,
                                ManagerBillWhiteActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("price", output);
                        bundle.putSerializable("input", input);
                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }
                });
        noti.setPositiveButton("Tính tiếp",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        viewModel.onSuccess(REPRICING,true);

                    }
                });
        noti.create().show();

    }

    private String getDecimalFormat(String value) {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt(-1 + str1.length()) == '.') {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--) {
            if (k < 0) {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3) {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }
    }
}
