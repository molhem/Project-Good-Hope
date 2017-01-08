package com.goodhopes.poovam.projectgoodhopes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.goodhopes.poovam.projectgoodhopes.common.CurrentView;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.goodhopes.poovam.projectgoodhopes.reader.cardview.CardViewFragment;
import com.goodhopes.poovam.projectgoodhopes.reader.listview.ListViewFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.goodhopes.poovam.projectgoodhopes.aboutus.AboutUsFragment;
import com.goodhopes.poovam.projectgoodhopes.common.BaseApplicationClass;
import com.goodhopes.poovam.projectgoodhopes.common.SettingsInfo;
import com.goodhopes.poovam.projectgoodhopes.favouritesfragment.FavouritesFragment;
import com.goodhopes.poovam.projectgoodhopes.home.HomeFragment;
import com.goodhopes.poovam.projectgoodhopes.settings.SettingsDialog;
import com.goodhopes.poovam.projectgoodhopes.shelffragment.ShelfFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.frame_container)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation)
    BottomNavigationViewEx bottomNavigationView;
    Fragment favouritesFragment;
    Fragment shelfFragment;
    Fragment aboutUsFragment;
    Fragment homeFragment;
    Fragment listViewFragment;
    Fragment cardViewFragment;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BaseApplicationClass baseApplicationClass =(BaseApplicationClass) getApplicationContext();
        SettingsInfo.StartPage startPage = baseApplicationClass.settingsInfo.startUpSetting;
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return onMenuIconClicked(item);
                    }
                });
        favouritesFragment = new FavouritesFragment();
        shelfFragment = new ShelfFragment();
        aboutUsFragment = new AboutUsFragment();
        homeFragment = new HomeFragment();
        listViewFragment = new ListViewFragment();
        cardViewFragment = new CardViewFragment();
        setUpStartPage(startPage);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean onMenuIconClicked(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        homeFragment,"HOME_FRAGMENT").commit();
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
                SettingsDialog settingsDialog=new SettingsDialog(MainActivity.this);
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


    private void setUpStartPage(SettingsInfo.StartPage startPage){
        if(startPage == null){
            onMenuIconClicked(bottomNavigationView.getMenu().getItem(0));
        }
        else {
            if(startPage == SettingsInfo.StartPage.HOME){
                onMenuIconClicked(bottomNavigationView.getMenu().getItem(0));
            }else {
                onMenuIconClicked(bottomNavigationView.getMenu().getItem(1));
                bottomNavigationView.setCurrentItem(1);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
