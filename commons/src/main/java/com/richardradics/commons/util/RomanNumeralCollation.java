package com.richardradics.commons.util;

import java.text.ParseException;
import java.text.RuleBasedCollator;


/**
 * Created by radicsrichard on 15. 04. 30..
 */
public class RomanNumeralCollation extends RuleBasedCollator {

    public RomanNumeralCollation() throws ParseException {
        super(romanNumerals);
    }

    private static String romanNumerals = "&9 < I < II < III < IV < V < VI < VII" +
            " < VIII < IX < X < XI < XII < XIII < XIV < XV < XVI < XVII < XVIII < XIX < XX" +
            " < XXI < XXII < XXIII < XXIV < XXV < XXVI < XXVII < XXVIII < XXIX" +
            " < XXX < XXXI < XL < L < LX < LXX < LXXX < XC < C < CI" +
            " < CL < CC < CCC < CD < D < DC < DCC < DCCC < CM < M < MDC < MDCC < MCM";

}
