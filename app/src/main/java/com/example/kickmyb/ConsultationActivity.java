package com.example.kickmyb;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kickmyb.databinding.ActivityConsultationBinding;
import com.google.android.material.navigation.NavigationView;

public class ConsultationActivity extends AppCompatActivity {
    private ActivityConsultationBinding binding;
    private ActionBarDrawerToggle abdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Consultation");

        binding = ActivityConsultationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavigationView nv = binding.navView;
        DrawerLayout dl = binding.drawerLayout;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.open_bar,R.string.close_bar){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.open_bar);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.close_bar);
            }
        };
        dl.addDrawerListener(abdt);
        abdt.syncState();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Accueil:
                        Intent i = new Intent(ConsultationActivity.this, HomeActivity.class);
                        startActivity(i);
                        return true;

                    case R.id.Ajout:
                        Intent i2 = new Intent(ConsultationActivity.this, CreationActivity.class);
                        startActivity(i2);
                        return true;

                    case R.id.Deconnexion:
                        Intent i3 = new Intent(ConsultationActivity.this, MainActivity.class);
                        startActivity(i3);
                        return true;
                }
                //Toast.makeText(ConsultationActivity.this, "Texte : "+ item.toString() , Toast.LENGTH_SHORT).show();
                dl.closeDrawers();
                return false;
            }
        });

        binding.txtNom.setText(getIntent().getStringExtra("texte"));
        int v = getIntent().getIntExtra("percentage",0);
        binding.txtPercentage.setText(""+v);
        int vv = getIntent().getIntExtra("time",0);
        binding.txtTimeElapsed.setText(""+vv);

        binding.txtDateLimite.setText(""+ (int) getIntent().getLongExtra("date",0));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (abdt.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        abdt.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        abdt.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

}
