package com.goodhopes.poovam.projectgoodhopes.favouritesfragment;

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
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by poovam on 3/12/16.
 * favourites fragment
 */

public class FavouritesFragment extends Fragment {
    @BindView(R.id.list) RecyclerView recyclerView;
    @OnClick(R.id.fab)
    public void onClick(){
        recyclerView.smoothScrollToPosition(0);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_view_fragment,container,false);
        ButterKnife.bind(this, root);

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
//        RecyclerView.Adapter mAdapter = new ListViewAdapter(a,a);
//        recyclerView.setAdapter(mAdapter);

        return root;
    }
}
