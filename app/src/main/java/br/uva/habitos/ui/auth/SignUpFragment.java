package br.uva.habitos.ui.auth;

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

import br.uva.habitos.R;
import br.uva.habitos.data.UsuarioViewModel;
import br.uva.habitos.databinding.FragmentSignUpBinding;
import br.uva.habitos.model.Usuario;
import br.uva.habitos.util.FabViewModel;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;

    private FabViewModel fabViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        fabViewModel = new ViewModelProvider(requireActivity()).get(FabViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabViewModel.setVisibility(false);

        UsuarioViewModel usuarioViewModel =
                new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);

        binding.signupButton.setOnClickListener(v -> {
            String nome = binding.signupNome.getText().toString(),
                    email = binding.signupEmail.getText().toString(),
                    senha = binding.signupSenha.getText().toString(),
                    confirmarSenha = binding.signupConfirmarSenha.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(requireContext(), "Todos os campos devem estar preenchidos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                Toast.makeText(requireContext(), "As senhas devem ser iguais.", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario(
                    nome, email
            );

            // Perform login
            usuarioViewModel.definirUsuario(usuario);

            // Navigate back to profile
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_signup_to_address);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}