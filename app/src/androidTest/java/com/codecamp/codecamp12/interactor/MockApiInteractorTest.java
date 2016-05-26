package com.codecamp.codecamp12.interactor;

import android.test.AndroidTestCase;

import com.codecamp.codecamp12.domain.model.Book;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

/**
 * Test fpr a[o omteractpr
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
public class MockApiInteractorTest {
    private MockApiInteractor apiInteractor;

    @Before
    public void setUp() throws Exception {
        this.apiInteractor = new MockApiInteractor(getTargetContext());
        File f = new File(getTargetContext().getFilesDir(), "books.json");
        f.delete();
    }

    @Test
    public void testGetBooks() throws Exception {
        List<Book> books = apiInteractor.getBooks().toBlocking().first();
        assertTrue(books.size() == 6);
        assertTrue(books.get(0).getId() == 1);
    }

    @Test
    public void testAddBook() {
        Book b = new Book();
        b.setAuthor("David Bilik");
        b.setId(8);
        b.setTitle("Title");
        this.apiInteractor.addBook(b).toBlocking().first();
        List<Book> books = apiInteractor.getBooks().toBlocking().first();
        assertTrue(books.size() == 7);
        assertTrue(books.get(books.size() - 1).getId() == 8);
    }
}