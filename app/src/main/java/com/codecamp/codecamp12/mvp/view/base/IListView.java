package com.codecamp.codecamp12.mvp.view.base;

import java.util.List;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/26/2016.
 */
public interface IListView<T> {
    void showData(List<T> data);
}
