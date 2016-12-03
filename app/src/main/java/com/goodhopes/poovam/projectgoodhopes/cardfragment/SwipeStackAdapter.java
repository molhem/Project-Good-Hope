package com.goodhopes.poovam.projectgoodhopes.cardfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goodhopes.poovam.projectgoodhopes.R;

import java.util.List;
/**
 * Created by poovam on 2/12/16.
 * adapter to maintain the stream for card views
 */
public class SwipeStackAdapter extends BaseAdapter {
    private List<String> mData;


    SwipeStackAdapter(List<String> data) {
        this.mData = data;
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
        titleText.setText(mData.get(position));
        subjectText.setText(mData.get(position));

        return convertView;
    }
}