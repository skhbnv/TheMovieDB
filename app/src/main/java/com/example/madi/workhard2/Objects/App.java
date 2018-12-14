package com.example.madi.workhard2.Objects;

import android.app.Application;

import com.example.madi.workhard2.interfaces.MovieDB;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static MovieDB movieDB;
    private static MovieDB movieGenres;
    private static Genre genre;
    private Retrofit retrofitMovies;
    private Retrofit retrofitGenres;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitMovies = new Retrofit.Builder().
                baseUrl("https://api.themoviedb.org").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        movieDB = retrofitMovies.create(MovieDB.class);

        retrofitGenres = new Retrofit.Builder().
                baseUrl("https://api.themoviedb.org").
                addConverterFactory(GsonConverterFactory.create()).
                build();
        movieGenres = retrofitGenres.create(MovieDB.class);
    }
    public static MovieDB getApi(){
        return movieDB;
    }
    public static MovieDB getGenres(){return movieGenres;}
}
