package com.example.madi.workhard2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Cast;

import com.example.madi.workhard2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalCastsAdapter extends
        RecyclerView.Adapter<HorizontalCastsAdapter.CastsViewHolder> {
    private List<Cast> mData;

    public HorizontalCastsAdapter(List<Cast> dataset){
        mData = dataset;
    }


    @NonNull
    @Override
    public CastsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cast_card_view, viewGroup, false);
        HorizontalCastsAdapter.CastsViewHolder vh = new HorizontalCastsAdapter.CastsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CastsViewHolder castsViewHolder, int i) {
        onBindDischarger(castsViewHolder, i);
    }

    private void onBindDischarger(CastsViewHolder holder, int i) {
        holder.mCastName.setText(mData.get(i).getName());
        String url = "http://image.tmdb.org/t/p/w780" + mData.get(i).getProfilePath();
        Picasso.
                with(holder.context).
                load(url).
                into(holder.mCastPhoto);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CastsViewHolder extends RecyclerView.ViewHolder{
        private TextView mCastName;

        private ImageView mCastPhoto;
        private Context context;

        public CastsViewHolder(@NonNull View itemView) {
            super(itemView);

            mCastName = itemView.findViewById(R.id.cast_name);
            mCastPhoto = itemView.findViewById(R.id.cast_profile);
            context = itemView.getContext();
        }
    }
}
