package com.goodhopes.poovam.projectgoodhopes.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by poovam on 11/12/16.
 * class that maintain
 */

public class BaseApplicationClass extends Application {
    public SettingsInfo settingsInfo;
    @Override
    public void onCreate() {
        super.onCreate();
        getSettings();
    }
    public void getSettings(){
        SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.saved_data), Context.MODE_PRIVATE);
        String settings = sharedPref.getString(this.getString(R.string.settings),"");
        Gson gson = new Gson();
        settingsInfo = gson.fromJson(settings,SettingsInfo.class);
        if(settingsInfo == null){
            settingsInfo = new SettingsInfo();
        }
    }
    public void updateSettings(){
        SharedPreferences sharedPref = this.getSharedPreferences(
                this.getString(R.string.saved_data), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String settings = gson.toJson(settingsInfo);
        editor.putString(this.getString(R.string.settings),settings);
        editor.apply();
    }
}
