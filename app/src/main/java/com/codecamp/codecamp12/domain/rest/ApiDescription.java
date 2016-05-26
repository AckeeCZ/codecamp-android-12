package com.codecamp.codecamp12.domain.rest;

import com.codecamp.codecamp12.domain.model.Book;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * REST api description for Retrofit
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/25/2016.
 */
public interface ApiDescription {

    String ENDPOINT_URL = "http://private-c5e89-codecamp1.apiary-mock.com/";

    @GET("/books")
    Observable<List<Book>> getBooks();

    @POST("/books")
    Observable<Void> addBook(@Body Book book);
}
