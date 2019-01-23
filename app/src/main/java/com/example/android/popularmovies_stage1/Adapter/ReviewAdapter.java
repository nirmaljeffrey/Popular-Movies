package com.example.android.popularmovies_stage1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies_stage1.Model.MovieReview;
import com.example.android.popularmovies_stage1.R;


import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private ArrayList<MovieReview> reviewArrayList;

    public ReviewAdapter() {

    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_item, viewGroup, false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder reviewAdapterViewHolder, int i) {
        MovieReview movieReview = reviewArrayList.get(i);
        reviewAdapterViewHolder.reviewerName.setText(movieReview.getAuthor());
        reviewAdapterViewHolder.review.setText(movieReview.getContent());


    }

    @Override
    public int getItemCount() {
        if (reviewArrayList == null) {
            return 0;
        }
        return reviewArrayList.size();
    }

    /**
     * Method to set  movies review data on ReviewAdapter.
     *
     * @param arrayList The movieTrailer object parsed by using JSONUtils.parseMovieReviewDetailsFromJSON
     */

    public void setMovieReviewData(ArrayList<MovieReview> arrayList) {

        reviewArrayList = arrayList;
        if (arrayList != null) {
            notifyDataSetChanged();
        }
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView reviewerName;
        private final TextView review;

        private ReviewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            review = itemView.findViewById(R.id.review_content);

        }
    }
}
