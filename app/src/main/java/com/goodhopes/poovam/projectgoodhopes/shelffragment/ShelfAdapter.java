package com.goodhopes.poovam.projectgoodhopes.shelffragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.goodhopes.poovam.projectgoodhopes.MainActivity;
import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.ListViewAdapter;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.reader.ReaderActivity;

import java.util.ArrayList;

/**
 * Created by poovam on 10/12/16.
 * SHELF
 */

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ShelfViewHolder> {
    private ArrayList<String> name;
    private ArrayList<Integer> icon;
    private FragmentActivity activity;
    class ShelfViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnail;
        ShelfViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.company_name);
            thumbnail = (ImageView) view.findViewById(R.id.company_logo);
        }
    }
    ShelfAdapter(FragmentActivity activity){
        name = new ArrayList<>();
        icon = new ArrayList<>();
        this.activity = activity;
        for(Subscription subscription: Subscription.values()){
            name.add(subscription.name);
            icon.add(subscription.iconID);
        }
    }
    @Override
    public ShelfAdapter.ShelfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shelf_cell, parent, false);
        final ShelfViewHolder holder = new ShelfViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Subscription s = Subscription.DAILYTHANTHI;

                Intent intent = new Intent(itemView.getContext(), ReaderActivity.class);
                for (Subscription subscription: Subscription.values()){
                    if(subscription.name.equals(name.get(holder.getAdapterPosition()))){
                        s = subscription;
                        break;
                    }
                }
                intent.putExtra("selection",s);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activity,holder.thumbnail, "profile");
                itemView.getContext().startActivity(intent,options.toBundle());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ShelfAdapter.ShelfViewHolder holder, int position) {
        holder.title.setText(name.get(position));
        holder.thumbnail.setImageDrawable(ContextCompat.getDrawable(holder.thumbnail.getContext(),
                icon.get(position)));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
