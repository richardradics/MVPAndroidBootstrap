package com.richardradics.commons.util;

/**
 * Created by PontApps on 2015.03.03..
 */
public class LogUtils {


    private LogUtils() {
        //You shall not pass
    }

    public static String generateTag(Object object) {
        return object.getClass().getCanonicalName();
    }
}


