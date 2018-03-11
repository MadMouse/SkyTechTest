package com.sky.techtest.presenters;



import com.sky.techtest.domain.model.Movie;
import com.sky.techtest.ui.BaseView;

import java.util.List;

/**
 * Created by dmilicic on 12/10/15.
 */
public interface MoviePresenter extends BasePresenter {

    interface View extends BaseView {

        void showMovieList(List<Movie> movieList);

        void showNoMoviesFound();

        void onCacheUpdate();

    }

    void resetCache();
    void applyFilter(String searchString);
    void getAllMovies();
    void getWebLibrary();
}
