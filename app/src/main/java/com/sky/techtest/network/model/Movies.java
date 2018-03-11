package com.sky.techtest.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by leslied on 11/03/2018.
 */

public class Movies {

    @SerializedName("data")
    @Expose
    private List<Movie> data = null;

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }
}
