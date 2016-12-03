package com.goodhopes.poovam.projectgoodhopes.cardfragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goodhopes.poovam.projectgoodhopes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.fls.swipestack.SwipeStack;

/**
 * Created by poovam on 3/12/16.
 * fragment holding the card view
 * the first module of the app
 */

public class CardViewFragment extends Fragment {
    @BindView(R.id.swipeStack) SwipeStack swipeStack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cardView = inflater.inflate(R.layout.card_view_fragment,container,false);
        ButterKnife.bind(this,cardView);
        ArrayList<String> a = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            a.add("Poovammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm " + i);
        }
        swipeStack.setAdapter(new SwipeStackAdapter(a));

        return cardView;
    }
}
