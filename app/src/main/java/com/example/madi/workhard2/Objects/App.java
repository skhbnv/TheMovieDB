package com.example.madi.workhard2.Objects;

import android.app.Application;

import com.example.madi.workhard2.interfaces.MovieDB;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static MovieDB movieDB;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder().
                baseUrl("https://api.themoviedb.org").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        movieDB = retrofit.create(MovieDB.class);
    }
    public static MovieDB getApi(){
        return movieDB;
    }
}
