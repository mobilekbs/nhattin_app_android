package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import android.content.Context;

import com.google.gson.Gson;

import java.io.Serializable;

import vn.ntlogistics.app.ordermanagement.Commons.Singleton.SCurrentUser;

/**
 * Created by Zanty on 20/05/2017.
 */

public class BaseInput implements Serializable {
    private String androidKey;

    public BaseInput(Context context) {
        androidKey = SCurrentUser.getCurrentUser(context).getPublickey();
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    public String getAndroidKey() {
        return androidKey;
    }

    public void setAndroidKey(String androidKey) {
        this.androidKey = androidKey;
    }
}
