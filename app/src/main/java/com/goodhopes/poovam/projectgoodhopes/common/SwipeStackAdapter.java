package com.goodhopes.poovam.projectgoodhopes.common;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by poovam on 2/12/16.
 * adapter to maintain the stream for card views
 */
public class SwipeStackAdapter extends BaseAdapter {
    private ArrayList<Entry> entries;


    public SwipeStackAdapter(ArrayList<Entry> entries) {
        this.entries = entries;
    }


    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Object getItem(int i) {
        return entries.get(i).title;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        TextView titleText = (TextView) convertView.findViewById(R.id.title_text);
        TextView subjectText = (TextView) convertView.findViewById(R.id.subject_text);
        TextView companyName = (TextView) convertView.findViewById(R.id.company_name);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumb_nail);
        ImageView logo = (ImageView) convertView.findViewById(R.id.company_logo);



        titleText.setText(entries.get(position).title);
        subjectText.setText(entries.get(position).content);
        companyName.setText(entries.get(position).author);
        time.setText(entries.get(position).time+"   ");
        logo.setImageDrawable(ContextCompat.getDrawable(logo.getContext(),
                entries.get(position).companyLogoId));

        thumbNail.setVisibility(View.GONE);

//
//        Picasso.with(parent.getContext())
//                .load(m1Data.get(position))
//                .resize(60, 60)
//                .into(thumbNail);

        return convertView;
    }
}