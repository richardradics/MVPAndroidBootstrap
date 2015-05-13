package com.richardradics.commons.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Richard Radics on 2014.12.08..
 */
public class TextUtil {

    private final static String EMPTY_STRING = "";
    private final static String THREE_DOTS = "…";

    public static String getLineSepartor() {
        return System.getProperty("line.separator");
    }

    public static String getDoubleLineSeparator() {
        StringBuilder sb = new StringBuilder();
        sb.append(getLineSepartor());
        sb.append(getLineSepartor());
        return sb.toString();
    }

    public static float getScaleIndependentSize(Context context, int fontSize) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, fontSize, metrics);
        return val;
    }

    /**
     * Truncates the string provided if its length is more than the value provided, and replaces
     * the extra text by "…" ensuring that the resulting length of the string will be
     * as maximum as the param maximumLengthAllowed
     *
     * @param maximumLengthAllowed The maximum allowed length for the provided string
     * @param string               The string that will be truncated if necessary
     * @return The original string if its length is less than maximumLengthAllowed,
     * or the string cropped and … appended at the end if it's length is more than maximumLengthAllowed
     */
    public static String truncateIfLengthMoreThan(final int maximumLengthAllowed, String string) {
        if (string.length() > maximumLengthAllowed) {
            return string.substring(0, maximumLengthAllowed - THREE_DOTS.length()).concat(THREE_DOTS);
        } else {
            return string;
        }
    }

}
