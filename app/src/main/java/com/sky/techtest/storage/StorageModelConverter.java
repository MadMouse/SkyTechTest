package com.sky.techtest.storage;



import com.sky.techtest.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leslied on 11/03/2018.
 */
public class StorageModelConverter {
    private static final String TAG = "StorageModelConverter";


    public static List<Movie> convertMoviesToDomainMovies(List<com.sky.techtest.network.model.Movie> networkMovies) {
        List<Movie> domainMovieList = new ArrayList<Movie>();

        for (com.sky.techtest.network.model.Movie movie : networkMovies) {
            domainMovieList.add(convertMovieToDomainMovie(movie));
        }
        return domainMovieList;
    }

    public static com.sky.techtest.network.model.Movie convertDomainMovieToMovie(Movie domainMovie) {

        return new com.sky.techtest.network.model.Movie(domainMovie.getId(),
                                                        domainMovie.getTitle(),
                                                        domainMovie.getYear(),
                                                        domainMovie.getGenre(),
                                                        domainMovie.getPoster());
    }

    public static Movie convertMovieToDomainMovie(com.sky.techtest.network.model.Movie webMovie) {

        return new Movie(   webMovie.getId(),
                            webMovie.getTitle(),
                            webMovie.getYear(),
                            webMovie.getGenre(),
                            webMovie.getPoster());
    }
}

