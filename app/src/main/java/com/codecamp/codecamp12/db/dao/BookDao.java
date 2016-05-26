package com.codecamp.codecamp12.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.codecamp.codecamp12.db.dao.base.BaseDao;
import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.domain.model.BookMapper;

import java.util.List;

import rx.Observable;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/25/2016.
 */
public class BookDao extends BaseDao<Book> {
    public static final String TAG = BookDao.class.getName();

    @Override
    public long insertItem(Book book) {
        return db.insert(Book.TABLE_NAME,
                BookMapper.contentValues()
                        .id(book.getId())
                        .title(book.getTitle())
                        .description(book.getDescription())
                        .author(book.getAuthor())
                        .genre(book.getGenre())
                        .color(book.getColor())
                        .image(book.getImage())
                        .build(), SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    protected String getTableName() {
        return Book.TABLE_NAME;
    }

    @Override
    public void createTable(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE(Book.TABLE_NAME,
                Book.COL_ID + " INTEGER PRIMARY KEY",
                Book.COL_TITLE + " TEXT",
                Book.COL_DESCRIPTION + " TEXT",
                Book.COL_AUTHOR + " TEXT",
                Book.COL_GENRE + " TEXT",
                Book.COL_COLOR + " TEXT",
                Book.COL_IMAGE + " INTEGER DEFAULT 0")
                .getSql());
    }

    public Observable<List<Book>> searchBooks(String query) {
        return query(SELECT("*").FROM(Book.TABLE_NAME).WHERE(Book.COL_TITLE + " LIKE ?"))
                .args("%" + query + "%")
                .run()
                .mapToList(BookMapper.MAPPER);
    }
}
