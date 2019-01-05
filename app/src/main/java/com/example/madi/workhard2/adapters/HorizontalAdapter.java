package com.example.madi.workhard2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madi.workhard2.Models.Movies;
import com.example.madi.workhard2.Models.Recomended_soup;
import com.example.madi.workhard2.R;
import com.example.madi.workhard2.interfaces.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MovieViewHolder>{
    private List<Recomended_soup> mData;

    public HorizontalAdapter(List<Recomended_soup> dataset){
        mData = dataset;
    }

    ItemClickListener itemClickListener;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.horizontal_item, viewGroup, false);
        HorizontalAdapter.MovieViewHolder vh = new HorizontalAdapter.MovieViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        onBindMovieHolder(movieViewHolder, i);
    }

    private void onBindMovieHolder(MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.mTitle.setText(mData.get(i).getTitle());
        String year[] = mData.get(i).getReleaseDate().split("-");
        movieViewHolder.mRelease.setText(year[0]);
//        String id = mData.get(i).getId().toString();
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

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        private TextView mTitle;
        private TextView mRelease;

        private ImageView mPoster;
        private Context context;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title_horizontal);
            mPoster = itemView.findViewById(R.id.horizontal_image);
            mRelease = itemView.findViewById(R.id.release_date_horizontal);
            context = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(mData.get(getAdapterPosition()));
        }
    }
}
