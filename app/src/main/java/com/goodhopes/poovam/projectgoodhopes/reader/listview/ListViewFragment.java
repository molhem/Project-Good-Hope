package com.goodhopes.poovam.projectgoodhopes.reader.listview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.ListItemSpacingDecoration;
import com.goodhopes.poovam.projectgoodhopes.common.ListViewAdapter;
import com.goodhopes.poovam.projectgoodhopes.common.NetworkConnection;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.interfaces.ResponseHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class ListViewFragment extends Fragment {

    @BindView(R.id.list) public RecyclerView recyclerView;
    @BindView(R.id.fab) public FloatingActionButton floatingActionButton;
    @BindView(R.id.empty_image) ImageView emptyImage;
    @BindView(R.id.empty_message)
    TextView emptyMessage;
    RecyclerView.Adapter mAdapter;
    @OnClick(R.id.fab)
    public void onClick(){
        recyclerView.scrollToPosition(0);
    }
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View listView = inflater.inflate(R.layout.list_view_fragment, container, false);
        ButterKnife.bind(this, listView);
        showMessageIfEmpty();
        //FAB sometimes appear at the top left corner
        floatingActionButton.hide();
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
                        if(recyclerView.getAdapter().getItemCount()== 0){
                            emptyMessage.setVisibility(View.GONE);
                        }
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new ListItemSpacingDecoration(12, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ListViewAdapter(entries,getActivity(),true);
        recyclerView.setAdapter(mAdapter);

        return listView;
    }
    public void notifyDatasetChanged(){
        mAdapter = new ListViewAdapter(entries,getActivity(),true);
        recyclerView.setAdapter(mAdapter);
        showMessageIfEmpty();
    }
    private void showMessageIfEmpty(){
        if(entries.size()>0){
            emptyMessage.setVisibility(View.GONE);
            emptyImage.setVisibility(View.GONE);
        }else {
            emptyMessage.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
        }
    }
}

