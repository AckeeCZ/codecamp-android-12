package com.codecamp.codecamp12.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecamp.codecamp12.R;
import com.codecamp.codecamp12.domain.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.functions.Action2;

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

    Action2<Book, Boolean> checkListener;

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

    public Book getItem(int position) {
        return books.get(position);
    }



    public void setCheckListener(Action2<Book, Boolean> checkListener) {
        this.checkListener = checkListener;
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
        @BindView(R.id.check_featured)
        CheckBox checkFeatured;

        public ListViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindView(Book book) {
            indicatorColor.setBackgroundColor(Book.getColor(itemView.getContext(), book));
            txtTitle.setText(book.getTitle());
            txtAuthor.setText(book.getAuthor());
            checkFeatured.setOnCheckedChangeListener(null);
            checkFeatured.setChecked(book.isFeatured());
            checkFeatured.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(checkListener != null) {
                    checkListener.call(book, isChecked);
                }
            });
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
