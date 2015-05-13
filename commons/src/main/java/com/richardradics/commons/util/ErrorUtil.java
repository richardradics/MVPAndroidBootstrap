package com.richardradics.commons.util;


import android.os.Build;

/**
 * Created by Rcsk on 2014.07.11..
 */
public class ErrorUtil {


    public static String getStrackTrace(Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        final StringBuffer report = new StringBuffer(e.toString());

        for (int i = 0; i < arr.length; i++) {
            report.append("    ");
            report.append(arr[i].toString());
            report.append(Constants.Text.SINGLE_LINE_SEP);
        }

        return report.toString();
    }

    public static String getCause(Throwable e) {
        Throwable cause = e.getCause();
        final StringBuffer report = new StringBuffer(e.toString());
        if (cause != null) {
            report.append(cause.toString());
            report.append(Constants.Text.DOUBLE_LINE_SEP);
            StackTraceElement[] arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report.append("    ");
                report.append(arr[i].toString());
                report.append(Constants.Text.SINGLE_LINE_SEP);
            }
        }

        return report.toString();
    }


    public static String getDeviceInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(getDevice());
        sb.append(Constants.Text.DOUBLE_LINE_SEP);
        sb.append(getAndroidRelease());
        sb.append(Constants.Text.DOUBLE_LINE_SEP);
        sb.append(getFirmware());
        return sb.toString();
    }


    public static String getDevice() {
        final StringBuffer report = new StringBuffer();

        report.append("Brand: ");
        report.append(Build.BRAND);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Device: ");
        report.append(Build.DEVICE);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Model: ");
        report.append(Build.MODEL);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Id: ");
        report.append(Build.ID);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Product: ");
        report.append(Build.PRODUCT);
        report.append(Constants.Text.SINGLE_LINE_SEP);

        return report.toString();
    }

    public static String getExceptionType(Throwable e) {
        return e.getClass().toString();
    }

    public static String getFirmware() {
        final StringBuffer report = new StringBuffer();

        report.append("SDK: ");
        report.append(Build.VERSION.SDK);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Release: ");
        report.append(Build.VERSION.RELEASE);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Incremental: ");
        report.append(Build.VERSION.INCREMENTAL);
        report.append(Constants.Text.SINGLE_LINE_SEP);

        return report.toString();
    }

    public static String getSDK() {
        final StringBuffer report = new StringBuffer();

        report.append(Build.VERSION.SDK);

        return report.toString();
    }

    public static String getAndroidRelease() {
        final StringBuffer report = new StringBuffer();

        report.append(Build.VERSION.RELEASE);

        return report.toString();
    }

    public static String getErrorReport(Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        final StringBuffer report = new StringBuffer(e.toString());
        final String lineSeperator = "-------------------------------\n\n";
        report.append(Constants.Text.DOUBLE_LINE_SEP);
        report.append("--------- Stack trace ---------\n\n");
        for (int i = 0; i < arr.length; i++) {
            report.append("    ");
            report.append(arr[i].toString());
            report.append(Constants.Text.SINGLE_LINE_SEP);
        }
        report.append(lineSeperator);
        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        report.append("--------- Cause ---------\n\n");
        Throwable cause = e.getCause();
        if (cause != null) {
            report.append(cause.toString());
            report.append(Constants.Text.DOUBLE_LINE_SEP);
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report.append("    ");
                report.append(arr[i].toString());
                report.append(Constants.Text.SINGLE_LINE_SEP);
            }
        }
        // Getting the Device brand,model and sdk verion details.
        report.append(lineSeperator);
        report.append("--------- Device ---------\n\n");
        report.append("Brand: ");
        report.append(Build.BRAND);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Device: ");
        report.append(Build.DEVICE);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Model: ");
        report.append(Build.MODEL);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Id: ");
        report.append(Build.ID);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Product: ");
        report.append(Build.PRODUCT);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append(lineSeperator);
        report.append("--------- Firmware ---------\n\n");
        report.append("SDK: ");
        report.append(Build.VERSION.SDK);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Release: ");
        report.append(Build.VERSION.RELEASE);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append("Incremental: ");
        report.append(Build.VERSION.INCREMENTAL);
        report.append(Constants.Text.SINGLE_LINE_SEP);
        report.append(lineSeperator);

        return report.toString();
    }

}
