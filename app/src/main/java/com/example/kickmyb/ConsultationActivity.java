package com.example.kickmyb;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kickmyb.databinding.ActivityConsultationBinding;

public class ConsultationActivity extends AppCompatActivity {
    private ActivityConsultationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Consultation");

        binding = ActivityConsultationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.txtNom.setText(getIntent().getStringExtra("texte"));
        int v = getIntent().getIntExtra("percentage",0);
        binding.txtPercentage.setText(""+v);
        int vv = getIntent().getIntExtra("time",0);
        binding.txtTimeElapsed.setText(""+vv);

        binding.txtDateLimite.setText(""+ (int) getIntent().getLongExtra("date",0));
    }
}
