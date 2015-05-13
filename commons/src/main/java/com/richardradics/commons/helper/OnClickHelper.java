package com.richardradics.commons.helper;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * Created by Richard Radics on 2015.02.11..
 */
public class OnClickHelper {
    private static Date occupiedUntil = null;

    public static int DEFAULT_WAIT_MS = 600;

    /**
     * Tries to occupy the onclick enabler.
     * @param waitMs the time interval to occupy
     * @return if noone occupied it, returns true, else false
     */
    public static boolean occupy(int waitMs){
        if(occupiedUntil != null && occupiedUntil.after(new Date())){
            return false;
        }
        occupiedUntil = DateUtils.addMilliseconds(new Date(), waitMs);
        return true;
    }

    /**
     * Tries to occupy the onclick enabler for the default time limit
     * @return if noone occupied it, returns true, else false
     */
    public static boolean occupy(){
        return occupy(DEFAULT_WAIT_MS);
    }
}
