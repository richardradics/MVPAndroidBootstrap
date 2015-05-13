package com.richardradics.commons.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.richardradics.commons.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Richard Radics on 2015.02.09..
 */
public class EditTextUtil {


    /**
     * Puts the text cursor to the end.
     *
     * @param editText
     */
    public static void putCursorAtTheEnd(EditText editText) {
        editText.setSelection(editText.getText().length());
    }

    /**
     * Sets the edittext text.
     *
     * @param editText
     * @param s
     */
    public static void setText(EditText editText, String s) {
        if (s == null) {
            return;
        } else {
            editText.setText(s);
        }

    }

    /**
     * Checks the edittext text is empty.
     *
     * @param editText
     * @return
     */
    public static boolean isEditTextEmpty(EditText editText) {
        if (editText != null) {
            if (editText.getText().toString().equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * Hides softkeyboard.
     *
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Hides soft input.
     *
     * @param view
     * @param context
     */
    public static void hideSoftInPut(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static View.OnKeyListener getSoftInPutHideListener(final Context context) {

        View.OnKeyListener onKeyList = new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    EditTextUtil.hideSoftInPut((EditText) v, context);
                    return true;
                }
                return false;
            }
        };

        return onKeyList;
    }

    public static OnEditorActionListener getSoftInputHideEditorActionListener(final Context context, final EditText editText) {
        OnEditorActionListener hideEditorListener = new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    EditTextUtil.hideSoftInPut(editText, context);
                    return true;
                }
                return false;
            }
        };

        return hideEditorListener;
    }

    /**
     * Sets the edittext to multiline.
     *
     * @param editText
     */
    public static void setEditTextMultiLine(EditText editText) {
        editText.setSingleLine(false);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    }


    private static Map<Character, Character> MAP_NORM;

    /**
     * Removes the accents from the string.
     *
     * @param value
     * @return
     */
    public static String removeAccents(String value) {
        if (MAP_NORM == null || MAP_NORM.size() == 0) {
            MAP_NORM = new HashMap<Character, Character>();
            MAP_NORM.put('À', 'A');
            MAP_NORM.put('Á', 'A');
            MAP_NORM.put('Â', 'A');
            MAP_NORM.put('Ã', 'A');
            MAP_NORM.put('Ä', 'A');
            MAP_NORM.put('È', 'E');
            MAP_NORM.put('É', 'E');
            MAP_NORM.put('Ê', 'E');
            MAP_NORM.put('Ë', 'E');
            MAP_NORM.put('Í', 'I');
            MAP_NORM.put('Ì', 'I');
            MAP_NORM.put('Î', 'I');
            MAP_NORM.put('Ï', 'I');
            MAP_NORM.put('Ù', 'U');
            MAP_NORM.put('Ú', 'U');
            MAP_NORM.put('Û', 'U');
            MAP_NORM.put('Ü', 'U');
            MAP_NORM.put('Ò', 'O');
            MAP_NORM.put('Ó', 'O');
            MAP_NORM.put('Ô', 'O');
            MAP_NORM.put('Õ', 'O');
            MAP_NORM.put('Ö', 'O');
            MAP_NORM.put('Ñ', 'N');
            MAP_NORM.put('Ç', 'C');
            MAP_NORM.put('ª', 'A');
            MAP_NORM.put('º', 'O');
            MAP_NORM.put('§', 'S');
            MAP_NORM.put('³', '3');
            MAP_NORM.put('²', '2');
            MAP_NORM.put('¹', '1');
            MAP_NORM.put('à', 'a');
            MAP_NORM.put('á', 'a');
            MAP_NORM.put('â', 'a');
            MAP_NORM.put('ã', 'a');
            MAP_NORM.put('ä', 'a');
            MAP_NORM.put('è', 'e');
            MAP_NORM.put('é', 'e');
            MAP_NORM.put('ê', 'e');
            MAP_NORM.put('ë', 'e');
            MAP_NORM.put('í', 'i');
            MAP_NORM.put('ì', 'i');
            MAP_NORM.put('î', 'i');
            MAP_NORM.put('ï', 'i');
            MAP_NORM.put('ù', 'u');
            MAP_NORM.put('ú', 'u');
            MAP_NORM.put('û', 'u');
            MAP_NORM.put('ü', 'u');
            MAP_NORM.put('ò', 'o');
            MAP_NORM.put('ó', 'o');
            MAP_NORM.put('ô', 'o');
            MAP_NORM.put('õ', 'o');
            MAP_NORM.put('ö', 'o');
            MAP_NORM.put('ñ', 'n');
            MAP_NORM.put('ç', 'c');
        }

        if (value == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder(value);

        for (int i = 0; i < value.length(); i++) {
            Character c = MAP_NORM.get(sb.charAt(i));
            if (c != null) {
                sb.setCharAt(i, c.charValue());
            }
        }

        return sb.toString();

    }


    /**
     * Sets the edittext's hint icon and text.
     *
     * @param editText
     * @param drawableResource
     * @param hintText
     */
    public static void setSearchHintIcon(EditText editText, int drawableResource, String hintText) {
        try {

            SpannableStringBuilder stopHint = new SpannableStringBuilder("   ");
            stopHint.append(hintText);

// Add the icon as an spannable
            Drawable searchIcon = editText.getContext().getResources().getDrawable(drawableResource);
            Float rawTextSize = editText.getTextSize();
            int textSize = (int) (rawTextSize * 1.25);
            searchIcon.setBounds(0, 0, textSize, textSize);
            stopHint.setSpan(new ImageSpan(searchIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set the new hint text
            editText.setHint(stopHint);
            //searchBox.setTextColor(Color.WHITE);
            editText.setHintTextColor(Color.LTGRAY);

        } catch (Exception e) {
            Log.e("EditTextUtil", e.getMessage(), e);
        }
    }



    /**
     * Sets the edittext's hint icon and text.
     *
     * @param editText
     * @param drawableResource
     * @param hintText
     */
    public static void setSearchHintWithColorIcon(EditText editText, int drawableResource, int textColor, String hintText) {
        try {

            SpannableStringBuilder stopHint = new SpannableStringBuilder("   ");
            stopHint.append(hintText);

// Add the icon as an spannable
            Drawable searchIcon = editText.getContext().getResources().getDrawable(drawableResource);
            Float rawTextSize = editText.getTextSize();
            int textSize = (int) (rawTextSize * 1.25);
            searchIcon.setBounds(0, 0, textSize, textSize);
            stopHint.setSpan(new ImageSpan(searchIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set the new hint text
            editText.setHint(stopHint);
            //searchBox.setTextColor(Color.WHITE);
            editText.setHintTextColor(textColor);

        } catch (Exception e) {
            Log.e("EditTextUtil", e.getMessage(), e);
        }
    }

}
