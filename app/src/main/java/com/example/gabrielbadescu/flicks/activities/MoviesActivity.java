package com.example.gabrielbadescu.flicks.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.gabrielbadescu.flicks.adapters.MoviesAdapter;
import com.example.gabrielbadescu.flicks.R;
import com.example.gabrielbadescu.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    SharedPreferences settings;
    ArrayList<Movie> movies;
    MoviesAdapter moviesAdapter;
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchMoviesAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //Get a reference to list view
        ListView lvMovies = (ListView) findViewById(R.id.lvMovies);

        //Get MOvies we want to display
        // ArrayList<Movie> movies = Movie.getFakeData();
        movies = new ArrayList<Movie>();

        //Create an array adapter
        //ArrayAdapter<Movie> moviesAdapter = new ArrayAdapter<Movie>(this,android.R.layout.simple_list_item_1,movies);
        moviesAdapter = new MoviesAdapter(this,movies);

        if (lvMovies != null) {
            //Link adapter to our movies
            lvMovies.setAdapter(moviesAdapter);
        }

        fetchMoviesAsync(0);

    }

    public void fetchMoviesAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");

                    movies.clear();

                    movies.addAll(Movie.loadMovies(movieJsonResults));

                    moviesAdapter.notifyDataSetChanged();

                    swipeContainer.setRefreshing(false);

                    Log.d("DEBUG",movieJsonResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Log.d("DEBUG",throwable.toString());
            }
        });

    }

}
