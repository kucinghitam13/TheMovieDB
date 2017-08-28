package com.example.dikadhitama.themoviedb.Model;

import java.io.Serializable;

/**
 * Created by dikaa_001 on 28/08/2017.
 */

public class Trailers implements Serializable {
    private String trailer_name;
    private String trailer_key;

    public Trailers(String trailer_name, String trailer_key) {
        this.trailer_name = trailer_name;
        this.trailer_key = trailer_key;
    }

    public String getTrailer_name() {
        return trailer_name;
    }

    public String getTrailer_key() {
        return trailer_key;
    }
}
