package com.example.kickmyb;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kickmyb.databinding.ActivityCreationBinding;

public class CreationActivity extends AppCompatActivity {
    private ActivityCreationBinding binding;
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


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreationActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}