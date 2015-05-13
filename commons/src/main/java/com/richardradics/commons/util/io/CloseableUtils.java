package com.richardradics.commons.util.io;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

/**
 * Utility for the {@link java.io.Closeable}.
 */
@SuppressWarnings("unused") // public APIs
public final class CloseableUtils {
    private static final String TAG = CloseableUtils.class.getSimpleName();
    private CloseableUtils() {}

    /**
     * Close closeable like i/o streams quietly.
     * Do NOT close {@link android.database.Cursor} with this method, or will cause crashing on some device.
     * @param closeable to close.
     */
    public static final void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            Log.e(TAG, "something went wrong on close", e);
        }
    }
}