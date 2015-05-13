package com.richardradics.commons.widget.echeckbox;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.richardradics.commons.util.ViewUtils;

/**
 * An enhanced {@code CheckBox} that differentiates between user clicks and
 * programmatic clicks. In particular, the {@code OnCheckedChangeListener} is
 * <strong>not</strong> triggered when the state of the checkbox is changed
 * programmatically.
 *
 */
public class EnhancedCheckbox extends CheckBox implements ProgrammaticallyCheckable{
    private CompoundButton.OnCheckedChangeListener mListener = null;
    public EnhancedCheckbox(Context context) {
        super(context);
    }

    public EnhancedCheckbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EnhancedCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener listener) {
        if (this.mListener == null) {this.mListener = listener;}
        super.setOnCheckedChangeListener(listener);
    }
    /**
     * Set the checked state of the checkbox programmatically. This is to differentiate it from a user click
     * @param checked Whether to check the checkbox
     */
    @Override
    public void setCheckedProgrammatically(boolean checked) {
        super.setOnCheckedChangeListener(null);
        super.setChecked(checked);
        super.setOnCheckedChangeListener(mListener);
    }

    @Override
    public int getCompoundPaddingLeft() {
        return   ViewUtils.convertToPix(this.getResources().getDisplayMetrics().density, 20);
    }


}