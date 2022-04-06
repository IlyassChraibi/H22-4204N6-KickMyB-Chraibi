package com.example.kickmyb.http;


import com.google.gson.Gson;

import org.kickmyb.CustomGson;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtil {

    private static Service instance;

    public static Service get() {
        if (instance == null) { //  ca sera le cas au tout premier appel
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(CustomGson.getIt()))
                    .client(client())
                    .baseUrl("http://10.0.2.2:8080")
                    .build();

            instance = retrofit.create(Service.class);
            return instance;
        } else {
            return instance;
        }
    }

    private static OkHttpClient client() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieJar jar = new SessionCookieJar();
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(jar)
                .addInterceptor(interceptor)
                .build();
        return client;

    }
}
