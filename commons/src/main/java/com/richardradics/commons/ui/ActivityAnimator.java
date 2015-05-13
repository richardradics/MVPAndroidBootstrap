package com.richardradics.commons.ui;

import android.app.Activity;

import com.richardradics.commons.R;

/**
 * Created by Richard Radics on 2015.02.09..
 */
public class ActivityAnimator {

    public static void flipHorizontalAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);
    }

    public static void flipVerticalAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.flip_vertical_in, R.anim.flip_vertical_out);
    }

    public static void fadeAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void disappearTopLeftAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.disappear_top_left_in, R.anim.disappear_top_left_out);
    }

    public static void appearTopLeftAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.appear_top_left_in, R.anim.appear_top_left_out);
    }

    public static void disappearBottomRightAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.disappear_bottom_right_in, R.anim.disappear_bottom_right_out);
    }

    public static void appearBottomRightAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
    }

    public static void unzoomAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.unzoom_in, R.anim.unzoom_out);
    }

    public static void PullRightPushLeft(Activity a)
    {
        a.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
    public static void PullLeftPushRight(Activity a)
    {
        a.overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

}
