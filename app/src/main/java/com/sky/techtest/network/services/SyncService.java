package com.sky.techtest.network.services;


import com.sky.techtest.network.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by leslied on 09/03/2018.
 */

public interface SyncService {

    @Headers({"Accept: application/json", "Content-Type: application/json;charset=utf-8", "Connection: close"})
    @GET("/api/movies")
    Call<Movies> fetchMovieLibrary();
}
