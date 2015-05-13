package com.richardradics.commons.util;

import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;

/**
 * Created by Richard Radics on 2015.02.12..
 */
public class PhoneUtil {

    /**
     * Checks to see if the user has rotation enabled/disabled in their phone settings.
     *
     * @param context The current Context or Activity that this method is called from
     * @return true if rotation is enabled, otherwise false.
     */
    public static boolean isRotationEnabled(Context context) {
        return android.provider.Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

    /**
     * Retuns device DPI.
     * @param c
     * @return
     */
    public static int deviceDPI(Context c) {
        return c.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * Retuns device resolution.
     * @param c
     * @return
     */
    public static String deviceResolution(Context c) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        return String.valueOf(metrics.widthPixels) + "x" + metrics.heightPixels;
    }

}
