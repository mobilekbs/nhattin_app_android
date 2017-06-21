package vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location;

import java.io.Serializable;

/**
 * Created by Zanty on 19/05/2017.
 */

public class District extends BaseLocation implements Serializable {
    private int value;
    private int cityID;

    public District() {
    }

    public District(int id, int value, String name, int cityID) {
        super(id, name);
        this.value = value;
        this.cityID = cityID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }
}
