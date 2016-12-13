package com.goodhopes.poovam.projectgoodhopes.reader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.BaseApplicationClass;
import com.goodhopes.poovam.projectgoodhopes.common.CurrentView;
import com.goodhopes.poovam.projectgoodhopes.common.Entry;
import com.goodhopes.poovam.projectgoodhopes.common.NetworkConnection;
import com.goodhopes.poovam.projectgoodhopes.common.SettingsInfo;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.interfaces.ResponseHandler;
import com.goodhopes.poovam.projectgoodhopes.reader.cardview.CardViewFragment;
import com.goodhopes.poovam.projectgoodhopes.reader.listview.ListViewFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by poovam on 11/12/16.
 * The time now is 3:23 AM
 */

public class ReaderActivity extends AppCompatActivity {
    @BindView(R.id.frame_container)
    FrameLayout frameLayout;
    @BindView(R.id.parent)
    LinearLayout parent;
    CardViewFragment cardViewFragment;
    ListViewFragment listViewFragment;
    public ArrayList<Entry> entries = new ArrayList<>();
    CurrentView currentView;
    Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_layout);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subscription =(Subscription) getIntent().getSerializableExtra("selection");
        listViewFragment = new ListViewFragment();
        cardViewFragment = new CardViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("enum",subscription);
        getSupportActionBar().setTitle(subscription.name);
        listViewFragment.setArguments(bundle);
        cardViewFragment.setArguments(bundle);

        BaseApplicationClass base = (BaseApplicationClass) getApplicationContext();
        currentView = base.settingsInfo.viewSetting;
        if(currentView == CurrentView.CARDVIEW){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    listViewFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    cardViewFragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    cardViewFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    listViewFragment).commit();
        }
        sendRequest(subscription);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        MenuItem item = menu.findItem(R.id.view_icon);
        if(currentView == CurrentView.CARDVIEW){
            item.setIcon(R.drawable.ic_list_black_24dp);
            item.setTitle("List View");
        }else {
            item.setIcon(R.drawable.ic_view_carousel_black_24dp);
            item.setTitle("Card View");
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.view_icon:
                switchViews(item);
                break;
            case R.id.refresh_icon:
                sendRequest(subscription);
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendRequest(final Subscription selection){
        new NetworkConnection(this).getRSS(selection.URL, new ResponseHandler() {
            @Override
            public void parse(String response) {
                entries = selection.getParser(response,ReaderActivity.this);
                listViewFragment.entries = entries;
                cardViewFragment.entries = entries;
                listViewFragment.notifyDatasetChanged();
                cardViewFragment.notifyDatasetChanged();
            }

            @Override
            public void error(String error) {
                Toast.makeText(ReaderActivity.this,error,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void switchViews(MenuItem item){
        if(currentView == CurrentView.LISTVIEW){
            currentView = CurrentView.CARDVIEW;
            item.setIcon(R.drawable.ic_list_black_24dp);
            item.setTitle("List View");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    cardViewFragment).commit();
        }else {
            currentView = CurrentView.LISTVIEW;
            item.setIcon(R.drawable.ic_view_carousel_black_24dp);
            item.setTitle("Card View");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    listViewFragment).commit();
        }
    }
}
