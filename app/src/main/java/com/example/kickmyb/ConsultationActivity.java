package com.example.kickmyb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kickmyb.databinding.ActivityConsultationBinding;
import com.example.kickmyb.http.RetrofitUtil;
import com.example.kickmyb.http.Service;
import com.google.android.material.navigation.NavigationView;

import org.kickmyb.transfer.TaskDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultationActivity extends AppCompatActivity {
    ProgressDialog progressD;
    private ActivityConsultationBinding binding;
    private ActionBarDrawerToggle abdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Consultation");

        binding = ActivityConsultationBinding.inflate(getLayoutInflater());
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
                        Intent i = new Intent(ConsultationActivity.this, HomeActivity.class);
                        startActivity(i);
                        return true;

                    case R.id.Ajout:
                        Intent i2 = new Intent(ConsultationActivity.this, CreationActivity.class);
                        startActivity(i2);
                        return true;

                    case R.id.Deconnexion:
                        progressD = ProgressDialog.show(ConsultationActivity.this, getString(R.string.wait),
                                getString(R.string.wait_msg), true);
                        service.SignOut().enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                progressD.dismiss();
                                if (response.isSuccessful()){
                                    Intent i3 = new Intent(ConsultationActivity.this, MainActivity.class);
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
                                //Log.i("RETROFIT", t.getMessage());
                                progressD.dismiss();
                                Toast.makeText(ConsultationActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                }
                //Toast.makeText(ConsultationActivity.this, "Texte : "+ item.toString() , Toast.LENGTH_SHORT).show();
                dl.closeDrawers();
                return false;
            }
        });

        progressD = ProgressDialog.show(ConsultationActivity.this, getString(R.string.wait),
                getString(R.string.wait_msg), true);
        Service service = RetrofitUtil.get();
        service.detail(getIntent().getLongExtra("id",0)).enqueue(new Callback<TaskDetailResponse>() {
            @Override
            public void onResponse(Call<TaskDetailResponse> call, Response<TaskDetailResponse> response) {
                progressD.dismiss();
                if (response.isSuccessful()) {
                   binding.txtNom.setText(response.body().name);
                   binding.txtDateLimite.setText(response.body().deadline.toString());
                   binding.txtPercentage.setText(String.valueOf(response.body().percentageDone));
                   binding.txtTimeElapsed.setText(String.valueOf(response.body().percentageTimeSpent));
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
            public void onFailure(Call<TaskDetailResponse> call, Throwable t) {
                progressD.dismiss();
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.error) , Toast.LENGTH_LONG).show();
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
