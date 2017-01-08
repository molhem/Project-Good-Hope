package com.goodhopes.poovam.projectgoodhopes.shelffragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.ListViewAdapter;
import com.goodhopes.poovam.projectgoodhopes.reader.ReaderActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by poovam on 3/12/16.
 *
 */

public class ShelfFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @BindView(R.id.shelf) RecyclerView shelf;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_shelf,container,false);
        setHasOptionsMenu(false);
        ButterKnife.bind(this, root);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2);
        shelf.setLayoutManager(mLayoutManager);
        shelf.addItemDecoration(new GridSpacingItemDecorator(2, dpToPx(10), true));
        shelf.setAdapter(new ShelfAdapter(getActivity()));
        return root;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
