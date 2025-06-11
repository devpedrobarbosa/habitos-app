package br.uva.habitos.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Arrays;
import java.util.List;

import br.uva.habitos.R;
import br.uva.habitos.data.UsuarioViewModel;
import br.uva.habitos.databinding.FragmentAddressBinding;
import br.uva.habitos.model.Usuario;
import br.uva.habitos.util.FabViewModel;

public class AddressFragment extends Fragment {

    public static final List<String> estados = Arrays.asList(
            "Acre", "Amapá", "Amazonas", "Pará", "Rondônia", "Roraima", "Tocantins",
            "Alagoas", "Bahia", "Ceará", "Maranhão", "Paraíba", "Pernambuco", "Piauí",
            "Distrito Federal", "Goiás", "Mato Grosso", "Mato Grosso do Sul",
            "Espírito Santo", "Minas Gerais", "Rio de Janeiro", "São Paulo",
            "Paraná", "Rio Grande do Sul", "Santa Catarina"
    );

    private FragmentAddressBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UsuarioViewModel usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        FabViewModel fabViewModel = new ViewModelProvider(requireActivity()).get(FabViewModel.class);
        fabViewModel.setVisibility(false);
        binding = FragmentAddressBinding.inflate(inflater, container, false);
        binding.addressEstado.setItems(estados, "Selecione um estado...", selected -> {});
        binding.addressButton.setOnClickListener(v -> {
            Usuario usuario = usuarioViewModel.getUsuario().getValue();
            if(usuario == null) return;
            String endereco = binding.addressNome.getText().toString(),
                    cidade = binding.addressCidade.getText().toString(),
                    estado = binding.addressEstado.getSelectedItem(),
                    codigoPostal = binding.addressPostal.getText().toString(),
                    telefone = binding.addressTelefone.getText().toString();
            if(endereco.isEmpty() || cidade.isEmpty() || estado.isEmpty() || codigoPostal.isEmpty() || telefone.isEmpty()) {
                Toast.makeText(requireContext(), "Todos os campos devem estar preenchidos.", Toast.LENGTH_SHORT).show();
                return;
            }
            usuario.setEndereco(endereco);
            usuario.setCidade(cidade);
            usuario.setEstado(estado);
            usuario.setCodigoPostal(codigoPostal);
            usuario.setTelefone(telefone);
            usuarioViewModel.definirUsuario(usuario);
            NavController controller = Navigation.findNavController(requireView());
            controller.navigate(R.id.action_address_to_payment);
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}