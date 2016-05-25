package com.codecamp.codecamp12.db.dao.base;

import android.database.sqlite.SQLiteDatabase;

import com.hannesdorfmann.sqlbrite.dao.Dao;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

/**
 * Base DAO with some utility methods
 * Created by David Bilik[david.bilik@ackee.cz] on {04/02/16}
 **/
public abstract class BaseDao<T> extends Dao {
    public static final String TAG = BaseDao.class.getName();

    public Observable<Boolean> insertInBatch(List<T> data) {
        return Observable.create(sub -> {
            BriteDatabase.Transaction t = newTransaction();
            try {
                for(T d: data) {
                    insertItem(d);
                }
                t.markSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                t.end();
            }
            sub.onNext(true);
            sub.onCompleted();
        });
    }

    public Observable<Boolean> updateData(List<T> data) {
        return Observable.create(sub -> {
            BriteDatabase.Transaction t = newTransaction();
            try {
                clearTable();

                for(T d: data) {
                    insertItem(d);
                }
                t.markSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                t.end();
            }
            sub.onNext(true);
            sub.onCompleted();
        });
    }

    public abstract long insertItem(T d);

    public long clearTable() {
        return db.delete(getTableName(), null);
    }

    protected abstract String getTableName();

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
