package com.sky.techtest;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by leslied on 11/03/2018.
 */

public class MovieApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
