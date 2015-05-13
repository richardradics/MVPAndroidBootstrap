package com.richardradics.commons.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by radicsrichard on 15. 04. 15..
 */
public class ResourceUtil {

    static public int getAndroidDrawableId(String pDrawableName){
        int resourceId= Resources.getSystem().getIdentifier(pDrawableName, "drawable", "android");
        if(resourceId==0){
            return 0;
        } else {
            return resourceId;
        }
    }


}
