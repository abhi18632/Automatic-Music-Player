package com.songspk.entity;

/**
 * Created by hackme on 21/7/18.
 */
public class Song {

    public String name;
    public String link;
    public String singers;
    public String director;
    public String movie;
    public Long album_id;

    public Song() {

    }


    public Song(String name, String link, String singers, String director, String movie, Long album_id) {
        this.name = name;
        this.link = link;
        this.singers = singers;
        this.director = director;
        this.movie = movie;
        this.album_id = album_id;
    }
}
