package com.richardradics.cleanaa.app;

import com.richardradics.cleanaa.BuildConfig;
import com.richardradics.core.error.ErrorHandler;

import org.androidannotations.annotations.EBean;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
@EBean(scope = EBean.Scope.Singleton)
public class CleanErrorHandler extends ErrorHandler {

    public void logExpception(Exception e) {
        if (BuildConfig.DEBUG) {
            logError(e);
        }
    }

    public void showSnackBar(String message) {
        showError(message);
    }

}
