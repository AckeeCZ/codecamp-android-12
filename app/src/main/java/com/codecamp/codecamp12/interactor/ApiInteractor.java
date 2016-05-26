package com.codecamp.codecamp12.interactor;

import com.codecamp.codecamp12.domain.rest.ApiDescription;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/25/2016.
 */
public class ApiInteractor implements IApiInteractor {
    public static final String TAG = ApiInteractor.class.getName();
    private final ApiDescription apiDescription;

    public ApiInteractor(ApiDescription apiDescription) {
        this.apiDescription = apiDescription;
    }
}
