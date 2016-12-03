package com.goodhopes.poovam.projectgoodhopes.common;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodhopes.poovam.projectgoodhopes.R;

import java.util.ArrayList;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder>{
    private ArrayList<String> mDataset;
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView subjectText;
        ViewHolder(View v) {
            super(v);
            titleText = (TextView)v.findViewById(R.id.title_text);
            subjectText = (TextView)v.findViewById(R.id.subject_text);
        }
    }

    public ListViewAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleText.setText(mDataset.get(position));
        holder.subjectText.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}

