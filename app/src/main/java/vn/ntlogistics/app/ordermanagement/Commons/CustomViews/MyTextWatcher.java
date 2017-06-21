package vn.ntlogistics.app.ordermanagement.Commons.CustomViews;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.StringTokenizer;

public class MyTextWatcher implements TextWatcher {

	EditText edtMoney;
	int type;

	public MyTextWatcher(EditText edtMoney, int type) {
		this.edtMoney = edtMoney;
		this.type = type;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		if (type == 0) {
			try {
				edtMoney.removeTextChangedListener(this);
				String value = edtMoney.getText().toString();
				if (value != null && !value.equals("")) {
					String str = edtMoney.getText().toString()
							.replaceAll(",", "");
					if (value != null && !value.equals(""))
						Double.valueOf(str).doubleValue();
					edtMoney.setText(getDecimalFormat(str));
					edtMoney.setSelection(edtMoney.getText().toString()
							.length());
				}
				edtMoney.addTextChangedListener(this);
				return;
			} catch (Exception localException) {
				localException.printStackTrace();
				edtMoney.addTextChangedListener(this);
			}
		}

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
