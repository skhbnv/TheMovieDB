package com.example.madi.workhard2.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.madi.workhard2.Models.Latest;
import com.example.madi.workhard2.R;

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