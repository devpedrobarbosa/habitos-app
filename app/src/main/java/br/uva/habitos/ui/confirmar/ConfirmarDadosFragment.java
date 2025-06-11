package br.uva.habitos.ui.confirmar;

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
import android.widget.Toast;

import java.util.Collections;

import br.uva.habitos.R;
import br.uva.habitos.data.UsuarioViewModel;
import br.uva.habitos.databinding.FragmentConfirmarDadosBinding;

public class ConfirmarDadosFragment extends Fragment {

    private FragmentConfirmarDadosBinding binding;

    private UsuarioViewModel usuarioViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        binding = FragmentConfirmarDadosBinding.inflate(inflater, container, false);
        binding.addressEstado.setEnabled(false);
        binding.paymentBandeira.setEnabled(false);
        usuarioViewModel.getUsuario().observe(getViewLifecycleOwner(), usuario -> {
            if(usuario == null) return;
            if(usuario.getNome() != null)
                binding.signupNome.setText(usuario.getNome());
            if(usuario.getEmail() != null)
                binding.signupEmail.setText(usuario.getEmail());
            if(usuario.getEndereco() != null)
                binding.addressNome.setText(usuario.getEndereco());
            if(usuario.getEstado() != null)
                binding.addressEstado.setItems(Collections.singletonList(usuario.getEstado()), "Selecione um estado...", selected -> {});
            if(usuario.getCidade() != null)
                binding.addressCidade.setText(usuario.getCidade());
            if(usuario.getCodigoPostal() != null)
                binding.addressPostal.setText(usuario.getCodigoPostal());
            if(usuario.getTelefone() != null)
                binding.addressTelefone.setText(usuario.getTelefone());
            if(usuario.getCpf() != null)
                binding.paymentCpf.setText(usuario.getCpf());
            if(usuario.getNumeroCartao() != null)
                binding.paymentNumeroCartao.setText(usuario.getNumeroCartao());
            if(usuario.getBandeiraCartao() != null)
                binding.paymentBandeira.setItems(Collections.singletonList(usuario.getBandeiraCartao()), "Selecione uma bandeira...", selected -> {});
            if(usuario.getTitularCartao() != null)
                binding.paymentNomeTitular.setText(usuario.getTitularCartao());
            if(usuario.getValidadeCartao() != null)
                binding.paymentValidadeCartao.setText(usuario.getValidadeCartao());
            if(usuario.getCodigoCartao() != null)
                binding.paymentCodigoCartao.setText(usuario.getCodigoCartao());
        });
        binding.confirmarButton.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Dados confirmados. Cadastro realizado!", Toast.LENGTH_SHORT).show();
            NavController controller = Navigation.findNavController(requireView());
            controller.navigate(R.id.action_confirmar_dados_to_profile);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}