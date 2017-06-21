package vn.ntlogistics.app.ordermanagement.Models.Outputs.Pricing;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.StringTokenizer;

import vn.ntlogistics.app.ordermanagement.Commons.Commons;

/**
 * Created by Zanty on 20/05/2017.
 */

public class PublicPriceOutput implements Serializable {
    private String publicPostage;
    private String controlAmt;
    private String suburbsFee;
    private String insuranceFee;
    private String deliveryFee;
    private String packingFee;
    private String countingFee;
    private String liftingFee;
    private String otherAmt;
    private double cod;

    public PublicPriceOutput() {
    }

    public String getTotalPriceShow(){
        try {
            BigDecimal tyu = BigDecimal.valueOf(getTotlePrice()).setScale(0);
            return getDecimalFormat(tyu.toPlainString());
        } catch (Exception e) {
            return null;
        }
    }

    public String getPublicPostageShow(){
        return getDecimalFormat(Commons.roundingNumber(getPublicPostage()));
    }
    public String getControlAmtShow(){
        return getDecimalFormat(Commons.roundingNumber(getControlAmt()));
    }
    public String getInsuranceFeeShow(){
        return getDecimalFormat(Commons.roundingNumber(getInsuranceFee()));
    }
    public String getSuburbsFeeShow(){
        return getDecimalFormat(Commons.roundingNumber(getSuburbsFee()));
    }
    public String getDeliveryFeeShow(){
        return getDecimalFormat(Commons.roundingNumber(getDeliveryFee()));
    }
    public String getPackingFeeShow(){
        return getDecimalFormat(Commons.roundingNumber(getPackingFee()));
    }
    public String getCountingFeeShow(){
        return getDecimalFormat(Commons.roundingNumber(getCountingFee()));
    }
    public String getLiftingFeeShow(){
        return getDecimalFormat(Commons.roundingNumber(getLiftingFee()));
    }
    public String getOtherAmtShow(){
        return getDecimalFormat(Commons.roundingNumber(getOtherAmt()));
    }
    public String getCodShow(){
        return getDecimalFormat(Commons.roundingNumber(getCod()+""));
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

    public double getTotlePrice(){
        Double a = Double.parseDouble(Commons.roundingNumber(getPublicPostage()));
        Double b = Double.parseDouble(Commons.roundingNumber(getSuburbsFee()));
        Double c = Double.parseDouble(Commons.roundingNumber(getInsuranceFee()));
        Double d = Double.parseDouble(Commons.roundingNumber(getDeliveryFee()));
        Double e = Double.parseDouble(Commons.roundingNumber(getPackingFee()));
        Double f = Double.parseDouble(Commons.roundingNumber(getCountingFee()));
        Double g = Double.parseDouble(Commons.roundingNumber(getLiftingFee()));
        Double h = Double.parseDouble(Commons.roundingNumber(getOtherAmt()));
        Double i = getCod();
        return a + b + c + d + e + f + g + h + i;
    }

    public String getPublicPostage() {
        return publicPostage;
    }

    public void setPublicPostage(String publicPostage) {
        this.publicPostage = publicPostage;
    }

    public String getControlAmt() {
        return controlAmt;
    }

    public void setControlAmt(String controlAmt) {
        this.controlAmt = controlAmt;
    }

    public String getSuburbsFee() {
        return suburbsFee;
    }

    public void setSuburbsFee(String suburbsFee) {
        this.suburbsFee = suburbsFee;
    }

    public String getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(String insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getPackingFee() {
        return packingFee;
    }

    public void setPackingFee(String packingFee) {
        this.packingFee = packingFee;
    }

    public String getCountingFee() {
        return countingFee;
    }

    public void setCountingFee(String countingFee) {
        this.countingFee = countingFee;
    }

    public String getLiftingFee() {
        return liftingFee;
    }

    public void setLiftingFee(String liftingFee) {
        this.liftingFee = liftingFee;
    }

    public String getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(String otherAmt) {
        this.otherAmt = otherAmt;
    }

    public double getCod() {
        return cod;
    }

    public void setCod(double cod) {
        this.cod = cod;
    }
}
