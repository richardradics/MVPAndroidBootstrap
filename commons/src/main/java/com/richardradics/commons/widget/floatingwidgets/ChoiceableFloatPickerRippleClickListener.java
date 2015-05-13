package com.richardradics.commons.widget.floatingwidgets;

import android.view.View;

import com.marvinlabs.widget.floatinglabel.FloatingLabelTextViewBase;

/**
 * Created by Richard Radics on 2015.02.12..
 */
public class ChoiceableFloatPickerRippleClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        (((FloatingLabelTextViewBase)v).getInputWidget()).performClick();
    }
}
