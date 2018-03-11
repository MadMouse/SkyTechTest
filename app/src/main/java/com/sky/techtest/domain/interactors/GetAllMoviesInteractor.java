package com.sky.techtest.domain.interactors;

import com.sky.techtest.domain.interactors.base.Interactor;
import com.sky.techtest.domain.model.Movie;

import java.util.List;

/**
 * Created by leslied on 09/03/2018.
 */

public interface GetAllMoviesInteractor extends Interactor {
    interface Callback {
        void onMoviesRetrieved(List<Movie> movieList);

        void noMoviesFound();
    }

}
