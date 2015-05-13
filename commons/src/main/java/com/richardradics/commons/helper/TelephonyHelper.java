package com.richardradics.commons.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

/**
 * Created by Richard Radics on 2015.02.11..
 */
public class TelephonyHelper {

    /**
     * Starts the dial activity with SIM enabled check.
     * @param context
     * @param phone
     */
    public static void startDialActivityWithSimCheck(Context context, String phone) {
        if (isTelephonyEnabled(context)) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + clearPhoneText(phone)));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    /**
     * Starts the dial activity.
     * @param context
     * @param phone
     */
    public static void startDialActivity(Context context, String phone) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + clearPhoneText(phone)));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
    }

    /**
     * Checks the device has sim card.
     * @param context
     * @return
     */
    public static boolean isTelephonyEnabled(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    public static String clearPhoneText(String phone){
        String s = phone.replace("/", "");
        String s2 = s.replace("-","");
        return s2;
    }

}
