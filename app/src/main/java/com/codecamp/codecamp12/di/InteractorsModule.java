package com.codecamp.codecamp12.di;


import com.codecamp.codecamp12.domain.rest.ApiDescription;
import com.codecamp.codecamp12.interactor.ApiInteractorFactory;
import com.codecamp.codecamp12.interactor.IApiInteractor;
import com.codecamp.codecamp12.interactor.MockApiInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module that handles injecting Interactors
 * Created by David Bilik[david.bilik@ackee.cz] on {2. 4. 2015}
 */
@Module(
        includes = {ServiceModule.class}
)
public class InteractorsModule {
    public static final String TAG = InteractorsModule.class.getName();

    @Provides
    @Singleton
    public IApiInteractor provideApiInteractor(ApiDescription apiDescription) {
        return ApiInteractorFactory.provideApiInteractor(apiDescription);
    }
}
