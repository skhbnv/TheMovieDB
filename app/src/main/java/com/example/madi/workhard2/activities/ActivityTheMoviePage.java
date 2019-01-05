package com.example.madi.workhard2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Cast;
import com.example.madi.workhard2.Models.App;
import com.example.madi.workhard2.Models.CreditsList;
import com.example.madi.workhard2.Models.Genre;
import com.example.madi.workhard2.Models.Movies;
import com.example.madi.workhard2.Models.RecomendedResults;
import com.example.madi.workhard2.Models.Recomended_soup;
import com.example.madi.workhard2.Models.Result;
import com.example.madi.workhard2.R;
import com.example.madi.workhard2.adapters.Adapter;
import com.example.madi.workhard2.adapters.HorizontalAdapter;
import com.example.madi.workhard2.adapters.HorizontalCastsAdapter;
import com.example.madi.workhard2.interfaces.ItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityTheMoviePage extends AppCompatActivity implements View.OnClickListener,
        ItemClickListener{
    private TextView mTitle;
    private TextView mGenre;
    private TextView mRating;
    private TextView mCredits;
    private TextView mRelease;
    private TextView mDescription;
    private TextView mFavoritesStatus;
    private DataSnapshot favoritesList;

    private DataSnapshot movieToRemove;
    private List<Cast> castList;
    private RecyclerView mRecyclerView;
    private HorizontalAdapter mAdapter;
    private List<Recomended_soup> dataForHorizontal= new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView mCastsRecycler;
    private HorizontalCastsAdapter mCastsAdapter;
    private List<Cast> dataForCasts= new ArrayList<>();
    private RecyclerView.LayoutManager mCastsLayout;

    boolean checker;

    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseDatabase database;

    private LinearLayout mAddButton;

    private ImageView mBackgr;
    private ImageView mFavButton;
    private ImageView mBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_movie_page);

        initUI();
        loadCredits();
        openDatabaseConnection();
        }

    private void pushCastsAdapter(List<Cast> castList) {
        mCastsRecycler = findViewById(R.id.casts_recycler);
        mCastsLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mCastsRecycler.setLayoutManager(mCastsLayout);
        mCastsAdapter = new HorizontalCastsAdapter(castList);

        mCastsRecycler.setAdapter(mCastsAdapter);

    }

    private void sendRequestForRecomendations() {
        String movieID = getMovie().getId().toString();

        App.getApi().
                getRecomended( movieID, "196f6483e4f6e361d943a20014f51698",
                        "ru", 1).enqueue(new Callback<RecomendedResults>() {
            @Override
            public void onResponse(Call<RecomendedResults> call, Response<RecomendedResults> response) {
                Log.d("___", "onResponse: " + response.body().getResults());
                onDataLoaded(response.body().getResults());
            }

            @Override
            public void onFailure(Call<RecomendedResults> call, Throwable t) {
//                showMessage(t.getMessage());
                Log.d("___", "onFailure: " + t.getMessage());
            }
        });
    }

    private void loadCredits() {
        String movieID = getMovie().getId().toString();
            App.getApi().
                    getTopCredits( movieID, "196f6483e4f6e361d943a20014f51698").
                    enqueue(new Callback<CreditsList>() {
                        @Override
                        public void onResponse(Call<CreditsList> call, Response<CreditsList>
                                response) {
                            fillContent(response.body().getCast());
                        }

                        @Override
                        public void onFailure(Call<CreditsList> call, Throwable t) {
                            showMessage("Failed to load credits");
                        }
                    });
    }



    Movies getMovie(){
        return getIntent().getParcelableExtra(Movies.class.getCanonicalName());
    }

    private void fillContent(List<Cast> cast) {
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

        String actors = "";

        pushCastsAdapter(cast);

        if (genresList.length() >=3){
            genresList = genresList.substring(0, genresList.length() - 2);
        }

        mGenre.setText(genresList);
        mRating.setText(getMovie().getVoteAverage().toString());

        String []year = getMovie().getReleaseDate().split("-");
        mRelease.setText(year[0]);
        sendRequestForRecomendations();
    }

    private void initUI() {
        mCredits = findViewById(R.id.the_movie_page_credits);
        mRelease = findViewById(R.id.the_movie_page_release);
        mRating = findViewById(R.id.the_movie_page_rate);
        mTitle = findViewById(R.id.the_movie_page_name);
        mGenre = findViewById(R.id.the_movie_page_genre);
        mBackgr = findViewById(R.id.the_movie_page_backgr);
        mFavButton = findViewById(R.id.star_fav_added);
        mBackButton= findViewById(R.id.back_button);
        mDescription = findViewById(R.id.the_movie_page_description);
        mAddButton = findViewById(R.id.add_to_fav_sec);
        mFavoritesStatus = findViewById(R.id.text_added_to_favorites);

        mAddButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                finish();
                break;
            case R.id.add_to_fav_sec:
                favListChecker(favoritesList);
                break;
        }
    }

    private void favListChecker(DataSnapshot dataSnapshot) {
        if(checker){
            for (DataSnapshot child: dataSnapshot.getChildren()){
                Movies movie = child.getValue(Movies.class);
                if(movie.getId().toString().equals(getMovie().getId().toString())){
                    movieToRemove = child;
                    movieToRemove.getRef().removeValue();
                }
            }
            checker = false;
            fillTheStar(false);
            showMessage("removed from favorites");
        }else{
            checker = true;
        writeToBase();
        }
    }

    private void fillTheStar(Boolean added) {
        if (added){
            mAddButton.setBackgroundDrawable(ContextCompat.getDrawable(this,
                    R.drawable.favorites_added));
            mFavoritesStatus.setText("В избранных");
            mFavoritesStatus.setTextColor(getResources().getColor(R.color.fav_already_added));
            mFavButton.setVisibility(View.VISIBLE);
        }
        else{
            mAddButton.setBackgroundDrawable(ContextCompat.getDrawable(this,
                    R.drawable.favorites_stroke));
            mFavoritesStatus.setText("Добавить в избранное");
            mFavoritesStatus.setTextColor(getResources().getColor(R.color.gold_yellow));
            mFavButton.setVisibility(View.INVISIBLE);
        }
    }

    private void openDatabaseConnection() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser;
        String key;

        if (mAuth.getCurrentUser() != null){

            currentUser = mAuth.getCurrentUser();
            database = FirebaseDatabase.getInstance();
            key = database.getReference("favorites").child(currentUser.getUid()).push().getKey();
            myRef = database.getReference("favorites").
                    child(currentUser.getUid()).child(key);
            checker = false;
            DatabaseReference forCheck = database.getReference("favorites").
                    child(currentUser.getUid());
            forCheck.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    favoritesList = dataSnapshot;

                    for (DataSnapshot child: dataSnapshot.getChildren()){
                        Movies movie = child.getValue(Movies.class);
                        if(movie.getId().toString().equals(getMovie().getId().toString())){
                            checker = true;
                            movieToRemove = child;
                            fillTheStar(true);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
            });


        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void writeToBase() {
        myRef.setValue(getMovie()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fillTheStar(true);
                if (task.isSuccessful()){
                    showMessage("added to favorites");
                }
                else{
                    showMessage(task.getException().toString());
                }
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void onDataLoaded(List<Recomended_soup> recomended_soups) {
        dataForHorizontal = recomended_soups;

        mRecyclerView = findViewById(R.id.horizontal_recylcer);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HorizontalAdapter(dataForHorizontal);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Movies movie) {
        Intent intent = new Intent(this, ActivityTheMoviePage.class);
        Log.d("___", "onClick: canonical name is " + Movies.class.getCanonicalName());
        intent.putExtra(Movies.class.getCanonicalName(), movie);
        String id = movie.getId().toString();
        startActivity(intent);
    }

    @Override
    public void onClick(Recomended_soup movie) {

    }
}
