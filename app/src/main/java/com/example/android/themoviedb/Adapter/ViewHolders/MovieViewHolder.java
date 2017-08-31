package com.example.android.themoviedb.Adapter.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.themoviedb.R;

public class MovieViewHolder extends RecyclerView.ViewHolder{
    public ImageView poster;
    public LinearLayout layoutParent;

    public MovieViewHolder(View itemView) {
        super(itemView);

        poster = (ImageView)itemView.findViewById(R.id.poster_movie);

        layoutParent = (LinearLayout) itemView.findViewById(R.id.movieItem);
    }
}
