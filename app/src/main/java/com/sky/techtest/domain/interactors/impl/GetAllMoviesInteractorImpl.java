package com.sky.techtest.domain.interactors.impl;

import com.sky.techtest.domain.executor.Executor;
import com.sky.techtest.domain.executor.MainThread;
import com.sky.techtest.domain.interactors.GetAllMoviesInteractor;
import com.sky.techtest.domain.interactors.base.AbstractInteractor;
import com.sky.techtest.domain.model.Movie;
import com.sky.techtest.domain.repository.MovieRepository;

import java.util.List;

/**
 * Created by leslied on 09/03/2018.
 */

public class GetAllMoviesInteractorImpl extends AbstractInteractor implements GetAllMoviesInteractor {


    private final MovieRepository mMovieRepository;
    private final Callback mCallback;

    public GetAllMoviesInteractorImpl(Executor threadExecutor,
                                      MainThread mainThread,
                                      MovieRepository movieRepository,
                                      Callback callback) {
        super(threadExecutor, mainThread);

        if (movieRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        mMovieRepository = movieRepository;
        mCallback = callback;
    }
    @Override
    public void run() {
        // Retrieve the movies from the repository
        final List<Movie> domainMoviesList = mMovieRepository.getAllMovies();

        // Show movies on the main thread
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onMoviesRetrieved(domainMoviesList);
                if(domainMoviesList != null && domainMoviesList.size() == 0) {
                    mCallback.noMoviesFound();
                }
            }
        });
    }
}
