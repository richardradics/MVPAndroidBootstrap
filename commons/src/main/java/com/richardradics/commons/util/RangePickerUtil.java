package com.richardradics.commons.util;

/**
 * Created by richardradics on 2015.02.17..
 */
public class RangePickerUtil {

    /**
     * Prints range selection
     *
     * @param leftValue 5
     * @param rightValue 10
     * @param separator -
     * @return 5 - 10
     */
    public static String printRangeValue(String leftValue, String rightValue, String separator){
        StringBuilder sb = new StringBuilder();
        sb.append(leftValue+ " " );
        sb.append(separator);
        sb.append(" " + rightValue);

        return sb.toString();
    }

}
