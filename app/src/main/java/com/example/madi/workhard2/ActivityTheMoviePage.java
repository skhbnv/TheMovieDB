package com.example.madi.workhard2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madi.workhard2.Objects.Movies;
import com.example.madi.workhard2.interfaces.MovieDB;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ActivityTheMoviePage extends AppCompatActivity {
    private TextView mTitle;
    private TextView mGenre;
    private TextView mDescription;

    private ImageView mBackgr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_movie_page);

        initUI();
        fillContent();
    }

    Movies getMovie(){
        return getIntent().getParcelableExtra(Movies.class.getCanonicalName());
    }

    private void fillContent() {
        mTitle.setText(getMovie().getTitle());
        mDescription.setText(getMovie().getOverview());
        String url = "http://image.tmdb.org/t/p/w780" + getMovie().getBackdropPath();
        Picasso.
                with(this).
                load(url).
                into(mBackgr);

//        mGenre.setText(getMovie().getGenreIds().toString());
    }

    private void initUI() {
        mTitle = findViewById(R.id.the_movie_page_name);
        mGenre = findViewById(R.id.the_movie_page_genre);
        mBackgr = findViewById(R.id.the_movie_page_backgr);
        mDescription = findViewById(R.id.the_movie_page_description);
    }

}
