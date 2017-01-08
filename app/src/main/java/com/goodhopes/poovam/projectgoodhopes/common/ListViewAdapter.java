package com.goodhopes.poovam.projectgoodhopes.common;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.webview.WebBrowserView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder>{
    private ArrayList<Entry> entries = new ArrayList<>();
    private FragmentActivity activity;
    boolean showFavIcon;



    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView subjectText;
        TextView companyName;
        TextView time;
        ImageView logo;
        View infoHeader;
        ImageView thumbNail;
        ImageView favIcon;
        ImageView shareIcon;
        ViewHolder(final View v) {
            super(v);
            titleText = (TextView)v.findViewById(R.id.title_text);
            subjectText = (TextView)v.findViewById(R.id.subject_text);
            infoHeader = v.findViewById(R.id.info_header);
            companyName = (TextView)infoHeader.findViewById(R.id.company_name);
            time = (TextView)infoHeader.findViewById(R.id.time);
            logo = (ImageView) infoHeader.findViewById(R.id.company_logo);
            favIcon = (ImageView) v.findViewById(R.id.favourite_icon);
            shareIcon = (ImageView) v.findViewById(R.id.share_icon);
            thumbNail = (ImageView)v.findViewById(R.id.thumb_nail);

        }
    }

    public ListViewAdapter(ArrayList<Entry> entries, FragmentActivity activity,boolean showFavIcon) {
        this.entries = entries;
        this.activity = activity;
        this.showFavIcon = showFavIcon;
    }

    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        holder.titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OnClickHandlers().webViewLoader(view,holder.getAdapterPosition(),entries,activity,v);
            }
        });
        holder.subjectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OnClickHandlers().webViewLoader(view,holder.getAdapterPosition(),entries,activity,v);
            }
        });
        holder.shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new OnClickHandlers().onShareIconClicked(view,holder.getAdapterPosition(),entries);
            }
        });

        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new OnClickHandlers().onFavouritesClicked(view,holder.getAdapterPosition(),entries);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.thumbNail.setVisibility(View.GONE);
        holder.titleText.setText(entries.get(position).title);
        holder.subjectText.setText(entries.get(position).content);
        holder.companyName.setText(entries.get(position).author);
        holder.time.setText(new Utilities().timeAgoConversion(entries.get(position).time));
        holder.logo.setImageDrawable(ContextCompat.getDrawable(holder.logo.getContext(),
                entries.get(position).companyLogoId));
        if(!showFavIcon){
            holder.favIcon.setVisibility(View.GONE);
        }
        if(URLUtil.isValidUrl(entries.get(position).thumbNailUrl)){
            holder.thumbNail.setVisibility(View.VISIBLE);
            Picasso.with(holder.thumbNail.getContext())
                    .load(entries.get(position).thumbNailUrl)
                    .fit()
                    .placeholder(ContextCompat.getDrawable(holder.thumbNail.getContext(),R.drawable.placeholder))
                    .into(holder.thumbNail);
        }
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

}

