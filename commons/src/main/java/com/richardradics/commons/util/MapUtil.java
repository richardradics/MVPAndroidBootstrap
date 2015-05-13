package com.richardradics.commons.util;

import java.util.Map;

/**
 * Created by radicsrichard on 15. 04. 16..
 */
public class MapUtil {

    public static <T,Z> T getSingleValue(Map<String, Z> map) {
        return (T) map.values().toArray()[0];
    }

    public static <T, Z> T getSingleKey(Map<String, Z> map) {
        return (T) map.keySet().toArray()[0];
    }

}
