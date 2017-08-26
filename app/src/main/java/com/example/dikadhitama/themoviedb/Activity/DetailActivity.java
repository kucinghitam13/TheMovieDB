package com.example.dikadhitama.themoviedb.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dikadhitama.themoviedb.BaseActivity;
import com.example.dikadhitama.themoviedb.Model.Movies;
import com.example.dikadhitama.themoviedb.R;
import com.example.dikadhitama.themoviedb.URLs;
import com.squareup.picasso.Picasso;

public class DetailActivity extends BaseActivity {

    private Movies movie;
    private TextView original_title, vote_average, release_date, overview;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = (Movies) getIntent().getSerializableExtra("movie");

        original_title = (TextView) findViewById(R.id.original_title_detail);
        vote_average = (TextView) findViewById(R.id.vote_average_detail);
        release_date = (TextView) findViewById(R.id.release_date_detail);
        overview = (TextView) findViewById(R.id.overview_detail);
        poster = (ImageView) findViewById(R.id.poster_detail);

        Picasso.with(getApplicationContext())
                .load(URLs.image342_URL + movie.getPoster_path())
                .into(poster);
        original_title.setText(movie.getTitle().equals(movie.getOriginal_title()) ? movie.getOriginal_title() : movie.getOriginal_title() + "\n(" + movie.getTitle() + ")");
        vote_average.setText(String.valueOf(movie.getVote_average()) + "/10");
        release_date.setText(movie.getRelease_date());
        overview.setText(movie.getOverview());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
