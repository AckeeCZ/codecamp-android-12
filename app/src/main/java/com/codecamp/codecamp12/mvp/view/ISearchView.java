package com.codecamp.codecamp12.mvp.view;

import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.mvp.view.base.IListView;
import com.codecamp.codecamp12.mvp.view.base.IProgressView;

/**
 * View of searh screen
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public interface ISearchView extends IListView<Book>, IProgressView {
    public static final String TAG = ISearchView.class.getName();
}
