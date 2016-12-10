package com.goodhopes.poovam.projectgoodhopes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.goodhopes.poovam.projectgoodhopes.aboutus.AboutUsFragment;
import com.goodhopes.poovam.projectgoodhopes.cardfragment.CardViewFragment;
import com.goodhopes.poovam.projectgoodhopes.common.CurrentView;
import com.goodhopes.poovam.projectgoodhopes.favouritesfragment.FavouritesFragment;
import com.goodhopes.poovam.projectgoodhopes.listfragment.ListViewFragment;
import com.goodhopes.poovam.projectgoodhopes.shelffragment.ShelfFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.frame_container)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    Fragment cardViewFragment;
    Fragment listViewFragment;
    Fragment favouritesFragment;
    Fragment shelfFragment;
    Fragment aboutUsFragment;
    Dialog settingsDialog;
    CurrentView currentView = CurrentView.LISTVIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return onMenuIconClicked(item);
                    }
                });
        cardViewFragment = new CardViewFragment();
        listViewFragment = new ListViewFragment();
        favouritesFragment = new FavouritesFragment();
        shelfFragment = new ShelfFragment();
        aboutUsFragment = new AboutUsFragment();
        settingsDialog = createDialog();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                cardViewFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    public boolean onMenuIconClicked(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.list_view_icon:
                switchViews(item);
                return true;
            case R.id.shelf_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        shelfFragment).commit();
                return true;
            case R.id.favourite_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        favouritesFragment).commit();
                return true;
            case R.id.settings_icon:
                settingsDialog.show();
                return false;
            case R.id.about_us_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        aboutUsFragment).commit();
                return true;
            default:
                return false;
        }
    }

    public void switchViews(MenuItem item){
        if(currentView == CurrentView.LISTVIEW){
            currentView = CurrentView.CARDVIEW;
            item.setIcon(R.drawable.ic_list_white_24dp);
            item.setTitle("List View");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    cardViewFragment).commit();
        }else {
            currentView = CurrentView.LISTVIEW;
            item.setIcon(R.drawable.ic_view_carousel_white_24dp);
            item.setTitle("Card View");
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    listViewFragment).commit();
        }
    }

    public Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Settings");
        builder.setView(getLayoutInflater().inflate(R.layout.about_us_framgent, null))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

}
