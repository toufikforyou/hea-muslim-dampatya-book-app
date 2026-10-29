package com.shahrinsiddeka.heamoslimdampatya;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(new ReadingPrefs(this).getNightMode());
    }
}
