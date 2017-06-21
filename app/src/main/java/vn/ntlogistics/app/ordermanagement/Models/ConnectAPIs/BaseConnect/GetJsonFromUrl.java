package vn.ntlogistics.app.ordermanagement.Models.ConnectAPIs.BaseConnect;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by NamNgo on 17/05/2016.
 */
public class GetJsonFromUrl {
    public String strUrl;
    public String form_data;//JSON

    public GetJsonFromUrl(String strUrl, String form_data) {
        this.strUrl = strUrl;
        this.form_data = form_data;
    }

    public InputStreamReader getResponse() {

        InputStreamReader reader = null;
        try {
            HttpURLConnection conn;
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//            conn.setRequestProperty("Content-Length", String.valueOf(form_data.length()));
            OutputStream os = conn.getOutputStream();
            os.write(form_data.getBytes("UTF-8"));

            os.flush();
            os.close();

            reader = new InputStreamReader(conn.getInputStream(), "UTF-8");

            conn.disconnect();
            //showResultToLog(reader);
        } catch (Exception e) {
            //cm.log("ERROR 1: GetJsonFromUrl getResponse: " + e);
        }


        return reader;
    }

    public String getResponseStringPOST() {

        InputStreamReader reader = null;
        String strJson = null;
        try {
            HttpURLConnection conn;
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(40000);
            conn.setConnectTimeout(40000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//            conn.setRequestProperty("Content-Length", String.valueOf(form_data.length()));
            OutputStream os = conn.getOutputStream();
            os.write(form_data.getBytes("UTF-8"));

            os.flush();
            os.close();

            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader breader = new BufferedReader(reader,
                        8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = breader.readLine()) != null) {
                    sb.append(line + " ");
                }
                reader.close();
                strJson = sb.toString();
                conn.disconnect();
            } else {
                conn.disconnect();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return strJson;
        }
        return strJson;
    }

    public String getResponseStringDELETE() {
        InputStreamReader reader = null;
        String strJson = null;
        try {
            HttpURLConnection conn;
            String strUrlGet = strUrl + form_data;
            URL url = new URL(strUrlGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("DELETE");
            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader breader = new BufferedReader(reader, 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = breader.readLine()) != null) {
                    sb.append(line + " ");
                }
                strJson = sb.toString();
                conn.disconnect();
            } else {
                conn.disconnect();
                return null;
            }
        } catch (Exception e) {
            return strJson;
        }
        return strJson;
    }

    public void showResultToLog(InputStreamReader reader) {
        try {
            //xuất kết quả ra log
            BufferedReader breader = new BufferedReader(reader,
                    8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = breader.readLine()) != null) {
                sb.append(line + " ");
            }
            //cm.log("GetJsonFromUrl showResultToLog strJson: " + sb.toString());
        } catch (Exception e) {
        }


    }

    public String getResponse_String_MethodGET() {
        InputStreamReader reader = null;
        String strJson = null;
        try {
            HttpURLConnection conn;
            String strUrlGet = strUrl + form_data;
            URL url = new URL(strUrlGet);
            //cm.log(url.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader breader = new BufferedReader(reader,
                        8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = breader.readLine()) != null) {
                    sb.append(line + " ");
                }
                reader.close();
                strJson = sb.toString();
                conn.disconnect();
            } else {
                conn.disconnect();
                return null;
            }
        } catch (Exception e) {
            return strJson;
        }
        return strJson;
    }

    public String getResponseStringGET() {
        InputStreamReader reader = null;
        String strJson = null;
        try {
            HttpURLConnection conn;
            String strUrlGet = strUrl + form_data;
            URL url = new URL(strUrlGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
            reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader breader = new BufferedReader(reader, 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = breader.readLine()) != null) {
                    sb.append(line + " ");
                }
                strJson = sb.toString();
                conn.disconnect();
            } else {
                conn.disconnect();
                return null;
            }
        } catch (Exception e) {
            return strJson;
        }
        return strJson;
    }

}//END

