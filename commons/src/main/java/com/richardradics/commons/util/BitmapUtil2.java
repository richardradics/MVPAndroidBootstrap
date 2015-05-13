package com.richardradics.commons.util;

import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by radicsrichard on 15. 03. 13..
 */
public class BitmapUtil2 {

    public static BitmapFactory.Options getSize(Context c, int resId){
        android.graphics.BitmapFactory.Options o = new android.graphics.BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(c.getResources(), resId, o);
        return o;
    }

}
