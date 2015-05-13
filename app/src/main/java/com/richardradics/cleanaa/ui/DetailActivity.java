package com.richardradics.cleanaa.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.richardradics.cleanaa.R;
import com.richardradics.cleanaa.app.CleanActivity;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DimensionPixelSizeRes;

@EActivity
@OptionsMenu(R.menu.menu_detail)
public class DetailActivity extends CleanActivity implements ObservableScrollViewCallbacks {

    public static final String EXTRA_IMAGE = "DetailActivity:image";
    private static boolean TOOLBAR_IS_STICKY = false;
    private static float MAX_TEXT_SCALE_DELTA = 0.3f;

    @DimensionPixelSizeRes(R.dimen.flexible_space_image_height)
    int mFlexibleSpaceImageHeight;

    @ViewById(R.id.image)
    ImageView image;

    @ColorRes(R.color.primary)
    int mToolBarColor;

    @ViewById(R.id.overlay)
    View mOverlayView;

    @ViewById(R.id.scroll)
    ObservableScrollView mScollView;

    Integer mActionBarSize;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        mActionBarSize = getActionBarSize();

        if (!TOOLBAR_IS_STICKY) {
            toolbar.setBackgroundColor(Color.TRANSPARENT);
        }

        image = (ImageView) findViewById(R.id.image);
        ViewCompat.setTransitionName(image, EXTRA_IMAGE);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(image);

        setTitle(null);
        mScollView.setScrollViewCallbacks(this);

        ScrollUtils.addOnGlobalLayoutListener(mScollView, new Runnable() {
            @Override
            public void run() {
                mScollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);
                mScollView.scrollTo(0, 1);
            }
        });

    }


    public static void launch(CleanActivity activity, View transitionView, String url) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE);
        Intent intent = new Intent(activity, DetailActivity_.class);
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
        float minOverLayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat((-scrollY), minOverLayTransitionY, 0F));
        ViewHelper.setTranslationY(image, ScrollUtils.getFloat((-scrollY / 2), minOverLayTransitionY, 0F));

        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat(scrollY / flexibleRange, 0F, 1F));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0F, MAX_TEXT_SCALE_DELTA);
        ViewHelper.setPivotX(titleTextView, 0F);
        ViewHelper.setPivotY(titleTextView, 0F);
        ViewHelper.setScaleX(titleTextView, scale);
        ViewHelper.setScaleY(titleTextView, scale);

        // Translate title text
        float maxTitleTranslationY = (mFlexibleSpaceImageHeight - titleTextView.getHeight() * scale);
        float titleTranslationY = maxTitleTranslationY - scrollY;
        if (TOOLBAR_IS_STICKY) {
            titleTranslationY = Math.max(0, titleTranslationY);
        }
        ViewHelper.setTranslationY(titleTextView, titleTranslationY);

        if (TOOLBAR_IS_STICKY) {
            // Change alpha of toolbar background
            if (-scrollY + mFlexibleSpaceImageHeight <= mActionBarSize) {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(1F, mToolBarColor));
            } else {
                toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0F, mToolBarColor));
            }
        } else {
            // Translate Toolbar
            if (scrollY < mFlexibleSpaceImageHeight) {
                ViewHelper.setTranslationY(toolbar, 0F);
            } else {
                ViewHelper.setTranslationY(toolbar, (-scrollY));
            }
        }

    }

    @OptionsItem(android.R.id.home)
    protected void homeSelected() {
        onBackPressed();
    }


    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
