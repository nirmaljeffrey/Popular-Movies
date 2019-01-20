package com.example.android.popularmovies_stage1.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.popularmovies_stage1.Model.MovieTrailer;
import com.example.android.popularmovies_stage1.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private final TrailerClickListener mTrailerClickListener;
    private ArrayList<MovieTrailer> mArrayList;

    //constructor
    public TrailerAdapter(TrailerClickListener trailerClickListener) {

        mTrailerClickListener = trailerClickListener;
    }

    // Method to creates new view when invoked by layout manager.
    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_list_item, viewGroup, false);
        return new TrailerAdapterViewHolder(view);
    }

    // Method to bind data to the new views when invoked by layout manger.
    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder trailerAdapterViewHolder, int i) {
        MovieTrailer movieTrailer = mArrayList.get(i);
        trailerAdapterViewHolder.trailerTitle.setText(movieTrailer.getTrailerName());
        trailerAdapterViewHolder.trailerSite.setText(movieTrailer.getTrailerSiteName());
        String thumbnailUrl = movieTrailer.getThumbnailUrl();

        Picasso.get().load(thumbnailUrl).into(trailerAdapterViewHolder.trailerImage);


    }

    @Override
    public int getItemCount() {
        if (mArrayList == null) {
            return 0;
        }
        return mArrayList.size();
    }
// Method to return the size of the data source when invoked by layout manager.

    /**
     * Method to set  movies trailer data on TrailerAdapter.
     *
     * @param arrayList The movieTrailer object parsed by using JSONUtils.parseMovieTrailerDetailsFromJSON
     */

    public void setMovieTrailerData(ArrayList<MovieTrailer> arrayList) {

        mArrayList = arrayList;
        if (arrayList != null) {
            notifyDataSetChanged();
        }
    }

    public interface TrailerClickListener {
        void onItemClick(MovieTrailer movieTrailer);

        void onShareClick(MovieTrailer movieTrailer);
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder {
        private final ImageView trailerImage;
        private final TextView trailerTitle;
        private final TextView trailerSite;
        private final ImageView trailerShareButton;

        private TrailerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerImage = itemView.findViewById(R.id.trailerImage);
            trailerSite = itemView.findViewById(R.id.trailerSiteLabel);
            trailerTitle = itemView.findViewById(R.id.trailerTitle);
            trailerShareButton = itemView.findViewById(R.id.shareButtonImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    MovieTrailer movieTrailer = mArrayList.get(position);
                    mTrailerClickListener.onItemClick(movieTrailer);
                }
            });
            trailerShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    MovieTrailer movieTrailer = mArrayList.get(position);
                    mTrailerClickListener.onShareClick(movieTrailer);
                }
            });
        }
    }
}
