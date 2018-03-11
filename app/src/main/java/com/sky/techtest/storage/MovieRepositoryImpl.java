package com.sky.techtest.storage;

import android.content.Context;

import com.sky.techtest.domain.model.Movie;
import com.sky.techtest.domain.repository.MovieRepository;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by leslied on 11/03/2018.
 */

public class MovieRepositoryImpl implements MovieRepository {

    private final Context mContext;
    public MovieRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public List<Movie> getAllMovies() {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<com.sky.techtest.network.model.Movie> movies = realm.where(com.sky.techtest.network.model.Movie.class).findAll();
        return StorageModelConverter.convertMoviesToDomainMovies(movies);
    }

    @Override
    public List<Movie> getFilterMovies(String filterText) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<com.sky.techtest.network.model.Movie> movies = realm.where(com.sky.techtest.network.model.Movie.class)
                .contains("title", filterText, Case.INSENSITIVE)
                .or()
                .contains("genre", filterText, Case.INSENSITIVE)
                .findAll();
        return StorageModelConverter.convertMoviesToDomainMovies(movies);
    }

    @Override
    public void insert(Movie domainMovie) {
        Realm realm = Realm.getDefaultInstance();
        com.sky.techtest.network.model.Movie dbMovie = StorageModelConverter.convertDomainMovieToMovie(domainMovie);
        realm.copyToRealmOrUpdate(dbMovie);
        realm.close();
    }

    @Override
    public void delete(Movie domainMovie) {
        Realm realm = Realm.getDefaultInstance();
        com.sky.techtest.network.model.Movie dbMovie = realm.where(com.sky.techtest.network.model.Movie.class)
                                                        .equalTo("id",domainMovie.getId())
                                                        .findFirst();

        dbMovie.deleteFromRealm();
        realm.close();
    }

    /**
     * Flush the current dataset.
     */
    @Override
    public void clear() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();

    }
}
