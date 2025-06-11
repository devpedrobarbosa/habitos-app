package br.uva.habitos.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FabViewModel extends ViewModel {

    private final MutableLiveData<Boolean> visibility = new MutableLiveData<>(true);

    public LiveData<Boolean> getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean b) {
        visibility.setValue(b);
    }
}
