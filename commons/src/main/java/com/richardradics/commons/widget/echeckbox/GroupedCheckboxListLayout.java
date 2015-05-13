package com.richardradics.commons.widget.echeckbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.richardradics.commons.R;
import com.richardradics.commons.util.ViewUtils;
import com.richardradics.commons.widget.ExpandableHeightGridView;
import com.richardradics.commons.widget.SimpleListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by radicsrichard on 15. 04. 15..
 */
public class GroupedCheckboxListLayout extends LinearLayout {

    private LinearLayout mRootLinearLayout;
    private EnhancedCheckbox mParentCheckbox;
    private SimpleListView mChildListView;

    private String parentName;
    private int mSelectedTextColor = android.R.color.black;
    private int mUnSelectedTextColor = android.R.color.darker_gray;
    private int mNumColumns = 3;
    private int mItemHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private float mHorizontalSpacing = 2f;
    private float mVerticalSpacing = 10f;
    private float drawableGap = 10f;

    private AttributeSet mAttributeSuet;
    private Context mContext;

    private HashMap<String, List<String>> childItemMap;

    private MultiCheckboxAdapter checkboxAdapter;

    private float mTextSize = 13f;

    private Drawable checkboxResouce;
    private int drawableResId = R.drawable.btn_check;

    public GroupedCheckboxListLayout(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public GroupedCheckboxListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mAttributeSuet = attrs;
        init();
    }

    public GroupedCheckboxListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mAttributeSuet = attrs;
        init();
    }

    public void setButtonDrawable(int drawableResId){
        this.drawableResId = drawableResId;
        checkboxAdapter.setCheckBoxButtonRes(drawableResId);
        mParentCheckbox.setButtonDrawable(drawableResId);
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.grouped_checkbox_list, this, true);

        mRootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);
        mParentCheckbox = (EnhancedCheckbox) findViewById(R.id.parentCheckbox);
        mChildListView = (SimpleListView) findViewById(R.id.childListView);

        mRootLinearLayout.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());

        childItemMap = new HashMap<String, List<String>>();

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
        mItemHeight = (int)getScaledSize(xmlAttrs.getDimension(R.styleable.GroupedCheckboxLayout_itemHeight, 200f));

        xmlAttrs.recycle();

    }


    private void initView() {
        mParentCheckbox.setText(parentName);
        mParentCheckbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);


        checkboxAdapter = new MultiCheckboxAdapter(mContext, R.layout.list_item_cb_picker, childItemMap);
        checkboxAdapter.setNumColoumns(mNumColumns);
        checkboxAdapter.setVerticalSpacing((int)mVerticalSpacing);
        checkboxAdapter.setHorizontalSpacing((int)mHorizontalSpacing);

        checkboxAdapter.setItemHeight(mItemHeight);
        checkboxAdapter.setAllSelectCheckbox(mParentCheckbox);
        checkboxAdapter.setSelectColor(mSelectedTextColor);
        checkboxAdapter.setUnSelectColor(mUnSelectedTextColor);
        checkboxAdapter.setTextSize(mTextSize);
        checkboxAdapter.setCheckBoxButtonRes(drawableResId);

        mChildListView.setDividerView(R.layout.default_divider);
        mChildListView.setAdapter(checkboxAdapter);


        mParentCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkboxAdapter.checkAll();

                }else{
                    checkboxAdapter.unCheckAll();

                }
            }
        });

    }


    public void setItems(Map<String, List<String>> items) {
      checkboxAdapter.addAll(items);
    }

    public void setSelectedItems(Map<String, List<String>> items) {
        checkboxAdapter.setSelectedItems(items);
    }

    public void selectAll() {
        checkboxAdapter.checkAll();
    }

    public void clearSelection() {
        checkboxAdapter.unCheckAll();
    }

    public Map<String, List<String>> getSelectedItems() {
        return checkboxAdapter.getSelectedItems();
    }

    private float getScaledSize(float fontSizeFromAttributes) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return fontSizeFromAttributes / scaledDensity;
    }

}
