package com.example.madi.workhard2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madi.workhard2.Objects.Movies;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MovieViewHolder> {
    private ArrayList<Movies> mData;

    public Adapter(ArrayList<Movies> dataset){
        mData = dataset;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mGenre;
        private ImageView mPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.film_name);
            mGenre = itemView.findViewById(R.id.film_genre);
            mPoster = itemView.findViewById(R.id.movie_image);
        }
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_movie, viewGroup, false);
        MovieViewHolder vh = new MovieViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.mGenre.setText(mData.get(i).getGenre());
        movieViewHolder.mName.setText(mData.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
