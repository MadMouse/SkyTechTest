package com.sky.techtest.presenters.impl;



import android.content.SharedPreferences;

import com.sky.techtest.domain.executor.Executor;
import com.sky.techtest.domain.executor.MainThread;
import com.sky.techtest.domain.interactors.GetAllMoviesInteractor;
import com.sky.techtest.domain.interactors.impl.GetAllMoviesInteractorImpl;
import com.sky.techtest.domain.interactors.impl.GetFilterMoviesInteractorImpl;
import com.sky.techtest.domain.model.Movie;
import com.sky.techtest.domain.repository.MovieRepository;
import com.sky.techtest.network.RestClient;
import com.sky.techtest.network.model.Movies;
import com.sky.techtest.network.services.SyncService;
import com.sky.techtest.presenters.AbstractPresenter;
import com.sky.techtest.presenters.BasePresenter;
import com.sky.techtest.presenters.MoviePresenter;
import com.sky.techtest.storage.StorageModelConverter;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leslied on 09/03/2018.
 */

public class MoviePresenterImpl extends AbstractPresenter implements    MoviePresenter,
        BasePresenter,
        GetAllMoviesInteractor.Callback{



    private MoviePresenter.View     mView;
    private MovieRepository         mMovieRepository;


    public MoviePresenterImpl(Executor executor,
                              MainThread mainThread,
                              MoviePresenter.View view,
                              MovieRepository movieRepository) {
        super(executor, mainThread);
        this.mView = view;
        this.mMovieRepository = movieRepository;
    }

    @Override
    public void resume() {

        getAllMovies();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        this.mView.showError(message);
    }

    @Override
    public void onMoviesRetrieved(List<Movie> movieList) {
        this.mView.hideProgress();
        this.mView.showMovieList(movieList);

    }

    @Override
    public void noMoviesFound() {
        this.mView.hideProgress();
        this.mView.showNoMoviesFound();
    }

    @Override
    public void resetCache() {
        mMovieRepository.clear();
    }

    @Override
    public void applyFilter(String searchString) {
        mView.showProgress();
        GetAllMoviesInteractor getFilterMoviesInteractor = new GetFilterMoviesInteractorImpl(mExecutor,
                mMainThread,
                mMovieRepository,
                this,
                searchString);

        getFilterMoviesInteractor.execute();
    }

    @Override
    public void getAllMovies() {
        GetAllMoviesInteractor getAllMoviesInteractor = new GetAllMoviesInteractorImpl( mExecutor,
                mMainThread,
                mMovieRepository,
                this);

        getAllMoviesInteractor.execute();
    }

    @Override
    public void getWebLibrary() {
        mView.showProgress();
        SyncService syncService = RestClient.getService(SyncService.class);

        Call<Movies> webMovieService = syncService.fetchMovieLibrary();
        webMovieService.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                for (com.sky.techtest.network.model.Movie movie : response.body().getData()) {
                    realm.copyToRealmOrUpdate(movie);
                }
                realm.commitTransaction();


                getAllMovies();
                mView.hideProgress();
                mView.onCacheUpdate();
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                mView.hideProgress();
                mView.showError("Web Library Fetch Error : " + t.getMessage());
            }
        });


    }
}
