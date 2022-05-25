package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kickmyb.databinding.ActivityMainBinding;
import com.example.kickmyb.http.RetrofitUtil;
import com.example.kickmyb.http.Service;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressD;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SignIn");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnRegisterMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = RetrofitUtil.get();
                SigninRequest signinRequest = new SigninRequest();
                signinRequest.username = binding.userIn.getText().toString();
                signinRequest.password = binding.passIn.getText().toString();

                progressD = ProgressDialog.show(MainActivity.this, getString(R.string.wait),
                        getString(R.string.wait_msg), true);

                service.SignIn(signinRequest).enqueue(new Callback<SigninResponse>() {
                    @Override
                    public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                        progressD.dismiss();
                        if (response.isSuccessful()) {
                            Singleton singleton = Singleton.getInstance();
                            singleton.username = response.body().username;
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                            Log.i("reponse",response.body().toString());
                        }
                        else {

                            if (response.code() == 403){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_log) , Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.wrong) , Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SigninResponse> call, Throwable t) {
                        //Log.i("RETROFIT", t.getMessage());
                        progressD.dismiss();
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.error) , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}