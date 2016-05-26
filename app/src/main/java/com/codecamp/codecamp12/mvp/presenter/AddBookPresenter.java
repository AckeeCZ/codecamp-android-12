package com.codecamp.codecamp12.mvp.presenter;

import android.text.TextUtils;

import com.codecamp.codecamp12.R;
import com.codecamp.codecamp12.mvp.view.IAddBookView;

import nucleus.presenter.RxPresenter;

/**
 * Presenter for add book screen
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public class AddBookPresenter extends RxPresenter<IAddBookView> {
    public static final String TAG = AddBookPresenter.class.getName();

    public void onSaveClicked(String title, String genre, String author) {
        getView().clearErrors();
        if (validate(title, genre, author)) {
            saveBook();
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

    private void saveBook() {
        //TODO handle saving
    }
}
