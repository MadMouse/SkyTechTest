package com.sky.techtest.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by leslied on 11/03/2018.
 */

@RealmClass
public class Movie extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("poster")
    @Expose
    private String poster;

    public Movie(){
    }

    public Movie(Long id, String title, String year, String genre, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.poster = poster;
    }

    public Long getId() {
        return id;
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
