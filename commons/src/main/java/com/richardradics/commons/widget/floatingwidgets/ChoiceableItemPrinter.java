package com.richardradics.commons.widget.floatingwidgets;

import com.marvinlabs.widget.floatinglabel.itempicker.ItemPrinter;

import java.util.Collection;

/**
 * Created by Richard Radics on 2015.02.10..
 */
public class ChoiceableItemPrinter implements ItemPrinter<Choiceable> {


    @Override
    public String print(Choiceable choiceable) {
        return choiceable.getTitleText() == null ? "" : choiceable.getTitleText();
    }

    @Override
    public String printCollection(Collection<Choiceable> choiceables) {
        if (choiceables.size() == 0) return "";

        StringBuilder sb = new StringBuilder();
        boolean prependSeparator = false;
        for (Choiceable item : choiceables) {
            if (prependSeparator) {
                sb.append(", ");
            } else {
                prependSeparator = true;
            }

            sb.append(print(item));
        }

        return sb.toString();
    }

}
