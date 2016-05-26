package com.codecamp.codecamp12.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.domain.model.BookMapper;
import com.codecamp.codecamp12.domain.model.Featured;
import com.hannesdorfmann.sqlbrite.dao.Dao;

import rx.Observable;

/**
 * Dao for featured books
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public class FeatureDao extends Dao {
    public static final String TAG = FeatureDao.class.getName();

    @Override
    public void createTable(SQLiteDatabase database) {
        CREATE_TABLE(Featured.TABLE_NAME,
                Book.COL_ID + " INTEGER PRIMARY KEY"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Observable<Void> setFeatured(int bookId, boolean isFavorite) {
        if (isFavorite) {
            return insert(Featured.TABLE_NAME, BookMapper.contentValues().id(bookId).build(), SQLiteDatabase.CONFLICT_REPLACE)
                    .map(id -> null);
        }
        return delete(Featured.TABLE_NAME, Book.COL_ID + " = ?", String.valueOf(bookId))
                .map(id -> null);
    }
}
