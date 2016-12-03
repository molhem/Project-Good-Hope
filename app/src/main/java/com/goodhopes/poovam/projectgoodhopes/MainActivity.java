package com.goodhopes.poovam.projectgoodhopes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.goodhopes.poovam.projectgoodhopes.aboutus.AboutUsFragment;
import com.goodhopes.poovam.projectgoodhopes.cardfragment.CardViewFragment;
import com.goodhopes.poovam.projectgoodhopes.favouritesfragment.FavouritesFragment;
import com.goodhopes.poovam.projectgoodhopes.listfragment.ListViewFragment;
import com.goodhopes.poovam.projectgoodhopes.uploadfragment.UploadFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import link.fls.swipestack.SwipeStack;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.frame_container) FrameLayout frameLayout;
    @BindView(R.id.card_view_icon) ImageView cardViewIcon;
    @BindView(R.id.list_view_icon) ImageView listViewIcon;
    @BindView(R.id.favourite_icon) ImageView favouriteIcon;
    @BindView(R.id.upload_icon) ImageView uploadIcon;
    @BindView(R.id.settings_icon) ImageView settingsIcon;
    @BindView(R.id.about_us_icon) ImageView aboutUsIcon;
    Fragment cardViewFragment;
    Fragment listViewFragment;
    Fragment favouritesFragment;
    Fragment uploadFragment;
    Fragment aboutUsFragment;
    Dialog settingsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardViewFragment = new CardViewFragment();
        listViewFragment = new ListViewFragment();
        favouritesFragment = new FavouritesFragment();
        uploadFragment = new UploadFragment();
        aboutUsFragment = new AboutUsFragment();
        settingsDialog = createDialog();
        ButterKnife.bind(this);
        setIconSize();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                cardViewFragment).commit();
        cardViewIcon.setColorFilter(ContextCompat.getColor(this,R.color.theme_pink));
    }

    private void setIconSize()
    {
        Configuration configuration = getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp/2;
        cardViewIcon.getLayoutParams().width = screenWidthDp;
        listViewIcon.getLayoutParams().width = screenWidthDp;
        favouriteIcon.getLayoutParams().width = screenWidthDp;
        uploadIcon.getLayoutParams().width = screenWidthDp;
        settingsIcon.getLayoutParams().width = screenWidthDp;
        aboutUsIcon.getLayoutParams().width = screenWidthDp;
    }

    public void onMenuIconClicked(View view) {
        ImageView icon = (ImageView) view;
        switch (icon.getId()){
            case R.id.card_view_icon:
                setColorForIcon(icon);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        cardViewFragment).commit();
                break;
            case R.id.list_view_icon:
                setColorForIcon(icon);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        listViewFragment).commit();
                break;
            case R.id.favourite_icon:
                setColorForIcon(icon);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        favouritesFragment).commit();
                break;
            case R.id.upload_icon:
                setColorForIcon(icon);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        uploadFragment).commit();
                break;
            case R.id.settings_icon:
                settingsDialog.show();
                break;
            case R.id.about_us_icon:
                setColorForIcon(icon);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        aboutUsFragment).commit();
                break;
        }
    }
    private void setColorForIcon(ImageView selectedIcon)
    {
        cardViewIcon.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary));
        listViewIcon.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary));
        favouriteIcon.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary));
        uploadIcon.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary));
        aboutUsIcon.setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary));
        selectedIcon.setColorFilter(ContextCompat.getColor(this,R.color.theme_pink));
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
