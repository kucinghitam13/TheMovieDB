package com.example.dikadhitama.themoviedb;

/**
 * Created by dikaa_001 on 15/08/2017. https://api.themoviedb.org/3/movie/top_rated?api_key=59a8c65586fec1aee362b633a4017178
 */

public class URLs {
    private static final String base_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "?api_key=59a8c65586fec1aee362b633a4017178";
    public static final String top_rated_URL = base_URL + "top_rated" + API_KEY;
    public static final String popular_URL = base_URL + "popular" + API_KEY;
    public static final String image185_URL = "https://image.tmdb.org/t/p/w185/";
    public static final String image342_URL = "https://image.tmdb.org/t/p/w342/";
}
