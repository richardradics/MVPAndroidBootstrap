package com.richardradics.core.mvp;

/**
 * Created by radicsrichard on 15. 04. 28..
 */
public interface Presenter {

    public void setView(View view);
    public void start();
    public void stop();
    public void onError(Exception exception);
}
