package com.codecamp.codecamp12.mvp.presenter;

import android.os.Bundle;

import com.codecamp.codecamp12.App;
import com.codecamp.codecamp12.db.dao.BookDao;
import com.codecamp.codecamp12.db.dao.FeatureDao;
import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.mvp.view.IBooksView;

import java.util.List;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/26/2016.
 */
public class BooksPresenter extends RxPresenter<IBooksView> {
    public static final String TAG = BooksPresenter.class.getName();

    @Inject
    BookDao bookDao;
    @Inject
    FeatureDao featureDao;

    private List<Book> books;

    private boolean showProgress = true;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onTakeView(IBooksView bookIListView) {
        super.onTakeView(bookIListView);
        updateView();
    }

    public void loadBooks(FeedType type) {
        switch (type) {
            case ALL:
                add(bookDao.getAllBooks()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onBooksLoaded, this::onLoadFailed));
                break;

            case FEATURED:
                add(bookDao.getFeaturedBooks()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onBooksLoaded, this::onLoadFailed));
                break;
        }
    }

    private void onBooksLoaded(List<Book> books) {
        this.books = books;
        showProgress = false;
        updateView();
    }

    private void onLoadFailed(Throwable throwable) {
        throwable.printStackTrace();
        showProgress = false;
        updateView();
    }

    private void updateView() {
        if (getView() == null) {
            return;
        }

        if (books != null) {
            getView().showData(books);
        }
        getView().showProgress(showProgress);
        getView().showEmpty(books == null || books.isEmpty());
    }

    public void setFeatured(Book book, Boolean isChecked) {
        featureDao.setFeatured(book.getId(), isChecked)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    public enum FeedType {
        ALL, FEATURED
    }
}
