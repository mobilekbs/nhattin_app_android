package vn.ntlogistics.app.shipper.Commons.Singleton;

import com.google.gson.JsonObject;

import vn.ntlogistics.app.shipper.Models.Outputs.Login.ApiKey;

/**
 * Created by Zanty on 23/06/2016.
 */
public class SApiKey {
    private static ApiKey ourInstance = new ApiKey();

    public static ApiKey getOurInstance() {
        if (ourInstance == null) {
            ourInstance = new ApiKey();
        }
        return ourInstance;
    }

    public static void setOurInstance(ApiKey k) {
        if (ourInstance == null) {
            ourInstance = new ApiKey();
        }
        ourInstance = k;
    }

    public static void deleteOurInstance() {
        ourInstance = null;
    }

    public static JsonObject getJsonApiKey() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("apiKey", ourInstance.getApiKey());
        jsonObject.addProperty("apiSecretKey", ourInstance.getApiSecretKey());
        return jsonObject;
    }
}
