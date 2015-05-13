package com.richardradics.commons.widget.floatingwidgets;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;

import com.marvinlabs.widget.floatinglabel.itempicker.ItemPicker;
import com.marvinlabs.widget.floatinglabel.itempicker.ItemPickerListener;
import com.marvinlabs.widget.floatinglabel.itempicker.ItemPrinter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard Radics on 2015.02.10..
 */
public class ChoiceablePickerDialogFragment extends DialogFragment implements ItemPicker<Choiceable> {

    protected ArrayList<Choiceable> availableItems = null;

    public static final String ARG_AVAILABLE_ITEMS = "AvailableItems";
    public static final String ARG_SELECTED_ITEMS_INDICES = "SelectedItemsIndices";
    public static final String ARG_TITLE = "Title";
    public static final String ARG_PICKER_ID = "PickerId";
    public static final String ARG_POSITIVE_BUTTON_TEXT = "PositiveButtonText";
    public static final String ARG_NEGATIVE_BUTTON_TEXT = "NegativeButtonText";
    public static final String ARG_ENABLE_MULTIPLE_SELECTION = "EnableMultipleSelection";

    protected int pickerId;
    protected String title;
    protected String positiveButtonText;
    protected String negativeButtonText;
    protected boolean enableMultipleSelection;
    protected SparseArray<Choiceable> selectedItems;
    protected ChoiceableItemPrinter itemPrinter;

    protected ArrayList<ItemPickerListener<Choiceable>> listeners = new ArrayList<ItemPickerListener<Choiceable>>();


    public static ChoiceablePickerDialogFragment newInstance(
            int pickerId,
            String title,
            String positiveButtonText,
            String negativeButtonText,
            boolean enableMultipleSelection,
            int[] selectedItemIndices,
            ArrayList<Choiceable> availableItems) {

        ChoiceablePickerDialogFragment f = new ChoiceablePickerDialogFragment();
        Bundle args = buildCommonArgsBundle(pickerId, title, positiveButtonText, negativeButtonText, enableMultipleSelection, selectedItemIndices);
        args.putParcelableArrayList(ARG_AVAILABLE_ITEMS, availableItems);
        f.setArguments(args);
        return f;
    }

