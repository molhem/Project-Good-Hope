package com.goodhopes.poovam.projectgoodhopes.cardfragment;

import android.content.Context;
import android.graphics.Typeface;
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
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * Created by poovam on 2/12/16.
 * adapter to maintain the stream for card views
 */
public class SwipeStackAdapter extends BaseAdapter {
    private List<String> mData;
    private List<String> m1Data;

    SwipeStackAdapter(List<String> data,List<String> data1) {
        this.mData = data;
        this.m1Data = data1;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
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
        ImageView thumbNail = (ImageView) convertView.findViewById(R.id.thumb_nail);
        titleText.setText(mData.get(position));
        subjectText.setText(m1Data.get(position));
        Picasso.with(parent.getContext())
                .load(m1Data.get(position))
                .resize(60, 60)
                .into(thumbNail);

        return convertView;
    }
}