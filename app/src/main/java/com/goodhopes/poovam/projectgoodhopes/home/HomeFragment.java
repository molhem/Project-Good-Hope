package com.goodhopes.poovam.projectgoodhopes.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.BaseApplicationClass;
import com.goodhopes.poovam.projectgoodhopes.common.CurrentView;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.NetworkConnection;
import com.goodhopes.poovam.projectgoodhopes.common.SettingsInfo;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.common.Utilities;
import com.goodhopes.poovam.projectgoodhopes.interfaces.ResponseHandler;
import com.goodhopes.poovam.projectgoodhopes.reader.ReaderActivity;
import com.goodhopes.poovam.projectgoodhopes.reader.cardview.CardViewFragment;
import com.goodhopes.poovam.projectgoodhopes.reader.listview.ListViewFragment;
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

    @BindView(R.id.recycler) RecyclerView recyclerView;


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
        setUpHomePage();
        return root;
    }
    private void setUpHomePage(){
        BaseApplicationClass base = (BaseApplicationClass) getActivity().getApplicationContext();
        if(base.settingsInfo.subscribedToo!=null){
           new Utilities().refreshHomePage((AppCompatActivity)getActivity(),true);
        }else {
            firstTimeEnteringApp();
        }
    }
    private void firstTimeEnteringApp(){
        ArrayList<String> name = new ArrayList<>();
        ArrayList<Integer> icon = new ArrayList<>();
        for(Subscription subscription: Subscription.values()){
            name.add(subscription.name);
            icon.add(subscription.iconID);
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecorator(3, dpToPx(10), true));
        recyclerView.setAdapter(new HomeAdapter(name,icon,true,getActivity()));
    }



    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
