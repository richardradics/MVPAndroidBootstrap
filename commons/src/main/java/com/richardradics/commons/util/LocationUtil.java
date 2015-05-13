package com.richardradics.commons.util;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by PontApps on 2015.03.02..
 */
public class LocationUtil {

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p/>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @param el1  pass 0.0 if you not intrested in height
     * @param el2  pass 0.0 if you not intrested in height
     * @return distance in meters
     */
    public static double distance(double lat1, double lat2, double lon1, double lon2,
                                  double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = deg2rad(lat2 - lat1);
        Double lonDistance = deg2rad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * The calculateKilometers method displays the kilometers that are equivalent to
     * a specified number of meters.
     *
     * @param meters
     * @return the number of kilometers
     */
    public static double calculateKilometers(double meters) {

        double kilometers = meters * 0.001;

        return kilometers;
    }

    public static boolean isGpsEnabled(Context context){
        LocationManager lm = null;
        boolean gps_enabled = false;
        if(lm==null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try{
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex){}

        if(!gps_enabled){
            return false;
        }else {
            return true;
        }
    }

    public static boolean isLocationProvidersEnabled(Context context){
        LocationManager lm = null;
        boolean gps_enabled = false,network_enabled = false;
        if(lm==null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try{
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex){}
        try{
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception ex){}

        if(!gps_enabled && !network_enabled){
            return false;
        }else {
            return true;
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    public static void startLocationSettingsActivity(Context context){
        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

    public static void startGoogleMapsActivityWitPoiAndTitle(Context context, Double latitude, Double longitude, String title) {
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("http://maps.google.com/maps?q=loc:");
            sb.append(latitude);
            sb.append(", ");
            sb.append(longitude);

            if(title != null) {
                sb.append(" (");
                sb.append(title);
                sb.append(")");
            }


            Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
            mapsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mapsIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method should calculate inches that are equivalent to a specified
     * number of meters.
     *
     * @param meters
     * @return the number of inches
     */
    public static double calculateInches(double meters) {

        double inches = meters * 39.37;

        return inches;
    }

    /**
     * This method should calculate the feet that are equivalent to a specified
     * number of meters.
     *
     * @param meters
     * @return The number of feet.
     */
    public static double calculateFeet(double meters) {

        double feet = meters * 3.281;

        return feet;
    }

}
