package vn.ntlogistics.app.shipper.Models.Outputs.Others;

/**
 * Created by Zanty on 26/07/2016.
 */
public class Vehicle {
    private String id;
    private String name;

    public Vehicle() {
    }

    public Vehicle(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
