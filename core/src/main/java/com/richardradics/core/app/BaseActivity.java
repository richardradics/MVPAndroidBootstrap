package com.richardradics.core.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.richardradics.commons.util.ErrorUtil;
import com.richardradics.core.error.NetworkErrorHandler;
import com.richardradics.core.navigation.Navigator;
import com.richardradics.core.retrofit.NoConnectivityListener;
import com.richardradics.core.util.CommonUseCases;
import com.richardradics.core.util.LoadAndToast;


import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by radicsrichard on 15. 04. 28..
 */
@EActivity
public abstract class BaseActivity extends ActionBarActivity implements NoConnectivityListener {

    @Bean
    protected Navigator navigator;

    @Bean
    protected CommonUseCases commonUseCases;

    @Bean
    protected NetworkErrorHandler networkErrorHandler;

    @Bean
    protected LoadAndToast progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
    }

    protected void handleUncaughtException(Thread thread, Throwable e) {
        Log.d("Report :: ", ErrorUtil.getErrorReport(e));

        String cause = ErrorUtil.getCause(e);
        String errorType = ErrorUtil.getExceptionType(e);
        String stackTrace = ErrorUtil.getStrackTrace(e);
        String deviceInfo = ErrorUtil.getDeviceInfo();

        Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
        i.putExtra(ErrorActivity.EXCEPTION_TYPE_ARG, errorType);
        i.putExtra(ErrorActivity.STACKTRACE_ARG, stackTrace);
        i.putExtra(ErrorActivity.DEVICE_INFO_ARG, deviceInfo);
        i.putExtra(ErrorActivity.CAUSE_ARG, cause);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);


        System.exit(0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        navigator.setCurrentActivityOnScreen(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigator.setCurrentActivityOnScreen(null);
    }

    @Override
    public void onNoConnectivityError(){
        progress.endLoading(false);
    }


}
