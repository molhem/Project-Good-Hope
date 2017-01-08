package com.goodhopes.poovam.projectgoodhopes.common;


/**
 * Created by poovam on 11/12/16.
 * Settings class to maintain user settings
 */

public class SettingsInfo {
    public enum StartPage{
        HOME,SHELF;
    }

    public StartPage startUpSetting = StartPage.SHELF;
    public CurrentView viewSetting = CurrentView.LISTVIEW;
    public Subscription subscribedToo;

    public void setStartUpSetting(StartPage startUpSetting) {
        this.startUpSetting = startUpSetting;
    }

    public void setViewSetting(CurrentView viewSetting) {
        this.viewSetting = viewSetting;
    }

    public void setHomePage(Subscription subscribeToo){
        subscribedToo = subscribeToo;
    }
}
