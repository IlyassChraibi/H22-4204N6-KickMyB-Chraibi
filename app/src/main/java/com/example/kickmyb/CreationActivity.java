package com.example.kickmyb;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kickmyb.databinding.ActivityCreationBinding;
import com.example.kickmyb.http.RetrofitUtil;
import com.example.kickmyb.http.Service;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreationActivity extends AppCompatActivity {
    private ActivityCreationBinding binding;
    private ActionBarDrawerToggle abdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Create task");
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#008080"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        binding = ActivityCreationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DrawerLayout dl = binding.drawerLayout;

        NavigationView nv = (NavigationView) binding.navView;
        View headerView = nv.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        Singleton singleton = Singleton.getInstance();
        navUsername.setText(singleton.username);

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
                Service service = RetrofitUtil.get();

                switch (item.getItemId()){
                    case R.id.Accueil:
                        Intent i = new Intent(CreationActivity.this, HomeActivity.class);
                        startActivity(i);
                        return true;

                    case R.id.Ajout:
                        Intent i2 = new Intent(CreationActivity.this, CreationActivity.class);
                        startActivity(i2);
                        return true;

                    case R.id.Deconnexion:
                        service.SignOut().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()){
                                    Intent i3 = new Intent(CreationActivity.this, MainActivity.class);
                                    startActivity(i3);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(CreationActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                }
                dl.closeDrawers();
                return false;
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreationActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
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