package br.uva.habitos.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import br.uva.habitos.model.Usuario;

public class UsuarioViewModel extends ViewModel {

    private final MutableLiveData<Usuario> usuarioData = new MutableLiveData<>(null);

    public LiveData<Usuario> getUsuario() {
        return usuarioData;
    }

    public void definirUsuario(Usuario usuario) {
        usuarioData.setValue(usuario);
    }

    public void sair() {
        usuarioData.setValue(null);
    }
}