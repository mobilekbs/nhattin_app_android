package vn.ntlogistics.app.shipper.Models.Outputs.Login;

/**
 * Created by Zanty on 10/08/2016.
 */
public class ApiKey {
    private String deviceIdentifier;
    public String apiKey;
    public String apiSecretKey;

    public ApiKey() {
    }

    public ApiKey(String apiKey, String apiSecretKey) {
        this.apiKey = apiKey;
        this.apiSecretKey = apiSecretKey;
    }

    public String getDeviceIdentifier() {
        return deviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        this.deviceIdentifier = deviceIdentifier;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
    }
}
