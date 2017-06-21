package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.Olds;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class putDataToServer {

	private String mesRes;

	public putDataToServer() {
		mesRes = "";
	}

	public static String putData(String url, String key, String bill,
			String mkh, String tkh, String tinh, String quan, String money,
			String moneyCod) {
		String result = "";
		Log.d("PUTDATA", "Key: " + key + ",Bill: " + bill + ",Mã khách: " + mkh
				+ ",Tên: " + tkh + ",Tỉnh: " + tinh + ",Quận: " + quan
				+ ",Cước chính: " + money + " " + ",Cod: " + moneyCod);
		try {
			URL object = new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			// con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject jsonparam = new JSONObject();
			try {
				jsonparam.put("AndroidKey", key);
				jsonparam.put("Bill", bill);
				jsonparam.put("ReceiverCode", mkh);
				jsonparam.put("ReceiverName", tkh);
				jsonparam.put("PayAmt", money);
				jsonparam.put("CodAmt", moneyCod);
				jsonparam.put("CityCode", tinh);
				jsonparam.put("DistrictCode", quan);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.d("JSON CALL API", url + "\n" +jsonparam.toString());
			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream(), Charset.forName("UTF-8"));
			wr.write(jsonparam.toString());
			wr.flush();

			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();

				System.out.println("vao: " + sb.toString());
				result = sb.toString();

			} else {
				System.out.println("eo vao: " + con.getResponseMessage());
			}
			int status = ((HttpURLConnection) con).getResponseCode();
			Log.i("", "Status : " + status);
			// showme();
		} catch (IOException s) {
			System.out.print(s.getMessage());
		}
		return result;
	}

	/**
	 * ----------------------------putdataDel----------------------------------
	 * ---
	 **/
	public static String putDataDel(String url, String key, String bill,
			String mkh, String tkh, String tinh, String quan, String money,
			String moneyCod) {
		String result = "";
		Log.d("PUTDATA", "Key: " + key + ",Bill: " + bill + ",Mã khách: " + mkh
				+ ",Tên: " + tkh + ",Tỉnh: " + tinh + ",Quận: " + quan
				+ ",Cước chính: " + money + " " + ",Cod: " + moneyCod);
		try {
			URL object = new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			// con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject jsonparam = new JSONObject();
			try {
				jsonparam.put("AndroidKey", key);
				jsonparam.put("Bill", bill);
				jsonparam.put("ReceiverCode", mkh);
				jsonparam.put("ReceiverName", tkh);
				jsonparam.put("PayAmt", money);
				jsonparam.put("CodAmt", moneyCod);
				jsonparam.put("CityCode", tinh);
				jsonparam.put("DistrictCode", quan);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.d("JSON CALL API", url + "\n" +jsonparam.toString());
			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream(), Charset.forName("UTF-8"));
			wr.write(jsonparam.toString());
			wr.flush();

			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();

				System.out.println("vao: " + sb.toString());
				result = sb.toString() + bill.toString().trim();

			} else {
				System.out.println("eo vao: " + con.getResponseMessage());
			}
			int status = ((HttpURLConnection) con).getResponseCode();
			Log.i("", "Status : " + status);
			// showme();
		} catch (IOException s) {
			System.out.print(s.getMessage());
		}
		return result;
	}

	/**
	 * ------------------------------------------------------------------------
	 * ---
	 **/
	public void putlom(String url, String key, String bill, String mkh,
			String tkh, String tinh, String quan, String money, String moneyCod) {
		try {
			URL object = new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			// con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject jsonparam = new JSONObject();
			try {
				jsonparam.put("AndroidKey", key);
				jsonparam.put("Bill", bill);
				jsonparam.put("ReceiverCode", mkh);
				jsonparam.put("ReceiverName", tkh);
				jsonparam.put("PayAmt", money);
				jsonparam.put("CodAmt", moneyCod);
				jsonparam.put("CityCode", tinh);
				jsonparam.put("DistrictCode", quan);
			}

			catch (JSONException e) {
				e.printStackTrace();
			}
			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream(), Charset.forName("UTF-8"));
			wr.write(jsonparam.toString());
			wr.flush();

			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				// P10000007
				br.close();

				System.out.println("VAO: " + sb.toString());

			} else {
				System.out.println("eo vao");
			}
			//showme();
		} catch (IOException s) {
			System.out.print(s.getMessage());
		}
	}

	// /---------------------------------sendDO------------------------------------------------------///
	public String putDO(String url, String key, String billDO, String TL,
			String SL, String TLQD, String SK, boolean shark) {

		String result = "";

		try {
			URL object = new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			// con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			JSONObject jsonparam = new JSONObject();
			try {
				jsonparam.put("AndroidKey", key);
				jsonparam.put("DONumber", billDO);
				jsonparam.put("Weight", TL);
				jsonparam.put("ItemQty", SL);
				jsonparam.put("DimensionWeight", TLQD);
				jsonparam.put("PackNo", SK);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.d("JSON CALL API", url + "\n" +jsonparam.toString());
			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream(), Charset.forName("UTF-8"));
			wr.write(jsonparam.toString());
			wr.flush();

			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();

				System.out.println("vao: " + sb.toString());
				result = shark ? sb.toString() + billDO.toString().trim() : sb
						.toString();

			} else {
				System.out.println("eo vao: " + con.getResponseMessage());
			}
			int status = ((HttpURLConnection) con).getResponseCode();
			Log.i("", "Status : " + status);
			// showme();
		} catch (IOException s) {
			System.out.print(s.getMessage());
		}
		return result;

	}

	public String putPricePublic(String url, String key, String SK,
			String SLKD, String Service, String TL, String TLQD, String Long,
			String Large, String Height, String fCity, String tCity,
			String fDis, String tDis) {

		String result = "";
		Log.d("SERVER", "KEY: " + key + ",So kien: " + SK + ",Soluongkiemdem: "
				+ SLKD + ",TL: " + TL + ",TLQD: " + TLQD + ",Long: " + Long
				+ ",Large: " + Large + ",Height: " + Height + ",Tu city: "
				+ fCity + ",Toi city: " + tCity + ",Tuquan: " + fDis
				+ ",Toiquan:" + tDis);

		try {
			URL object = new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			con.setRequestMethod("POST");

			JSONObject jsonparam = new JSONObject();
			try {
				jsonparam.put("AndroidKey", key);
				jsonparam.put("SenderProvince", fCity);
				jsonparam.put("ReceiverProvince", tCity);
				jsonparam.put("SenderDistrict", fDis);
				jsonparam.put("ReceiverDistrict", tDis);
				jsonparam.put("Service", Service);
				jsonparam.put("PackageNo", SK);
				jsonparam.put("Weight", TL);
				jsonparam.put("DimensionWeight", TLQD);
				jsonparam.put("Long", Long);
				jsonparam.put("Wide", Large);
				jsonparam.put("High", Height);
				jsonparam.put("ItemQty", SLKD);
				jsonparam.put("CODAmt", "0");

			} catch (JSONException e) {
				e.printStackTrace();
			}
			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream(), Charset.forName("UTF-8"));
			wr.write(jsonparam.toString());
			wr.flush();

			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();

				System.out.println("vao: " + sb.toString());
				result = sb.toString();

			} else {
				System.out.println("eo vao: " + con.getResponseMessage());
			}
			int status = ((HttpURLConnection) con).getResponseCode();
			Log.i("", "Status : " + status);
			// showme();
		} catch (IOException s) {
			System.out.print(s.getMessage());
		}
		return result;
	}

	public String putBillWhite(String url, String key, String bill,
			String HTTT, String mkh, String SK, String SLKD, String Service,
			String TL, String TLQD, String Long, String Large, String Height,
			String fCity, String tCity, String fDis, String tDis,
			String FeeDongGoi, String FeeNangHa, String FeeBaoHiem,
			String FeeKhac, String FeeCuocChinh, String FeeNTX, String FeeCOD,
			String Sodienthoai, String DCGui) {

		String result = "";
		Log.d("SERVER", FeeDongGoi + ", " + FeeNangHa + ", " + FeeBaoHiem
				+ ", " + FeeKhac +"cuoc chinh: "+FeeCuocChinh);

		try {
			URL object = new URL(url);
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			con.setRequestMethod("POST");

			JSONObject jsonparam = new JSONObject();
			try {
				jsonparam.put("AndroidKey", key);
				jsonparam.put("Bill", bill);
				jsonparam.put("SenderCode", mkh);
				jsonparam.put("ReceiverCode", "");
				jsonparam.put("Sender", "");
				jsonparam.put("Receiver", "");
				jsonparam.put("SenderAddress", "");
				jsonparam.put("ReceiverAddress", DCGui);
				jsonparam.put("SenderContact", "");
				jsonparam.put("ReceiverContact", Sodienthoai);
				jsonparam.put("SenderProvince", fCity);
				jsonparam.put("ReceiverProvince", tCity);
				jsonparam.put("SenderDistrict", fDis);
				jsonparam.put("ReceiverDistrict", tDis);

				jsonparam.put("IsDocument", "");
				jsonparam.put("IsPro", "");
				jsonparam.put("IsOther", "");
				jsonparam.put("Description", "");
				jsonparam.put("THProductCode", "");

				jsonparam.put("THPaymentCode", HTTT);
				jsonparam.put("Service", Service);
				jsonparam.put("PackageNo", SK);
				jsonparam.put("Weight", TL);
				jsonparam.put("DimensionWeight", TLQD);
				jsonparam.put("Long", Long);
				jsonparam.put("Wide", Large);
				jsonparam.put("High", Height);
				jsonparam.put("ItemQty", SLKD);
				jsonparam.put("CODAmt", FeeCOD);

				jsonparam.put("InsuranceFee", FeeBaoHiem);
				jsonparam.put("PackingFee", FeeDongGoi);
				jsonparam.put("LiftingFee", FeeNangHa);
				jsonparam.put("DeliveryFee", "0");
				jsonparam.put("OtherAmt", FeeKhac);
				
				jsonparam.put("Postage", FeeCuocChinh);
				jsonparam.put("SuburbsFee", FeeNTX);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.d("JSON CALL API", url + "\n" +jsonparam.toString());
			OutputStreamWriter wr = new OutputStreamWriter(
					con.getOutputStream(), Charset.forName("UTF-8"));
			wr.write(jsonparam.toString());
			wr.flush();

			StringBuilder sb = new StringBuilder();
			int HttpResult = con.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream(), "UTF-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();

				System.out.println("vao: " + sb.toString());
				result = sb.toString();

			} else {
				System.out.println("eo vao: " + con.getResponseMessage());
			}
			int status = ((HttpURLConnection) con).getResponseCode();
			Log.i("", "Status : " + status);
			// showme();
		} catch (IOException s) {
			System.out.print(s.getMessage());
		}
		return result;
	}

	/*public void showme() throws ClientProtocolException, IOException {
		String myUri = "http://ws.ntlogistics.vn:5656/NTAndroidService/webresources/UpdatePinkBill";
		HttpGet httpRequest = new HttpGet(myUri);
		HttpEntity httpEntity = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(httpRequest);
		int status = response.getStatusLine().getStatusCode();
		Log.i("SHOW ME", "Status : " + status);
	}*/

	public void putMesSes(String MesSes) {
		this.mesRes = MesSes;
	}

	public String getMesSes() {
		return this.mesRes;
	}
}
//HD SOFT - Nguyễn Ngọc Giao
