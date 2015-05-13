package com.richardradics.commons.widget.echeckbox;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.richardradics.commons.util.MapUtil;
import com.richardradics.commons.util.ResourceUtil;
import com.richardradics.commons.util.ViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radicsrichard on 15. 04. 15..
 */
public class MultiCheckboxAdapter extends BaseAdapter {

    private static int CB_INSTANCES = 312;

    List<Map<String, List<String>>> values;

    HashMap<String, Boolean> parentValues = new HashMap<String, Boolean>();
    HashMap<String, HashMap<String, Boolean>> valueMap = new HashMap<String, HashMap<String, Boolean>>();
    HashMap<String, GroupedCheckboxLayout> viewMap = new HashMap<String, GroupedCheckboxLayout>();
    Context context;

    Integer checkBoxButtonRes;
    Integer itemHeight;
    int mSelectColor;
    int mUnSelectColor;
    Integer numColoumns;
    Integer verticalSpacing;
    Integer horizontalSpacing;


    float mTextSize = 13;

    EnhancedCheckbox allSelectCheckbox;
    boolean isAllSelected = false;


    public Integer getNumColoumns() {
        return numColoumns;
    }

    public void setNumColoumns(Integer numColoumns) {
        this.numColoumns = numColoumns;
    }

    public Integer getVerticalSpacing() {
        return verticalSpacing;
    }

