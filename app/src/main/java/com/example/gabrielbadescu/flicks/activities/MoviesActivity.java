package com.example.gabrielbadescu.flicks.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.gabrielbadescu.flicks.MoviesAdapter;
import com.example.gabrielbadescu.flicks.R;
import com.example.gabrielbadescu.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        //Get a reference to list view
        ListView lvMovies = (ListView) findViewById(R.id.lvMovies);

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();






        //Get MOvies we want to display
        //ArrayList<Movie> movies = Movie.getFakeData();

        //Create an array adapter
        //ArrayAdapter<Movie> moviesAdapter = new ArrayAdapter<Movie>(this,android.R.layout.simple_list_item_1,movies);
        MoviesAdapter moviesAdapter = new MoviesAdapter(this,movies);

        if (lvMovies != null) {
            //Link adapter to our movies
            lvMovies.setAdapter(moviesAdapter);
        }

    }
}
