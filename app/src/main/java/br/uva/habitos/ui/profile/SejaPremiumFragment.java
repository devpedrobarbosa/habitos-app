package br.uva.habitos.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.uva.habitos.R;
import br.uva.habitos.databinding.FragmentSejaPremiumBinding;
import br.uva.habitos.util.FabViewModel;

public class SejaPremiumFragment extends Fragment {

    private FragmentSejaPremiumBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FabViewModel fabViewModel = new ViewModelProvider(requireActivity()).get(FabViewModel.class);
        fabViewModel.setVisibility(false);
        binding = FragmentSejaPremiumBinding.inflate(inflater, container, false);
        binding.premiumButton.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(requireView());
            controller.navigate(R.id.action_seja_premium_to_signup);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}