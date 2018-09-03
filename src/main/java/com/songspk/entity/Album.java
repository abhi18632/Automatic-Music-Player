package com.songspk.entity;

/**
 * Created by hackme on 21/7/18.
 */
public class Album {

    public String movie;
    public String link;
    public Boolean is_fetched;
    public String released;
    public String cast;
    public String director;
    public Long id;

    public Album(){

    }

    public Album(String movie, String link, Boolean is_fetched, String released, String cast, String director, Long id) {
        this.movie = movie;
        this.link = link;
        this.is_fetched = is_fetched;
        this.released = released;
        this.cast = cast;
        this.director = director;
        this.id = id;
    }



    public String getMovie() {
        return movie;
    }

    public String getLink() {
        return link;
    }

    public Boolean getIs_fetched() {
        return is_fetched;
    }

    public String getReleased() {
        return released;
    }

    public String getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public Long getId() {
        return id;
    }
}
