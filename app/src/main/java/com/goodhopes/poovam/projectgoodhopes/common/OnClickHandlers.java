package com.goodhopes.poovam.projectgoodhopes.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.webview.WebBrowserView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by poovam on 14/12/16.
 */

public class OnClickHandlers {

    void onFavouritesClicked(View view, int position,ArrayList<Entry> entries){
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(0);
        anim.setDuration(700);

        SharedPreferences sharedPref = view.getContext().getSharedPreferences(
                view.getContext().getString(R.string.saved_data), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String savedFavs = sharedPref.getString(view.getContext().getString(R.string.favourites),"");
        ArrayList<Entry> favourites;
        Type type = new TypeToken<List<Entry>>() {
        }.getType();
        favourites = gson.fromJson(savedFavs, type);
        if(favourites == null){
            favourites = new ArrayList<>();
        }
        boolean alreadyPresent = false;
        for(Entry f:favourites){
            if(f.content.equals((entries.get(position).content))){
                alreadyPresent = true;
                break;
            }
        }
        if(alreadyPresent){
            Toast.makeText(view.getContext(), "Article has already been saved to favourites", Toast.LENGTH_SHORT)
                    .show();
        }else {
            view.startAnimation(anim);
            favourites.add(0,entries.get(position));
            savedFavs = gson.toJson(favourites);
            editor.putString(view.getContext().getString(R.string.favourites), savedFavs);
            editor.apply();
            Toast.makeText(view.getContext(), "Successfully saved to favourites", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void onShareIconClicked(View view,int position,ArrayList<Entry> entries){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
        String sAux = "\n"+entries.get(position).contentUrl+"\n\n";
        sAux = sAux + "Shared from Mithran - Tamil News App";
        i.putExtra(Intent.EXTRA_TEXT, sAux);
        view.getContext().startActivity(Intent.createChooser(i, "Choose one"));
    }

    public void webViewLoader(View view, int position, ArrayList<Entry> entries, Activity activity,
                       View parentView){
        Intent intent =new Intent(view.getContext(), WebBrowserView.class);
        intent.putExtra("contentURL",entries.get(position).contentUrl);
        intent.putExtra("title",entries.get(position).title);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity,parentView, "profile");
        view.getContext().startActivity(intent,options.toBundle());
    }
}
