package com.richardradics.commons.widget.echeckbox;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import com.richardradics.commons.BuildConfig;
import com.richardradics.commons.util.ResourceUtil;
import com.richardradics.commons.util.ViewUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radicsrichard on 15. 04. 15..
 */
public class CheckboxAdapter extends ArrayAdapter<String> {

    private static int CB_INSTANCES = 312;

    List<String> values;

    Map<String, Boolean> valueMap = new HashMap<String, Boolean>();
    Map<String, EnhancedCheckbox> viewMap = new HashMap<String, EnhancedCheckbox>();
    Context context;

    Integer checkBoxButtonRes;
    Integer itemHeight;
    int mSelectColor;
    int mUnSelectColor;

    float mTextSize;

    EnhancedCheckbox allSelectCheckbox;
    boolean isAllSelected = false;

    Typeface mTypeFace;

    public Typeface getCustomFont() {
        return mTypeFace;
    }

    public void setCustomFont(Typeface mTypeFace) {
        this.mTypeFace = mTypeFace;
    }

    private OnSelectionListener mListener;

    public OnSelectionListener getSelectionListener() {
        return mListener;
    }

    public void setSelectionListener(OnSelectionListener mListener) {
        this.mListener = mListener;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public int getSelectColor() {
        return mSelectColor;
    }

    public void setSelectColor(int mSelectColor) {
        this.mSelectColor = mSelectColor;
    }

    public int getUnSelectColor() {
        return mUnSelectColor;
    }

    public void setUnSelectColor(int mUnSelectColor) {
        this.mUnSelectColor = mUnSelectColor;
    }

    public CheckboxAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        mSelectColor = context.getResources().getColor(android.R.color.black);
        mUnSelectColor = context.getResources().getColor(android.R.color.darker_gray);
        mTextSize = 13;
        checkBoxButtonRes = ResourceUtil.getAndroidDrawableId("btn_check");
        this.values = new ArrayList<String>();
        this.values.addAll(objects);
        valueMap.clear();
        viewMap.clear();
        for (String s : values) {
            valueMap.put(s, false);
        }
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void setCheckBoxButtonRes(int buttonRes) {
        this.checkBoxButtonRes = buttonRes;
    }

    public void setAllSelectCheckbox(EnhancedCheckbox enhancedCheckbox) {
        this.allSelectCheckbox = enhancedCheckbox;
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public String getItem(int position) {
        return values.get(position);
    }

    @Override
    public int getPosition(String item) {
        return values.indexOf(item);
    }


    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public void add(String object) {
        values.add(object);
    }

    @Override
    public void addAll(Collection<? extends String> collection) {
        values.addAll(collection);

        for (String s : values) {
            if (!valueMap.containsKey(s)) {
                valueMap.put(s, false);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public void remove(String object) {
        values.remove(object);
    }

    @Override
    public void clear() {
        values.clear();
    }

    public boolean isSelectedAll() {

        if (valueMap.isEmpty()) {
            return false;
        }

        boolean isselected = true;
        for (Map.Entry<String, Boolean> e : valueMap.entrySet()) {
            if (!e.getValue()) {
                isselected = false;
            }
        }

        return isselected;
    }

    public void checkAll() {
        valueMap.clear();
        for (String s : values) {
            valueMap.put(s, true);
        }

        if (allSelectCheckbox != null) {
            allSelectCheckbox.setCheckedProgrammatically(true);
        }

        isAllSelected = true;
        notifyDataSetChanged();
    }

    public void unCheckAll() {
        valueMap.clear();
        for (String s : values) {
            valueMap.put(s, false);
        }

        if (allSelectCheckbox != null) {
            allSelectCheckbox.setCheckedProgrammatically(false);
        }

        isAllSelected = false;
        notifyDataSetChanged();
    }

    public void setSelectedItems(List<String> items) {
        for (String s : values) {
            valueMap.put(s, false);
        }

        for (String s : items) {
            valueMap.put(s, true);
        }

        notifyDataSetChanged();
    }

    public List<String> getSelectedItems() {
        List<String> selected = new ArrayList<String>();

        for (Map.Entry<String, Boolean> e : valueMap.entrySet()) {
            if (e.getValue()) {
                selected.add(e.getKey());
            }
        }

        return selected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String current = values.get(position);

        EnhancedCheckbox enhancedCheckbox;

        if (!viewMap.containsKey(current)) {
            enhancedCheckbox = new EnhancedCheckbox(context);
            enhancedCheckbox.setId(CB_INSTANCES++);

            if (itemHeight != null) {
                enhancedCheckbox.setHeight(ViewUtils.convertToPix(context.getResources().getDisplayMetrics().density, itemHeight));
            }

            enhancedCheckbox.setText(current);
            if (mTypeFace != null) {
                enhancedCheckbox.setTypeface(mTypeFace);
            }

            enhancedCheckbox.setGravity(Gravity.START);
            enhancedCheckbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
            enhancedCheckbox.setTag(current);
            if (checkBoxButtonRes != null) {
                enhancedCheckbox.setButtonDrawable(context.getResources().getDrawable(checkBoxButtonRes));
               // if (Build.VERSION.SDK_INT >= 17) {
                    final float scale = context.getResources().getDisplayMetrics().density;
                    enhancedCheckbox.setPadding(enhancedCheckbox.getCompoundPaddingLeft(), //+ (int) (10.0f * scale + 0.5f),
                           0,
                            enhancedCheckbox.getPaddingRight(),
                            enhancedCheckbox.getPaddingBottom());
               // }
            }


            enhancedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (allSelectCheckbox != null) {
                        if (isAllSelected) {
                            isAllSelected = false;
                            allSelectCheckbox.setCheckedProgrammatically(false);
                            allSelectCheckbox.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
                        }
                    }

                    String s = (String) buttonView.getTag();
                    valueMap.put(s, isChecked);

                    if (mListener != null) {
                        mListener.onSelectionChange(s, isChecked, false);
                    }

                    if (isChecked) {
                        if (allSelectCheckbox != null) {
                            if (isSelectedAll()) {
                                allSelectCheckbox.setTextColor(context.getResources().getColor(android.R.color.black));
                                allSelectCheckbox.setCheckedProgrammatically(true);
                                isAllSelected = true;
                            } else {

                            }
                        }
                    } else {

                    }

                    notifyDataSetChanged();
                }
            });

            viewMap.put(current, enhancedCheckbox);
        } else {
            enhancedCheckbox = viewMap.get(current);
        }

        enhancedCheckbox.setCheckedProgrammatically(valueMap.get(current));

        if (enhancedCheckbox.isChecked()) {
            //TODO szinek behozasa valahogy rendesen
            enhancedCheckbox.setTextColor(context.getResources().getColor(android.R.color.black));

            if (isSelectedAll()) {
                allSelectCheckbox.setTextColor(context.getResources().getColor(android.R.color.black));
                allSelectCheckbox.setChecked(true);
                isAllSelected = true;
            }

        } else {
            enhancedCheckbox.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        }

        enhancedCheckbox.invalidate();
        enhancedCheckbox.refreshDrawableState();

        return enhancedCheckbox;
    }
}
