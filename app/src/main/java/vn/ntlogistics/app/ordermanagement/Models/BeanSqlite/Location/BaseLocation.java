package vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location;

import java.io.Serializable;

/**
 * Created by Zanty on 20/05/2017.
 */

public class BaseLocation implements Serializable {
    private int id;
    private String name;
    private boolean checked;

    public BaseLocation() {
    }

    public BaseLocation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
