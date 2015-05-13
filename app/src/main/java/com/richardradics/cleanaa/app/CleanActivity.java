package com.richardradics.cleanaa.app;

import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.richardradics.cleanaa.R;
import com.richardradics.core.app.BaseActivity;
import com.richardradics.core.navigation.Navigator;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

/**
 * Created by radicsrichard on 15. 05. 13..
 */
@EActivity
public class CleanActivity extends BaseActivity {

    protected Toolbar toolbar;


    protected TextView titleTextView;

    @Bean
    protected CleanErrorHandler cleanErrorHandler;

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initToolBar();
    }

    protected void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            titleTextView = (TextView) findViewById(R.id.toolbar_title);
            setSupportActionBar(toolbar);
            titleTextView.setText(getTitle());
            toolbar.setNavigationIcon(R.drawable.ic_backarrow);
        }
    }

    @OptionsItem(android.R.id.home)
    protected void homeSelected() {
        Navigator.navigateUp(this);
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

}
