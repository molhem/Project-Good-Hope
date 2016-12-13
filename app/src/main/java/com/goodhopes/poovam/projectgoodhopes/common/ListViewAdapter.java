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
                Intent intent =new Intent(view.getContext(), WebBrowserView.class);
                intent.putExtra("contentURL",entries.get(holder.getAdapterPosition()).contentUrl);
                intent.putExtra("title",entries.get(holder.getAdapterPosition()).title);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activity,v, "profile");
                view.getContext().startActivity(intent,options.toBundle());
            }
        });
        holder.subjectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(), WebBrowserView.class);
                intent.putExtra("contentURL",entries.get(holder.getAdapterPosition()).contentUrl);
                intent.putExtra("title",entries.get(holder.getAdapterPosition()).title);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activity,v, "profile");
                view.getContext().startActivity(intent,options.toBundle());
            }
        });
        holder.shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String sAux = "\n"+entries.get(holder.getAdapterPosition()).contentUrl+"\n\n";
                sAux = sAux + entries.get(holder.getAdapterPosition()).contentUrl;
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                holder.shareIcon.getContext().startActivity(Intent.createChooser(i, "Choose one"));
            }
        });

        holder.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = holder.favIcon.getContext().getSharedPreferences(
                        holder.favIcon.getContext().getString(R.string.saved_data),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                Gson gson = new Gson();
                String savedFavs = sharedPref.getString(holder.favIcon.getContext().getString(R.string.favourites),"");
                ArrayList<Entry> favourites;
                Type type = new TypeToken<List<Entry>>() {
                }.getType();
                favourites = gson.fromJson(savedFavs, type);
                if(favourites == null){
                    favourites = new ArrayList<>();
                }
                favourites.add(entries.get(holder.getAdapterPosition()));
                savedFavs = gson.toJson(favourites);
                editor.putString(holder.favIcon.getContext().getString(R.string.favourites),savedFavs);
                editor.apply();
                Toast.makeText(holder.favIcon.getContext(),"Successfully saved to favourites",Toast.LENGTH_SHORT)
                        .show();
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
        holder.time.setText(entries.get(position).time+"");
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
                    .placeholder(ContextCompat.getDrawable(holder.thumbNail.getContext(),R.drawable.dinakaran_logo))
                    .into(holder.thumbNail);
        }


    }

    @Override
    public int getItemCount() {
        return entries.size();
    }


}

