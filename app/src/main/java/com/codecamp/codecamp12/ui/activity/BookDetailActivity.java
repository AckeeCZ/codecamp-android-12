package com.codecamp.codecamp12.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecamp.codecamp12.R;
import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.mvp.presenter.BookDetailPresenter;
import com.codecamp.codecamp12.mvp.view.IBookDetailView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Activity with book detail ui
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
@RequiresPresenter(BookDetailPresenter.class)
public class BookDetailActivity extends NucleusAppCompatActivity<BookDetailPresenter> implements IBookDetailView {
    public static final String TAG = BookDetailActivity.class.getName();

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.backdrop)
    ImageView image;
    @BindView(R.id.txt_author)
    TextView txtAuthor;
    @BindView(R.id.txt_genre)
    TextView txtGenre;
    @BindView(R.id.txt_description)
    TextView txtDescription;
    @BindView(R.id.fab_featured)
    FloatingActionButton fabFeatured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getPresenter().onActivityCreated(getIntent().getExtras());
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public void showBook(Book book) {
        collapsingToolbarLayout.setTitle(book.getTitle());
        collapsingToolbarLayout.setBackgroundColor(Book.getColor(this, book));
        collapsingToolbarLayout.setStatusBarScrimColor(Book.getColor(this, book));
        if (book.getImage() != null) {
            Picasso.with(this).load(book.getImage()).into(image);
        }
        showOrHideText(getString(R.string.book_author), book.getAuthor(), txtAuthor);
        showOrHideText(getString(R.string.book_genre), book.getGenre(), txtGenre);
        showOrHideText(null, book.getDescription(), txtDescription);
        fabFeatured.setImageResource(book.isFeatured() ? R.drawable.ic_fab_like : R.drawable.ic_fab_like_full);
    }

    private void showOrHideText(String key, String text, TextView textView) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(key)) {
                textView.setText(Html.fromHtml("<b>" + key + ":</b> " + text));
            } else {
                textView.setText(text);
            }
        }
    }

    @OnClick(R.id.fab_featured)
    public void onFabClicked() {
        getPresenter().onFabClicked();
    }
}
