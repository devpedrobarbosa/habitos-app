package br.uva.habitos.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.Arrays;
import java.util.List;

public class MultiSpinner extends AppCompatSpinner implements
        DialogInterface.OnClickListener, DialogInterface.OnCancelListener {  // Changed interface

    private List<String> items;
    private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;
    private int lastSelectedIndex = 0;  // Track last selected index

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // Update selection
        Arrays.fill(selected, false);
        selected[which] = true;
        lastSelectedIndex = which;

        // Update spinner text immediately
        updateSpinnerText();

        // Notify listener
        if (listener != null) {
            listener.onItemsSelected(selected);
        }

        // Dismiss the dialog after selection
        dialog.dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // No need for special handling since we update on click
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setSingleChoiceItems(
                items.toArray(new CharSequence[0]), lastSelectedIndex, this);
        builder.setPositiveButton(android.R.string.ok,
                (dialog, which) -> dialog.dismiss());
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    private void updateSpinnerText() {
        String spinnerText = defaultText;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                spinnerText = items.get(i);
                break; // Only one item can be selected
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[] { spinnerText });
        setAdapter(adapter);
    }

    public void setItems(List<String> items, String allText,
                         MultiSpinnerListener listener) {
        this.items = items;
        this.defaultText = allText;
        this.listener = listener;

        // Initialize selection array
        selected = new boolean[items.size()];
        if (!items.isEmpty()) {
            selected[0] = true; // Select first item by default
        }

        // Set initial spinner text
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                new String[] {!items.isEmpty() ? items.get(0) : allText });
        setAdapter(adapter);
    }

    public int itemIndex(String item) {
        return items.indexOf(item);
    }

    public interface MultiSpinnerListener {
        void onItemsSelected(boolean[] selected);
    }

    // Helper method to get selected item
    public String getSelectedItem() {
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                return items.get(i);
            }
        }
        return null;
    }
}