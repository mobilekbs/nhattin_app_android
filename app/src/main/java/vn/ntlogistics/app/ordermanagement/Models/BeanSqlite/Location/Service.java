package vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location;

import java.io.Serializable;

/**
 * Created by Zanty on 19/05/2017.
 */

public class Service extends BaseLocation implements Serializable{
    private int value;

    public Service() {
    }

    public Service(int id, int value, String name) {
        super(id,name);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
