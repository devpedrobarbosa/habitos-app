package br.uva.habitos.ui.habito;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.uva.habitos.R;
import br.uva.habitos.data.HabitosViewModel;
import br.uva.habitos.model.Habito;
import br.uva.habitos.util.MultiSpinner;

public class EditHabitoDialog extends DialogFragment {

    public static EditHabitoDialog newInstance(Habito habito) {
        EditHabitoDialog dialog = new EditHabitoDialog();
        Bundle args = new Bundle();
        args.putSerializable("habito", habito);
        dialog.setArguments(args);
        return dialog;
    }

    private HabitosViewModel habitosViewModel;

    private NumberPicker numberPicker;

    public interface EditHabitoDialogListener {
        void onHabitoEdited(Habito habito, int intervalo);
    }

    private EditHabitoDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (EditHabitoDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement EditHabitoDialogListener");
        }
    }

    private Habito selectedHabito;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_habito_dialog, null);

        if (getArguments() != null) {
            selectedHabito = (Habito) getArguments().getSerializable("habito");
        }

        habitosViewModel = new ViewModelProvider(requireActivity()).get(HabitosViewModel.class);

        numberPicker = view.findViewById(R.id.edit_intervalo_habito);

        // Set up number picker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(12);
        numberPicker.setValue(1); // Default value

        builder.setView(view)
                .setPositiveButton("Ok", (dialog, id) -> {
                    // Get interval value
                    int intervalo = numberPicker.getValue();
                    listener.onHabitoEdited(selectedHabito, intervalo * 60 * 60 * 1000);
                })
                .setNegativeButton("Cancelar", (dialog, id) -> Objects.requireNonNull(EditHabitoDialog.this.getDialog()).cancel());

        return builder.create();
    }
}