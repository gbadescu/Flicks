package com.example.gabrielbadescu.flicks.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gabrielbadescu.flicks.R;
import com.example.gabrielbadescu.flicks.adapters.MoviesAdapter;
import com.example.gabrielbadescu.flicks.models.Movie;
import com.example.gabrielbadescu.flicks.utilities.EndlessScrollListener;
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
    ListView lvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.flicks_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                movies.clear();

                fetchMoviesAsync(1);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //Get a reference to list view
        lvMovies = (ListView) findViewById(R.id.lvMovies);

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

        //Retrieve first page of movies
        fetchMoviesAsync(1);

        lvMovies.setOnScrollListener(new EndlessScrollListener(5,1) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                fetchMoviesAsync(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        setupListViewListener();

    }

    private boolean setupListViewListener()
    {

        lvMovies.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {




                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {



                        Intent detailIntent = new Intent(MoviesActivity.this,MovieDetailActivity.class);



                        detailIntent.putExtra("originalTitle", movies.get(pos).getOriginalTitle());
                        detailIntent.putExtra("overview",movies.get(pos).getOverview());


                        detailIntent.putExtra("posterPath",movies.get(pos).getPosterPath());
                        detailIntent.putExtra("backdropPath",movies.get(pos).getBackdropPath());

                        detailIntent.putExtra("releaseDate",movies.get(pos).getReleaseDate());
                        detailIntent.putExtra("voteAverage",movies.get(pos).getVoteAverage());

                        startActivityForResult(detailIntent,200);

                    }



                }
        );

        return true;
    }

    public void fetchMoviesAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        String url = String.format("https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&page=%s",page);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");

                    //movies.clear();

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
