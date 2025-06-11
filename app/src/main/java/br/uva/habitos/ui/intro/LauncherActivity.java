package br.uva.habitos.ui.intro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.uva.habitos.MainActivity;
import br.uva.habitos.databinding.ActivityLauncherBinding;

public class LauncherActivity extends AppCompatActivity {

    ActivityLauncherBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.iniciarButton.setOnClickListener(v -> startActivity(new Intent(LauncherActivity.this, MainActivity.class)));
    }
}
