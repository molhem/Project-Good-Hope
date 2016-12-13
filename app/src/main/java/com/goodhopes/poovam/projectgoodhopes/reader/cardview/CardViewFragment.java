package com.goodhopes.poovam.projectgoodhopes.reader.cardview;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.common.SwipeStackAdapter;

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
    @BindView(R.id.swipeStack) public SwipeStack swipeStack;
    SwipeStackAdapter adapter;
    public ArrayList<Entry> entries = new ArrayList<>();
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Subscription subscription =(Subscription) getArguments().getSerializable("enum");
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getActivity()
                        .getString(R.string.saved_data),
                Context.MODE_PRIVATE);
        String result = sharedPref.getString(getString(subscription.stringID),"-");
        if(!result.equals("-")){
            entries = subscription.getParser(result,getContext());
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
    public void notifyDatasetChanged(){
        adapter = new SwipeStackAdapter(entries);
        swipeStack.setAdapter(adapter);
        Toast.makeText(context,"News is updated",Toast.LENGTH_SHORT).show();
    }
}
