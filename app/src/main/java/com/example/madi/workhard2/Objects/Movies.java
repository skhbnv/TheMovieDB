package com.example.madi.workhard2.Objects;

import android.graphics.Bitmap;

public class Movies {
    private String name;
    private String genre;
    private Bitmap poster;

    public Movies(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.poster = poster;
    }
    public Movies(String name, String genre, Bitmap poster) {
        this.name = name;
        this.genre = genre;
        this.poster = poster;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPoster(Bitmap poster) {
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public Bitmap getPoster() {
        return poster;
    }
}
