package com.example.madi.workhard2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private RecyclerView mRecyclerView;
    private HorizontalAdapter mAdapter;
    private List<Movies> dataset = new ArrayList<>();
    private List<Recomended_soup> dataForHorizontal= new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;

    boolean checker;

    FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseDatabase database;

    private LinearLayout mAddButton;

    private ImageView mBackgr;
    private ImageView mFavButton;
    private ImageView mBackButton;
    String movieId;

    private int newColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_movie_page);




        initUI();
        loadCredits();
        }

    private void sendRequestForRecomendations() {
        String movieID = getMovie().getId().toString();

        App.getApi().
                getRecomended( movieID, "196f6483e4f6e361d943a20014f51698",
                        "ru", 1).enqueue(new Callback<RecomendedResults>() {
            @Override
            public void onResponse(Call<RecomendedResults> call, Response<RecomendedResults> response) {
                Log.d("___", "onResponse: " + response.body().getResults());
//                onDataLoaded(response.body().getResults());
            }

            @Override
            public void onFailure(Call<RecomendedResults> call, Throwable t) {

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

        for(int i = 0; i < 5; i++){
            actors += cast.get(i).getName()+", ";
        }

        if (genresList.length() >=3){
            genresList = genresList.substring(0, genresList.length() - 2);
        }

        mCredits.setText(actors);
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
        mFavButton = findViewById(R.id.star_fav);
        mBackButton= findViewById(R.id.back_button);
        mDescription = findViewById(R.id.the_movie_page_description);
        mAddButton = findViewById(R.id.add_to_fav_sec);

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
                openDatabaseConnection();
                break;
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

            //            writeToBase();

            checker = false;
            DatabaseReference forCheck = database.getReference("favorites").
                    child(currentUser.getUid());
            forCheck.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child: dataSnapshot.getChildren()){
                        Movies movie = child.getValue(Movies.class);
                        Log.d("___", "onDataChange: " + movie.getId() + getMovie().getId());
                        if(movie.getId().toString().equals(getMovie().getId().toString())){
                            Log.d("___", "entered the if statement: " + getMovie().getId());
                            child.getRef().removeValue();
                            checker = true;
                            showMessage("removed from favorites");
                        }
                    }
                    if (!checker){
                    writeToBase();
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
                if (task.isSuccessful()){
                    showMessage("added to favorites");
                }
                else{
                    showMessage(task.getException().toString());
                }
            }
        });
    }

    private void showMessage(String added_to_favorites) {
        Toast.makeText(this, added_to_favorites, Toast.LENGTH_SHORT).show();
    }
    private void onDataLoaded(List<Recomended_soup> results) {
        dataForHorizontal = results;

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
