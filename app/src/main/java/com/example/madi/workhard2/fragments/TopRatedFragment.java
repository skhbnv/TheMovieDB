package com.example.madi.workhard2.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.madi.workhard2.Models.Recomended_soup;
import com.example.madi.workhard2.activities.ActivityTheMoviePage;
import com.example.madi.workhard2.adapters.Adapter;
import com.example.madi.workhard2.Models.App;
import com.example.madi.workhard2.Models.Movies;
import com.example.madi.workhard2.Models.Result;
import com.example.madi.workhard2.R;
import com.example.madi.workhard2.interfaces.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedFragment extends Fragment implements ItemClickListener{
    private Object response;

    public TopRatedFragment(){}
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Movies> dataset = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getResponse();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);
        return view;
    }

    public void getResponse() {
        App.getApi().
                getDataPopular( "196f6483e4f6e361d943a20014f51698", "ru", 1).
                enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        onDataLoaded(response.body().getResults());

                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.d("___", "onResponse: " + t.toString());
                        Toast.makeText(getContext(),t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void onDataLoaded(List<Movies> results) {
        mRecyclerView = getView().findViewById(R.id.top_rated_recylcer);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter(results);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Movies movie) {
//        Log.d("___", "onClick: " + movie.getGenreIds());
        Intent intent = new Intent(getContext(), ActivityTheMoviePage.class);
        intent.putExtra(Movies.class.getCanonicalName(), movie);
        String id = movie.getId().toString();
        startActivity(intent);
    }

    @Override
    public void onClick(Recomended_soup movie) {

    }
}
