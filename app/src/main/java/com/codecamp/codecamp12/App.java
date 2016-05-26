package com.codecamp.codecamp12;

import android.app.Application;

import com.codecamp.codecamp12.di.AppComponent;
import com.codecamp.codecamp12.di.AppModule;
import com.codecamp.codecamp12.di.DaggerAppComponent;
import com.facebook.stetho.Stetho;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/25/2016.
 */
public class App extends Application {
    public static final String TAG = App.class.getName();

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Dagger
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        // Initialize Stetho
        Stetho.initializeWithDefaults(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
