package com.example.android.themoviedb.Model;

import java.io.Serializable;

/**
 * Created by kucinghitam13 on 31/08/2017.
 */

public class Reviews implements Serializable {
    private String review_author, review_content, review_URL;

    public Reviews(String review_author, String review_content, String review_URL) {
        this.review_author = review_author;
        this.review_content = review_content;
        this.review_URL = review_URL;
    }

    public String getReview_author() {
        return review_author;
    }

    public String getReview_content() {
        return review_content;
    }

    public String getReview_URL() {
        return review_URL;
    }
}
