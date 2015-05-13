package com.richardradics.commons.widget;

/**
 * Created by radicsrichard on 15. 03. 17..
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);

    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);

        if (y == 0) {
            if (scrollViewListener != null) {
                scrollViewListener.onScrollStartHitted();
            }
        }

        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }


            if (Math.abs(y - oldy) < 2 || y >= getMeasuredHeight() || y == 0) {
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollEnded();
                }
            }


        int dy = y - oldy;
        if (dy > 10) {
            if (scrollViewListener != null) {
                scrollViewListener.onScrollUp();
            }
        } else if (dy < -10) {
            if (scrollViewListener != null) {
                scrollViewListener.onScrollDown();
            }
        }

        View view = (View) this.getChildAt(this.getChildCount() - 1);
        int diff = (view.getBottom() - (this.getHeight() + this.getScrollY()));

        // if diff is zero, then the bottom has been reached
        if (diff == 0) {
            if (scrollViewListener != null) {
                scrollViewListener.onScrollBottomHitted();
            }
        }

    }

    public interface ScrollViewListener {

        void onScrollStartHitted();

        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

        void onScrollBottomHitted();

        void onScrollUp();

        void onScrollDown();

        void onScrollEnded();

    }

}