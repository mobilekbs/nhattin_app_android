package vn.ntlogistics.app.ordermanagement.Olds.ScanMS.scanlibrary;

import android.graphics.Bitmap;

public class ItemBill {

	Bitmap img;
	String bill;
	boolean chekbox;

	public Bitmap getImg() {
		return img;
	}

	public boolean isChekbox() {
		return chekbox;
	}

	public void setChekbox(boolean chekbox) {
		this.chekbox = chekbox;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

}
