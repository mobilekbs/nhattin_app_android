package vn.ntlogistics.app.ordermanagement.Commons.Location.GeocodeMaps;


import vn.ntlogistics.app.ordermanagement.Commons.Location.Lonlat;

/**
 * Created by minhtan2908 on 10/8/16.
 */

public interface IGeocode {
    void loadSuccess(Lonlat latLng);
}
