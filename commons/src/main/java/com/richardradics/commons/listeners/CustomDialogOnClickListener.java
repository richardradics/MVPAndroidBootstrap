package com.richardradics.commons.listeners;

import android.content.DialogInterface;

/**
 * Created by richardradics on 2015.02.23..
 */
public class CustomDialogOnClickListener implements DialogInterface.OnClickListener {

    private int id;

    public int getId() {
        return id;
    }

    ExtendedDialogOnClickListener extendedDialogOnClickListener;

    public CustomDialogOnClickListener(int id, ExtendedDialogOnClickListener extendedDialogOnClickListener){
        this.id = id;
        this.extendedDialogOnClickListener = extendedDialogOnClickListener;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(extendedDialogOnClickListener != null) {
            extendedDialogOnClickListener.onDialogClick(dialogInterface, i, id);
        }
    }
}
