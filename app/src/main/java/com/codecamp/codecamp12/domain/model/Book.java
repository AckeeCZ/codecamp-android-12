package com.codecamp.codecamp12.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.sqlbrite.objectmapper.annotation.Column;
import com.hannesdorfmann.sqlbrite.objectmapper.annotation.ObjectMappable;

/**
 * Book POJO.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/2/2016.
 */
@ObjectMappable
public class Book implements Parcelable {
    public static final String TAG = Book.class.getName();
    public static final String TABLE_NAME = "books";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_AUTHOR = "author";
    public static final String COL_GENRE = "genre";
    public static final String COL_COLOR = "color";
    public static final String COL_IMAGE = "image";

    @Column(COL_ID)
    private int id;

    @Column(COL_TITLE)
    private String title;

    @Column(COL_DESCRIPTION)
    private String description;

    @Column(COL_AUTHOR)
    private String author;

    @Column(COL_GENRE)
    private String genre;

    @Column(COL_COLOR)
    private String color;

    @Column(COL_IMAGE)
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.author);
        dest.writeString(this.genre);
        dest.writeString(this.color);
        dest.writeString(this.image);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.author = in.readString();
        this.genre = in.readString();
        this.color = in.readString();
        this.image = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
