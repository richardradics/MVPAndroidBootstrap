package com.richardradics.commons.widget.floatingwidgets;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.richardradics.commons.R;
import com.richardradics.commons.util.EditTextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard Radics on 2015.02.11..
 */
public class ChoiceableAutoCompleteAdapter extends ArrayAdapter<Choiceable> implements Filterable {

    private ArrayList<Choiceable> choiceableArrayList;

    private ArrayList<Choiceable> backupList;

    public ChoiceableAutoCompleteAdapter(Context context, List<Choiceable> choiceableList) {
        this(context, R.layout.flw_widget_dropdown_item, choiceableList);
        this.backupList = new ArrayList<Choiceable>(choiceableList);
    }

    public ChoiceableAutoCompleteAdapter(Context context, int textViewResourceId, List<Choiceable> choiceableList) {
        super(context, textViewResourceId);
        this.backupList = new ArrayList<Choiceable>(choiceableList);
    }

    /**
     * This method should be implemented by subclasses to provide the filtered data to display in
     * the autocomplete popup.
     *
     * @param constraint The characters already entered by the user
     * @return A list of Strings matching the user constraint
     */
    protected ArrayList<Choiceable> asyncGetResults(CharSequence constraint) {
        ArrayList<Choiceable> resultList = new ArrayList<Choiceable>();

        for (Choiceable c : backupList) {
            if (c.getTitleText().toLowerCase().contains(constraint.toString().toLowerCase())
                    || EditTextUtil.removeAccents(c.getTitleText().toLowerCase()).contains(constraint.toString().toLowerCase())) {
                resultList.add(c);
            }
        }

        return resultList;
    }

    @Override
    public int getCount() {
        return choiceableArrayList.size();
    }

    @Override
    public Choiceable getItem(int index) {
        return choiceableArrayList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                    choiceableArrayList = asyncGetResults(constraint);

                    // Assign the data to the FilterResults
                    filterResults.values = choiceableArrayList;
                    filterResults.count = choiceableArrayList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                if(resultValue instanceof Choiceable){
                    return ((Choiceable) resultValue).getTitleText();
                }

                return super.convertResultToString(resultValue);
            }
        };
        return filter;
    }
}
