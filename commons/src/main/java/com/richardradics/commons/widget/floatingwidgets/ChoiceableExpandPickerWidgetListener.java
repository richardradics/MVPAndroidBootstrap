package com.richardradics.commons.widget.floatingwidgets;

import android.view.View;
import android.widget.TextView;

import com.richardradics.commons.animations.ExpandAnimation;

/**
 * Created by Richard Radics on 2015.02.12..
 */
public class ChoiceableExpandPickerWidgetListener implements FloatingLabelItemPickerWithSetSelectedItems.OnWidgetEventListener{

    private boolean isExpanded = false;

    private View viewToExpand;

    public ChoiceableExpandPickerWidgetListener(View viewToExpand){
        this.viewToExpand = viewToExpand;
    }


    @Override
    public void onShowItemPickerDialog(FloatingLabelItemPickerWithSetSelectedItems floatingLabelItemPicker) {
        if (!isExpanded) {
            floatingLabelItemPicker.floatLabel();
            ExpandAnimation.expand(viewToExpand);
            isExpanded = true;
        } else {
            if (((TextView) floatingLabelItemPicker.getInputWidget()).getText().toString().equals("")) {
               floatingLabelItemPicker.anchorLabel();
            }
            ExpandAnimation.collapse(viewToExpand);
            isExpanded = false;
        }
    }
}
