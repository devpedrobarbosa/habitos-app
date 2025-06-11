package br.uva.habitos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import br.uva.habitos.data.HabitosViewModel;
import br.uva.habitos.data.UsuarioViewModel;
import br.uva.habitos.databinding.ActivityMainBinding;
import br.uva.habitos.model.Habito;
import br.uva.habitos.model.Usuario;
import br.uva.habitos.ui.habito.AddHabitoDialog;
import br.uva.habitos.ui.habito.EditHabitoDialog;
import br.uva.habitos.util.FabViewModel;

public class MainActivity extends AppCompatActivity implements AddHabitoDialog.AddHabitoDialogListener, EditHabitoDialog.EditHabitoDialogListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private HabitosViewModel habitosViewModel;
    private UsuarioViewModel usuarioViewModel;

    private FabViewModel fabViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        habitosViewModel = new ViewModelProvider(this).get(HabitosViewModel.class);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.definirUsuario(new Usuario("Pedrao", "pedrao@gmail.com"));

        fabViewModel = new ViewModelProvider(this).get(FabViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {
            List<Habito> currentHabitos = habitosViewModel.getHabitos().getValue();
            if (currentHabitos == null) currentHabitos = new ArrayList<>();
            if (currentHabitos.size() >= Habito.values().length) {
                Toast.makeText(this, "Todos os Hábitos adicionados. Tente editá-los.", Toast.LENGTH_SHORT).show();
                return;
            }
            new AddHabitoDialog().show(getSupportFragmentManager(), null);
        });

        fabViewModel.getVisibility().observe(this, b -> binding.appBarMain.fab.setVisibility(b ? View.VISIBLE : View.GONE));

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_list)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onHabitoAdded(String selectedHabito, int intervalo) {
        habitosViewModel.setHabito(Habito.acharPeloDisplayName(selectedHabito), intervalo * 60 * 60 * 1000L);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Em desenvolvimento...", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_diminuir) {
            habitosViewModel.diminuirTempo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHabitoEdited(Habito habito, int intervalo) {
        habitosViewModel.setHabito(habito, intervalo);
    }
}