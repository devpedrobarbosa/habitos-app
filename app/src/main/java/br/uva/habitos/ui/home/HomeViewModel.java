package br.uva.habitos.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> proximoHabitoTitle = new MutableLiveData<>("Próximo Hábito"), habitoEmTitle = new MutableLiveData<>("Em Até");

    public HomeViewModel() {
    }

    public LiveData<String> getProximoHabitoTitle() {
        return proximoHabitoTitle;
    }

    public LiveData<String> getHabitoEmTitle() {
        return habitoEmTitle;
    }
}