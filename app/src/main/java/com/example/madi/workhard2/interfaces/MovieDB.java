package com.example.madi.workhard2.interfaces;

import com.example.madi.workhard2.Models.CreditsList;
import com.example.madi.workhard2.Models.Genres;
import com.example.madi.workhard2.Models.Latest;
import com.example.madi.workhard2.Models.Movies;
import com.example.madi.workhard2.Models.RecomendedResults;
import com.example.madi.workhard2.Models.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDB {
    @GET("3/movie/top_rated")
    Call<Result> getData(@Query("api_key") String key,
                         @Query("language") String lang,
                         @Query("page") int page);
    @GET("3/movie/popular")
    Call<Result> getDataPopular(@Query("api_key") String key,
                         @Query("language") String lang,
                         @Query("page") int page);
    @GET("3/movie/upcoming")
    Call<Result> getDataUpcoming(@Query("api_key") String key,
                         @Query("language") String lang,
                         @Query("page") int page);

    @GET("3/movie/latest")
    Call<Latest> getDataLatest(@Query("api_key") String key,
                               @Query("language") String lang,
                               @Query("page") int page);


    @GET("3/movie/now_playing")
    Call<Result> getDataNowPlaying(@Query("api_key") String key,
                         @Query("language") String lang,
                         @Query("page") int page);

    @GET("3/genre/movie/list")
    Call<Genres> getGenres(@Query("api_key") String key,
                           @Query("language") String lang);


    @GET("/3/movie/{movie_id}/credits")
    Call<CreditsList> getTopCredits(
            @Path(value = "movie_id", encoded = true) String movie_id
            ,@Query("api_key") String key
            );

    @GET("/3/movie/{movie_id}/recommendations")
    Call<RecomendedResults> getRecomended(
            @Path(value = "movie_id", encoded = true) String movie_id
            ,@Query("api_key") String key
            ,@Query("language") String language
            ,@Query("page") int page
    );
}