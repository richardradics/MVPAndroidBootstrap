package com.richardradics.cleanaa.app;

import com.richardradics.cleanaa.R;
import com.richardradics.core.app.BaseApplication;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.res.ColorRes;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
@EApplication
public class CleanApplication extends BaseApplication {

    @ColorRes(R.color.primary)
    int snackBarColor;

    @AfterInject
    void onAfterInjectFinished() {
        networkErrorHandler.setSnackBarBackgroundColor(snackBarColor);
    }

}
