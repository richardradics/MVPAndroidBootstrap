package com.richardradics.commons.listeners;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Richard Radics on 2015.02.11..
 */
public class OnItemSelectedListenerWrapper implements AdapterView.OnItemSelectedListener {

    private int lastPosition;
    private AdapterView.OnItemSelectedListener listener;

    private boolean isFirstStart = true;


    public OnItemSelectedListenerWrapper(AdapterView.OnItemSelectedListener listener) {
        lastPosition = 0;
        this.listener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> aParentView, View aView, int aPosition, long anId) {
        if (!isFirstStart) {
            if (lastPosition == aPosition) {
                Log.d("ItemSelectedWrapper", "Ignoring onItemSelected for same position: " + aPosition);
            } else {//
                Log.d("ItemSelectedWrapper", "Passing on onItemSelected for different position: " + aPosition);
                listener.onItemSelected(aParentView, aView, aPosition, anId);
            }
            lastPosition = aPosition;
        } else {
            isFirstStart = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> aParentView) {
        listener.onNothingSelected(aParentView);
    }

}
