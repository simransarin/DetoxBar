package com.innprojects.smartbar.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.innprojects.smartbar.R;

public class MainActivityMachine extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 4000;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_mainmachine);

        /** If splash is to be displayed only once when app is installed */
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!mPreferences.contains("usernamemachine")) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(MainActivityMachine.this, LoginActivityMachine.class);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

        } else {
            Intent loginIntent = new Intent(MainActivityMachine.this, BluetoothActivity.class);
            startActivity(loginIntent);
            finish();
        }

    }
}