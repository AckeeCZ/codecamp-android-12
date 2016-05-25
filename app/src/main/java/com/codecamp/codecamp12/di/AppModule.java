package com.codecamp.codecamp12.di;

import android.app.Application;
import android.view.LayoutInflater;

import com.codecamp.codecamp12.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module that handle injecting application class (ie Context)
 * Created by David Bilik[david.bilik@ackee.cz] on {2. 4. 2015}
 */
@Module(
        includes = {
                InteractorsModule.class
        }
)
public class AppModule {
    public static final String TAG = AppModule.class.getName();
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(app);
    }
}
