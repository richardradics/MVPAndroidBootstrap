package com.richardradics.commons.util;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

/**
 * Created by Richard Radics on 2015.02.12..
 */
public class ServiceUtil {

    /**
     * Checks the specified service is running.
     * @param service
     * @param context
     * @return
     */
    public static boolean isServiceRunning(Class<? extends Service> service, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
