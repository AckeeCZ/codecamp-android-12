package com.codecamp.codecamp12.mvp.presenter;

import android.os.Bundle;

import com.codecamp.codecamp12.App;
import com.codecamp.codecamp12.db.dao.BookDao;
import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.mvp.view.ISearchView;

import java.util.List;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter for searching in books by title
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public class SearchPresenter extends RxPresenter<ISearchView> {
    public static final String TAG = SearchPresenter.class.getName();
    @Inject
    BookDao mBookDao;
    private List<Book> books;
    private boolean searching;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        App.getAppComponent().inject(this);
    }

    public void onSearchQuery(String query) {
        getView().showProgress(true);
        searching = true;
        mBookDao.searchBooks(query)
                .subscribeOn(Schedulers.newThread())
                .first()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSearchFinished, err -> err.printStackTrace());
    }

    private void onSearchFinished(List<Book> books) {
        this.books = books;
        searching = false;
        updateView();
    }

    @Override
    protected void onTakeView(ISearchView iSearchView) {
        super.onTakeView(iSearchView);
        updateView();
    }

    private void updateView() {
        if (getView() == null) {
            return;
        }
        getView().showData(books);
        getView().showEmpty(books == null || books.isEmpty());
        getView().showProgress(searching);
    }
}
