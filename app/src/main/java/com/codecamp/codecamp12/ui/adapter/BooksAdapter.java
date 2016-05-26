package com.codecamp.codecamp12.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecamp.codecamp12.R;
import com.codecamp.codecamp12.domain.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for books list.
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 4/2/2016.
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    public static final String TAG = BooksAdapter.class.getName();

    private List<Book> books;
    private Type type;

    public BooksAdapter(Type type, List<Book> books) {
        this.books = books;
        this.type = type;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (type) {
            case LIST:
                return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false));
            case CARD:
                return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_book, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bindView(books.get(position));
    }

    public void setData(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return books == null ? 0 : books.size();
    }

    abstract class BookViewHolder extends RecyclerView.ViewHolder {

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void bindView(Book book);
    }

    class ListViewHolder extends BookViewHolder {

        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_author)
        TextView txtAuthor;
        @BindView(R.id.indicator_color)
        View indicatorColor;

        public ListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Book book) {
            indicatorColor.setBackgroundColor(TextUtils.isEmpty(book.getColor()) ? Color.WHITE : Color.parseColor(book.getColor()));
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
        }
    }

    class CardViewHolder extends BookViewHolder {

        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_author)
        TextView txtAuthor;
        @BindView(R.id.txt_genre)
        TextView txtGenre;
        @BindView(R.id.image)
        ImageView image;

        public CardViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(Book book) {
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            txtGenre.setText(book.getGenre());

            if (book.getImage() != null) {
                image.setVisibility(View.VISIBLE);
                Picasso.with(itemView.getContext()).load(book.getImage()).into(image);
            } else {
                image.setVisibility(View.GONE);
            }
        }
    }

    public enum Type {
        LIST, CARD
    }
}
