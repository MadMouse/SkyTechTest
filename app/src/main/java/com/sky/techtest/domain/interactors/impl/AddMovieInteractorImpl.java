package com.sky.techtest.domain.interactors.impl;

import com.sky.techtest.domain.executor.Executor;
import com.sky.techtest.domain.executor.MainThread;
import com.sky.techtest.domain.interactors.AddMovieInteractor;
import com.sky.techtest.domain.interactors.base.AbstractInteractor;
import com.sky.techtest.domain.model.Movie;
import com.sky.techtest.domain.repository.MovieRepository;

/**
 * Created by leslied on 09/03/2018.
 */

public class AddMovieInteractorImpl extends AbstractInteractor implements AddMovieInteractor {


    private final MovieRepository mMovieRepository;
    private final Callback        mCallback;
    private final Movie           mMovie;

    public AddMovieInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  MovieRepository movieRepository,
                                  Callback callback,
                                  Movie movie) {
        super(threadExecutor, mainThread);

        if (movieRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.mMovieRepository = movieRepository;
        this.mCallback = callback;
        this.mMovie = movie;
    }

    @Override
    public void run() {

    }
}
