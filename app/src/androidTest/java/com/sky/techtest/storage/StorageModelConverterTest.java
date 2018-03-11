package com.sky.techtest.storage;

import com.sky.techtest.network.model.Movie;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by leslied on 11/03/2018.
 */

public class StorageModelConverterTest {
    @Test
    public void testMovieToDomainMovieConversion() throws Exception {
        Movie networkModel = new Movie(912312l,"Dunkirk","2017","History", "https://goo.gl/1zUyyq");

        com.sky.techtest.domain.model.Movie domainMovie = StorageModelConverter.convertMovieToDomainMovie(networkModel);

        assertEquals(networkModel.getId(), domainMovie.getId());
        assertEquals(networkModel.getTitle(), domainMovie.getTitle());
        assertEquals(networkModel.getGenre(), domainMovie.getGenre());
        assertEquals(networkModel.getYear(), domainMovie.getYear());
        assertEquals(networkModel.getPoster(), domainMovie.getPoster());

    }

    @Test
    public void testDomainMovieToMovieConversion() throws Exception {
        com.sky.techtest.domain.model.Movie domainMovie = new com.sky.techtest.domain.model.Movie(912312l,"Dunkirk","2017","History", "https://goo.gl/1zUyyq");

        Movie networkMovie = StorageModelConverter.convertDomainMovieToMovie(domainMovie);

        assertEquals(networkMovie.getId(), domainMovie.getId());
        assertEquals(networkMovie.getTitle(), domainMovie.getTitle());
        assertEquals(networkMovie.getGenre(), domainMovie.getGenre());
        assertEquals(networkMovie.getYear(), domainMovie.getYear());
        assertEquals(networkMovie.getPoster(), domainMovie.getPoster());

    }
}
