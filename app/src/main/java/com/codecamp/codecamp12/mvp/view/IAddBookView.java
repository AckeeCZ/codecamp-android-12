package com.codecamp.codecamp12.mvp.view;

/**
 * View of add book screen
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public interface IAddBookView {
    public static final String TAG = IAddBookView.class.getName();

    void clearErrors();

    void setErrorTitle(int resId);

    void setErrorAuthor(int resId);

    void setErrorGenre(int resId);

    void showProgress(boolean show);

    void close();
}
