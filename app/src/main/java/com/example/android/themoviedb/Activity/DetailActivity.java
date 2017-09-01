package com.example.android.themoviedb.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.themoviedb.Adapter.ListAdapter;
import com.example.android.themoviedb.Adapter.ViewHolders.ReviewViewHolder;
import com.example.android.themoviedb.Adapter.ViewHolders.TrailerViewHolder;
import com.example.android.themoviedb.AppMovie;
import com.example.android.themoviedb.BaseActivity;
import com.example.android.themoviedb.Model.Movies;
import com.example.android.themoviedb.Model.Reviews;
import com.example.android.themoviedb.Model.Trailers;
import com.example.android.themoviedb.R;
import com.example.android.themoviedb.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity {
    private static final String TAG = "DetailActivity";
    private Movies movie;
    private TextView title, vote_average, release_date, overview;
    private ImageView poster;

    private RecyclerView trailerRecycler, reviewRecycler;
    private ListAdapter trailerAdapter, reviewAdapter;
    private ArrayList<Trailers> trailerList = new ArrayList<>();
    private ArrayList<Reviews> reviewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = (Movies) getIntent().getSerializableExtra("movie");

        getSupportActionBar().setTitle(movie.getTitle());

        title = (TextView) findViewById(R.id.title_detail);
        vote_average = (TextView) findViewById(R.id.vote_average_detail);
        release_date = (TextView) findViewById(R.id.release_date_detail);
        overview = (TextView) findViewById(R.id.overview_detail);
        poster = (ImageView) findViewById(R.id.poster_detail);
        trailerRecycler = (RecyclerView) findViewById(R.id.recycler_view_trailer);
        reviewRecycler = (RecyclerView) findViewById(R.id.recycler_view_review);

        initRecycler(trailerRecycler);
        initRecycler(reviewRecycler);
        initAdapterTrailer();
        initAdapterReview();

        showDialog("Now Loading");
        if (isInternetConnectionAvailable()) {
            getInfo();
            getTrailer();
            getReview();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

        final ImageView favorite = (ImageView) findViewById(R.id.favorite);
        if (getDB().isMovieExist(movie)) {
            favorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            favorite.setImageResource(android.R.drawable.btn_star_big_off);
        }

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getDB().isMovieExist(movie)) {
                    favorite.setImageResource(android.R.drawable.btn_star_big_off);
                    getDB().deleteMovie(movie);
                    Toast.makeText(getApplicationContext(), movie.getOriginal_title() + " has been removed from your favorite list.", Toast.LENGTH_SHORT).show();
                } else {
                    favorite.setImageResource(android.R.drawable.btn_star_big_on);
                    getDB().addMovie(movie);
                    Toast.makeText(getApplicationContext(), movie.getOriginal_title() + " has been added to your favorite list.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    private void initRecycler(RecyclerView recyclerView) {
        RecyclerView r = recyclerView;
        r.setLayoutManager(new LinearLayoutManager(this));
        r.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
    }

    private void initAdapterTrailer() {
        trailerAdapter = new ListAdapter<Trailers, TrailerViewHolder>(R.layout.trailer_list, TrailerViewHolder.class, Trailers.class, trailerList) {
            @Override
            protected void bindView(TrailerViewHolder holder, final Trailers model, int position) {
                TextView header = (TextView) findViewById(R.id.trailer_text);
                header.setText("Trailers:");

                Picasso.with(getApplicationContext())
                        .load(URLs.youtube_thumbnail_URL + model.getTrailer_key() + "/1.jpg")
                        .into(holder.trailerThumbnail);

                holder.trailerName.setText(model.getTrailer_name());
                holder.trailerParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.youtube_URL + model.getTrailer_key())));
                    }
                });
            }
        };
        trailerRecycler.setAdapter(trailerAdapter);
    }

    private void initAdapterReview() {
        reviewAdapter = new ListAdapter<Reviews, ReviewViewHolder>(R.layout.review_list, ReviewViewHolder.class, Reviews.class, reviewList) {
            @Override
            protected void bindView(ReviewViewHolder holder, final Reviews model, int position) {
                TextView header = (TextView) findViewById(R.id.review_text);
                header.setText("Reviews:");

                holder.reviewContent.setText(model.getReview_content() + " ");
                holder.reviewContent.setMovementMethod(LinkMovementMethod.getInstance());
                holder.reviewAuthor.setText("by " + model.getReview_author());
                holder.reviewAuthor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getReview_URL())));
                    }
                });
            }
        };
        reviewRecycler.setAdapter(reviewAdapter);
    }

    private void getInfo() {
        final String API_URL = URLs.base_URL + movie.getId() + URLs.API_KEY;
        Picasso.with(getApplicationContext())
                .load(URLs.image342_URL + movie.getPoster_path())
                .into(poster);

        StringRequest request = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parent = new JSONObject(response);

                    movie.setOriginal_title(parent.getString("original_title"));
                    movie.setTitle(parent.getString("title"));
                    movie.setOverview(parent.getString("overview"));
                    movie.setVote_average(parent.getDouble("vote_average"));
                    movie.setRelease_date(parent.getString("release_date"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                title.setText(movie.getTitle().equals(movie.getOriginal_title()) ? movie.getOriginal_title() : movie.getOriginal_title() + "\n(" + movie.getTitle() + ")");
                vote_average.setText("Score:\n" + String.valueOf(movie.getVote_average()) + " / 10");
                release_date.setText("Release date:\n" + movie.getRelease_date());
                overview.setText(movie.getOverview());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppMovie.getInstance().addToRequestQueue(request, TAG);
    }

    private void getTrailer() {
        final String API_URL = URLs.base_URL + movie.getId() + "/videos" + URLs.API_KEY;

        StringRequest request = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parent = new JSONObject(response);
                    JSONArray t = parent.getJSONArray("results");
                    if (t.length() == 0) {
                        TextView noTrailer = (TextView) findViewById(R.id.notrailer_text);
                        noTrailer.setText("No trailers are available");
                        noTrailer.setVisibility(View.VISIBLE);

                        reviewRecycler.setVisibility(View.INVISIBLE);
                    } else {
                        for (int i = 0; i < t.length(); i++) {
                            JSONObject child = t.getJSONObject(i);
                            Trailers trailer = new Trailers(
                                    child.getString("name"),
                                    child.getString("key"));

                            trailerList.add(trailer);
                        }
                        trailerAdapter.swapData(trailerList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AppMovie.getInstance().addToRequestQueue(request, TAG);
    }

    private void getReview() {
        final String API_URL = URLs.base_URL + movie.getId() + "/reviews" + URLs.API_KEY;

        StringRequest request = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parent = new JSONObject(response);
                    JSONArray r = parent.getJSONArray("results");
                    if (r.length() == 0) {
                        TextView noReview = (TextView) findViewById(R.id.noreview_text);
                        noReview.setText("No reviews are available");
                        noReview.setVisibility(View.VISIBLE);

                        reviewRecycler.setVisibility(View.INVISIBLE);
                    } else {
                        for (int i = 0; i < r.length(); i++) {
                            JSONObject child = r.getJSONObject(i);
                            Reviews review = new Reviews(
                                    child.getString("author"),
                                    child.getString("content"),
                                    child.getString("url"));

                            reviewList.add(review);
                        }
                        reviewAdapter.swapData(reviewList);
                    }
                    hideDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
            }
        });
        AppMovie.getInstance().addToRequestQueue(request, TAG);
    }
}

//TODO(1) bikin keterangan empty data di recyclerview
//TODO(2) bikin teks dialog loading yang dinamis
//TODO(3) progress bar lesson 2
