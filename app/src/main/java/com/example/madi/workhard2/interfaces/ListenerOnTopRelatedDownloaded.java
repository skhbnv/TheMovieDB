package com.example.madi.workhard2.interfaces;

import com.example.madi.workhard2.Objects.Result;
import com.example.madi.workhard2.Objects.TopRatedMovie;

import java.util.List;

public interface ListenerOnTopRelatedDownloaded {
    public void onDataLoaded(List<TopRatedMovie> result);
}
