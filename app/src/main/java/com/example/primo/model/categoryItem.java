package com.example.primo.model;

public class categoryItem {

    Integer id;
    String movieName;
    String imageUrl;
    Double rating;
    Integer TvId;
    String ActorName;
    String ActorImage;

    public categoryItem(Integer id, String movieName, String imageUrl, Double rating, Integer tvId, String actorName, String actorImage) {
        this.id = id;
        this.movieName = movieName;
        this.imageUrl = imageUrl;
        this.rating = rating;
        TvId = tvId;
        ActorName = actorName;
        ActorImage = actorImage;
    }

    public String getActorName() {
        return ActorName;
    }

    public void setActorName(String actorName) {
        ActorName = actorName;
    }

    public String getActorImage() {
        return ActorImage;
    }

    public void setActorImage(String actorImage) {
        ActorImage = actorImage;
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

    public categoryItem() {

    }

    public categoryItem(categoryItem model) {
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

}
