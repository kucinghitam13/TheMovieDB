package com.example.dikadhitama.themoviedb.Adapter.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dikadhitama.themoviedb.R;

/**
 * Created by dikaa_001 on 28/08/2017.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    public TextView trailerName;
    public LinearLayout trailerParent;

    public TrailerViewHolder(View itemView) {
        super(itemView);

        trailerName = (TextView) itemView.findViewById(R.id.trailer_name);
        trailerParent = (LinearLayout) itemView.findViewById(R.id.trailer_parent);
    }
}
