package com.richardradics.commons.widget.echeckbox;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class EnhancedToggleButton extends ToggleButton implements ProgrammaticallyCheckable {
    private CompoundButton.OnCheckedChangeListener mListener = null;
    public EnhancedToggleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EnhancedToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhancedToggleButton(Context context) {
        super(context);
    }

    @Override
    public void setOnCheckedChangeListener(
            CompoundButton.OnCheckedChangeListener listener){
        if(this.mListener == null) {this.mListener = listener;}
        super.setOnCheckedChangeListener(listener);
    }

    @Override
    public void setCheckedProgrammatically(boolean checked){
        super.setOnCheckedChangeListener(null);
        super.setChecked(checked);
        super.setOnCheckedChangeListener(mListener);
    }
}