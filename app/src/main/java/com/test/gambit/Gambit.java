package com.test.gambit;

import android.app.Application;

import com.test.gambit.utils.PreferenceManager;

public class Gambit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(this);
    }
}
