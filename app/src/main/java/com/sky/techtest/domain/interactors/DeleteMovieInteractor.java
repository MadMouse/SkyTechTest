package com.sky.techtest.domain.interactors;


import com.sky.techtest.domain.interactors.base.Interactor;
import com.sky.techtest.domain.model.Movie;

/**
 * Created by leslied on 09/03/2018.
 */
public interface DeleteMovieInteractor extends Interactor {

    interface Callback {
        void onMovieDeleted(Movie movie);

        void onAddMovieFailed(String error);
    }
}
