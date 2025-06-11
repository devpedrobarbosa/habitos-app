package br.uva.habitos.util;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.uva.habitos.R;
import br.uva.habitos.model.Habito;

public class HabitosAdapter extends RecyclerView.Adapter<HabitosAdapter.HabitoViewHolder> {

    private final List<Habito> habitos;
    private final Map<Habito, Long> habitosStatus;
    private final OnHabitoClickListener listener;

    public interface OnHabitoClickListener {
        void onHabitoSet(Habito habito);
    }

    public HabitosAdapter(OnHabitoClickListener listener) {
        this.habitos = Arrays.asList(Habito.values());
        this.habitosStatus = new HashMap<>();
        this.listener = listener;
        
        // Initialize status (you would load this from your ViewModel)
        for (Habito habito : habitos) {
            habitosStatus.put(habito, 0L); // Default to not set
        }
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_habito, parent, false);
        return new HabitoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        Habito habito = habitos.get(position);
        double intervalo = habitosStatus.get(habito);
        
        holder.habitoName.setText(habito.getDisplayName());
        holder.habitoIcon.setImageResource(habito.getPngId());
        
        if (intervalo > 0) {
            holder.habitoStatus.setText(String.format(Locale.getDefault(), "%d em %d hora" + (intervalo == 1 ? "" : "s"), (int) intervalo, (int) intervalo));
            holder.habitoStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.teal_700));
            holder.actionButton.setText("Alterar");
        } else {
            holder.habitoStatus.setText("Não definido");
            holder.habitoStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_700));
            holder.actionButton.setText("Definir");
        }
        
        holder.actionButton.setOnClickListener(v -> listener.onHabitoSet(habito));
    }

    @Override
    public int getItemCount() {
        return habitos.size();
    }

    public void updateHabitoStatus(Habito habito, long intervalo) {
        habitosStatus.put(habito, intervalo / 1000 / 60 / 60);
        notifyItemChanged(habitos.indexOf(habito));
    }

    static class HabitoViewHolder extends RecyclerView.ViewHolder {
        ImageView habitoIcon;
        TextView habitoName;
        TextView habitoStatus;
        Button actionButton;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            habitoIcon = itemView.findViewById(R.id.habito_icon);
            habitoName = itemView.findViewById(R.id.habito_name);
            habitoStatus = itemView.findViewById(R.id.habito_status);
            actionButton = itemView.findViewById(R.id.habito_action_button);
        }
    }
}