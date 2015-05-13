package com.richardradics.commons.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Richard Radics on 2015.02.11..
 */
public class NetworkUtil {

    /**
     * Checks the internet is active.
     * @param context
     * @return
     */
    public static boolean checkInternetIsActive(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || mobile.isConnected()) {
            return true;
        }

        return false;

    }

    /**
     * Checks wifi is active.
     * @param context
     * @return
     */
    public static boolean checkWifiIsActive(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * Checks mobile network is active.
     * @param context
     * @return
     */
    public static boolean checkMobileIsActive(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile.isConnected()) {
            return true;
        }
        return false;
    }

}
