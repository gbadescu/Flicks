package com.example.gabrielbadescu.flicks.models;

import java.util.*;
/**
 * Created by gbadesc on 5/17/16.
 */
public class Movie {
    public String title;
    public String posterImageUrl;
    public int score;

    public Movie(String title, String posterImageUrl, int score) {
        this.title = title;
        this.posterImageUrl = posterImageUrl;
        this.score = score;
    }

    public static ArrayList<Movie> getFakeData()
    {
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i<160; i++) {
            movies.add(new Movie("Civil War", "", 70));
            movies.add(new Movie("Batman vs Superman", "", 40));
        }

        return movies;
    }
}
