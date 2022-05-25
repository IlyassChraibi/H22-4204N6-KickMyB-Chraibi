package com.example.kickmyb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kickmyb.databinding.ActivityHomeBinding;
import com.example.kickmyb.http.Network;
import com.example.kickmyb.http.RetrofitUtil;
import com.example.kickmyb.http.Service;
import com.google.android.material.navigation.NavigationView;

import org.kickmyb.transfer.HomeItemResponse;
import org.kickmyb.transfer.SigninRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    ProgressDialog progressD;
    private ActivityHomeBinding binding;
    private ActionBarDrawerToggle abdt;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
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
            Service service = RetrofitUtil.get();
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Accueil:
                        Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(i);
                        return true;

                    case R.id.Ajout:
                        Intent i2 = new Intent(HomeActivity.this, CreationActivity.class);
                        startActivity(i2);
                        return true;

                    case R.id.Deconnexion:
                        progressD = ProgressDialog.show(HomeActivity.this, getString(R.string.wait),
                                getString(R.string.wait_msg), true);
                        service.SignOut().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                progressD.dismiss();
                                if (response.isSuccessful()){
                                    Intent i3 = new Intent(HomeActivity.this, MainActivity.class);
                                    startActivity(i3);
                                }
                                else {
                                    if (response.code() == 403){
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_log) , Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.error) , Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progressD.dismiss();
                                if (!Network.isInternetConnected(HomeActivity.this)) {
                                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.internet) , Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(HomeActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        return true;
                }
                dl.closeDrawers();
                return false;
            }
        });

        this.initRecycler();
        this.remplirRecycler();
    }

    private void remplirRecycler() {
        progressD = ProgressDialog.show(HomeActivity.this, getString(R.string.wait),
                getString(R.string.wait_msg), true);
        Service service = RetrofitUtil.get();
        service.GetList().enqueue(new Callback<List<HomeItemResponse>>() {
            @Override
            public void onResponse(Call<List<HomeItemResponse>> call, Response<List<HomeItemResponse>> response) {
                if (response.isSuccessful()) {
                    progressD.dismiss();
                    adapter.list = response.body();
                    adapter.notifyDataSetChanged();
                }
                else {
                    if (response.code() == 403){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_log) , Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.error) , Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HomeItemResponse>> call, Throwable t) {
                progressD.dismiss();
                //Log.i("RETROFIT", t.getMessage());
                if (!Network.isInternetConnected(HomeActivity.this)) {
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.internet) , Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(HomeActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* Date date = new Date(System.currentTimeMillis());
        for (int i = 0 ; i < 200 ; i++) {
            task p = new task();
            p.nom = "Bob " + i;
            p.pourcentage = 30;
            p.dateLimite = date;
            p.tempsEcoule =3;
            //p.age = 20 + (new Random().nextInt(20));
            adapter.list.add(p);

            Log.i("DATE", p.dateLimite.toString());
        }
        adapter.notifyDataSetChanged();*/
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.item1:
                Intent i = new Intent(HomeActivity.this, CreationActivity.class);
                startActivity(i);
        }

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