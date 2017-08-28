package com.example.dikadhitama.themoviedb.Adapter.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dikadhitama.themoviedb.R;

public class MovieViewHolder extends RecyclerView.ViewHolder{
    public ImageView poster;
    public LinearLayout layoutParent;

    MovieViewHolder(View itemView){
        super(itemView);

        poster = (ImageView)itemView.findViewById(R.id.poster_movie);

        layoutParent = (LinearLayout) itemView.findViewById(R.id.movieItem);
    }
}
