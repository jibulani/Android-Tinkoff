package com.eugene.contractorsearch;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.eugene.contractorsearch.db.AppDatabase;

public class App extends Application {

    public static App instance;
    private AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "database").build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}

