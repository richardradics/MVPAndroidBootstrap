package com.richardradics.core.error;

import com.richardradics.core.retrofit.NoConnectivityException;

import org.androidannotations.annotations.EBean;

/**
 * Created by radicsrichard on 15. 04. 28..
 */
@EBean(scope = EBean.Scope.Singleton)
public class NetworkErrorHandler extends ErrorHandler {

    @Override
    public void handlerError(Exception e) {
        super.handlerError(e);
        String s = "Hiba történt!";
        if (e instanceof NoConnectivityException) {
            s = "Nincs internet!";
            showError(s);
        }
    }

}
