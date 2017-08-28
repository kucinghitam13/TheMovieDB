package com.example.dikadhitama.themoviedb.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dikadhitama.themoviedb.Adapter.ListAdapter;
import com.example.dikadhitama.themoviedb.Adapter.ViewHolders.TrailerViewHolder;
import com.example.dikadhitama.themoviedb.AppMovie;
import com.example.dikadhitama.themoviedb.BaseActivity;
import com.example.dikadhitama.themoviedb.Model.Movies;
import com.example.dikadhitama.themoviedb.Model.Trailers;
import com.example.dikadhitama.themoviedb.R;
import com.example.dikadhitama.themoviedb.URLs;
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
    private Button bt_favorite;

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private ArrayList<Trailers> trailerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = (Movies) getIntent().getSerializableExtra("movie");

        title = (TextView) findViewById(R.id.title_detail);
        vote_average = (TextView) findViewById(R.id.vote_average_detail);
        release_date = (TextView) findViewById(R.id.release_date_detail);
        overview = (TextView) findViewById(R.id.overview_detail);
        poster = (ImageView) findViewById(R.id.poster_detail);

        initRecyclerTrailer();
        initAdapterTrailer();

        showDialog("Now Loading");
        if (isInternetConnectionAvailable()) {
            getInfo();
            getTrailer();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            hideDialog();
        }

        bt_favorite = (Button) findViewById(R.id.bt_favorite);
        bt_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDB().addMovie(movie);
                Toast.makeText(getApplicationContext(), movie.getOriginal_title() + " has been added to your favorite list", Toast.LENGTH_SHORT).show();
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

    private void initRecyclerTrailer() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_trailer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
    }

    private void initAdapterTrailer() {
        listAdapter = new ListAdapter<Trailers, TrailerViewHolder>(R.layout.trailer_list, TrailerViewHolder.class, Trailers.class, trailerList) {
            @Override
            protected void bindView(TrailerViewHolder holder, final Trailers model, int position) {
                holder.trailerName.setText(model.getTrailer_name());
                holder.trailerParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URLs.youtube_URL + model.getTrailer_key())));
                    }
                });
            }
        };
        recyclerView.setAdapter(listAdapter);
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

                    title.setText(movie.getTitle().equals(movie.getOriginal_title()) ? movie.getOriginal_title() : movie.getOriginal_title() + "\n(" + movie.getTitle() + ")");
                    vote_average.setText(String.valueOf(movie.getVote_average()) + "/10");
                    release_date.setText(movie.getRelease_date());
                    overview.setText(movie.getOverview());
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

    private void getTrailer() {
        final String API_URL = URLs.base_URL + movie.getId() + "/videos" + URLs.API_KEY;

        StringRequest request = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parent = new JSONObject(response);
                    JSONArray t = parent.getJSONArray("results");
                    for (int i = 0; i < t.length(); i++) {
                        JSONObject child = t.getJSONObject(i);
                        Trailers trailer = new Trailers(child.getString("name"), child.getString("key"));

                        trailerList.add(trailer);
                    }
                    listAdapter.swapData(trailerList);
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

//TODO(1) bikin lebih pendek dengan buat method
//TODO(2) benerin models biar lebih efektif
