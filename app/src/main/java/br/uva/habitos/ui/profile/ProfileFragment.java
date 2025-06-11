package br.uva.habitos.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import br.uva.habitos.R;
import br.uva.habitos.data.UsuarioViewModel;
import br.uva.habitos.databinding.FragmentProfileBinding;
import br.uva.habitos.model.Usuario;
import br.uva.habitos.util.FabViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private UsuarioViewModel usuarioViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        usuarioViewModel = new ViewModelProvider(requireActivity()).get(UsuarioViewModel.class);
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        FabViewModel fabViewModel = new ViewModelProvider(requireActivity()).get(FabViewModel.class);
        fabViewModel.setVisibility(false);

        // Observe user state
        usuarioViewModel.getUsuario().observe(getViewLifecycleOwner(), usuario -> {
            if (usuario == null || !usuario.isPremium()) {
                binding.getRoot().post(() -> {
                    NavController controller = Navigation.findNavController(requireView());
                    controller.navigate(R.id.action_profile_to_seja_premium);
                });
            } else {
                showProfileContent(profileViewModel, usuario);
            }
        });

        return root;
    }

    private void showProfileContent(ProfileViewModel profileViewModel, Usuario usuario) {
//        binding.logoutButton.setOnClickListener(v -> usuarioViewModel.definirUsuario(null));
//        if(usuario.getNome() != null)
//            binding.profileInfoNome.setText(usuario.getNome());
//        if(usuario.getEmail() != null)
//            binding.profileInfoEmail.setText(usuario.getEmail());
//        if(usuario.getCpf() != null)
//            binding.profileInfoCpf.setText(usuario.getCpf());
//        if(usuario.getTelefone() != null)
//            binding.profileInfoTelefone.setText(usuario.getTelefone());
//        if(usuario.getEndereco() != null)
//            binding.profileInfoEndereco.setText(usuario.getEndereco());
//        if(usuario.getCidade() != null)
//            binding.profileInfoCidade.setText(usuario.getCidade());
//        if(usuario.getEstado() != null)
//            binding.profileInfoEstado.setText(usuario.getEstado());
//        if(usuario.getCodigoPostal() != null)
//            binding.profileInfoPostal.setText(usuario.getCodigoPostal());
//        if(usuario.getNumeroCartao() != null)
//            binding.profileInfoNumeroCartao.setText(usuario.getNumeroCartao());
//        if(usuario.getTitularCartao() != null)
//            binding.profileInfoTitularCartao.setText(usuario.getTitularCartao());
//        if(usuario.getValidadeCartao() != null)
//            binding.profileInfoValidadeCartao.setText(usuario.getValidadeCartao());
//        if(usuario.getCodigoCartao() != null)
//            binding.profileInfoCodigoCartao.setText(usuario.getCodigoCartao());
//        binding.profileInfoPremium.setChecked(usuario.isPremium());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}