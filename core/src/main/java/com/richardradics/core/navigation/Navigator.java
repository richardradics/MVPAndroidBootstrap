package com.richardradics.core.navigation;

import android.content.Context;

import com.richardradics.core.app.BaseActivity;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;


/**
 * Created by radicsrichard on 15. 03. 28..
 */
@EBean(scope = EBean.Scope.Singleton)
public class Navigator {

    @RootContext
    Context context;


    private BaseActivity currentActivityOnScreen;


    public BaseActivity getCurrentActivityOnScreen() {
        return currentActivityOnScreen;
    }

    public void setCurrentActivityOnScreen(BaseActivity currentActivityOnScreen) {
        this.currentActivityOnScreen = currentActivityOnScreen;
    }

    public static void navigateUp(BaseActivity activity) {
        activity.finish();
    }

    public boolean isInForeground(){
        if(currentActivityOnScreen != null){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

}
