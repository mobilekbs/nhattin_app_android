package vn.ntlogistics.app.shipper.Models.ConnectAPIs.BaseConnect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Zanty on 04/07/2016.
 */
public class JsonParserArrayKey {
    public static JSONObject getJsonObject(String jsonContent) {
        JSONObject issue = null;
        try {
            JSONObject issueObj = new JSONObject(jsonContent);

            Iterator<String> iterator = issueObj.keys();
            while (iterator.hasNext())

            {
                String key = iterator.next();

                try {
                    issue = issueObj.getJSONObject(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        catch (Exception e){
        }
        return issue;
    }
}
