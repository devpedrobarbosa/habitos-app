package br.uva.habitos.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Timer;
import java.util.TimerTask;

import br.uva.habitos.model.Habito;
import br.uva.habitos.data.HabitosViewModel;
import br.uva.habitos.R;
import br.uva.habitos.databinding.FragmentHomeBinding;
import br.uva.habitos.util.FabViewModel;
import br.uva.habitos.util.TimeUtil;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView proximoHabitoTitle, proximoHabito, habitoEmTitle, habitoEm;
    private ImageView habitoPng;
    private Button concluirHabito;
    private HabitosViewModel habitosViewModel;
    private Timer timer;
    private boolean isToastShown = false;
    private Long lastInterval = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        habitosViewModel = new ViewModelProvider(requireActivity()).get(HabitosViewModel.class);

        habitosViewModel.calcularHabito();

        FabViewModel fabViewModel = new ViewModelProvider(requireActivity()).get(FabViewModel.class);
        fabViewModel.setVisibility(true);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeViews();
        setupObservers(homeViewModel);

        concluirHabito.setOnClickListener(v -> {
            habitosViewModel.finalizarHabito();
            Toast.makeText(requireContext(), "Hábito concluído! +1 ponto", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    private void initializeViews() {
        proximoHabitoTitle = binding.proximoHabitoTitle;
        proximoHabito = binding.proximoHabito;
        habitoEmTitle = binding.habitoEmTitle;
        habitoEm = binding.habitoEm;
        habitoPng = binding.habitoPng;
        concluirHabito = binding.concluirHabito;
    }

    private void setupObservers(HomeViewModel homeViewModel) {
        homeViewModel.getProximoHabitoTitle().observe(getViewLifecycleOwner(), proximoHabitoTitle::setText);
        homeViewModel.getHabitoEmTitle().observe(getViewLifecycleOwner(), habitoEmTitle::setText);

        // Observe habitoEm only once outside the timer
        updateUI(0);
        habitosViewModel.getHabitoEm().observe(getViewLifecycleOwner(), intervalo -> {
            if (intervalo == null) return;

            // Check if interval actually changed
            if (lastInterval != null && lastInterval.equals(intervalo)) {
                return;
            }
            lastInterval = intervalo;

            if (intervalo <= 0) {
                if (!isToastShown) {
                    showExpiredToast();
                    isToastShown = true;
                    habitosViewModel.finalizarHabito();
                }
                return;
            }

            isToastShown = false;
            updateUI(intervalo);
        });

        habitosViewModel.getHabito().observe(getViewLifecycleOwner(), this::handleHabitoChanges);
    }

    private void handleHabitoChanges(Habito habito) {
        preencherHabito(habito);

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        isToastShown = false;
        lastInterval = null;

        if (habito != null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    updateHabitoStatus();
                }
            }, 0, 1000);
        }
    }

    private void updateHabitoStatus() {
        if (getActivity() == null) return;

        getActivity().runOnUiThread(() -> {
            // Just trigger the calculation - the observer will handle the update
            habitosViewModel.calcularHabitoEm();
        });
    }

    private void showExpiredToast() {
        Toast.makeText(requireActivity(), "Hábito expirou! -1 ponto", Toast.LENGTH_SHORT).show();
    }

    private void updateUI(long intervalo) {
        habitoEm.setText(TimeUtil.writeInterval(intervalo));
        habitoEm.setTextColor(intervalo < 10000 ? Color.RED : Color.GRAY);
        concluirHabito.setEnabled(habitosViewModel.getHabito().getValue() != null && intervalo < 10000);
    }

    private void preencherHabito(Habito habito) {
        if (habitoPng != null) {
            habitoPng.setImageResource(habito != null ? habito.getPngId() : R.drawable.not_found);
            habitoPng.setAlpha(habito != null ? 1 : 0.5F);
        }
        if (proximoHabito != null) {
            proximoHabito.setText(habito != null ? habito.getDisplayName() : "Nenhum");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        binding = null;
    }
}