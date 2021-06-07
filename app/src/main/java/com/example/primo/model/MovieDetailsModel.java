package com.example.primo.model;

public class MovieDetailsModel {
    String videoKey;

    public MovieDetailsModel(String videoKey) {
        this.videoKey = videoKey;
    }

    public MovieDetailsModel() {
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }
}

