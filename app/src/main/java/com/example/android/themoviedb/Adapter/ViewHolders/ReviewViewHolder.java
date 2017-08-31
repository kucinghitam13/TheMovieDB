package com.example.android.themoviedb.Adapter.ViewHolders;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.themoviedb.R;

/**
 * Created by kucinghitam13 on 31/08/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    public TextView reviewContent, reviewAuthor;
    public ConstraintLayout reviewParent;

    public ReviewViewHolder(View itemView) {
        super(itemView);

        reviewContent = (TextView) itemView.findViewById(R.id.review_content);
        reviewAuthor = (TextView) itemView.findViewById(R.id.review_author);
        reviewParent = (ConstraintLayout) itemView.findViewById(R.id.review_parent);
    }
}
