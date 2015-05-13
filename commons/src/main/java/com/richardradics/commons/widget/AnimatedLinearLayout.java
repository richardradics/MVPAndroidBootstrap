package com.richardradics.commons.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class AnimatedLinearLayout extends LinearLayout {
    private static class ChildBounds {
        public Rect start = new Rect();
        public Rect end = new Rect();
    }

    private enum Capture {
        START,
        END
    }

    private WeakHashMap<View, ChildBounds> childrenBounds;
    private boolean animate = false;

    public AnimatedLinearLayout(Context context) {
        this(context, null, 0);
    }

    public AnimatedLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        childrenBounds = new WeakHashMap<View, ChildBounds>();
    }

    @Override
    public void setOrientation(int orientation) {
        prepareLayoutAnimation();
        super.setOrientation(orientation);
    }

    @Override
    public void addView(View child) {
        prepareLayoutAnimation();
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        prepareLayoutAnimation();
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        prepareLayoutAnimation();
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        prepareLayoutAnimation();
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        prepareLayoutAnimation();
        super.addView(child, index, params);
    }

    @Override
    public void removeView(View child) {
        prepareLayoutAnimation();
        childrenBounds.remove(child);
        super.removeView(child);
    }

    @Override
    public void removeViews(int start, int count) {
        prepareLayoutAnimation();

        for (int i = 0; i < count; i++) {
            childrenBounds.remove(getChildAt(i));
        }

        super.removeViews(start, count);
    }

    @Override
    public void removeViewAt(int index) {
        prepareLayoutAnimation();
        childrenBounds.remove(getChildAt(index));
        super.removeViewAt(index);
    }

    public void setAnimationsEnabled(boolean enabled) {
        animate = enabled;
    }

    private void waitForNextFrame() {
        ViewTreeObserver treeObserver = getViewTreeObserver();
        if (treeObserver != null && treeObserver.isAlive()) {
            treeObserver.addOnPreDrawListener(new PreDrawListener());
        }
    }

    private ChildBounds getChildBounds(View child) {
        ChildBounds bounds = childrenBounds.get(child);
        if (bounds == null) {
            bounds = new ChildBounds();
            childrenBounds.put(child, bounds);
        }

        return bounds;
    }

    private void setRectFromView(Rect rect, View v) {
        rect.set(v.getLeft(),
                v.getTop(),
                v.getRight(),
                v.getBottom());
    }

    private void captureChildrenBounds(Capture capture) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            ChildBounds bounds = getChildBounds(child);
            if (capture == Capture.START) {
                setRectFromView(bounds.start, child);
                bounds.end.setEmpty();
            } else {
                setRectFromView(bounds.end, child);
            }
        }
    }

    private void prepareLayoutAnimation() {
        if (!animate) {
            return;
        }

        captureChildrenBounds(Capture.START);
        waitForNextFrame();
    }

    private Animator prepareNewChildAnimator(View child) {
        child.setAlpha(0f);
        return ObjectAnimator.ofFloat(child, "alpha", 0f, 1f);
    }

    private Animator prepareBoundsAnimator(View child, ChildBounds bounds) {
        int startLeft = bounds.start.left;
        int startTop = bounds.start.top;
        int startRight = bounds.start.right;
        int startBottom = bounds.start.bottom;

        int endLeft = bounds.end.left;
        int endTop = bounds.end.top;
        int endRight = bounds.end.right;
        int endBottom = bounds.end.bottom;

        int changeCount = 0;

        if (startLeft != endLeft) {
            child.setLeft(startLeft);
            changeCount++;
        }
        if (startTop != endTop) {
            child.setTop(startTop);
            changeCount++;
        }
        if (startRight != endRight) {
            child.setRight(startRight);
            changeCount++;
        }
        if (startBottom != endBottom) {
            child.setBottom(startBottom);
            changeCount++;
        }

        if (changeCount == 0) {
            return null;
        }

        PropertyValuesHolder pvh[] = new PropertyValuesHolder[changeCount];
        int pvhIndex = -1;

        if (startLeft != endLeft) {
            pvh[++pvhIndex] = PropertyValuesHolder.ofInt("left", startLeft, endLeft);
        }
        if (startTop != endTop) {
            pvh[++pvhIndex] = PropertyValuesHolder.ofInt("top", startTop, endTop);
        }
        if (startRight != endRight) {
            pvh[++pvhIndex] = PropertyValuesHolder.ofInt("right", startRight, endRight);
        }
        if (startBottom != endBottom) {
            pvh[++pvhIndex] = PropertyValuesHolder.ofInt("bottom", startBottom, endBottom);
        }

        return ObjectAnimator.ofPropertyValuesHolder(child, pvh);
    }

    private void animateBounds() {
        List<Animator> newChildAnimators = new ArrayList<Animator>();
        List<Animator> boundsAnimators = new ArrayList<Animator>();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            ChildBounds bounds = getChildBounds(child);

            // No start bounds, this is a new child in the container
            if (bounds.start.isEmpty()) {
                newChildAnimators.add(prepareNewChildAnimator(child));
                continue;
            }

            Animator boundsAnimator = prepareBoundsAnimator(child, bounds);
            if (boundsAnimator != null) {
                boundsAnimators.add(boundsAnimator);
            }
        }

        AnimatorSet boundsAnimSet = new AnimatorSet();
        boundsAnimSet.playTogether(boundsAnimators);

        AnimatorSet newChildAnimSet = new AnimatorSet();
        newChildAnimSet.setStartDelay(2000);
        newChildAnimSet.playTogether(newChildAnimators);

        // It's not really safe to animate the bounds of a view without suppressing
        // mid-air layout requests. Android's Transition framework supresses re-layout
        // on the scene root while the transition is running with a private API
        // i.e. ViewGroup.supressLayout(boolean).
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.play(boundsAnimSet).before(newChildAnimSet);
        finalSet.start();
    }

    private class PreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        @Override
        public boolean onPreDraw() {
            getViewTreeObserver().removeOnPreDrawListener(this);
            captureChildrenBounds(Capture.END);
            animateBounds();

            return true;
        }
    }
}