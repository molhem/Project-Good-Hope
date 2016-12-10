package com.goodhopes.poovam.projectgoodhopes.cardfragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
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
import link.fls.swipestack.SwipeStack;

/**
 * Created by poovam on 3/12/16.
 * fragment holding the card view
 * the first module of the app
 */

public class CardViewFragment extends Fragment {
    @BindView(R.id.swipeStack) SwipeStack swipeStack;
    ArrayList<Entry> entries = new ArrayList<>();

    SwipeStackAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(final Subscription subscription: Subscription.values()){
            NetworkConnection.getInstance(getActivity().getApplicationContext()).getRSS(subscription.URL,
                            new ResponseHandler() {
                                @Override
                                public void parse(String response) {
                                    entries.addAll(subscription.getParser(response));
                                    adapter.notifyDataSetChanged();
                                    Log.d("Enters",subscription.name);
                                }});
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cardView = inflater.inflate(R.layout.card_view_fragment,container,false);
        ButterKnife.bind(this,cardView);
        adapter =  new SwipeStackAdapter(entries);
        swipeStack.setAdapter(adapter);
        return cardView;
    }
}
