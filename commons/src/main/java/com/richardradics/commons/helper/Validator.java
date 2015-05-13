package com.richardradics.commons.helper;

import android.text.TextUtils;

/**
 * Created by PontApps on 2015.03.11..
 */
public class Validator {

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