    protected static Bundle buildCommonArgsBundle(int pickerId, String title, String positiveButtonText, String negativeButtonText, boolean enableMultipleSelection, int[] selectedItemIndices) {
        Bundle args = new Bundle();
        args.putInt(ARG_PICKER_ID, pickerId);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_POSITIVE_BUTTON_TEXT, positiveButtonText);
        args.putString(ARG_NEGATIVE_BUTTON_TEXT, negativeButtonText);
        args.putBoolean(ARG_ENABLE_MULTIPLE_SELECTION, enableMultipleSelection);
        args.putIntArray(ARG_SELECTED_ITEMS_INDICES, selectedItemIndices);
        return args;
    }

    @Override
    public void onPause() {
        // Persist the new selected items in the arguments
        getArguments().putIntArray(ARG_SELECTED_ITEMS_INDICES, getSelectedIndices());

        super.onPause();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        readArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(positiveButtonText, dialogButtonClickListener)
                .setNegativeButton(negativeButtonText, dialogButtonClickListener);

        if (title != null) builder.setTitle(title);

        if (enableMultipleSelection) {
            setupMultiChoiceDialog(builder);
        } else {
            setupSingleChoiceDialog(builder);
        }

        return builder.create();
    }

    protected void setupSingleChoiceDialog(AlertDialog.Builder builder) {
        final List<Choiceable> availableItems = getAvailableItems();

        final ItemPrinter<Choiceable> ip = getItemPrinter();
        CharSequence[] items = new CharSequence[availableItems.size()];
        for (int i = 0; i < availableItems.size(); ++i) {
            items[i] = ip.print(availableItems.get(i));
        }

        int checked = -1;
        if (selectedItems.size() > 0) {
            checked = selectedItems.keyAt(0);
            selectedItems.put(checked, getItemAt(checked));
        }

        builder.setSingleChoiceItems(items, checked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedItems.clear();
                selectedItems.put(which, getItemAt(which));
            }
        });
    }

    protected void setupMultiChoiceDialog(AlertDialog.Builder builder) {
        final List<Choiceable> availableItems = getAvailableItems();

        final ItemPrinter<Choiceable> ip = getItemPrinter();
        CharSequence[] items = new CharSequence[availableItems.size()];
        boolean[] checked = new boolean[availableItems.size()];
        for (int i = 0; i < availableItems.size(); ++i) {
            items[i] = ip.print(getItemAt(i));
            if (selectedItems.get(i) != null) {
                checked[i] = true;
            } else {
                checked[i] = false;
            }
        }

        builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) selectedItems.put(which, getItemAt(which));
                else selectedItems.delete(which);
            }
        });
    }

    DialogInterface.OnClickListener dialogButtonClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_POSITIVE) {
                notifyItemsSelected();
            } else {
                notifyDialogCancelled();
            }
            dialog.dismiss();
        }
    };

    protected void readArguments() {
        final Bundle args = getArguments();

        pickerId = args.getInt(ARG_PICKER_ID);
        title = args.getString(ARG_TITLE);
        positiveButtonText = args.getString(ARG_POSITIVE_BUTTON_TEXT);
        negativeButtonText = args.getString(ARG_NEGATIVE_BUTTON_TEXT);
        enableMultipleSelection = args.getBoolean(ARG_ENABLE_MULTIPLE_SELECTION);

        setSelectedItems(args.getIntArray(ARG_SELECTED_ITEMS_INDICES));
    }


    @SuppressWarnings("unchecked")
    protected void notifyDialogCancelled() {
        if (getActivity() instanceof ItemPickerListener) {
            ((ItemPickerListener<Choiceable>) getActivity()).onCancelled(getPickerId());
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            ((ItemPickerListener<Choiceable>) getParentFragment()).onCancelled(getPickerId());
        }

        if (getTargetFragment() instanceof ItemPickerListener) {
            ((ItemPickerListener<Choiceable>) getTargetFragment()).onCancelled(getPickerId());
        }
    }

    @SuppressWarnings("unchecked")
    protected void notifyItemsSelected() {
        if (getActivity() instanceof ItemPickerListener) {
            ((ItemPickerListener<Choiceable>) getActivity()).onItemsSelected(getPickerId(), getSelectedIndices());
        }

        if (getParentFragment() instanceof ItemPickerListener) {
            ((ItemPickerListener<Choiceable>) getParentFragment()).onItemsSelected(getPickerId(), getSelectedIndices());
        }

        if (getTargetFragment() instanceof ItemPickerListener) {
            ((ItemPickerListener<Choiceable>) getTargetFragment()).onItemsSelected(getPickerId(), getSelectedIndices());
        }
    }

    public ItemPrinter<Choiceable> getItemPrinter() {
        if (itemPrinter == null) {
            itemPrinter = new ChoiceableItemPrinter();
        }
        return itemPrinter;
    }

    @Override
    public int getPickerId() {
        return pickerId;
    }

    @Override
    public void setSelectedItems(int[] itemIndices) {
        selectedItems = new SparseArray();

        if (itemIndices != null) {
            final List<Choiceable> availableItems = getAvailableItems();
            final int availableItemsCount = availableItems.size();

            for (int i : itemIndices) {
                if (i >= 0 && i < availableItemsCount) {
                    selectedItems.put(i, availableItems.get(i));
                }
            }
        }
    }

    @Override
    public int[] getSelectedIndices() {
        int[] selection = new int[selectedItems.size()];
        for (int i = 0; i < selectedItems.size(); ++i) {
            selection[i] = selectedItems.keyAt(i);
        }
        return selection;
    }

    @Override
    public boolean isSelectionEmpty() {
        return selectedItems.size() == 0;
    }

    /**
     * Get all the items that can be selected by the user
     *
     * @return
     */
    protected List<Choiceable> getAvailableItems(){
        if (availableItems == null) {
            availableItems = getArguments().getParcelableArrayList(ARG_AVAILABLE_ITEMS);
            if (availableItems == null || availableItems.isEmpty()) {
                throw new RuntimeException("ChoicableDialogFragment needs some items to pick from");
            }
        }
        return availableItems;
    }

    /**
     * Get
     *
     * @param position
     * @return
     */
    protected Choiceable getItemAt(int position) {
        return getAvailableItems().get(position);
    }


}
