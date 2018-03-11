package com.sky.techtest.domain.model;

/**
 * Created by leslied on 11/03/2018.
 */


public class Movie {
    private Long   Id;
    private String title;
    private String year;
    private String genre;
    private String poster;


    public Movie(long id, String title, String year, String genre, String poster) {
        Id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.poster = poster;
    }

    public Long getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getPoster() {
        return poster;
    }
}
