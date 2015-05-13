package com.richardradics.commons.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

/**
 * Project: commons
 * Package: com.richardradics.commons.widget
 * Created by richardradics on 2015.02.24..
 */
public class FixedHeightListView extends LinearLayout implements View.OnClickListener {

    private ArrayAdapter mList;
    private View.OnClickListener mListener;
    private View view;

    public FixedHeightListView(Context context) {
        super(context);
    }

    public FixedHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedHeightListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(ArrayAdapter list) {
        this.mList = list;
        setOrientation(VERTICAL);

        if (mList != null) {
            for (int i = 0; i < mList.getCount(); i++) {
                view = mList.getView(i, null, null);
                this.addView(view);
            }
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
    }


}