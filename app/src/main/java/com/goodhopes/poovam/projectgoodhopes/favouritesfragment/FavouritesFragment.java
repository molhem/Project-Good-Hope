package com.goodhopes.poovam.projectgoodhopes.favouritesfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.ListItemSpacingDecoration;
import com.goodhopes.poovam.projectgoodhopes.common.ListViewAdapter;
import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by poovam on 3/12/16.
 * favourites fragment
 */

public class FavouritesFragment extends Fragment {
    @BindView(R.id.list) RecyclerView recyclerView;
    @BindView(R.id.empty_image) ImageView emptyImage;
    @BindView(R.id.empty_message)
    TextView emptyMessage;
    @OnClick(R.id.fab)
    public void onClick(){
        recyclerView.smoothScrollToPosition(0);
    }
    ArrayList<Entry> favourites = new ArrayList<>();
    boolean deleteIt;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getContext().getString(R.string.saved_data), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String savedFavs = sharedPref.getString(getContext().getString(R.string.favourites),"");

        Type type = new TypeToken<List<Entry>>() {
        }.getType();
        favourites = gson.fromJson(savedFavs, type);
        if(favourites == null){
            favourites = new ArrayList<>();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.list_view_fragment,container,false);
        ButterKnife.bind(this, root);
        if(favourites.size()>0){
            emptyMessage.setVisibility(View.GONE);
            emptyImage.setVisibility(View.GONE);
        }else {
            emptyMessage.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
        }
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
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        final Entry deleted = favourites.get(viewHolder.getAdapterPosition());
                        final int deletedPosition = viewHolder.getAdapterPosition();
                        favourites.remove(viewHolder.getAdapterPosition());
                        recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
                        Gson gson = new Gson();
                        SharedPreferences sharedPref = getContext().getSharedPreferences(
                                getContext().getString(R.string.saved_data), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        String savedFavs = gson.toJson(favourites);
                        editor.putString(getContext().getString(R.string.favourites),savedFavs);
                        editor.apply();
                        Snackbar.make(root,"The article has been removed from favourites",
                                Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteIt = false;
                                favourites.add(deletedPosition,deleted);
                                recyclerView.getAdapter().notifyItemInserted(deletedPosition);
                                Gson gson = new Gson();
                                SharedPreferences sharedPref = getContext().getSharedPreferences(
                                        getContext().getString(R.string.saved_data), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                String savedFavs = gson.toJson(favourites);
                                editor.putString(getContext().getString(R.string.favourites),savedFavs);
                                editor.apply();
                                if(favourites.size() > 0){
                                    emptyMessage.setVisibility(View.GONE);
                                    emptyImage.setVisibility(View.GONE);
                                }
                            }
                        }).show();
                        if(favourites.size() == 0){
                            emptyMessage.setVisibility(View.VISIBLE);
                            emptyImage.setVisibility(View.VISIBLE);
                        }
                    }
                };


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        recyclerView.addItemDecoration(new ListItemSpacingDecoration(12, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.Adapter mAdapter = new ListViewAdapter(favourites,getActivity(),false);
        recyclerView.setAdapter(mAdapter);

        return root;
    }
}
