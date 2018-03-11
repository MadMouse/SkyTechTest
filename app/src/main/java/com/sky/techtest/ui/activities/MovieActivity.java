package com.sky.techtest.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sky.techtest.R;
import com.sky.techtest.domain.executor.impl.ThreadExecutor;
import com.sky.techtest.domain.model.Movie;
import com.sky.techtest.network.RestClient;
import com.sky.techtest.network.services.SyncService;
import com.sky.techtest.presenters.MoviePresenter;
import com.sky.techtest.presenters.impl.MoviePresenterImpl;
import com.sky.techtest.storage.MovieRepositoryImpl;
import com.sky.techtest.threading.MainThreadImpl;
import com.sky.techtest.ui.adapters.MovieAdapter;

import java.util.List;

import retrofit2.Call;

/**
 * Created by leslied on 11/03/2018.
 */

public class MovieActivity extends AppCompatActivity implements MoviePresenter.View {

    private static final String TAG = MovieActivity.class.getName();

    private static final String PREF_MOVIE_DB_LAST_REFRESH = "dbLastRefesh";
    private static final String PREF_NAME = "SKYPREF";

    private MoviePresenter mMoviePresenter;
    private MovieAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Integer mColumnCount = 2;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText mEditSeach;
    ProgressDialog mProgressDialog = null;
    private SharedPreferences mSharedPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {

        mSharedPreference = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        // setup recycler view adapter
        mAdapter = new MovieAdapter(this, this);
        mRecyclerView = findViewById(R.id.movieList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);



        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //         instantiate the presenter
        mMoviePresenter = new MoviePresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new MovieRepositoryImpl(this));



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mSharedPreference != null) {
          Long lastUpdate =  mSharedPreference.getLong(PREF_MOVIE_DB_LAST_REFRESH, 0);
          if ((lastUpdate + (10 * 60 * 1000)) < System.currentTimeMillis()){
              mMoviePresenter.resetCache();
          }
        }
        ;
        mMoviePresenter.resume();
        updateUI(this.mRecyclerView);
    }


    @Override
    protected void onDestroy() {
        mMoviePresenter.destroy();
        mSharedPreference = null;
        super.onDestroy();
    }



    @Override
    public void showMovieList(List<Movie> movieList) {
        Log.d(TAG, "Movie List Received." + movieList.size());
        mAdapter.addMovies(movieList);
    }

    @Override
    public void showNoMoviesFound() {

        Log.d(TAG, "No Movies Found");
        mMoviePresenter.getWebLibrary();
    }

    @Override
    public void onCacheUpdate() {
        if(mSharedPreference != null){
            SharedPreferences.Editor editor = mSharedPreference.edit();
            editor.putLong(PREF_MOVIE_DB_LAST_REFRESH,System.currentTimeMillis());
            editor.commit();
        }

    }

    @Override
    public void showProgress() {
        mProgressDialog = ProgressDialog.show(this, "", getString(R.string.dialog_progress_description));
    }

    @Override
    public void hideProgress() {
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void showError(String message) {

    }

    private void updateUI(View view){
        if(view != null) {
            // Set the adapter
            if (view instanceof RecyclerView) {
                mColumnCount = getResources().getInteger(R.integer.columnCount);
                if (mColumnCount <= 1) {
                    ((RecyclerView) view).setLayoutManager(new LinearLayoutManager(this));
                } else {
                    ((RecyclerView) view).setLayoutManager(new GridLayoutManager(this, mColumnCount));
                }
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        updateUI(this.mRecyclerView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));
            //Reset Movie List when search filter is closed.
            mMoviePresenter.getAllMovies();
            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            mEditSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            mEditSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            mEditSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close));

            isSearchOpened = true;
        }
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    private void doSearch() {
        if(mEditSeach != null){
            mMoviePresenter.applyFilter(mEditSeach.getText().toString());
        }
//
    }
}
