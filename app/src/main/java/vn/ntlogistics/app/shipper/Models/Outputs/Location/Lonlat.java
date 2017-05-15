package vn.ntlogistics.app.shipper.Models.Outputs.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zanty on 04/07/2016.
 */
public class Lonlat {
    private double longitude;
    private double latitude;
    private long time;

    public Lonlat() {
    }

    public Lonlat(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Lonlat(double longitude, double latitude, long time) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("longitude", longitude);
        result.put("latitude", latitude);
        result.put("time", time);

        return result;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
