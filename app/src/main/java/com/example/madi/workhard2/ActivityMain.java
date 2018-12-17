package com.example.madi.workhard2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.madi.workhard2.Objects.App;
import com.example.madi.workhard2.Objects.Genre;
import com.example.madi.workhard2.Objects.Genres;
import com.example.madi.workhard2.fragments.LatestFragment;
import com.example.madi.workhard2.fragments.NowPlaying;
import com.example.madi.workhard2.fragments.PopularFragment;
import com.example.madi.workhard2.fragments.TopRatedFragment;
import com.example.madi.workhard2.fragments.UpcomingFragment;
import com.example.madi.workhard2.interfaces.onGenresLoadedListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityMain extends AppCompatActivity implements onGenresLoadedListener{
    private DrawerLayout mDrawerLayout;
    private static List<Genre> genres;

    static List<Genre> getGenres(){
        return genres;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getResponseAndFillGenres();
        contentChanger(R.id.user_popular);
        initUI();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                contentChanger(menuItem.getItemId());
                return true;
            }
        });
    }

    private void getResponseAndFillGenres() {
        App.getApi().
                getGenres( "196f6483e4f6e361d943a20014f51698", "ru").
                enqueue(new Callback<Genres>() {
                    @Override
                    public void onResponse(Call<Genres> call, Response<Genres> response) {
                        onGenresLoaded(response.body().getGenres());
                    }

                    @Override
                    public void onFailure(Call<Genres> call, Throwable t) {
                        //toast
                    }
                });
    }
    @Override
    public void onGenresLoaded(List<Genre> resp) {
        genres = resp;
        Log.d("___", "onGenresLoaded: " + resp.get(0).getName());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void contentChanger(int itemId) {
        Fragment fragment = null;

        switch (itemId){
            case R.id.user_popular:
                fragment = new PopularFragment();
                break;
            case R.id.user_top_rated:
                fragment = new TopRatedFragment();
                break;
            case R.id.user_upcoming:
                fragment = new UpcomingFragment();
                break;
//            case R.id.user_latest:
//                fragment = new LatestFragment();
//                break;
            case R.id.now_playing:
                fragment = new NowPlaying();
                break;
        }

        if(fragment != null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().
                    beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        mDrawerLayout = findViewById(R.id.drawer_layout);
    }
}
