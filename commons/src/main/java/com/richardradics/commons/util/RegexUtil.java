package com.richardradics.commons.util;

/**
 * Common regexps.
 *
 * Created by Richard Radics on 2015.02.11..
 */
public class RegexUtil {


    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // public static final String PASSWORD_PATTERN =
    // "(?=(.*[a-zA-Z]))^[0-9a-zA-Z!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]{4,}$";

    public static final String PASSWORD_PATTERN = ".{4,}";


    public static final String USERNAME_PATTERN = "(?=(.*[a-zA-Z]){3})^[0-9a-zA-Z]{3,}$";

    public static final String ANYTHING_PATTERN = ".*";

    // public static final String USERNAME_LENGTH_PATTERN =
    // "((?=.*\d)|(?=.*[a-z])|(?=.*[a-z])|(?=.*[A-Z]).{5,20})";

    public static final String FULL_NAME_PATTERN = "^([ \\x{00C0}-\\x{01FF}a-zA-Z\\'\\-\\.])*$"; //

    public static final String PHONE_PATTERN = "^[0-9]{8,13}$";



}
