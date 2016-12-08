package com.goodhopes.poovam.projectgoodhopes.listfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.ListItemSpacingDecoration;
import com.goodhopes.poovam.projectgoodhopes.common.ListViewAdapter;
import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.NetworkConnection;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.interfaces.ResponseHandler;
import com.goodhopes.poovam.projectgoodhopes.parsers.XMLParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by poovam on 3/12/16.
 * Fragment containing the listview
 */

public class ListViewFragment extends Fragment {

    @BindView(R.id.list) RecyclerView recyclerView;
    @OnClick(R.id.fab)
    public void onClick(){
        recyclerView.smoothScrollToPosition(0);
    }
    ArrayList<Entry> a = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

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
//        for(final Subscription subscription: Subscription.values()){
//
//        }
        NetworkConnection.getInstance(getActivity().getApplicationContext()).
                getRSS(Subscription.DINAMALAR.URL,
                        new ResponseHandler() {
                            @Override
                            public void parse(String response) {
                                a.addAll(Subscription.DINAMALAR.getParser(response));
                                recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new ListItemSpacingDecoration(10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.Adapter mAdapter = new ListViewAdapter(a);
        recyclerView.setAdapter(mAdapter);

        return listView;
    }

}

