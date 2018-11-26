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
import android.widget.Toast;

import com.example.madi.workhard2.Adapter;
import com.example.madi.workhard2.Objects.App;
import com.example.madi.workhard2.Objects.Movies;
import com.example.madi.workhard2.Objects.Result;
import com.example.madi.workhard2.Objects.TopRatedMovie;
import com.example.madi.workhard2.R;
import com.example.madi.workhard2.interfaces.ListenerOnTopRelatedDownloaded;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PopularFragment extends Fragment implements ListenerOnTopRelatedDownloaded{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<TopRatedMovie> dataset = new ArrayList<>();

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
                        Log.d("___", "onResponse: " + response.body().getResults().
                                get(0).getTitle());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.d("___", "onResponse: " + t.toString());
                        Toast.makeText(getContext(),t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onDataLoaded(List<TopRatedMovie> result) {
        dataset = result;
        mRecyclerView = getView().findViewById(R.id.popular_recylcer);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new Adapter(dataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}