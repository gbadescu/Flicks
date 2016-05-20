package com.example.gabrielbadescu.flicks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabrielbadescu.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gbadesc on 5/17/16.
 */
public class MoviesAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        TextView movieTitle;
        ImageView moviePoster;
    }

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(context,R.layout.item_movie,movies);


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
            viewHolder.moviePoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            Log.d("MoviesAdapter","Recycled:" + position); //tag should be the class
        }
        // Populate the data into the template view using the data object
        viewHolder.movieTitle.setText(movie.title);

        Picasso.with(getContext()).load("http://i.imgur.com/DvpvklR.png").into(viewHolder.moviePoster);
        //viewHolder.home.setText(user.hometown);

        //moviePoster.setImageURI(movie.posterImageUrl);
        // Return the completed view to render on screen
        Log.d("MoviesAdapter","Position:" + position); //tag should be the class
        return convertView;

    }
}
