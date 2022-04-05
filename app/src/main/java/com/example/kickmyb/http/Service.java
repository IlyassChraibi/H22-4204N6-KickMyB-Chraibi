package com.example.kickmyb.http;

import com.example.kickmyb.task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {
        @POST("/api/id/signin")
        //@POST("/api/id/signup")

        @GET("users/{utilisateur}/repos")
        Call<String> listReposString(@Path("utilisateur") String utilisateur);


}
