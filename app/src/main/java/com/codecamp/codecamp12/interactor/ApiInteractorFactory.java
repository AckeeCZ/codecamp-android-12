package com.codecamp.codecamp12.interactor;

import com.codecamp.codecamp12.BuildConfig;
import com.codecamp.codecamp12.domain.rest.ApiDescription;

/**
 * Factory for creating ApiInteractor instance
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public class ApiInteractorFactory {
    public static final String TAG = ApiInteractorFactory.class.getName();

    private ApiInteractorFactory() {
    }

    public static IApiInteractor provideApiInteractor(ApiDescription apiDescription) {
        if(BuildConfig.FLAVOR.equals("mockApi")) {
            return new MockApiInteractor();
        }
        return new ApiInteractor(apiDescription);
    }

}
