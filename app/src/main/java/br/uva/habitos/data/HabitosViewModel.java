package br.uva.habitos.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.uva.habitos.model.Habito;

public class HabitosViewModel extends ViewModel {

    private final MutableLiveData<List<Habito>> habitos = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Map<Habito, Long>> intervaloHabitos = new MutableLiveData<>(new HashMap<>()), habitosRealizados = new MutableLiveData<>(new HashMap<>());
    private final MutableLiveData<Habito> habito = new MutableLiveData<>();
    private final MutableLiveData<Long> habitoEm = new MutableLiveData<>();

    public LiveData<List<Habito>> getHabitos() {
        return habitos;
    }

    public LiveData<Map<Habito, Long>> getIntervaloHabitos() {
        return intervaloHabitos;
    }

    public LiveData<Map<Habito, Long>> getHabitosRealizados() {
        return habitosRealizados;
    }

    public LiveData<Habito> getHabito() {
        return habito;
    }

    public LiveData<Long> getHabitoEm() {
        return habitoEm;
    }

    public void finalizarHabito() {
        Habito currentHabito = habito.getValue();
        if (currentHabito == null) return;
        Map<Habito, Long> currentHabitosRealizados = habitosRealizados.getValue();
        if (currentHabitosRealizados == null) {
            currentHabitosRealizados = new HashMap<>();
        }
        currentHabitosRealizados.put(currentHabito, System.currentTimeMillis());
        habitosRealizados.setValue(currentHabitosRealizados);
        calcularHabito();
    }

    public void calcularHabito() {
        Map<Habito, Long> currentIntervaloHabitos = intervaloHabitos.getValue(), currentHabitosRealizados = habitosRealizados.getValue();
        if (currentIntervaloHabitos == null || currentHabitosRealizados == null) return;
        Habito proximo = null;
        long menorIntervalo = 0;
        for (Map.Entry<Habito, Long> entry : currentHabitosRealizados.entrySet()) {
            Long l = currentIntervaloHabitos.get(entry.getKey());
            if (l == null) continue;
            long intervalo = l - (System.currentTimeMillis() - entry.getValue());
            if (menorIntervalo <= 0 || intervalo < menorIntervalo) {
                menorIntervalo = intervalo;
                proximo = entry.getKey();
            }
        }
        habito.setValue(proximo);
    }

    public void setHabito(Habito habito, long intervalo) {
        List<Habito> currentHabitos = habitos.getValue();
        if (currentHabitos == null) {
            currentHabitos = new ArrayList<>();
        }
        if(!currentHabitos.contains(habito)) {
            currentHabitos.add(habito);
            habitos.setValue(currentHabitos);
        }
        Map<Habito, Long> currentIntervaloHabitos = intervaloHabitos.getValue(), currentHabitosRealizados = habitosRealizados.getValue();
        if (currentIntervaloHabitos == null) {
            currentIntervaloHabitos = new HashMap<>();
        }
        currentIntervaloHabitos.put(habito, intervalo);
        intervaloHabitos.setValue(currentIntervaloHabitos);
        if (currentHabitosRealizados == null) {
            currentHabitosRealizados = new HashMap<>();
        }
        currentHabitosRealizados.put(habito, System.currentTimeMillis());
        habitosRealizados.setValue(currentHabitosRealizados);
        calcularHabito();
    }

    public void calcularHabitoEm() {
        Habito currentHabito = habito.getValue();
        if (currentHabito == null) return;
        Map<Habito, Long> currentIntervaloHabitos = intervaloHabitos.getValue(), currentHabitosRealizados = habitosRealizados.getValue();
        if(currentIntervaloHabitos == null || currentHabitosRealizados == null) return;
        Long intervalo = currentIntervaloHabitos.get(currentHabito), execucao = currentHabitosRealizados.get(currentHabito);
        if(intervalo == null || execucao == null) return;
        long tempoDesdeAExecucao = System.currentTimeMillis() - execucao;
        habitoEm.setValue(intervalo - tempoDesdeAExecucao);
    }

    public void diminuirTempo() {
        Habito currentHabito = habito.getValue();
        if(currentHabito == null) return;
        Map<Habito, Long> currentHabitosRealizados = habitosRealizados.getValue();
        if(currentHabitosRealizados == null) return;
        Long currentHabitoEm = habitoEm.getValue();
        if(currentHabitoEm == null) return;
        currentHabitosRealizados.put(currentHabito, System.currentTimeMillis() - currentHabitoEm);
        habitosRealizados.setValue(currentHabitosRealizados);
    }
}