    public void setVerticalSpacing(Integer verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public Integer getHorizontalSpacing() {
        return horizontalSpacing;
    }

    public void setHorizontalSpacing(Integer horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
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

    public MultiCheckboxAdapter(Context context, int resource, Map<String, List<String>> objects) {

        this.context = context;
        mSelectColor = context.getResources().getColor(android.R.color.black);
        mUnSelectColor = context.getResources().getColor(android.R.color.darker_gray);
        mTextSize = 13;
        checkBoxButtonRes = ResourceUtil.getAndroidDrawableId("btn_check");

        this.values = new ArrayList<Map<String, List<String>>>();

        parentValues.clear();

        for (Map.Entry<String, List<String>> e : objects.entrySet()) {
            HashMap<String, List<String>> tmp = new HashMap<String, List<String>>();
            tmp.put(e.getKey(), e.getValue());
            values.add(tmp);
            parentValues.put(e.getKey(), false);
        }

        valueMap.clear();

        for (Map<String, List<String>> e : values) {
            for (Map.Entry<String, List<String>> t : e.entrySet()) {
                HashMap<String, Boolean> tmp = new HashMap<String, Boolean>();
                for (String s : t.getValue()) {
                    tmp.put(s, false);
                }
                valueMap.put(t.getKey(), tmp);
            }
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
    public Map<String, List<String>> getItem(int position) {
        return values.get(position);
    }

    public int getPosition(Map<String, List<String>> item) {
        return values.indexOf(item);
    }


    @Override
    public int getCount() {
        return values.size();
    }

    public void add(Map<String, List<String>> object) {
        values.add(object);
    }

    public void addAll(Map<String, List<String>> collection) {


        for (Map.Entry<String, List<String>> e : collection.entrySet()) {
            HashMap<String, List<String>> tmp = new HashMap<String, List<String>>();
            tmp.put(e.getKey(), e.getValue());
            values.add(tmp);
            parentValues.put(e.getKey(), false);
        }

        for (Map.Entry<String, List<String>> e : collection.entrySet()) {
            HashMap<String, Boolean> tmp = new HashMap<String, Boolean>();
            for (String s : e.getValue()) {
                tmp.put(s, false);
            }
            valueMap.put(e.getKey(), tmp);
        }

        notifyDataSetChanged();
    }

    public void clear() {
        values.clear();
        valueMap.clear();
    }

    public boolean isSelectedAll() {
        boolean isselected = true;


        for (Map.Entry<String, HashMap<String, Boolean>> e : valueMap.entrySet()) {
            for (Map.Entry<String, Boolean> t : e.getValue().entrySet()) {
                if (!t.getValue()) {
                    isselected = false;
                }
            }
        }

        return isselected;
    }

    public void checkAll() {
        //  valueMap.clear();

        for (Map<String, List<String>> e : values) {
            for (Map.Entry<String, List<String>> t : e.entrySet()) {
                for (Map.Entry<String, Boolean> b : valueMap.get(t.getKey()).entrySet()) {
                    b.setValue(true);
                }
            }
        }

        for (Map.Entry<String, Boolean> e : parentValues.entrySet()) {
            e.setValue(true);
        }

        for (Map.Entry<String, GroupedCheckboxLayout> d : viewMap.entrySet()) {
            d.getValue().checkAll();
        }

        isAllSelected = true;
        notifyDataSetChanged();
    }

    public void unCheckAll() {
        // valueMap.clear();

        for (Map<String, List<String>> e : values) {
            for (Map.Entry<String, List<String>> t : e.entrySet()) {
                for (Map.Entry<String, Boolean> b : valueMap.get(t.getKey()).entrySet()) {
                    b.setValue(false);
                }
            }
        }

        for (Map.Entry<String, Boolean> e : parentValues.entrySet()) {
            e.setValue(false);
        }

        for (Map.Entry<String, GroupedCheckboxLayout> d : viewMap.entrySet()) {
            d.getValue().unCheckAll();
        }

        isAllSelected = false;
        notifyDataSetChanged();

    }

    public void setSelectedItems(Map<String, List<String>> collection) {

        for (Map.Entry<String, List<String>> e : collection.entrySet()) {
            if (!e.getValue().isEmpty()) {
                for (String s : e.getValue()) {
                    valueMap.get(e.getKey()).put(s, true);
                }
            } else {
                parentValues.put(e.getKey(), true);
            }
        }


        notifyDataSetChanged();
    }

    public Map<String, List<String>> getSelectedItems() {
        Map<String, List<String>> selected = new HashMap<String, List<String>>();

        for (Map.Entry<String, HashMap<String, Boolean>> e : valueMap.entrySet()) {
            if (!e.getValue().isEmpty()) {
                boolean hasselection = false;
                List<String> se = new ArrayList<String>();
                for (HashMap.Entry<String, Boolean> t : e.getValue().entrySet()) {
                    if (t.getValue()) {
                        hasselection = true;
                        se.add(t.getKey());
                    }
                }
                if (hasselection) {
                    selected.put(e.getKey(), se);
                }
            }
        }

        for (Map.Entry<String, Boolean> e : parentValues.entrySet()) {
            if (e.getValue() && !selected.containsKey(e.getKey())) {
                selected.put(e.getKey(), Collections.EMPTY_LIST);
            }
        }

        return selected;
    }

    private List<String> getEntrySelection(Map<String, List<String>> e) {
        List<String> eSelection = new ArrayList<String>();

        HashMap<String, Boolean> searched = valueMap.get(MapUtil.getSingleKey(e));
        List<String> currentSelection = MapUtil.getSingleValue(e);

        for (String s : currentSelection) {
            if (searched.get(s)) {
                eSelection.add(s);
            }
        }

        return eSelection;
    }

    private void clearSelectionForEntry(Map<String, List<String>> e) {
        String current = MapUtil.getSingleKey(e);

        for (Map.Entry<String, Boolean> cur : valueMap.get(current).entrySet()) {
            cur.setValue(false);
        }
    }

    private void selectAllForEntry(Map<String, List<String>> e) {
        String current = MapUtil.getSingleKey(e);

        for (Map.Entry<String, Boolean> cur : valueMap.get(current).entrySet()) {
            cur.setValue(true);
        }
    }

    private boolean isFullSelected() {
        boolean isall = true;
        for (Map.Entry<String, HashMap<String, Boolean>> e : valueMap.entrySet()) {
            for (Map.Entry<String, Boolean> t : e.getValue().entrySet()) {
                if (!t.getValue()) {
                    isall = false;
                }
            }
        }

        for (Map.Entry<String, Boolean> e : parentValues.entrySet()) {
            if (!e.getValue()) {
                isall = false;
            }
        }

        return isall;
    }

    private boolean isAllSelectedForEntry(Map<String, List<String>> e) {
        List<String> eSelection = new ArrayList<String>();

        String current = MapUtil.getSingleKey(e);
        HashMap<String, Boolean> searched = valueMap.get(current);
        List<String> currentSelection = MapUtil.getSingleValue(e);

        boolean isas = true;
        for (String s : currentSelection) {
            if (!searched.get(s)) {
                isas = false;
            }
        }

        if (isas && !currentSelection.isEmpty()) {
            parentValues.put(current, true);
        } else {
            if (!isas && !currentSelection.isEmpty()) {
                parentValues.put(current, false);
            } else {
                return false;
            }
        }

        return isas;
    }


    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        Map<String, List<String>> e = values.get(position);
        String currentKey = MapUtil.getSingleKey(e);
        List<String> items = MapUtil.getSingleValue(e);
        final GroupedCheckboxLayout groupedCheckboxLayout;

        if (!viewMap.containsKey(currentKey)) {

            groupedCheckboxLayout = new GroupedCheckboxLayout(context);

            int padding = ViewUtils.convertToPix(context.getResources().getDisplayMetrics().density, 5);
            groupedCheckboxLayout.setPadding(0, padding, padding, padding);
            groupedCheckboxLayout.setSelectedTextColor(mSelectColor);
            groupedCheckboxLayout.setUnSelectedTextColor(mUnSelectColor);
            groupedCheckboxLayout.setItems(items);

            if (items.isEmpty()) {
                groupedCheckboxLayout.getChildGridView().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 0));
            }

            groupedCheckboxLayout.setButtonDrawable(checkBoxButtonRes);

            groupedCheckboxLayout.setParentName(currentKey);

            groupedCheckboxLayout.setTag(e);
            groupedCheckboxLayout.setChangeListener(new GroupedCheckboxLayout.OnCheckboxLayoutSelectionChange() {
                @Override
                public void onSelectionChange(GroupedCheckboxLayout view, String value, boolean isChecked, boolean isFromParent) {
                    Map<String, List<String>> e = (Map<String, List<String>>) view.getTag();
                    if (isFromParent) {
                        parentValues.put(value, isChecked);

                        if (!isChecked) {
                            clearSelectionForEntry(e);
                        } else {
                            selectAllForEntry(e);
                        }
                    } else {
                        valueMap.get(MapUtil.getSingleKey(e)).put(value, isChecked);
                        isAllSelectedForEntry(e);
                    }
                    notifyDataSetChanged();
                }
            });

            viewMap.put(currentKey, groupedCheckboxLayout);
        } else {
            groupedCheckboxLayout = viewMap.get(currentKey);
        }


        boolean isVmi = parentValues.get(MapUtil.getSingleKey(e));

        groupedCheckboxLayout.getAllSelectCheckbox().setCheckedProgrammatically(isVmi);

        if (isVmi) {
            groupedCheckboxLayout.getAllSelectCheckbox().setTextColor(mSelectColor);
        } else {
            groupedCheckboxLayout.getAllSelectCheckbox().setTextColor(mUnSelectColor);
        }

        if (allSelectCheckbox != null) {
            if (isFullSelected()) {
                allSelectCheckbox.setCheckedProgrammatically(true);
                allSelectCheckbox.setTextColor(mSelectColor);
            } else {
                allSelectCheckbox.setCheckedProgrammatically(false);
                allSelectCheckbox.setTextColor(mUnSelectColor);
            }
        }
        groupedCheckboxLayout.setSelectedItems(getEntrySelection(e));

        groupedCheckboxLayout.notifyDataSetChanged();

        groupedCheckboxLayout.requestLayout();

        return groupedCheckboxLayout;
    }
}
