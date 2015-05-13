package com.richardradics.commons.widget.echeckbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.richardradics.commons.R;
import com.richardradics.commons.util.ViewUtils;
import com.richardradics.commons.widget.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radicsrichard on 15. 04. 15..
 */
public class GroupedCheckboxLayout extends LinearLayout implements OnSelectionListener {

    private LinearLayout mRootLinearLayout;
    private EnhancedCheckbox mParentCheckbox;
    private ExpandableHeightGridView mChildGridView;

    private String parentName;
    private int mSelectedTextColor = android.R.color.black;
    private int mUnSelectedTextColor = android.R.color.darker_gray;
    private int mNumColumns = 3;
    private int mItemHeight = 50;
    private float mHorizontalSpacing = 2f;
    private float mVerticalSpacing = 10f;
    private float drawableGap = 10f;

    private AttributeSet mAttributeSuet;
    private Context mContext;

    private List<String> childItemList;

    private CheckboxAdapter checkboxAdapter;

    private float mTextSize = 13;

    private Drawable checkboxResouce;
    private int drawableResId = R.drawable.btn_check;


    public interface OnCheckboxLayoutSelectionChange {
        void onSelectionChange(GroupedCheckboxLayout groupedCheckboxLayout, String value, boolean isChecked, boolean isParentCheckbox);
    }

    public ExpandableHeightGridView getChildGridView() {
        return mChildGridView;
    }


    private OnSelectionListener mSelectionListener;
    private OnCheckboxLayoutSelectionChange mChangeListener;

    public OnCheckboxLayoutSelectionChange getChangeListener() {
        return mChangeListener;
    }

    public void setChangeListener(OnCheckboxLayoutSelectionChange mChangeListener) {
        this.mChangeListener = mChangeListener;
    }


    public GroupedCheckboxLayout(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public GroupedCheckboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mAttributeSuet = attrs;
        init();
    }

