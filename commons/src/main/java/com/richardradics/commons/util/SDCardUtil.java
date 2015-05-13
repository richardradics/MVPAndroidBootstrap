package com.richardradics.commons.util;

import android.os.Environment;
import android.os.StatFs;

/**
 * Created by Richard Radics on 2015.02.11..
 */
public class SDCardUtil {

    /**
     * Returns the SD card is available.
     * @return
     */
    public static Boolean checkAvailable() {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks the SD card is readonly.
     * @return
     */
    public static Boolean checkIsReadOnly() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the available space in megabytes.
     * @return
     */
    public static int getAvailableSpaceInMegaBytes() {
        int availableSpace = 0;

        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            availableSpace = stat.getAvailableBlocks() * stat.getBlockSize() / 1048576;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableSpace;
    }


}
