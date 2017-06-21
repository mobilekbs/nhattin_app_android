package vn.ntlogistics.app.ordermanagement.Commons.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by minhtan2908 on 1/11/17.
 */

public class CurrentLocation {
    Context context;

    private LocationManager mLocationMgr;

    private Location mLastLocation;
    //private boolean mGeocoderAvailable;

    private static final int UPDATE_LASTLATLNG = 4;
    private static final int LAST_UP = 3;
    private static final int UPDATE_LATLNG = 2;
    private static final int UPDATE_ADDRESS = 1;

    private static final int SECONDS_TO_UP = 10000;
    private static final int METERS_TO_UP = 10;
    private static final int MINUTES_TO_STALE = 1000 * 60 * 2;

    public CurrentLocation(Context context) {
        this.context = context;
        onCreate();
        setup();
    }

    public Location getLastLocation(){
        return mLastLocation;
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        double latitude = 0;
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
        }
        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        double longitude = 0;
        if (mLastLocation != null) {
            longitude = mLastLocation.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public void onCreate() {
        //mGeocoderAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && Geocoder.isPresent();
        mLocationMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    protected void onStop() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationMgr.removeUpdates(listener);
    }

    private void setup() {
        Location newLocation = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationMgr.removeUpdates(listener);

        newLocation = requestUpdatesFromProvider(LocationManager.PASSIVE_PROVIDER);

        if (newLocation == null)
            newLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER);

        // If gps location doesn't work, try network location
        if (newLocation == null) {
            newLocation = requestUpdatesFromProvider(LocationManager.NETWORK_PROVIDER);
        }

        if (newLocation != null) {
            updateUILocation(getBestLocation(newLocation, mLastLocation));
        }
    }

    /**
     * This code is based on this code: http://developer.android.com/guide/topics/location/obtaining-user-location.html
     * @param newLocation
     * @param currentBestLocation
     * @return
     */
    protected Location getBestLocation(Location newLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return newLocation;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isNewerThanStale = timeDelta > MINUTES_TO_STALE;
        boolean isOlderThanStale = timeDelta < -MINUTES_TO_STALE;
        boolean isNewer = timeDelta > 0;

        if (isNewerThanStale) {
            return newLocation;
        } else if (isOlderThanStale) {
            return currentBestLocation;
        }

        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return newLocation;
        } else if (isNewer && !isLessAccurate) {
            return newLocation;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return newLocation;
        }
        return currentBestLocation;
    }

    private Location requestUpdatesFromProvider(final String provider) {
        Location location = null;
        if (mLocationMgr.isProviderEnabled(provider)) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            mLocationMgr.requestLocationUpdates(provider, SECONDS_TO_UP, METERS_TO_UP, listener);
            location = mLocationMgr.getLastKnownLocation(provider);
            mLocationMgr.removeUpdates(listener);
        }
        return location;
    }

    /*private void doReverseGeocoding(Location location) {
        (new ReverseGeocode(context)).execute(new Location[] {location});
    }*/

    private void updateUILocation(Location location) {
        /*Message.obtain(mHandler, UPDATE_LATLNG, location.getLatitude() + ", " + location.getLongitude()).sendToTarget();
        if (mLastLocation != null) {
            Message.obtain(mHandler, UPDATE_LASTLATLNG, mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude()).sendToTarget();
        }*/
        mLastLocation = location;
        /*Date now = new Date();
        Message.obtain(mHandler, LAST_UP, now.toString()).sendToTarget();*/

        /*if (mGeocoderAvailable)
            doReverseGeocoding(location);*/
    }

    private final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateUILocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };


    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}

