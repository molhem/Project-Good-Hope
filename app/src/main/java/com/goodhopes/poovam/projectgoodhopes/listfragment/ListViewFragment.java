package com.goodhopes.poovam.projectgoodhopes.listfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodhopes.poovam.projectgoodhopes.common.ListItemSpacingDecoration;
import com.goodhopes.poovam.projectgoodhopes.common.ListViewAdapter;
import com.goodhopes.poovam.projectgoodhopes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by poovam on 3/12/16.
 * Fragment containing the listview
 */

public class ListViewFragment extends Fragment {

    @BindView(R.id.list) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.list_view_fragment, container, false);
        ButterKnife.bind(this, listView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder
                            viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        ArrayList<String> a = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            a.add("Poovammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm " + i);
        }
        recyclerView.addItemDecoration(new ListItemSpacingDecoration(10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.Adapter mAdapter = new ListViewAdapter(a);
        recyclerView.setAdapter(mAdapter);

        return listView;
    }

}
