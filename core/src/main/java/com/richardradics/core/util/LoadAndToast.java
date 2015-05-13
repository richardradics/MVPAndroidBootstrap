package com.richardradics.core.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.richardradics.commons.util.DensityUtil;
import com.richardradics.commons.widget.loadtoast.LoadToast;
import com.richardradics.core.navigation.Navigator;


import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by mark on 2015. 04. 29..
 */
@EBean(scope = EBean.Scope.Singleton)
public class LoadAndToast {
    @RootContext
    Context context;

    @Bean
    CommonUseCases commonUseCases;

    LoadToast loadToast;
    Snackbar loadingSnackBar;


    protected void init(Context context) {
        if (context != null) {
            loadToast = new LoadToast(context);
            setupLoadToast();
            loadingSnackBar = Snackbar.with(context);
            setupSnackBar();
        }
    }

    protected void setupLoadToast() {
        if (loadToast != null) {
            loadToast.setTranslationY((commonUseCases.getScreenSize().y-commonUseCases.getNavigationBarHeight() - commonUseCases.getScreenSize().y / 5)-5);
          //  loadToast.setTranslationY(Float.floatToIntBits(DensityUtil.convertPixelsToDp(commonUseCases.getScreenSize().y,context) - DensityUtil.convertPixelsToDp(commonUseCases.getScreenSize().y/7,context)));
        }
    }

    protected void setupSnackBar() {
        if (loadingSnackBar != null) {
            loadingSnackBar.duration(Snackbar.SnackbarDuration.LENGTH_SHORT);
        }
    }

    public void showLoading(Context context, String message) {
        if (loadingSnackBar == null)
            init(context);
        loadToast.setText(message);
        loadToast.show();
    }

    public void endLoading(boolean success) {
        if (loadToast != null)
            if (success)
                loadToast.success();
            else
                loadToast.error();
    }


    public void showMessage(Context context, String message) {
        if (loadToast == null)
            init(context);

        loadingSnackBar.text(message);
        SnackbarManager.show(loadingSnackBar);
    }
}
