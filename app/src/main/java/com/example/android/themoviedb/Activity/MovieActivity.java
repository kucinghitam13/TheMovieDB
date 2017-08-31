package com.example.android.themoviedb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.themoviedb.Adapter.ListAdapter;
import com.example.android.themoviedb.Adapter.ViewHolders.MovieViewHolder;
import com.example.android.themoviedb.AppMovie;
import com.example.android.themoviedb.BaseActivity;
import com.example.android.themoviedb.Model.Movies;
import com.example.android.themoviedb.R;
import com.example.android.themoviedb.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.themoviedb.URLs.popular_URL;

public class MovieActivity extends BaseActivity {
    private static final String TAG = "MovieActivity";
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private ArrayList<Movies> movieList = new ArrayList<>();
    private String API_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        initRecycler();
        initAdapterMovies();

        API_URL = popular_URL;
        getSupportActionBar().setTitle("Movies (Popular)");
        loadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        movieList.clear();
        switch (item.getItemId()) {
            case R.id.popular_menu:
                if (!API_URL.equals(URLs.popular_URL)) {
                    API_URL = popular_URL;
                    getSupportActionBar().setTitle("Movies (Popular)");
                    loadList();
                } else {
                    return true;
                }
                break;
            case R.id.top_rated_menu:
                if (!API_URL.equals(URLs.top_rated_URL)) {
                    API_URL = URLs.top_rated_URL;
                    getSupportActionBar().setTitle("Movies (Top Rated)");
                    loadList();
                } else {
                    return true;
                }
                break;
            case R.id.favorites_menu:
                getSupportActionBar().setTitle("Movies (Favorites)");
                getLocalData();
                API_URL = "";
                break;
        }
//        getDB().clearMovie();
        return true;
    }

    private void loadList() {
        showDialog("Now Loading");
        if (isInternetConnectionAvailable()) {
            getData();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            hideDialog();
        }
    }

    private void initRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_movie);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), 1));
    }

    private void initAdapterMovies() {
        listAdapter = new ListAdapter<Movies, MovieViewHolder>(R.layout.movie_list, MovieViewHolder.class, Movies.class, movieList) {
            @Override
            protected void bindView(MovieViewHolder holder, final Movies model, int position) {

                Picasso.with(getApplicationContext())
                        .load(URLs.image185_URL + model.getPoster_path())
                        .into(holder.poster);

                holder.layoutParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent i = new Intent(MovieActivity.this, DetailActivity.class);
                        //i.putExtra("movie", model);

                        startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("movie", model));
                    }
                });
            }
        };
        recyclerView.setAdapter(listAdapter);
    }

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parent = new JSONObject(response);

                    JSONArray m = parent.getJSONArray("results");
                    for (int i = 0; i < m.length(); i++) {
                        JSONObject child = m.getJSONObject(i);

                        Movies movie = new Movies();
                        movie.setId(child.getInt("id"));
                        movie.setPoster_path(child.getString("poster_path"));
                        movie.setTitle(child.getString("title"));

                        movieList.add(movie);
                    }
                    listAdapter.swapData(movieList);
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

    private void getLocalData(){
        movieList = getDB().getAllListMovies();
        listAdapter.swapData(movieList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (API_URL.isEmpty()) {
            getLocalData();
        }
    }
}
