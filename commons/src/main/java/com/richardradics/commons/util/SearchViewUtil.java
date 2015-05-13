package com.richardradics.commons.util;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.richardradics.commons.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Richard Radics on 2015.02.09..
 */
public class SearchViewUtil {


    /**
     * Sets the searchview textcolor.
     *
     * @param searchView
     * @param color
     */
    public static void setSearchViewTextColor(SearchView searchView, int color) {
        for (TextView textView : findChildrenByClass(searchView, TextView.class)) {
            textView.setTextColor(color);
        }
    }

    public static <V extends View> Collection<V> findChildrenByClass(ViewGroup viewGroup, Class<V> clazz) {

        return gatherChildrenByClass(viewGroup, clazz, new ArrayList<V>());
    }

    private static <V extends View> Collection<V> gatherChildrenByClass(ViewGroup viewGroup, Class<V> clazz, Collection<V> childrenFound) {

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            final View child = viewGroup.getChildAt(i);
            if (clazz.isAssignableFrom(child.getClass())) {
                childrenFound.add((V) child);
            }
            if (child instanceof ViewGroup) {
                gatherChildrenByClass((ViewGroup) child, clazz, childrenFound);
            }
        }

        return childrenFound;
    }


    /**
     * Sets the searchview's close icon drawable.
     *
     * @param searchView
     * @param drawableResource
     */
    public static void setCloseSearchIcon(SearchView searchView, int drawableResource) {
        try {
            Field searchField = SearchView.class.getDeclaredField("mCloseButton");
            searchField.setAccessible(true);
            ImageView closeBtn = (ImageView) searchField.get(searchView);
            closeBtn.setImageResource(drawableResource);

        } catch (NoSuchFieldException e) {
            Log.e("SearchView", e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e("SearchView", e.getMessage(), e);
        }
    }

    /**
     * Sets the searchview's edittext background color.
     *
     * @param searchView
     * @param color
     */
    public static void setSearchViewEditTextBackgroundColor(SearchView searchView, int color) {
        try {
            View e = searchView.findViewById(R.id.search_plate);
            e.setBackgroundColor(color); //←If you just want a color
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the searchview's hint icon and text.
     *
     * @param searchView
     * @param drawableResource
     * @param hintText
     */
    public static void setSearchHintIcon(SearchView searchView, int drawableResource, String hintText) {
        try {
            // Accessing the SearchAutoComplete
            int queryTextViewId = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            View autoComplete = searchView.findViewById(queryTextViewId);

            //Class<?> clazz = Class.forName("android.widget.SearchView$SearchAutoComplete");

            TextView searchBox = (TextView) searchView.findViewById(R.id.search_src_text);


            SpannableStringBuilder stopHint = new SpannableStringBuilder("   ");
            stopHint.append(hintText);

// Add the icon as an spannable
            Drawable searchIcon = searchView.getContext().getResources().getDrawable(drawableResource);
            Float rawTextSize = searchBox.getTextSize();
            int textSize = (int) (rawTextSize * 1.25);
            searchIcon.setBounds(0, 0, textSize, textSize);
            stopHint.setSpan(new ImageSpan(searchIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set the new hint text
            searchBox.setHint(stopHint);
            //searchBox.setTextColor(Color.WHITE);
            searchBox.setHintTextColor(Color.LTGRAY);

        } catch (Exception e) {
            Log.e("SearchView", e.getMessage(), e);
        }
    }

    /**
     * Sets the searchview's icon button.
     *
     * @param searchView
     * @param drawableResource
     */
    public static void setSearchButtonIcon(SearchView searchView, int drawableResource) {
        try {
            Field searchField = SearchView.class.getDeclaredField("mSearchButton");
            searchField.setAccessible(true);
            ImageView closeBtn = (ImageView) searchField.get(searchView);
            closeBtn.setImageResource(drawableResource);

        } catch (NoSuchFieldException e) {
            Log.e("SearchView", e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e("SearchView", e.getMessage(), e);
        }
    }


}
