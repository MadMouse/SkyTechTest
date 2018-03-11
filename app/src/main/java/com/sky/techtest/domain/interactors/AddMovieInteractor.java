package com.sky.techtest.domain.interactors;


import com.sky.techtest.domain.interactors.base.Interactor;
import com.sky.techtest.domain.model.Movie;

/**
 * Created by leslied on 09/03/2018.
 */
public interface AddMovieInteractor extends Interactor {

    interface Callback {
        void onMovieAdded(Movie movie);

        void onAddMovieFailed(String error);
    }
}
