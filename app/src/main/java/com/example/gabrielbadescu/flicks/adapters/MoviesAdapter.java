package com.example.gabrielbadescu.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabrielbadescu.flicks.R;
import com.example.gabrielbadescu.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by gbadesc on 5/17/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    String imagePath;

    private static class ViewHolder {
        TextView originalTitle;
        TextView overview;
        ImageView imagePath;
        //ImageView backdropPath;
    }

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context, R.layout.item_movie,movies);


    }

    public View getView(int position, View convertView, ViewGroup parent)
    {


        //get movies we want to display

        //get the xml we want to display it in
        // Get the data item for this position - this is coming from the ArrayAdapter class
        Movie movie = getItem(position);

        //update the xml based on movie data
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.imagePath = (ImageView) convertView.findViewById(R.id.ivImage);
            //viewHolder.backdropPath = (ImageView) convertView.findViewById(R.id.ivBackdrop);
            viewHolder.originalTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            Log.d("MoviesAdapter","Recycled:" + position); //tag should be the class
        }
        // Populate the data into the template view using the data object
        viewHolder.originalTitle.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());


        boolean isLandscape = getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscape) {
            imagePath = movie.getBackdropPath();
        } else {
            imagePath = movie.getPosterPath();
        }


        Picasso.with(getContext()).load(imagePath).fit().centerCrop().transform(new RoundedCornersTransformation(10, 10)).placeholder(R.drawable.image_not_available).into(viewHolder.imagePath);


        //posterPath.setImageURI(movie.posterPath);
        // Return the completed view to render on screen
        Log.d("MoviesAdapter","Position:" + position); //tag should be the class
        return convertView;

    }
}
