package com.codecamp.codecamp12.mvp.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.codecamp.codecamp12.App;
import com.codecamp.codecamp12.R;
import com.codecamp.codecamp12.db.dao.BookDao;
import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.interactor.IApiInteractor;
import com.codecamp.codecamp12.mvp.view.IAddBookView;

import java.util.Arrays;
import java.util.Collections;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter for add book screen
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public class AddBookPresenter extends RxPresenter<IAddBookView> {
    public static final String TAG = AddBookPresenter.class.getName();

    @Inject
    IApiInteractor apiInteractor;
    @Inject
    BookDao bookDao;
    private boolean showProgress;
    private boolean bookSaved;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onTakeView(IAddBookView iAddBookView) {
        super.onTakeView(iAddBookView);
        updateView();
    }

    public void onSaveClicked(String title, String genre, String author) {
        getView().clearErrors();
        if (validate(title, genre, author)) {
            saveBook(new Book(title, genre, author));
        }
    }

    private boolean validate(String title, String genre, String author) {
        boolean success = true;
        if (TextUtils.isEmpty(title)) {
            getView().setErrorTitle(R.string.add_book_error_empty_title);
            success = false;
        }
        if (TextUtils.isEmpty(genre)) {
            getView().setErrorGenre(R.string.add_book_error_empty_genre);
            success = false;
        }
        if (TextUtils.isEmpty(author)) {
            getView().setErrorAuthor(R.string.add_book_error_empty_author);
            success = false;
        }
        return success;
    }

    private void saveBook(Book book) {
        showProgress = true;
        getView().showProgress(true);
        apiInteractor.addBook(book)
                .subscribeOn(Schedulers.newThread())
                .flatMap(b -> bookDao.insertInBatch(Collections.singletonList(b)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> {
                    showProgress = false;
                    bookSaved = true;
                    updateView();
                }, Throwable::printStackTrace);
    }

    private void updateView() {
        if (getView() == null) {
            return;
        }
        getView().showProgress(showProgress);
        if(bookSaved) {
            getView().close();
        }
    }
}
