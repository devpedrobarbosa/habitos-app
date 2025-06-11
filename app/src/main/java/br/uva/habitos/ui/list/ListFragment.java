package br.uva.habitos.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Map;

import br.uva.habitos.data.HabitosViewModel;
import br.uva.habitos.databinding.FragmentListBinding;
import br.uva.habitos.model.Habito;
import br.uva.habitos.ui.habito.EditHabitoDialog;
import br.uva.habitos.util.HabitosAdapter;

public class ListFragment extends Fragment implements HabitosAdapter.OnHabitoClickListener {

    private FragmentListBinding binding;
    private ListViewModel viewModel;
    private HabitosViewModel habitosViewModel;
    private HabitosAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        habitosViewModel = new ViewModelProvider(requireActivity()).get(HabitosViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);

        // Setup RecyclerView
        adapter = new HabitosAdapter(this);
        binding.habitosRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.habitosRecyclerView.setAdapter(adapter);

        // Observe habit status changes
        habitosViewModel.getIntervaloHabitos().observe(getViewLifecycleOwner(), statusMap -> {
            for (Map.Entry<Habito, Long> entry : statusMap.entrySet()) {
                adapter.updateHabitoStatus(entry.getKey(), entry.getValue());
            }
        });
    }

    @Override
    public void onHabitoSet(Habito habito) {
        // Handle habit setting - this would show a dialog or another fragment
        // to configure the habit

        // For example:
        EditHabitoDialog dialog = EditHabitoDialog.newInstance(habito);
        dialog.show(getChildFragmentManager(), "EditHabitoDialog");

        // After configuration is done, update the ViewModel
        // viewModel.setHabitoConfigured(habito, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}