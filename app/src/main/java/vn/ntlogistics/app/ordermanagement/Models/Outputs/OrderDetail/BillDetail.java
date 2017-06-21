package vn.ntlogistics.app.ordermanagement.Models.Outputs.OrderDetail;

import java.io.Serializable;
import java.util.List;

public class BillDetail implements Serializable{

	private double totalShippingFee;
	private double totalExtraFee;
	private double totalVasFee;
	private double totalCodFee;
	private double totalFee;
	private double totalVatAmount;
	private double totalCodAmount;
	private double total;
	private String senderTrusted; //null là không có gì | 0 là untrust | 1 là trust

	private List<BLDetail> data;

	/**
	 * List photo
	 */
	private List<String> cargoPhoto;

	/**
	 * Tự tính tiền
	 */
	private double totalAmountSender;

	public BillDetail() {
	}

	public double getTotalAmountSender(){
		if(totalAmountSender > 0){
			return totalAmountSender;
		}
		else {
			if(data != null){
				for (BLDetail bl: data) {
					totalAmountSender += bl.getTotalFeeSend();
				}
			}
			return totalAmountSender;
		}
	}

	public List<String> getCargoPhoto() {
		return cargoPhoto;
	}

	public void setCargoPhoto(List<String> cargoPhoto) {
		this.cargoPhoto = cargoPhoto;
	}

	public String getSenderTrusted() {
		return senderTrusted;
	}

	public void setSenderTrusted(String senderTrusted) {
		this.senderTrusted = senderTrusted;
	}

	public double getTotalShippingFee() {
		return totalShippingFee;
	}

	public void setTotalShippingFee(double totalShippingFee) {
		this.totalShippingFee = totalShippingFee;
	}

	public double getTotalExtraFee() {
		return totalExtraFee;
	}

	public void setTotalExtraFee(double totalExtraFee) {
		this.totalExtraFee = totalExtraFee;
	}

	public double getTotalVasFee() {
		return totalVasFee;
	}

	public void setTotalVasFee(double totalVasFee) {
		this.totalVasFee = totalVasFee;
	}

	public double getTotalCodFee() {
		return totalCodFee;
	}

	public void setTotalCodFee(double totalCodFee) {
		this.totalCodFee = totalCodFee;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public double getTotalVatAmount() {
		return totalVatAmount;
	}

	public void setTotalVatAmount(double totalVatAmount) {
		this.totalVatAmount = totalVatAmount;
	}

	public double getTotalCodAmount() {
		return totalCodAmount;
	}

	public void setTotalCodAmount(double totalCodAmount) {
		this.totalCodAmount = totalCodAmount;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<BLDetail> getData() {
		return data;
	}

	public void setData(List<BLDetail> data) {
		this.data = data;
	}
}
