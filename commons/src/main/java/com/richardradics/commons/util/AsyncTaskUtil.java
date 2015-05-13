package com.richardradics.commons.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by Richard Radics on 2015.02.09..
 */
public class AsyncTaskUtil {

    /**
     * AsyncTask executor, if the phone is below HoneyComb it's use a single executor
     * above HoneyComb parallal executor.
     * @param task
     * @param <P>
     * @param <T>
     */
    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task) {
        execute(task, (P[]) null);
    }

    @SuppressLint("NewApi")
    public static <P, T extends AsyncTask<P, ?, ?>> void execute(T task, P... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }
}
