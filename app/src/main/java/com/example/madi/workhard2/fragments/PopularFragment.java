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

import com.example.madi.workhard2.Models.Recomended_soup;
import com.example.madi.workhard2.activities.ActivityTheMoviePage;
import com.example.madi.workhard2.adapters.Adapter;
import com.example.madi.workhard2.Models.App;
import com.example.madi.workhard2.Models.Result;
import com.example.madi.workhard2.Models.Movies;
import com.example.madi.workhard2.R;
import com.example.madi.workhard2.interfaces.ItemClickListener;
import com.example.madi.workhard2.interfaces.ListenerOnTopRelatedDownloaded;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragment extends Fragment implements ListenerOnTopRelatedDownloaded,
        ItemClickListener{
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Movies> dataset = new ArrayList<>();

    public PopularFragment(){ }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getResponse();
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
                enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        onDataLoaded(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.d("___", "onResponse: " + t.toString());
//                        Toast.makeText(getContext(),t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onDataLoaded(List<Movies> result) {
        dataset = result;
        mRecyclerView = getView().findViewById(R.id.popular_recylcer);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter(dataset);

        mAdapter.setItemClickListener(this);        //?? Zachem adapter i zachem this?

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Movies movie) {
        Intent intent = new Intent(getContext(), ActivityTheMoviePage.class);
        Log.d("___", "onClick: canonical name is " + Movies.class.getCanonicalName());
        intent.putExtra(Movies.class.getCanonicalName(), movie);
        String id = movie.getId().toString();
        startActivity(intent);
    }

    @Override
    public void onClick(Recomended_soup movie) {

    }
}