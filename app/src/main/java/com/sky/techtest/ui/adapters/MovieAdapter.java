package com.sky.techtest.ui.adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.techtest.R;
import com.sky.techtest.domain.model.Movie;
import com.sky.techtest.presenters.MoviePresenter;
import com.sky.techtest.ui.listeners.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by leslied on 11/03/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements RecyclerViewClickListener {

    private final MoviePresenter.View mView;
    private final Context mContext;
    private List<Movie> mMovieList;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView    mMoviePoster;
        public TextView     mMovieTitle;
        public TextView     mMovieGenre;
        public TextView     mMovieYear;



        public TextView mTotalCost;

        private RecyclerViewClickListener mListener;

        public void setup(Movie movie) {
            Context context = mMoviePoster.getContext();

            Picasso.with(context).load(movie.getPoster()).into(mMoviePoster);

            mMovieTitle.setText(movie.getTitle());
            mMovieGenre.setText(movie.getGenre());
            mMovieYear.setText(movie.getYear());
        }

        @Override
        public void onClick(View v) {
            mListener.onClickView(getAdapterPosition());
        }

        public ViewHolder(View v, final RecyclerViewClickListener listener) {
            super(v);
            ButterKnife.bind(this, v);
            this.mMoviePoster = v.findViewById(R.id.moviePoster);
            this.mMovieTitle = v.findViewById(R.id.movieTitle);
            this.mMovieGenre = v.findViewById(R.id.movieGenre);
            this.mMovieYear = v.findViewById(R.id.movieYear);
            v.setOnClickListener(this);
            mListener = listener;
        }
    }

    public MovieAdapter(MoviePresenter.View view, Context context) {
        mMovieList = new ArrayList<>();
        mView = view;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.card_movie, parent, false);
        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie itinerary = mMovieList.get(position);

        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).setup(itinerary);
        }
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    @Override
    public void onClickView(int position) {

    }

    public void addMovies(@NonNull List<Movie> movieList) {
        if(movieList != null) {
            // clean up old data
            if (this.mMovieList != null) {
                this.mMovieList.clear();
            }
            this.mMovieList = movieList;

            notifyDataSetChanged();
        }
    }
}
