package com.richardradics.commons.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Rcsk on 2014.06.16..
 */
public class DecimalFormatUtil {

    /**
     * Returns a thousans separated decimar formatter.
     *
     * @return
     */
    public static DecimalFormat getThousandSeperatedDecimalFormat() {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);
        return df;
    }


    /**
     *
     * @param value e.g. 1.199999
     * @return 1.2
     */
    public static String formatDouble(double value) {
        return new DecimalFormat("#.##").format(value);
    }

}
