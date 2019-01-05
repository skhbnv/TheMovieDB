package com.example.madi.workhard2.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.madi.workhard2.Models.Movies;
import com.example.madi.workhard2.R;
import com.example.madi.workhard2.interfaces.ItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements ItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private List<Movies> dataset = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference baseRef;
    private FirebaseDatabase mDatabase;

    private SwipeRefreshLayout swipeRefreshLayout;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getResponse();
        initUI();
    }

    private void initUI() {
        swipeRefreshLayout = getView().findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
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

    FirebaseUser getCurrentUser(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser;
        if(mAuth.getCurrentUser() != null){
           currentUser = mAuth.getCurrentUser();
           return currentUser;
        }
        else {
            return null;
        }
    }

    public void getResponse() {
        mDatabase = FirebaseDatabase.getInstance();
        baseRef = mDatabase.getReference();
        baseRef.child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Movies> data = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    if (child.getKey().equals(getCurrentUser().getUid())){
                        for (DataSnapshot second_child: child.getChildren()){
                            Movies movie = second_child.getValue(Movies.class);
                            data.add(movie);
                        }
                    }
                }
                onDataLoaded(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showMessage(databaseError.toException().toString());
            }
        });
    }

    private void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void onDataLoaded(List<Movies> results) {
        dataset = overTurnTheList(results);
        mRecyclerView = getView().findViewById(R.id.favorites_recycler);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Adapter(dataset);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<Movies> overTurnTheList(List<Movies> results) {
        List<Movies> newList = new ArrayList<>();
        for (int i = results.size()-1; i >= 0 ; i--){
            newList.add(results.get(i));
        }
        return newList;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getResponse();
                swipeRefreshLayout.setRefreshing(false);}
                }, 5000);
    }
}
