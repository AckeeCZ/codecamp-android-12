package com.codecamp.codecamp12.mvp.view;

/**
 * View of add book screen
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public interface IAddBookView {
    public static final String TAG = IAddBookView.class.getName();

    void clearErrors();

    public void setErrorTitle(int resId);

    public void setErrorAuthor(int resId);

    public void setErrorGenre(int resId);

}
