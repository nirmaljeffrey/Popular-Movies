package com.example.android.popularmovies_stage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //  This text view is used to display movie's user rating
       TextView ratingTextView=findViewById(R.id.tv_user_rating_detail);
        //  This text view is used to display movie's storyline
         TextView plotTextView=findViewById(R.id.plot_detail);
        //  This text view is used to display movie's release date
       TextView releaseDateTextView=findViewById(R.id.tv_release_date_detail);
        // //  This image view is used to display movie's poster
       ImageView movieImageView=findViewById(R.id.iv_image_detail);
        //Receiving the intent from the main activity
        Intent intent=getIntent();
        //  This title of the detail activity is set to display movie list title
        if(intent.hasExtra("movie_title")) {
            setTitle(intent.getStringExtra("movie_title"));
        }
       if(intent.hasExtra("movie_release_date")) {
           releaseDateTextView.setText(intent.getStringExtra("movie_release_date"));
       }
        if(intent.hasExtra("movie_rating")) {
            String rating =intent.getStringExtra("movie_rating")+getString(R.string.detail_rating_value);
            ratingTextView.setText(rating);
        }
        if(intent.hasExtra("movie_plot")) {
            plotTextView.setText(intent.getStringExtra("movie_plot"));
        }
            if(intent.hasExtra("movie_imageUrl")) {
                Picasso.get().load(intent.getStringExtra("movie_imageUrl")).into(movieImageView);
            }

    }
}
