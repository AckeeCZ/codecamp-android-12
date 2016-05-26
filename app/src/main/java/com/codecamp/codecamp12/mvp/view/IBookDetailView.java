package com.codecamp.codecamp12.mvp.view;

import com.codecamp.codecamp12.domain.model.Book;

/**
 * View of book detail screen
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public interface IBookDetailView {
    public static final String TAG = IBookDetailView.class.getName();

    public void showBook(Book book);
}
