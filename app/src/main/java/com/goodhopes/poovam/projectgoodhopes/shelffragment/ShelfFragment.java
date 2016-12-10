package com.goodhopes.poovam.projectgoodhopes.shelffragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.ListViewAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by poovam on 3/12/16.
 *
 */

public class ShelfFragment extends Fragment {
    @Nullable
    @BindView(R.id.shelf) RecyclerView shelf;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_shelf,container,false);
        ButterKnife.bind(this, root);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        shelf.setLayoutManager(mLayoutManager);
        shelf.addItemDecoration(new GridSpacingItemDecorator(2, dpToPx(10), true));
        shelf.setAdapter(new ShelfAdapter());
        return root;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
