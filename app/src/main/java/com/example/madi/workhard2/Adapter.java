package com.example.madi.workhard2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madi.workhard2.Objects.Movies;
import com.example.madi.workhard2.Objects.TopRatedMovie;
import com.squareup.picasso.Cache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MovieViewHolder> {
    private List<TopRatedMovie> mData;
    public Adapter(List<TopRatedMovie> dataset){
        mData = dataset;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mGenre;
        private ImageView mPoster;
        private Context context;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.film_name);
            mGenre = itemView.findViewById(R.id.film_genre);
            mPoster = itemView.findViewById(R.id.movie_image);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card, viewGroup, false);
        MovieViewHolder vh = new MovieViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.mName.setText(mData.get(i).getTitle());
        String url = "http://image.tmdb.org/t/p/w780" + mData.get(i).getPosterPath();
        Picasso.
                with(movieViewHolder.context).
                load(url).
                into(movieViewHolder.mPoster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
