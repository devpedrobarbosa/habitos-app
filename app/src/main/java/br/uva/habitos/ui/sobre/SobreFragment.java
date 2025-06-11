package br.uva.habitos.ui.sobre;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.uva.habitos.databinding.FragmentSobreBinding;
import br.uva.habitos.util.FabViewModel;

public class SobreFragment extends Fragment {

    private FragmentSobreBinding binding;

    private FabViewModel fabViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fabViewModel = new ViewModelProvider(requireActivity()).get(FabViewModel.class);
        fabViewModel.setVisibility(false);
        binding = FragmentSobreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}