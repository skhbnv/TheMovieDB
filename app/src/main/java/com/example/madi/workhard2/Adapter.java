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
import com.example.madi.workhard2.interfaces.ItemClickListener;
import com.example.madi.workhard2.interfaces.OnItemCreatedListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MovieViewHolder>  {
    private List<Movies> mData;
    public Adapter(List<Movies> dataset){
        mData = dataset;
    }
    ItemClickListener itemClickListener;
    private OnItemCreatedListener itemCreatedListener;


    public static class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private TextView mName;
        private TextView mGenre;
        private ImageView mPoster;
        private TextView mDate;
        private Context context;
        private ItemClickListener itemClickListener;



        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.film_name);
            mGenre = itemView.findViewById(R.id.film_genre);
            mPoster = itemView.findViewById(R.id.movie_image);
            mDate = itemView.findViewById(R.id.film_date);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition());
        }


        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
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
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.mName.setText(mData.get(i).getTitle());
        String year[] = mData.get(i).getReleaseDate().split("-");
        movieViewHolder.mDate.setText(year[0]);
        String id = mData.get(i).getId().toString();

        movieViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                String id = mData.get(position).getId().toString();
            }
        });

        String url = "http://image.tmdb.org/t/p/w780" + mData.get(i).getPosterPath();
        Picasso.
                with(movieViewHolder.context).
                load(url).
                into(movieViewHolder.mPoster);

    }
    public void SetOnItemClickListener(final ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
