package com.goodhopes.poovam.projectgoodhopes.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.home.HomeFragment;
import com.goodhopes.poovam.projectgoodhopes.interfaces.ResponseHandler;
import com.goodhopes.poovam.projectgoodhopes.reader.ReaderActivity;
import com.goodhopes.poovam.projectgoodhopes.reader.cardview.CardViewFragment;
import com.goodhopes.poovam.projectgoodhopes.reader.listview.ListViewFragment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by poovam on 12/12/16.
 * used as an utility class
 */

public class Utilities {

    public void refreshHomePage(AppCompatActivity activity,boolean commitNow){
        BaseApplicationClass base =(BaseApplicationClass) activity.getApplicationContext();
        ListViewFragment listViewFragment = new ListViewFragment();
        CardViewFragment cardViewFragment = new CardViewFragment();
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("enum",base.settingsInfo.subscribedToo);
        listViewFragment.setArguments(bundle);
        cardViewFragment.setArguments(bundle);
        if(base.settingsInfo.subscribedToo!=null) {
            if (base.settingsInfo.viewSetting == CurrentView.CARDVIEW) {
                FragmentTransaction card = activity.getSupportFragmentManager().
                        beginTransaction().replace(R.id.frame_container,
                        cardViewFragment,"HOME_FRAGMENT");
                if(commitNow){
                    card.commit();
                    sendRequest(base.settingsInfo.subscribedToo,listViewFragment,cardViewFragment,
                            activity,base);
                }
            } else {
                FragmentTransaction list= activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container,
                        listViewFragment,"HOME_FRAGMENT");
                if(commitNow){
                    list.commit();
                    sendRequest(base.settingsInfo.subscribedToo,listViewFragment,cardViewFragment,
                            activity, base);
                }
            }
        } else {
            FragmentTransaction home = activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container,
                    homeFragment,"HOME_FRAGMENT");
            if(commitNow){
                home.commit();
            }
        }
    }

    String timeAgoConversion(Timestamp pubDate){
        long diff = Calendar.getInstance().getTimeInMillis() - pubDate.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String time = null;
        if (diffDays > 0) {
            if (diffDays == 1) {
                time = diffDays + " day ago ";
            } else {
                time = diffDays + " days ago ";
            }
        } else {
            if (diffHours > 0) {
                if (diffHours == 1) {
                    time = diffHours + " hr ago";
                } else {
                    time = diffHours + " hrs ago";
                }
            } else {
                if (diffMinutes > 0) {
                    if (diffMinutes == 1) {
                        time = diffMinutes + " min ago";
                    } else {
                        time = diffMinutes + " mins ago";
                    }
                } else {
                    if (diffSeconds > 0) {
                        time = diffSeconds + " secs ago";
                    }
                }

            }

        }

        return time;
    }

    private void sendRequest(final Subscription selection, final ListViewFragment listViewFragment,
                             final CardViewFragment cardViewFragment, final Activity activity,
                             final BaseApplicationClass base){

        new NetworkConnection(activity).getRSS(selection.URL, new ResponseHandler() {
            @Override
            public void parse(String response) {
                ArrayList<Entry> entries;
                entries = selection.getParser(response,activity);
                listViewFragment.entries = entries;
                cardViewFragment.entries = entries;
                if(base.settingsInfo.viewSetting == CurrentView.CARDVIEW){
                    cardViewFragment.notifyDatasetChanged();
                }else {
                    listViewFragment.notifyDatasetChanged();
                }
            }

            @Override
            public void error(String error) {
                Toast.makeText(activity,error,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
