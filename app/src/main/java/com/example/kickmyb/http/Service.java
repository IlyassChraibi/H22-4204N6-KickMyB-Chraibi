package com.example.kickmyb.http;

import com.example.kickmyb.task;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {
        @POST("/api/id/signup")
        Call<SigninResponse> SignUp(@Body SignupRequest signupRequest);

        @POST("/api/id/signin")
        Call<SigninResponse> SignIn(@Body SigninRequest signinRequest);

        @GET("users/{utilisateur}/repos")
        Call<String> listReposString(@Path("utilisateur") String utilisateur);


}
