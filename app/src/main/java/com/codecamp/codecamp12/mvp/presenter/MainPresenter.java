package com.codecamp.codecamp12.mvp.presenter;

import android.os.Bundle;
import android.util.Log;

import com.codecamp.codecamp12.App;
import com.codecamp.codecamp12.db.dao.BookDao;
import com.codecamp.codecamp12.interactor.IApiInteractor;
import com.codecamp.codecamp12.mvp.view.IMainView;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/25/2016.
 */
public class MainPresenter extends RxPresenter<IMainView> {
    public static final String TAG = MainPresenter.class.getName();

    @Inject
    IApiInteractor apiInteractor;

    @Inject
    BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        App.getAppComponent().inject(this);
        Log.d(TAG, "onCreate: ");

        downloadBooksFromApi();
    }

    private void downloadBooksFromApi() {
        add(apiInteractor.getBooks()
                .subscribeOn(Schedulers.newThread())
                .flatMap(books -> bookDao.updateData(books)
                        .map(bool -> books))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> Log.d(TAG, "Books successfully loaded and stored"), Throwable::printStackTrace));
    }
}
