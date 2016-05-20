package com.example.gabrielbadescu.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
/**
 * Created by gbadesc on 5/17/16.
 */
public class Movie {
    String originalTitle;
    String posterPath;
    String overview;
    String backdropPath;

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s",backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s",posterPath);
    }

    public String getOverview() {
        return overview;
    }



    public Movie(String originalTitle, String posterPath, String overview) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
    }

    public Movie(JSONObject movieJSON) throws JSONException
    {
        this.posterPath = movieJSON.getString("poster_path");
        this.originalTitle = movieJSON.getString("original_title");
        this.overview = movieJSON.getString("overview");
        this.backdropPath = movieJSON.getString("backdrop_path");
    }

    public static ArrayList<Movie> loadMovies(JSONArray movies)
    {
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i=0; i < movies.length(); i++)
        {
            try {
                results.add(i,new Movie(movies.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
