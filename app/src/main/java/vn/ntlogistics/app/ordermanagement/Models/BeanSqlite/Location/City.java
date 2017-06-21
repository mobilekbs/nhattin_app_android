package vn.ntlogistics.app.ordermanagement.Models.BeanSqlite.Location;

import java.io.Serializable;

/**
 * Created by Zanty on 19/05/2017.
 */

public class City extends BaseLocation implements Serializable {
    private int areacode;
    private int idPosition;

    public City() {
    }

    public City(int id, String name, int areacode, int idPosition) {
        super(id, name);
        this.areacode = areacode;
        this.idPosition = idPosition;
    }

    public int getAreacode() {
        return areacode;
    }

    public void setAreacode(int areacode) {
        this.areacode = areacode;
    }

    public int getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }
}
