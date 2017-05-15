package vn.ntlogistics.app.shipper.Commons.Location.GeocodeMaps;


import vn.ntlogistics.app.shipper.Models.Outputs.Location.Lonlat;

/**
 * Created by minhtan2908 on 10/8/16.
 */

public interface IGeocode {
    void loadSuccess(Lonlat latLng);
}
