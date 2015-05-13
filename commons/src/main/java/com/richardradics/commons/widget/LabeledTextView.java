package com.richardradics.commons.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richardradics.commons.R;

/**
 * Created by radicsrichard on 15. 03. 18..
 */
public class LabeledTextView extends LinearLayout {

    private LinearLayout mRootLinearLayout;
    private TextView mHintTextView;
    private TextView mValueTextView;

    private AttributeSet mAttributeSet;
    private Context mContext;

    private int mHintTextColor;
    private int mValueTextColor;

    private String mHintText;
    private String mValueText;

    private float mHintTextSize;
    private float mValueTextSize;

    public LabeledTextView(Context context) {
        super(context);
        this.mContext = context;
        initializeView();
    }

    public LabeledTextView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        this.mContext = context;
        this.mAttributeSet = attributeSet;
        initializeView();
    }

    public LabeledTextView(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
        this.mContext = context;
        this.mAttributeSet = attributeSet;
        initializeView();
    }

    private void initializeView(){
         if(mContext == null){
             return;
         }

        LayoutInflater.from(mContext).inflate(R.layout.labeled_textview, this, true);

        mRootLinearLayout = (LinearLayout)findViewById(R.id.labeled_textview_linear_layout);
        mHintTextView = (TextView)findViewById(R.id.labeled_hint_textview);
        mValueTextView = (TextView)findViewById(R.id.labeled_value_textview);

        mRootLinearLayout.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        mRootLinearLayout.setOrientation(getOrientation());

        getAttributesFromXmlAndStoreLocally();
        initTextViews();

    }

    private void getAttributesFromXmlAndStoreLocally(){

        if(mAttributeSet ==null){
            return;
        }

        TypedArray attributesFromXmlLayout = mContext.obtainStyledAttributes(mAttributeSet, R.styleable.LabeledEditText);

        mHintText = attributesFromXmlLayout.getString(R.styleable.LabeledEditText_hint);
        mValueText = attributesFromXmlLayout.getString(R.styleable.LabeledEditText_valueText);

        mHintTextColor = attributesFromXmlLayout.getColor(R.styleable.LabeledEditText_hintTextColor, android.R.color.darker_gray);
        mValueTextColor = attributesFromXmlLayout.getColor(R.styleable.LabeledEditText_valueTextColor, android.R.color.darker_gray);

        mHintTextSize = getScaledFontSize(attributesFromXmlLayout.getDimension(R.styleable.LabeledEditText_hintTextSize, 15));
        mValueTextSize = getScaledFontSize(attributesFromXmlLayout.getDimension(R.styleable.LabeledEditText_valueTextSize, 15));

        attributesFromXmlLayout.recycle();

    }

    private void initTextViews(){
        mHintTextView.setText(mHintText);
        mHintTextView.setTextColor(mHintTextColor);
        mHintTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mHintTextSize);

        mValueTextView.setText(mValueText);
        mValueTextView.setTextColor(mValueTextColor);
        mValueTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mValueTextSize);
    }

    public void setHintText(String hintText){
        this.mHintText = hintText;
        mHintTextView.setText(hintText);
    }

    private float getScaledFontSize(float fontSizeFromAttributes) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return fontSizeFromAttributes / scaledDensity;
    }

    public void setValueText(String valueText){
        this.mValueText = valueText;
        mValueTextView.setText(valueText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mHintTextView != null){
            mHintTextView.draw(canvas);
        }

        if(mValueTextView != null){
            mValueTextView.draw(canvas);
        }
    }

    public String getValueText() {
        return mValueText;
    }

}
