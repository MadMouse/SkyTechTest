package com.sky.techtest.domain.repository;

import com.sky.techtest.domain.model.Movie;

import java.util.List;

/**
 * Created by leslied on 11/03/2018.
 */

public interface MovieRepository {

    List<Movie> getAllMovies();
    List<Movie> getFilterMovies(String filterTest);

    void insert(Movie domainMovie);
    void delete(Movie domainMovie);

    void clear();
}
