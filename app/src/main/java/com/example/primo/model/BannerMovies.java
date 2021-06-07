package com.example.primo.model;

public class BannerMovies {

    Integer id;
    String movieName;
    String imageUrl;
    String FileUrl;
    Double rating;
    Integer TvId;

    public BannerMovies(Integer id, String movieName, String imageUrl, String fileUrl, Double rating, Integer tvId) {
        this.id = id;
        this.movieName = movieName;
        this.imageUrl = imageUrl;
        FileUrl = fileUrl;
        this.rating = rating;
        TvId = tvId;
    }

    public Integer getTvId() {
        return TvId;
    }

    public void setTvId(Integer tvId) {
        TvId = tvId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public BannerMovies() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }
}
