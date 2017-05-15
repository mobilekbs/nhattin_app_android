package vn.ntlogistics.app.shipper.Commons.Location;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.ntlogistics.app.shipper.Commons.Location.GeocodeMaps.GetLocationFromAddress;
import vn.ntlogistics.app.shipper.Commons.Location.GeocodeMaps.IGeocode;
import vn.ntlogistics.app.shipper.Models.Outputs.Location.Lonlat;

/**
 * Created by Zanty on 27/07/2016.
 */
public class LocationCommon {
    /*public static LatLng searchLatLngByAddress(Activity context, String address){
        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> addresses = new ArrayList<>();
            try {
                addresses = geocoder.getFromLocationName(address, 1);
            } catch (Exception e) {
                Log.d("Not Get Latlng","________________________");
                e.printStackTrace();
            }
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                return new LatLng(latitude,longitude);
            }
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public static void searchLatLngByAddress(final Activity activity, final String address, final IGeocode geocode){

        GetLocationFromAddress get = new GetLocationFromAddress(activity,address);
        try {
            get.run(new IGeocode() {
                @Override
                public void loadSuccess(Lonlat latLng) {
                    Lonlat latLngR = latLng;
                    if(latLngR == null){
                        Geocoder geocoder = new Geocoder(activity);
                        List<Address> addresses = new ArrayList<>();
                        try {
                            addresses = geocoder.getFromLocationName(address, 1);
                        } catch (Exception e) {
                        }
                        if(addresses.size() > 0) {
                            double latitude= addresses.get(0).getLatitude();
                            double longitude= addresses.get(0).getLongitude();
                            latLngR = new Lonlat(longitude,latitude);
                        }
                    }
                    geocode.loadSuccess(latLngR);
                }
            });
        } catch (Exception e) {
            Geocoder geocoder = new Geocoder(activity);
            List<Address> addresses = new ArrayList<>();
            try {
                addresses = geocoder.getFromLocationName(address, 1);
            } catch (Exception ee) {
            }
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                //latLngR = new LatLng(latitude,longitude);
                geocode.loadSuccess(new Lonlat(longitude,latitude));
            }
        }
    }

    public static Float searchDistanceBetween2Location(Lonlat ll1, Lonlat ll2){
        Location location1 = null;
        if(ll1 != null) {
            location1 = new Location("");
            location1.setLatitude(ll1.getLatitude());
            location1.setLongitude(ll1.getLongitude());
        }
        Location location2 = null;
        if(ll2 != null) {
            location2 = new Location("");
            location2.setLatitude(ll2.getLatitude());
            location2.setLongitude(ll2.getLongitude());
        }
        //Lấy khoảng cách giữa 2 điểm

        try {
            return location1.distanceTo(location2);
        } catch (Exception e) {
            return -1f;
        }
    }

    /*public static String replaceAddress(String address){
        if(address.contains("Q."))
            return address.replace("Q.","Quận ");
        else if(address.contains("Q. "))
            return address.replace("Q. ","Quận ");
        else if(address.contains("Q "))
            return address.replace("Q ","Quận ");
        else if(address.contains("q."))
            return address.replace("q.","Quận ");
        else if(address.contains("q. "))
            return address.replace("q. ","Quận ");
        else if(address.contains("q "))
            return address.replace("q ","Quận ");
        else {
            return replaceAddressNumber(address);
        }
    }*/

    public static String LongLocation(String address){
        for(int i = 1; i < 13; i++){
            if(address.contains("q"+i))
                return address.replace("q"+i,"Quận "+i);
            else if(address.contains("Q"+i))
                return address.replace("Q"+i,"Quận "+i);
            else if(address.contains("Q."+i))
                return address.replace("Q."+i,"Quận "+i);
            else if(address.contains("Q. "+i))
                return address.replace("Q. "+i,"Quận "+i);
            else if(address.contains("Q "+i))
                return address.replace("Q "+i,"Quận "+i);
            else if(address.contains("q."+i))
                return address.replace("q."+i,"Quận "+i);
            else if(address.contains("q. "+i))
                return address.replace("q. "+i,"Quận "+i);
            else if(address.contains("q "+i))
                return address.replace("q "+i,"Quận "+i);
        }

        return LocationCommon.cutAddress(address);
    }

    public static String cutAddress(String s){
        String ss = s.replaceAll("HCM","Hồ Chí Minh");

        int startIndex = s.indexOf("(");
        int endIndex = s.indexOf(")");

        if(startIndex > -1 && endIndex > -1){
            String toBeReplaced = s.substring(startIndex, endIndex+1);
            ss = s.replace(toBeReplaced,"");
            ss = cutAddress(ss);
        }
        else
            return ss;
        return ss;
    }

    public static String cutHeaderAddress(String s){
        String ss = s;
        try {
            String[] lstHeaderAdd = s.split(" ");
            if(lstHeaderAdd.length > 0) {
                String headerAdd = lstHeaderAdd[0];
                if (headerAdd.length() > 0) {
                    Pattern p = Pattern.compile("[^0-9/]", Pattern.CASE_INSENSITIVE);
                    if (!p.matcher(headerAdd.substring(0,1)).find()) {
                        return s.replace(headerAdd + " ", "");
                    }
                    else {
                        try {
                            Matcher matcher = p.matcher(headerAdd.substring(1,2));
                            if(!matcher.find()){
                                return s.replace(headerAdd + " ", "");
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return ss;
    }
}
