package com.example.android.popularmovies_stage1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.popularmovies_stage1.Model.Movie;
import com.example.android.popularmovies_stage1.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {


    private final onMovieItemClickLister movieItemClickLister;
    private ArrayList<Movie> movieArrayList;

    public MovieAdapter(onMovieItemClickLister movieClickListener) {
        movieItemClickLister = movieClickListener;
    }

    // Method to creates new view when invoked by layout manager.
    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new MovieAdapterViewHolder(view);

    }

    // Method to bind data to the new views when invoked by layout manger.
    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        Movie movie = movieArrayList.get(position);
        movieAdapterViewHolder.title.setText(movie.getTitle());
        movieAdapterViewHolder.rating.setText(movie.getUserRating());
        String imageUrl = movie.getImagePath();
        if (!imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(movieAdapterViewHolder.moviePoster);

        }
    }

    // Method to return the size of the data source when invoked by layout manager.
    @Override
    public int getItemCount() {

        if (movieArrayList == null) {
            return 0;
        }

        return movieArrayList.size();
    }

    /**
     * Method to set  movies data on MovieAdapter.
     *
     * @param arrayList The movie object parsed by using JSONUtils.parseMovieDetailsFromJSON
     */

    public void setMovieData(ArrayList<Movie> arrayList) {

        movieArrayList = arrayList;
        notifyDataSetChanged();
    }


    public interface onMovieItemClickLister {
        void onClickItem(Movie movie);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView moviePoster;
        private final TextView title;
        private final TextView rating;


        private MovieAdapterViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.iv_movie_poster);
            title = itemView.findViewById(R.id.tv_movie_tile);
            rating = itemView.findViewById(R.id.tv_movie_rating);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movieArrayList.get(adapterPosition);
            movieItemClickLister.onClickItem(movie);
        }
    }


}
