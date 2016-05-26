package com.codecamp.codecamp12.mvp.view;

import com.codecamp.codecamp12.domain.model.Book;

/**
 * View of searh screen
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public interface ISearchView extends IListView<Book> {
    public static final String TAG = ISearchView.class.getName();

    void showEmpty(boolean show);

    void showProgress(boolean show);
}
