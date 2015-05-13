package com.richardradics.commons.animations;

import android.view.animation.Interpolator;

/**
 * Created by radicsrichard on 15. 03. 28..
 */
public class MaterialInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float x) {
        return (float) (6 * Math.pow(x, 2) - 8 * Math.pow(x, 3) + 3 * Math.pow(x, 4));
    }
}