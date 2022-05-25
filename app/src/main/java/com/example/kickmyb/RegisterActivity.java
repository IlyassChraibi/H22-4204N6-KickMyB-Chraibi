package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kickmyb.databinding.ActivityRegisterBinding;
import com.example.kickmyb.http.RetrofitUtil;
import com.example.kickmyb.http.Service;

import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog progressD;
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SignUp");

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Service service = RetrofitUtil.get();
                SignupRequest signupRequest = new SignupRequest();
                signupRequest.username = binding.userUp.getText().toString();
                signupRequest.password = binding.passUp.getText().toString();

                if (binding.passUp.getText().toString().equals(binding.confirmation.getText().toString())) {

                    progressD = ProgressDialog.show(RegisterActivity.this, getString(R.string.wait),
                            getString(R.string.wait_msg), true);
                service.SignUp(signupRequest).enqueue(new Callback<SigninResponse>() {
                    @Override
                    public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                        progressD.dismiss();
                        if (response.isSuccessful()) {
                            Singleton singleton = Singleton.getInstance();
                            singleton.username = response.body().username;
                            Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(i);
                            Log.i("reponse",response.body().toString());
                        }
                        else {
                            if (response.code() == 403){
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_log) , Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.exist) , Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SigninResponse> call, Throwable t) {
                        //Log.i("RETROFIT", t.getMessage());
                        progressD.dismiss();
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.exist) , Toast.LENGTH_LONG).show();
                    }
                });

                }

            }
        });
    }
}