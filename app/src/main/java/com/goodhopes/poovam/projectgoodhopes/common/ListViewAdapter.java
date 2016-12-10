package com.goodhopes.poovam.projectgoodhopes.common;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.webview.WebBrowserView;

import java.util.ArrayList;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder>{
    private ArrayList<Entry> entries;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView subjectText;
        TextView companyName;
        TextView time;
        ImageView logo;
        View infoHeader;
        ImageView thumbNail;
        ViewHolder(final View v) {
            super(v);
            titleText = (TextView)v.findViewById(R.id.title_text);
            subjectText = (TextView)v.findViewById(R.id.subject_text);
            infoHeader = v.findViewById(R.id.info_header);
            companyName = (TextView)infoHeader.findViewById(R.id.company_name);
            time = (TextView)infoHeader.findViewById(R.id.time);
            logo = (ImageView) infoHeader.findViewById(R.id.company_logo);

            thumbNail = (ImageView)v.findViewById(R.id.thumb_nail);

        }
    }

    public ListViewAdapter(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        final ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(), WebBrowserView.class);
                intent.putExtra("contentURL",entries.get(holder.getAdapterPosition()).contentUrl);
                intent.putExtra("title",entries.get(holder.getAdapterPosition()).title);
                view.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.thumbNail.setVisibility(View.GONE);
        holder.titleText.setText(entries.get(position).title);
        holder.subjectText.setText(entries.get(position).content);
        holder.companyName.setText(entries.get(position).author);
        holder.time.setText(entries.get(position).time+"");
        holder.logo.setImageDrawable(ContextCompat.getDrawable(holder.logo.getContext(),
                entries.get(position).companyLogoId));


    }

    @Override
    public int getItemCount() {
        return entries.size();
    }


}

