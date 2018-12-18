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
import com.example.madi.workhard2.Objects.Latest;
import com.example.madi.workhard2.Objects.Movies;
import com.example.madi.workhard2.interfaces.MovieDB;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ActivityTheLatestPage extends AppCompatActivity {
    private TextView mTitle;
    private TextView mLanguage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_latest);
        initUI();
        fillTheContent();
    }

    private void fillTheContent() {
        mLanguage.setText(getMovie().getOriginalLanguage());
        mTitle.setText(getMovie().getTitle());
    }

    Latest getMovie(){
        return getIntent().getParcelableExtra(Latest.class.getCanonicalName());
    }

    private void initUI() {
        mLanguage = findViewById(R.id.latest_title);
        mTitle = findViewById(R.id.latest_language_id);
    }
}