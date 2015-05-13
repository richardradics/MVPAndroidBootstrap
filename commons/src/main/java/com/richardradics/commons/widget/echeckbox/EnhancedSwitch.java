package com.richardradics.commons.widget.echeckbox;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.Switch;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class EnhancedSwitch extends Switch implements ProgrammaticallyCheckable {
    private CompoundButton.OnCheckedChangeListener mListener = null;

    public EnhancedSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EnhancedSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhancedSwitch(Context context) {
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