package com.example.madi.workhard2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.madi.workhard2.Adapter;
import com.example.madi.workhard2.Objects.App;
import com.example.madi.workhard2.Objects.Movies;
import com.example.madi.workhard2.Objects.TopRatedMovie;
import com.example.madi.workhard2.R;
import com.example.madi.workhard2.interfaces.MovieDB;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopularFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Object popularResponse;
    private Object rsponse;


    public PopularFragment(){ }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Movies> dataset = new ArrayList<>();

        getResponse();
//        dataset.add(new Movies("Adjara Gudju", "Atata"));
//        dataset.add(new Movies("Adjara Gudju", "Atata"));
//        dataset.add(new Movies("Adjara Gudju", "Atata"));

        mRecyclerView = getView().findViewById(R.id.popular_recylcer);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new Adapter(dataset);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        return view;
    }


    public void getResponse() {
        App.getApi().
                getData("196f6483e4f6e361d943a20014f51698", "ru", 1).
                enqueue(new Callback<ArrayList<TopRatedMovie>>() {
            @Override
            public void onResponse(Call<ArrayList<TopRatedMovie>> call,
                                   Response<ArrayList<TopRatedMovie>> response) {
                Log.d("___", "onResponse: response!");
            }

            @Override
            public void onFailure(Call<ArrayList<TopRatedMovie>> call, Throwable t) {

            }
        });
    }
}