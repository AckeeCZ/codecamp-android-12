package com.codecamp.codecamp12.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.codecamp.codecamp12.R;
import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.mvp.presenter.BookDetailPresenter;
import com.codecamp.codecamp12.mvp.presenter.SearchPresenter;
import com.codecamp.codecamp12.mvp.view.ISearchView;
import com.codecamp.codecamp12.ui.adapter.BooksAdapter;
import com.codecamp.codecamp12.ui.fragment.helper.ItemClickSupport;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Activity for searching in books
 * Created by David Bilik[david.bilik@ackee.cz] on {26/05/16}
 **/
@RequiresPresenter(SearchPresenter.class)
public class SearchActivity extends NucleusAppCompatActivity<SearchPresenter> implements ISearchView {
    public static final String TAG = SearchActivity.class.getName();
    private static final long SEARCH_DELAY_MILLIS = 300;

    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.btn_clear)
    View btnClear;
    @BindView(android.R.id.empty)
    FrameLayout emptyLayout;
    @BindView(android.R.id.list)
    RecyclerView recyclerView;
    @BindView(android.R.id.progress)
    View progress;
    private Subscription searchSubscribtion;
    private BooksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initToolbar();
        initRecyclerView();
        initEmptyView();
        initSearchView();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v->finish());
    }

    private void initRecyclerView() {
        adapter = new BooksAdapter(BooksAdapter.Type.LIST, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener((recyclerView1, position, v) -> SearchActivity.this.onItemClicked(adapter.getItem(position)));
    }

    public void onItemClicked(Book book) {
        Intent i = new Intent(this, BookDetailActivity.class);
        i.putExtras(BookDetailPresenter.getArguments(book));
        startActivity(i);

    }

    private void initEmptyView() {
        emptyLayout.addView(LayoutInflater.from(this).inflate(R.layout.view_empty_list, emptyLayout, false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (searchSubscribtion != null && !searchSubscribtion.isUnsubscribed()) {
            searchSubscribtion.unsubscribe();
        }
    }

    private void initSearchView() {
        searchSubscribtion = RxTextView
                .textChangeEvents(editSearch)
                .doOnNext(event -> {
                    boolean emptyQuery = TextUtils.isEmpty(event.text());
                    btnClear.setVisibility(emptyQuery ? View.GONE : View.VISIBLE);
                    showEmpty(emptyQuery);
                })
                .debounce(SEARCH_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onTextChangeEvent -> {
                    boolean emptyQuery = TextUtils.isEmpty(onTextChangeEvent.text());
                    if (!emptyQuery) {
                        getPresenter().onSearchQuery(onTextChangeEvent.text().toString());
                    } else {
                        adapter.setData(null);
                    }
                });

        btnClear.setOnClickListener(v -> editSearch.setText(""));
    }

    @Override
    public void showEmpty(boolean show) {
        emptyLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgress(boolean show) {
        Log.d(TAG, "showProgress() called with: " + "show = [" + show + "]");
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showData(List<Book> data) {
        adapter.setData(data);
    }


}
