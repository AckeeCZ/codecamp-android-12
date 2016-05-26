package com.codecamp.codecamp12.mvp.presenter;

import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.mvp.view.IListView;

import nucleus.presenter.RxPresenter;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/26/2016.
 */
public class BooksPresenter extends RxPresenter<IListView<Book>> {
    public static final String TAG = BooksPresenter.class.getName();
}
