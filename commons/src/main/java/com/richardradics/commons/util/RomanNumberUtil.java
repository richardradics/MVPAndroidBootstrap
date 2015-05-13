package com.richardradics.commons.util;

/**
 * Created by radicsrichard on 15. 04. 30..
 */
/**
 * Created by Rcsk on 2014.08.13..
 */
public class RomanNumberUtil {

    public static int romanToInteger(java.lang.String romanNumber) {
        int decimal = 0;
        int lastNumber = 0;
        String romanNumeral = romanNumber.toUpperCase();
         /* operation to be performed on upper cases even if user enters roman values in lower case chars */
        for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
            char convertToDecimal = romanNumeral.charAt(x);

            switch (convertToDecimal) {
                case 'M':
                    decimal = processDecimal(1000, lastNumber, decimal);
                    lastNumber = 1000;
                    break;

                case 'D':
                    decimal = processDecimal(500, lastNumber, decimal);
                    lastNumber = 500;
                    break;

                case 'C':
                    decimal = processDecimal(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;

                case 'L':
                    decimal = processDecimal(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;

                case 'X':
                    decimal = processDecimal(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;

                case 'V':
                    decimal = processDecimal(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;

                case 'I':
                    decimal = processDecimal(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;
            }
        }
        return decimal;
    }

    public static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
        if (lastNumber > decimal) {
            return lastDecimal - decimal;
        } else {
            return lastDecimal + decimal;
        }
    }

    final static RomanValue[] ROMAN_VALUE_TABLE = {
            new RomanValue(1000, "M"),
            new RomanValue( 900, "CM"),
            new RomanValue( 500, "D"),
            new RomanValue( 400, "CD"),
            new RomanValue( 100, "C"),
            new RomanValue(  90, "XC"),
            new RomanValue(  50, "L"),
            new RomanValue(  40, "XL"),
            new RomanValue(  10, "X"),
            new RomanValue(   9, "IX"),
            new RomanValue(   5, "V"),
            new RomanValue(   4, "IV"),
            new RomanValue(   1, "I")
    };

    public static String integerToRoman(int n) {
        if (n >= 4000  || n < 1) {
            throw new NumberFormatException("Numbers must be in range 1-3999");
        }
        StringBuffer result = new StringBuffer(10);

        //... Start with largest value, and work toward smallest.
        for (RomanValue equiv : ROMAN_VALUE_TABLE) {
            //... Remove as many of this value as possible (maybe none).
            while (n >= equiv.intVal) {
                n -= equiv.intVal;            // Subtract value.
                result.append(equiv.romVal);  // Add roman equivalent.
            }
        }
        return result.toString();
    }

    private static class RomanValue {
        //============================================================== fields
        //... No need to make this fields private because they are
        //    used only in this private value class.
        int    intVal;     // Integer value.
        String romVal;     // Equivalent roman numeral.

        //========================================================= constructor
        RomanValue(int dec, String rom) {
            this.intVal = dec;
            this.romVal = rom;
        }
    }

}