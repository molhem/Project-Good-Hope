package com.goodhopes.poovam.projectgoodhopes.settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.goodhopes.poovam.projectgoodhopes.R;
import com.goodhopes.poovam.projectgoodhopes.common.BaseApplicationClass;
import com.goodhopes.poovam.projectgoodhopes.common.CurrentView;
import com.goodhopes.poovam.projectgoodhopes.common.SettingsInfo;
import com.goodhopes.poovam.projectgoodhopes.common.Subscription;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by poovam on 12/12/16.
 * dialog to show settings
 */

public class SettingsDialog extends Dialog  {

    @BindView(R.id.view_pref) RadioGroup viewPreference;
    @BindView(R.id.start_page_pref) RadioGroup startPagePreference;
    @BindView(R.id.subscription_spinner) Spinner subscriptionSpinner;
    @BindView(R.id.btn_yes) Button apply;
    @BindView(R.id.btn_no) Button cancel;
    @OnClick(R.id.btn_yes)
    void onAcceptClick(){
        base.settingsInfo.setViewSetting(getCurrentView());
        base.settingsInfo.setHomePage(getHomePage());
        base.settingsInfo.setStartUpSetting(getStartPage());
        base.updateSettings();
        dismiss();
    }
    @OnClick(R.id.btn_no)
    void onCancelClick(){
        dismiss();
    }
    private Activity activity;
    private BaseApplicationClass base;
    public SettingsDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        base =(BaseApplicationClass) getContext().getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_dialog);
        ButterKnife.bind(this);
        setSubscriptionSpinner();
        setViewPreference();
        setStartPagePreference();
    }

    private CurrentView getCurrentView(){
        int viewPrefId = viewPreference.getCheckedRadioButtonId();
        CurrentView currentView;
        if(viewPrefId==R.id.list){
            currentView = CurrentView.LISTVIEW;
        }else {
            currentView = CurrentView.CARDVIEW;
        }
        return currentView;
    }

    private SettingsInfo.StartPage getStartPage(){
        int startPagePrefId = startPagePreference.getCheckedRadioButtonId();
        SettingsInfo.StartPage startPage ;
        if(startPagePrefId == R.id.home){
            startPage = SettingsInfo.StartPage.HOME;
        }else {
            startPage = SettingsInfo.StartPage.SHELF;
        }
        return startPage;
    }

    private Subscription getHomePage() {
        Subscription s = null;
        for (Subscription subscription : Subscription.values()) {
            if (subscription.name.equals(subscriptionSpinner.getSelectedItem().toString())) {
                s = subscription;
                break;
            }
        }
        return s;
    }

    private void setViewPreference(){
        if(base.settingsInfo.startUpSetting == SettingsInfo.StartPage.HOME){
            startPagePreference.check(R.id.home);
        }
        else{
            startPagePreference.check(R.id.shelf);
        }
    }

    private void setStartPagePreference(){
        if(base.settingsInfo.viewSetting == CurrentView.LISTVIEW) {
            viewPreference.check(R.id.list);
        }else {
            viewPreference.check(R.id.card);
        }
    }

    private void setSubscriptionSpinner(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Select One");
        for(Subscription subscription:Subscription.values()){
            arrayList.add(subscription.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity.getBaseContext(),
                R.layout.spinner_item, arrayList);
        subscriptionSpinner.setAdapter(adapter);
    }
}
