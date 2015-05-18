package com.richardradics.core.retrofit;

import com.richardradics.core.error.NetworkErrorHandler;
import com.richardradics.core.navigation.Navigator;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import hugo.weaving.DebugLog;

/**
 * Created by radicsrichard on 15. 05. 16..
 */
@EBean
public class BaseRetryHandler {

    @Bean
    protected NetworkErrorHandler networkErrorHandler;

    @Bean
    protected Navigator navigator;

    public int DEFAULT_RETRY_COUNT = 2;
    public int RETRY_401_UNAUTHORIZED = 3;
    public int RETRY_400_BADREQUEST = 3;
    public int RETRY_403_FORBIDDEN = 3;
    public int RETRY_404_NOTFOUND = 3;
    public int RETRY_500_ISE = 3;


    @DebugLog
    public void on500() {

    }

    @DebugLog
    public void on401() {

    }

    @DebugLog
    public void on400() {

    }


    @DebugLog
    public void on404() {

    }

    @DebugLog
    public void on403() {

    }

    public void onNoInternet() {
        if (navigator.isInForeground()) {
            navigator.getCurrentActivityOnScreen().onNoConnectivityError();
        }
        networkErrorHandler.handlerError(new NoConnectivityException("Nincs internet!"));
    }

}
