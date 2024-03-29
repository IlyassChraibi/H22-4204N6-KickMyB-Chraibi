package com.example.kickmyb.http;

import com.example.kickmyb.task;

import org.kickmyb.transfer.AddTaskRequest;
import org.kickmyb.transfer.HomeItemResponse;
import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;
import org.kickmyb.transfer.TaskDetailResponse;

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

        @POST("/api/id/signout")
        Call<String> SignOut();

        @POST("/api/add")
        Call<Void> AddTask(@Body AddTaskRequest task);

        @GET("/api/home")
        Call<List<HomeItemResponse>> GetList();

        @GET("/api/detail/{id}")
        Call<TaskDetailResponse> detail(@Path("id") Long id);

        @GET("/api/progress/{id}/{valeur}")
        Call<Void> avancement(@Path("id") Long id, @Path("valeur") Integer valeur);
}
