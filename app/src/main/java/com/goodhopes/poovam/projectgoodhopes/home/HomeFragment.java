package com.goodhopes.poovam.projectgoodhopes.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.shelffragment.GridSpacingItemDecorator;
import com.goodhopes.poovam.projectgoodhopes.shelffragment.ShelfAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by poovam on 11/12/16.
 * HOME PAGE |-| () /\/\ E
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.above_recycler) RecyclerView aboveRecycler;
    @BindView(R.id.below_recycler) RecyclerView belowRecycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment,container,false);
        ButterKnife.bind(this,root);
        firstTimeEnteringApp();
        return root;
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    void firstTimeEnteringApp(){
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> name1 = new ArrayList<>();
        ArrayList<String> name2 = new ArrayList<>();
        ArrayList<Integer> icon = new ArrayList<>();
        ArrayList<Integer> icon1 = new ArrayList<>();
        ArrayList<Integer> icon2 = new ArrayList<>();
        for(Subscription subscription: Subscription.values()){
            name.add(subscription.name);
            icon.add(subscription.iconID);
        }
        for(int i=0;i<5;i++){
            name1.add(name.get(i));
            icon1.add(icon.get(i));
        }
        for(int i=5;i<10;i++){
            name2.add(name.get(i));
            icon2.add(icon.get(i));
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),5);
        aboveRecycler.setLayoutManager(mLayoutManager);
        aboveRecycler.addItemDecoration(new GridSpacingItemDecorator(5, dpToPx(10), true));
        aboveRecycler.setAdapter(new HomeAdapter(name1,icon1,true));
        belowRecycler.setLayoutManager( new GridLayoutManager(getActivity(),5));
        belowRecycler.addItemDecoration(new GridSpacingItemDecorator(5, dpToPx(10), true));
        belowRecycler.setAdapter(new HomeAdapter(name2,icon2,true));
    }

}
