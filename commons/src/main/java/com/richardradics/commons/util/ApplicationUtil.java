package com.richardradics.commons.util;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.PowerManager;

/**
 * Created by Richard Radics on 2015.02.09..
 */
public class ApplicationUtil {

    /**
     * Returns the application versionname.
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        String version = "";

        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return version;
    }

    /**
     * Starts Google Play application for this application.
     * @param context
     */
    public static void startGooglePlay(Context context) {
        final String appPackageName = context.getPackageName();// context.getPackageName();
        // //
        // getPackageName()
        // from Context
        // or Activity
        // object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /**
     * Starts Google Play Services in Play application.
     * @param context
     */
    public static void startGooglePlayServicesRefresh(Context context){
        try{
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.gms")));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Wake ups the phone.
     * @param context
     */
    public static void partialWakeUpLock(Context context){
        PowerManager pm = (PowerManager)context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();
    }

    /**
     * Release the devices screen lock.
     * @param context
     */
    public static void releaseScreenLock(Context context){
        KeyguardManager keyguardManager = (KeyguardManager) context.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
    }

    /**
     * Wake ups and release the screen lock.
     * @param context
     */
    public static void wakeLockWithScreenUnLock(Context context){
        partialWakeUpLock(context);
        releaseScreenLock(context);
    }



}
