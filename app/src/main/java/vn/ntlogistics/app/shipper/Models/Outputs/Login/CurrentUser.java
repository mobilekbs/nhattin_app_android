package vn.ntlogistics.app.shipper.Models.Outputs.Login;

import vn.ntlogistics.app.shipper.Models.Outputs.User.User;

/**
 * Created by Zanty on 10/08/2016.
 */
public class CurrentUser extends User {
    private String sessionToken;

    public CurrentUser() {
    }

    public CurrentUser(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
