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
import android.view.MenuItem;

import com.example.madi.workhard2.fragments.MainPageFragment;
import com.example.madi.workhard2.fragments.PopularFragment;
import com.example.madi.workhard2.fragments.TopRatedFragment;

public class ActivityMain extends AppCompatActivity{
    private DrawerLayout mDrawerLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentChanger(R.id.user_all_movies);
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

    private void contentChanger(int itemId) {
        Fragment fragment = null;

        switch (itemId){
            case R.id.user_all_movies:
                fragment = new MainPageFragment();
                break;
            case R.id.user_popular:
                fragment = new PopularFragment();
                break;
            case R.id.user_top_rated:
                fragment = new TopRatedFragment();
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
