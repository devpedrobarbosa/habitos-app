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

import br.uva.habitos.model.Habito;
import br.uva.habitos.data.HabitosViewModel;
import br.uva.habitos.util.MultiSpinner;
import br.uva.habitos.R;

public class AddHabitoDialog extends DialogFragment {

    private HabitosViewModel habitosViewModel;

    private MultiSpinner multiSpinner;
    private NumberPicker numberPicker;

    public interface AddHabitoDialogListener {
        void onHabitoAdded(String selectedHabito, int intervalo);
    }

    private AddHabitoDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddHabitoDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AddHabitoDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_habito_dialog, null);

        habitosViewModel = new ViewModelProvider(requireActivity()).get(HabitosViewModel.class);

        // Initialize views
        multiSpinner = view.findViewById(R.id.nome_habito);
        numberPicker = view.findViewById(R.id.intervalo_habito);

        // Set up number picker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(12);
        numberPicker.setValue(1); // Default value

        // Set up habit options (you can load these from resources or database)
        List<Habito> currentHabitos = habitosViewModel.getHabitos().getValue();
        if(currentHabitos == null) currentHabitos = new ArrayList<>();
        List<String> habitOptions = new ArrayList<>();
        for(Habito habito : Habito.values()) {
            if(currentHabitos.contains(habito)) continue;
            habitOptions.add(habito.getDisplayName());
        }

        // Configure the multi-spinner
        multiSpinner.setItems(habitOptions, "Selecione um hábito", selected -> {
            // Handle selection if needed
        });

        builder.setView(view)
                .setPositiveButton("Ok", (dialog, id) -> {
                    // Get selected habit
                    String selectedHabito = multiSpinner.getSelectedItem().toString();

                    // Get interval value
                    int intervalo = numberPicker.getValue();

                    // Pass data back to activity
                    if (!selectedHabito.isEmpty()) {
                        listener.onHabitoAdded(selectedHabito, intervalo);
                    }
                })
                .setNegativeButton("Cancelar", (dialog, id) -> Objects.requireNonNull(AddHabitoDialog.this.getDialog()).cancel());

        return builder.create();
    }
}