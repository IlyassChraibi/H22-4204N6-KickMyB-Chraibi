package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kickmyb.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("SignUp");

    }
}