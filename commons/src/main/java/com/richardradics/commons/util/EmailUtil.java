package com.richardradics.commons.util;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Project: commons
 * Package: com.richardradics.commons.util
 * Created by richardradics on 2015.02.25..
 */
public class EmailUtil {


    public static void startEmailActivity(Context context, String to, String subject, String body, String pickerTitle){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{to});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT   , body);
        try {
            context.startActivity(Intent.createChooser(i, pickerTitle).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }


}
