package br.uva.habitos.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> loggedIn = new MutableLiveData<>("Logged in"), loggedOut = new MutableLiveData<>("Logged out");

    public LiveData<String> getLoggedIn() {
        return loggedIn;
    }

    public LiveData<String> getLoggedOut() {
        return loggedOut;
    }
}