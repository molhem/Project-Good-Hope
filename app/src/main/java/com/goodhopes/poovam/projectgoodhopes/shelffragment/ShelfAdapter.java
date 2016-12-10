package com.goodhopes.poovam.projectgoodhopes.shelffragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;

/**
 * Created by poovam on 10/12/16.
 */

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ShelfViewHolder> {
    Subscription subscription;
    public class ShelfViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnail;
        public ShelfViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.company_name);
            thumbnail = (ImageView) view.findViewById(R.id.company_logo);
        }
    }

    @Override
    public ShelfAdapter.ShelfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shelf_cell, parent, false);

        return new ShelfViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShelfAdapter.ShelfViewHolder holder, int position) {
        holder.title.setText(Subscription.DAILYTHANTHI.name);
        holder.thumbnail.setImageDrawable(ContextCompat.getDrawable(holder.thumbnail.getContext(),
                Subscription.DAILYTHANTHI.iconID));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
