package com.example.dikadhitama.themoviedb.Adapter.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dikadhitama.themoviedb.R;

public class MovieViewHolder extends RecyclerView.ViewHolder{
//    public TextView title, release_date, vote_average;
    public ImageView poster;
    private LinearLayout layoutParent;

    MovieViewHolder(View itemView){
        super(itemView);
//        title = (TextView)itemView.findViewById(R.id.title_movie);
//        release_date = (TextView)itemView.findViewById(R.id.release_date_movie);
//        vote_average = (TextView)itemView.findViewById(R.id.vote_average_movie);

        poster = (ImageView)itemView.findViewById(R.id.poster_movie);

        layoutParent = (LinearLayout) itemView.findViewById(R.id.movieItem);
    }

    public LinearLayout getLayoutParent() {
        return layoutParent;
    }
}
