package com.example.kickmyb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.kickmyb.databinding.ActivityHomeBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        this.initRecycler();
        this.remplirRecycler();
    }

    private void remplirRecycler() {
        Date date = new Date(System.currentTimeMillis());
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
        adapter.notifyDataSetChanged();
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
        return super.onOptionsItemSelected(item);
    }
}