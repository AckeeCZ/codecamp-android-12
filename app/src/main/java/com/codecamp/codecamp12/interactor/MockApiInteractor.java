package com.codecamp.codecamp12.interactor;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.codecamp.codecamp12.App;
import com.codecamp.codecamp12.domain.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Api Interactors that mocks real api by storing data to file
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/25/2016.
 */
public class MockApiInteractor implements IApiInteractor {
    public static final String TAG = MockApiInteractor.class.getName();
    private static final java.lang.String BOOKS_FILE_NAME = "books.json";
    private static final Gson GSON= new Gson();
    private static final Type BOOK_TYPE = new TypeToken<List<Book>>() {
    }.getType();


    @Inject
    Application mContext;


    public MockApiInteractor() {
        App.getAppComponent().inject(this);
    }


    public MockApiInteractor(Context context) {
        this.mContext = (Application) context.getApplicationContext();
    }

    @Override
    public Observable<List<Book>> getBooks() {
        return Observable.create(sub -> {
            checkBooksFile();
            List<Book> list = null;
            try {
                list = getBooksFromFile();
                sub.onNext(list);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                sub.onError(e);
            }
            sub.onCompleted();
        });
    }

    private void checkBooksFile() {
        File booksFile = getBooksFile();
        if (!booksFile.exists()) { //if our mock file does not exists, copy it from assets folder
            copyMockFile();
        }
    }


    @Override
    public Observable<Book> addBook(Book book) {
        return Observable.create(sub -> {
            try {
                checkBooksFile();
                List<Book> books = getBooksFromFile();
                books.add(book);
                writeBooksToFile(books);
                book.setId(books.size());
                sub.onNext(book);
            }catch (Exception e) {
                sub.onError(e);
            }
            sub.onCompleted();
        });
    }

    private void writeBooksToFile(List<Book> books) throws IOException {
        String text = GSON.toJson(books);
        Writer writer = new BufferedWriter(new FileWriter(getBooksFile()));
        writer.write(text);
        writer.flush();
        writer.close();
    }


    private List<Book> getBooksFromFile() throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(getBooksFile()));
        List<Book> books = GSON.fromJson(reader, BOOK_TYPE);
        return books;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void copyMockFile() {
        AssetManager assetManager = mContext.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(BOOKS_FILE_NAME);
            File outFile = getBooksFile();
            out = new FileOutputStream(outFile);
            copyFile(in, out);
        } catch (IOException e) {
            Log.e(TAG, "Failed to copy asset file: " + BOOKS_FILE_NAME, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }

    }

    private File getBooksFile() {
        return new File(mContext.getFilesDir(), "books.json");
    }

}
