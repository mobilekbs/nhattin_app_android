package vn.ntlogistics.app.ordermanagement.Models.Inputs;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Zanty on 20/06/2017.
 */

public class CommonInput<T> implements Serializable {

    private T data;

    public CommonInput() {
    }

    public String toJson(){
        return new Gson().toJson(this);
    }

    public CommonInput(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
