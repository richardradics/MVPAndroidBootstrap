package com.richardradics.commons.widget.justifiedtextview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * @author Mohsen Navabi
 * @version 1.0
 * @see http://www.mohsennavabi.ir
 * @see http://www.noghteh.ir
 */
public class JustifiedTextView extends View {

    private Context mContext;

    private XmlToClassAttribHandler mXmlParser;

    private TextPaint textPaint;

    private int lineSpace=7;

    private int lineHeight;

    private int textAreaWidth;

    private int measuredViewHeight,measuredViewWidth;

    private String text;

    private List<String> lineList=new ArrayList<String>();


    /**
     * when we want to draw text after view created to avoid loop in drawing we use this boolean
     */
    boolean hasTextBeenDrown=false;

    public JustifiedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        constructor(context,attrs);
    }
    public JustifiedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        constructor(context,attrs);
    }
    public JustifiedTextView(Context context) {
        super(context);
        constructor(context,null);

    }

    private void constructor(Context context, AttributeSet attrs) {

        mContext=context;
        mXmlParser=new XmlToClassAttribHandler(mContext,attrs);
        initTextPaint();

        if (attrs!=null){
            String text;
            int textColor;
            int textSize;
            int textSizeUnit;

            text=mXmlParser.getTextValue();
            textColor=mXmlParser.getColorValue();
            textSize=mXmlParser.getTextSize();
            textSizeUnit=mXmlParser.gettextSizeUnit();


            setText(text);
            setTextColor(textColor);
            if (textSizeUnit==-1)
                setTextSize(textSize);
            else
                setTextSize(textSizeUnit, textSize);

//			setText(XmlToClassAttribHandler.GetAttributeStringValue(mContext, attrs, namespace, key, ""));

        }

        ViewTreeObserver observer=getViewTreeObserver();


        observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                if (hasTextBeenDrown)
                    return;
                hasTextBeenDrown=true;
                setTextAreaWidth(getWidth()-(getPaddingLeft()+getPaddingRight()));
                calculate();

            }


        });

    }

    private void calculate() {
        setLineHeight(getTextPaint());
        lineList.clear();
        lineList=divideOriginalTextToStringLineList(getText());
        setMeasuredDimentions(lineList.size(),getLineHeight(),getLineSpace());
        measure(getMeasuredViewWidth(),getMeasuredViewHeight());
    }

    private void initTextPaint(){
        textPaint=new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Align.RIGHT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getMeasuredViewWidth()>0){
            requestLayout();
            setMeasuredDimension(getMeasuredViewWidth(),getMeasuredViewHeight());
        }
        else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec );
        }
        invalidate();
    }


    private int rowIndex=0,colIndex=0;
    @Override
    protected void onDraw(Canvas canvas) {

        rowIndex=getPaddingTop();
        if (getAlignment()==Align.RIGHT)
            colIndex=getPaddingLeft()+getTextAreaWidth();
        else
            colIndex=getPaddingLeft();

        for (int i=0;i<lineList.size();i++){
            rowIndex+=getLineHeight()+getLineSpace();

            canvas.drawText(lineList.get(i), colIndex,rowIndex , getTextPaint());
        }

    }


    /***
     * this method get the string and divide it to a list of StringLines according to textAreaWidth
     * @param originalText
     * @return
     */
    private List<String> divideOriginalTextToStringLineList(String originalText) {

        List<String> listStringLine=new ArrayList<String>();

        String line="";
        float textWidth;

        String[] listParageraphes = originalText.split("\n");

        for(int j=0;j<listParageraphes.length;j++)
        {
            String[] arrayWords = listParageraphes[j].split(" ");

            for (int i=0;i<arrayWords.length;i++){

                line += arrayWords[i]+" ";
                textWidth = getTextPaint().measureText(line);

                //if text width is equal to textAreaWidth then just add it to ListStringLine
                if (getTextAreaWidth()==textWidth){

                    listStringLine.add(line);
                    line="";//make line clear
                    continue;
                }
                //else if text width excite textAreaWidth then remove last word and justify the StringLine
                else if (getTextAreaWidth()<textWidth){

                    int lastWordCount=arrayWords[i].length();

                    //remove last word that cause line width to excite textAreaWidth
                    line=line.substring(0, line.length()-lastWordCount-1);

                    // if line is empty then should be skipped
                    if (line.trim().length()==0)
                        continue;

                    //and then we need to justify line
                    line=justifyTextLine(textPaint,line.trim(),getTextAreaWidth());

                    listStringLine.add(line);
                    line="";
                    i--;
                    continue;
                }

                //if we are now at last line of paragraph then just add it
                if (i==arrayWords.length-1){
                    listStringLine.add(line);
                    line="";
                }
            }
        }

        return listStringLine;

    }

    /**
     * this method add space in line until line width become equal to textAreaWidth
     * @param lineString
     * @param lineWidth
     * @param textAreaWidth
     * @return
     */
    private String justifyTextLine(TextPaint textPaint,String lineString, int textAreaWidth) {

        int gapIndex=0;

        float lineWidth=textPaint.measureText(lineString);

        while (lineWidth<textAreaWidth && lineWidth>0){

            gapIndex=lineString.indexOf(" ", gapIndex+2);
            if (gapIndex==-1){
                gapIndex=0;
                gapIndex=lineString.indexOf(" ", gapIndex+1);
                if (gapIndex==-1)
                    return lineString;
            }

            lineString=lineString.substring(0, gapIndex)+ "  " +lineString.substring(gapIndex+1, lineString.length());

            lineWidth=textPaint.measureText(lineString);
        }
        return lineString;
    }
    /***
     * this method calculate height for a line of text according to defined TextPaint
     * @param textPaint
     */
    private void setLineHeight(TextPaint textPaint) {

        Rect bounds=new Rect();
        String sampleStr="این حسین کیست که عالم همه دیوانه اوست";
        textPaint.getTextBounds(sampleStr, 0,sampleStr.length(), bounds);

        setLineHeight(bounds.height());

    }

    /***
     * this method calculate  view's height   according to line count and line height and view's width
     * @param lineListSize
     * @param lineHeigth
     * @param lineSpace
     */
    public void setMeasuredDimentions(int lineListSize,int lineHeigth, int lineSpace){
        int mHeight=lineListSize*(lineHeigth+lineSpace)+lineSpace;

        mHeight+=getPaddingRight()+getPaddingLeft();

        setMeasuredViewHeight(mHeight);

        setMeasuredViewWidth(getWidth());
    }


    private int getTextAreaWidth() {
        return textAreaWidth;
    }
    private void setTextAreaWidth(int textAreaWidth) {
        this.textAreaWidth = textAreaWidth;
    }

    private int getLineHeight() {
        return lineHeight;
    }
    private int getMeasuredViewHeight() {
        return measuredViewHeight;
    }
    private void setMeasuredViewHeight(int measuredViewHeight) {
        this.measuredViewHeight = measuredViewHeight;
    }
    private int getMeasuredViewWidth() {
        return measuredViewWidth;
    }
    private void setMeasuredViewWidth(int measuredViewWidth) {
        this.measuredViewWidth = measuredViewWidth;
    }
    private void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public String getText() {
        return text;
    }

    /***
     * Sets the string value of the JustifiedTextView. JustifiedTextView does not accept HTML-like formatting.
     * Related XML Attributes
     * -noghteh:text
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        calculate();
        invalidate();
    }

    public void setText(int resid) {
        setText(mContext.getResources().getString(resid));
    }

    public Typeface getTypeFace() {
        return getTextPaint().getTypeface();
    }
    public void setTypeFace(Typeface typeFace) {
        getTextPaint().setTypeface(typeFace);
    }

    public float getTextSize() {
        return getTextPaint().getTextSize();
    }
    public void setTextSize(int unit,float textSize) {
        textSize=TypedValue.applyDimension(unit, textSize, mContext.getResources().getDisplayMetrics());
        setTextSize(textSize);
    }

    private void setTextSize(float textSize) {
        getTextPaint().setTextSize(textSize);
        calculate();
        invalidate();
    }

    public TextPaint getTextPaint() {
        return textPaint;
    }
    public void setTextPaint(TextPaint textPaint) {
        this.textPaint = textPaint;
    }

    /***
     * set text color
     * @param textColor
     */
    public void setTextColor(int textColor) {
        getTextPaint().setColor(textColor);
        invalidate();
    }
    /***
     * define space between lines
     * @param lineSpace
     */
    public void setLineSpacing(int lineSpace) {
        this.lineSpace = lineSpace;
        invalidate();
    }

    /***
     *
     * @return text color
     */
    public int getTextColor() {
        return getTextPaint().getColor();
    }



    /***
     * space between lines - default is 0
     * @return
     */
    public int getLineSpace() {
        return lineSpace;
    }


    /***
     * get text alignment
     * @return
     */
    public Align getAlignment() {
        return getTextPaint().getTextAlign();
    }
    /***
     * Align text according to your language
     * @param align
     */
    public void setAlignment(Align align) {
        getTextPaint().setTextAlign(align);
        invalidate();
    }

}