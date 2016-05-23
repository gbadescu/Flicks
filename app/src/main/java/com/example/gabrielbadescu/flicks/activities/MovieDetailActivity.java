package com.example.gabrielbadescu.flicks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gabrielbadescu.flicks.R;
import com.example.gabrielbadescu.flicks.utilities.DeviceDimensionsHelper;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

         //= findViewById(R.id.imageView);
        TextView tvTitle  = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(getIntent().getStringExtra("originalTitle"));

        TextView releaseDate  = (TextView) findViewById(R.id.tvReleaseDate);
        releaseDate.setText(getIntent().getStringExtra("releaseDate"));

        TextView overview  = (TextView) findViewById(R.id.overview);
        overview.setText(getIntent().getStringExtra("overview"));

        RatingBar voteAverage = (RatingBar) findViewById(R.id.ratingBar);
        voteAverage.setNumStars(10);
        voteAverage.setIsIndicator(true);
        int rating = (int) (Double.parseDouble(getIntent().getStringExtra("voteAverage")));
        voteAverage.setRating(rating);


        ImageView backdrop = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this.getBaseContext()).load(getIntent().getStringExtra("backdropPath")).transform(new RoundedCornersTransformation(10, 10)).placeholder(R.drawable.image_not_available).resize(DeviceDimensionsHelper.getDisplayWidth(this.getBaseContext()),0).into(backdrop);
    }
}
