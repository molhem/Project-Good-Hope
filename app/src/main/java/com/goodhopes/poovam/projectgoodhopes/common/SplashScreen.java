package com.goodhopes.poovam.projectgoodhopes.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.goodhopes.poovam.projectgoodhopes.MainActivity;

/**
 * Created by poovam on 16/12/16.
 */

public class SplashScreen extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();


    }
}