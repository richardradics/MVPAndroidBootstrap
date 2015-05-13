package com.richardradics.commons.helper;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: commons
 * Package: com.richardradics.commons.helper
 * Created by richardradics on 2015.02.26..
 */
public class DataHelper {

    public static Integer getIntValueWithDefault0(String s) {
        Integer i = 0;
        String s2 = s.trim();
        if (StringUtils.isNotBlank(s2) && StringUtils.isNumeric(s2)) {
            return Integer.parseInt(s2);
        }

        return i;
    }


    public static String getHungarianAcronym(String s) {

        List<String> hungarianChars = new ArrayList<String>();
        hungarianChars.add("Cs");
        hungarianChars.add("Zs");
        hungarianChars.add("Sz");
        hungarianChars.add("Ly");
        hungarianChars.add("Gy");
        hungarianChars.add("Ny");
        hungarianChars.add("Ty");

        List<Character> secCars = new ArrayList<Character>();
        secCars.add('y');
        secCars.add('s');
        secCars.add('z');
        StringBuilder sb = new StringBuilder();

        int count = 0;
        char[] currentWord = s.toCharArray();
        for (int i = 0; i < currentWord.length; i++) {
            char c = currentWord[i];
            StringBuilder tmp = new StringBuilder();
            if (65 <= c && c <= 90) {
                sb.append(c);
                tmp.append(c);
                count++;
                if (i != currentWord.length - 1 && secCars.contains(currentWord[i + 1])) {
                    tmp.append(currentWord[i + 1]);
                    if (hungarianChars.contains(tmp.toString())) {
                        sb.append(currentWord[i + 1]);
                    }
                }
            }
        }

        if (count == 1) {
            return s;
        } else {
            return sb.toString();
        }
    }

    public static String getAcronym(String s) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (char c : s.toCharArray()) {
            if (65 <= c && c <= 90) {
                sb.append(c);
                count++;
            }
        }

        if (count == 1) {
            return s;
        } else {
            return sb.toString();
        }
    }

    public static int getPercent(int n, int v) {
        return (n * 100) / v;
    }

    public static Long getLongValueWithDefault0(String s) {
        Long i = 0L;

        String s2 = s.trim();
        if (StringUtils.isNotBlank(s2) && StringUtils.isNumeric(s2)) {
            return Long.parseLong(s2);
        }

        return i;
    }


    public static Double getDoubleValueWithDefault0(String s) {
        Double i = 0D;

        String s2 = s.trim();
        if (StringUtils.isNotBlank(s2)) {
            return Double.parseDouble(s2);
        }

        return i;
    }

    /**
     * @param s 0 or 1
     * @return 0 = false, 1 = true
     */
    public static Boolean getBooleanValueFromIntWithDefaultFalse(String s) {
        Boolean i = false;
        String s2 = s.trim();
        if (StringUtils.isNotBlank(s2) && StringUtils.isNumeric(s2)) {
            Integer number = Integer.parseInt(s);

            if (number.equals(0)) {
                return Boolean.FALSE;
            } else if (number.equals(1)) {
                return Boolean.TRUE;
            }

        }

        return i;
    }

    public static Integer getIntValueWithDefaultNull(String s) {
        Integer i = null;
        String s2 = s.trim();
        if (StringUtils.isNotBlank(s2) && StringUtils.isNumeric(s2)) {
            return Integer.parseInt(s2);
        }

        return i;
    }

    public static Long getLongValueWithDefaultNull(String s) {
        Long i = null;

        String s2 = s.trim();
        if (StringUtils.isNotBlank(s2) && StringUtils.isNumeric(s2)) {
            return Long.parseLong(s2);
        }

        return i;
    }

    public static Double getDoubleValueWithDefaultNull(String s) {
        Double i = null;

        String s2 = s.trim();
        if (StringUtils.isNotBlank(s2)) {
            return Double.parseDouble(s2);
        }

        return i;
    }

    public static String convertBooleanToString(Boolean b) {
        if (b == null) {
            return "Nincs adat.";
        }

        if (b.booleanValue() == Boolean.TRUE) {
            return "Igen";
        } else {
            return "Nem";
        }

    }

    public static double round(double d, int decimalPlace) {
        // see the Javadoc about why we use a String in the constructor
        // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }


}
