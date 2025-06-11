package br.uva.habitos.ui.payment;

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

import java.util.Arrays;
import java.util.List;

import br.uva.habitos.R;
import br.uva.habitos.data.UsuarioViewModel;
import br.uva.habitos.databinding.FragmentPaymentBinding;
import br.uva.habitos.model.Usuario;
import br.uva.habitos.util.FabViewModel;
import br.uva.habitos.util.MultiSpinner;

public class PaymentFragment extends Fragment {

    public static final List<String> bandeiras = Arrays.asList("Visa", "Mastercard", "Elo", "American Express", "Hipercard", "Diners Club", "Discover");

    private FragmentPaymentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UsuarioViewModel usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        FabViewModel fabViewModel = new ViewModelProvider(requireActivity()).get(FabViewModel.class);
        fabViewModel.setVisibility(false);
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        binding.paymentBandeira.setItems(bandeiras, "Selecionar bandeira...", selected -> {
        });
        binding.paymentButton.setOnClickListener(v -> {
            Usuario usuario = usuarioViewModel.getUsuario().getValue();
            if (usuario == null) {
                NavController controller = Navigation.findNavController(requireView());
                controller.navigate(R.id.action_payment_to_profile);
                return;
            }
            String cpf = binding.paymentCpf.getText().toString(),
                    numeroCartao = binding.paymentNumeroCartao.getText().toString(),
                    bandeiraCartao = binding.paymentBandeira.getSelectedItem(),
                    titularCartao = binding.paymentNomeTitular.getText().toString(),
                    validadeCartao = binding.paymentValidadeCartao.getText().toString(),
                    codigoCartao = binding.paymentCodigoCartao.getText().toString();
            if(cpf.isEmpty() || numeroCartao.isEmpty() || titularCartao.isEmpty() || validadeCartao.isEmpty() || codigoCartao.isEmpty()) {
                Toast.makeText(requireContext(), "Todos os campos devem estar preenchidos.", Toast.LENGTH_SHORT).show();
                return;
            }
            usuario.setCpf(cpf);
            usuario.setPremium(true);
            if (binding.paymentRemember.isChecked()) {
                usuario.setNumeroCartao(numeroCartao);
                usuario.setBandeiraCartao(bandeiraCartao);
                usuario.setTitularCartao(titularCartao);
                usuario.setValidadeCartao(validadeCartao);
                usuario.setCodigoCartao(codigoCartao);
            }
            usuarioViewModel.definirUsuario(usuario);
            NavController controller = Navigation.findNavController(requireView());
            controller.navigate(R.id.action_payment_to_confirmar_dados);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}