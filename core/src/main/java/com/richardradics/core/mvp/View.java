package com.richardradics.core.mvp;

/**
 * Created by radicsrichard on 15. 04. 28..
 */
public interface View {
    void showLoading (String message);
    void hideLoading (boolean sucess);
    void showActionLabel(String message);
    void hideActionLabel ();
}
