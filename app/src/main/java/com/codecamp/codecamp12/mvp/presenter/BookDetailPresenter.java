package com.codecamp.codecamp12.mvp.presenter;

import android.os.Bundle;
import android.os.Parcelable;

import com.codecamp.codecamp12.App;
import com.codecamp.codecamp12.db.dao.FeatureDao;
import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.mvp.view.IBookDetailView;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter for book detail screen
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public class BookDetailPresenter extends RxPresenter<IBookDetailView> {
    public static final String TAG = BookDetailPresenter.class.getName();
    private static final String BOOK_KEY = "book";
    private Book book;
    @Inject
    FeatureDao mFeatureDao;

    public static Bundle getArguments(Book book) {
        Bundle args = new Bundle();
        args.putParcelable(BOOK_KEY, book);
        return args;
    }


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        App.getAppComponent().inject(this);
    }

    public void onActivityCreated(Bundle arguments) {
        this.book = arguments.getParcelable(BOOK_KEY);
    }

    @Override
    protected void onTakeView(IBookDetailView iBookDetailView) {
        super.onTakeView(iBookDetailView);
        updateView();
    }

    private void updateView() {
        if(getView() == null) {
            return;
        }
        getView().showBook(book);
    }

    public void onFabClicked() {
        mFeatureDao.setFeatured(book.getId(), !book.isFeatured())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> {
                    book.setFeatured(!book.isFeatured());
                    updateView();
                })
        ;
    }
}
