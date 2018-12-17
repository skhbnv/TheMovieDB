package com.example.madi.workhard2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madi.workhard2.Objects.Genre;
import com.example.madi.workhard2.Objects.Genres;
import com.example.madi.workhard2.Objects.Movies;
import com.example.madi.workhard2.interfaces.MovieDB;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ActivityTheMoviePage extends AppCompatActivity implements View.OnClickListener{
    private TextView mTitle;
    private TextView mGenre;
    private TextView mRating;
    private TextView mVotes;
    private TextView mRelease;
    private TextView mDescription;

    private ImageView mBackgr;
    private ImageView mBackButton;

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
        List<Genre> genres = ActivityMain.getGenres();
        String genresList = "";
        mTitle.setText(getMovie().getTitle());
        mDescription.setText(getMovie().getOverview());
        String url = "http://image.tmdb.org/t/p/w780" + getMovie().getBackdropPath();
        Picasso.
                with(this).
                load(url).
                into(mBackgr);

        for (int i = 0; i < genres.size(); i++) {
            for (int j = 0; j < getMovie().getGenreIds().size(); j++) {
                if (genres.get(i).getId() == getMovie().getGenreIds().get(j)) {
                    genresList += genres.get(i).getName() + ",\n";
                }
            }
        }
        genresList = genresList.substring(0, genresList.length() - 2);
        mGenre.setText(genresList);
        mRating.setText(getMovie().getVoteAverage().toString());

        String []year = getMovie().getReleaseDate().split("-");
        mRelease.setText(year[0]);
    }

    private void initUI() {
        mVotes = findViewById(R.id.the_movie_page_vote_number);
        mRelease = findViewById(R.id.the_movie_page_release);
        mRating = findViewById(R.id.the_movie_page_rate);
        mTitle = findViewById(R.id.the_movie_page_name);
        mGenre = findViewById(R.id.the_movie_page_genre);
        mBackgr = findViewById(R.id.the_movie_page_backgr);
        mBackButton= findViewById(R.id.back_button);
        mDescription = findViewById(R.id.the_movie_page_description);

        mBackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                finish();
                break;
        }
    }
}