    public GroupedCheckboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mAttributeSuet = attrs;
        init();
    }

    public void setButtonDrawable(int drawableResId) {
        this.drawableResId = drawableResId;
        checkboxAdapter.setCheckBoxButtonRes(drawableResId);
        mParentCheckbox.setButtonDrawable(drawableResId);
    }

    public EnhancedCheckbox getAllSelectCheckbox() {
        return mParentCheckbox;
    }

    public int getNumColumns() {
        return mNumColumns;

    }

    public void setNumColumns(int mNumColumns) {
        this.mNumColumns = mNumColumns;
        mChildGridView.setNumColumns(mNumColumns);
    }

    public void setHorizontalSpacing(float mHorizontalSpacing) {
        this.mHorizontalSpacing = mHorizontalSpacing;
        mChildGridView.setHorizontalSpacing(ViewUtils.convertToDip(mContext.getResources().getDisplayMetrics(), (int) mHorizontalSpacing));
    }

    public void setVerticalSpacing(float mVerticalSpacing) {
        this.mVerticalSpacing = mVerticalSpacing;
        mChildGridView.setVerticalSpacing(ViewUtils.convertToDip(mContext.getResources().getDisplayMetrics(), (int) mVerticalSpacing));
    }

    public String getParentName() {
        return parentName;
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;

        if (checkboxAdapter != null) {
            checkboxAdapter.setTextSize(mTextSize);
        }
        mParentCheckbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
        mParentCheckbox.setText(parentName);
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public void setSelectedTextColor(int mSelectedTextColor) {
        this.mSelectedTextColor = mSelectedTextColor;
        checkboxAdapter.setSelectColor(mSelectedTextColor);
    }

    public int getUnSelectedTextColor() {
        return mUnSelectedTextColor;
    }

    public void setUnSelectedTextColor(int mUnSelectedTextColor) {
        this.mUnSelectedTextColor = mUnSelectedTextColor;
        checkboxAdapter.setUnSelectColor(mUnSelectedTextColor);
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.grouped_checkbox, this, true);

        mRootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);
        mParentCheckbox = (EnhancedCheckbox) findViewById(R.id.parentCheckbox);
        mChildGridView = (ExpandableHeightGridView) findViewById(R.id.childPickGridView);

        mRootLinearLayout.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());

        childItemList = new ArrayList<String>();

        getAttributesAndStore();
        initView();
    }


    private void getAttributesAndStore() {
        if (mAttributeSuet == null) {
            return;
        }

        TypedArray xmlAttrs = mContext.obtainStyledAttributes(mAttributeSuet, R.styleable.GroupedCheckboxLayout);
        parentName = xmlAttrs.getString(R.styleable.GroupedCheckboxLayout_parentname);

        mSelectedTextColor = xmlAttrs.getColor(R.styleable.GroupedCheckboxLayout_selectTextColor, android.R.color.black);
        mUnSelectedTextColor = xmlAttrs.getColor(R.styleable.GroupedCheckboxLayout_unselectTextColor, android.R.color.darker_gray);
        mNumColumns = xmlAttrs.getInt(R.styleable.GroupedCheckboxLayout_numColumns, 3);

        final Drawable d = xmlAttrs.getDrawable(R.styleable.GroupedCheckboxLayout_checkboxButtonStyle);

        if (d != null) {
            mParentCheckbox.setButtonDrawable(d);
            drawableResId = xmlAttrs.getResourceId(R.styleable.GroupedCheckboxLayout_checkboxButtonStyle, R.drawable.btn_check);

        }

        mVerticalSpacing = getScaledSize(xmlAttrs.getDimension(R.styleable.GroupedCheckboxLayout_verticalSpacing, 10f));
        mHorizontalSpacing = getScaledSize(xmlAttrs.getDimension(R.styleable.GroupedCheckboxLayout_horizontalSpacing, 2.5f));
        drawableGap = getScaledSize(xmlAttrs.getDimension(R.styleable.GroupedCheckboxLayout_drawableGap, 10f));
        mTextSize = getScaledSize(xmlAttrs.getDimension(R.styleable.GroupedCheckboxLayout_textSize, 15f));
        mItemHeight = (int) getScaledSize(xmlAttrs.getDimension(R.styleable.GroupedCheckboxLayout_itemHeight, 200f));

        xmlAttrs.recycle();

    }

    public void notifyDataSetChanged() {
        checkboxAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mParentCheckbox.setText(parentName);
        mParentCheckbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        mParentCheckbox.setTextColor(mUnSelectedTextColor);
        mChildGridView.setNumColumns(mNumColumns);
        mChildGridView.setVerticalSpacing(ViewUtils.convertToDip(mContext.getResources().getDisplayMetrics(), (int) mVerticalSpacing));
        mChildGridView.setHorizontalSpacing(ViewUtils.convertToDip(mContext.getResources().getDisplayMetrics(), (int) mHorizontalSpacing));


        childItemList = new ArrayList<String>();
        checkboxAdapter = new CheckboxAdapter(mContext, R.layout.list_item_cb_picker, childItemList);

        checkboxAdapter.setItemHeight(mItemHeight);
        checkboxAdapter.setAllSelectCheckbox(mParentCheckbox);
        checkboxAdapter.setSelectColor(mSelectedTextColor);
        checkboxAdapter.setUnSelectColor(mUnSelectedTextColor);
        checkboxAdapter.setTextSize(mTextSize);
        checkboxAdapter.setCheckBoxButtonRes(drawableResId);
        checkboxAdapter.setCustomFont(mParentCheckbox.getTypeface());
        mChildGridView.setAdapter(checkboxAdapter);
        mChildGridView.setExpanded(true);
        checkboxAdapter.setSelectionListener(this);
        mSelectionListener = this;

        mParentCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mParentCheckbox.setTextColor(mSelectedTextColor);
                    checkAll();
                } else {
                    mParentCheckbox.setTextColor(mUnSelectedTextColor);
                    unCheckAll();
                }

                if (mSelectionListener != null) {
                    mSelectionListener.onSelectionChange(parentName, isChecked, true);
                }
            }
        });
    }

    public void unCheckAll() {
        checkboxAdapter.unCheckAll();
    }

    public void checkAll() {
        checkboxAdapter.checkAll();
    }

    @Override
    public void onSelectionChange(String value, boolean isChecked, boolean isFromParent) {
        if (mChangeListener != null) {
            mChangeListener.onSelectionChange(this, value, isChecked, isFromParent);
        }
    }

    public void setItems(List<String> items) {
        checkboxAdapter.addAll(items);
        mChildGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View lastChild = mChildGridView.getChildAt(mChildGridView.getChildCount() - 1);
                if (lastChild != null) {
                    mChildGridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    mChildGridView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, lastChild.getBottom() + getPaddingBottom()));
                }
            }
        });
    }

    public void setSelectedItems(List<String> items) {
        checkboxAdapter.setSelectedItems(items);
    }

    public void selectAll() {
        checkboxAdapter.checkAll();
    }

    public void clearSelection() {
        checkboxAdapter.unCheckAll();
    }

    public List<String> getSelectedItems() {
        return checkboxAdapter.getSelectedItems();
    }

    public Map<String, List<String>> getSelection() {
        Map<String, List<String>> selection = new HashMap<String, List<String>>();


        return selection;
    }

    private float getScaledSize(float fontSizeFromAttributes) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return fontSizeFromAttributes / scaledDensity;
    }

}
