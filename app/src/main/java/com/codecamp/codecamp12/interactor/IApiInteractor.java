package com.codecamp.codecamp12.interactor;

import com.codecamp.codecamp12.domain.model.Book;

import java.util.List;

import rx.Observable;

/**
 * Interface describing api operations
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/25/2016.
 */
public interface IApiInteractor {

    public Observable<List<Book>> getBooks();

    public Observable<Void> addBook(Book book);
}
