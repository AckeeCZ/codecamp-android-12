package com.codecamp.codecamp12.di;

import com.codecamp.codecamp12.interactor.MockApiInteractor;
import com.codecamp.codecamp12.mvp.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Main application component
 * Created by David Bilik[david.bilik@ackee.cz] on {23. 6. 2015}
 **/
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainPresenter mainPresenter);

    void inject(MockApiInteractor mockApiInteractor);
}
