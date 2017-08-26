package com.example.dikadhitama.themoviedb.Model;

import java.io.Serializable;

/**
 * Created by dikaa_001 on 15/08/2017.
 */

public class Movies implements Serializable {
    private int id;
    private int vote_count;
    private double vote_average;
    private int popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String overview;
    private String release_date;
    private String title;

    public int getId() {
        return id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
