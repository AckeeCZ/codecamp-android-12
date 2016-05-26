package com.codecamp.codecamp12.ui.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codecamp.codecamp12.R;
import com.codecamp.codecamp12.domain.model.Book;
import com.codecamp.codecamp12.mvp.presenter.BookDetailPresenter;
import com.codecamp.codecamp12.mvp.presenter.BooksPresenter;
import com.codecamp.codecamp12.mvp.view.IBooksView;
import com.codecamp.codecamp12.ui.activity.BookDetailActivity;
import com.codecamp.codecamp12.ui.adapter.BooksAdapter;
import com.codecamp.codecamp12.ui.fragment.helper.ItemClickSupport;
import com.codecamp.codecamp12.ui.fragment.helper.RecyclerViewHelper;

import java.util.List;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;
import rx.functions.Action1;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/26/2016.
 */
@RequiresPresenter(BooksPresenter.class)
public abstract class BooksFragment extends NucleusSupportFragment<BooksPresenter>
        implements RecyclerViewHelper.RecyclerViewHelperInterface, IBooksView {
    public static final String TAG = BooksFragment.class.getName();

    protected RecyclerViewHelper recyclerViewHelper;

    protected BooksAdapter adapter;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        if (savedState == null) {
            getPresenter().loadBooks(getFeedType());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        adapter = new BooksAdapter(getAdapterType(), null);
adapter.setCheckListener((b, isChecked)-> {
    getPresenter().setFeatured(b, isChecked);
});
        recyclerViewHelper = RecyclerViewHelper.attach(this);
        recyclerViewHelper.onViewCreated(view);
        recyclerViewHelper.setEmptyResId(R.layout.view_empty_list);

        recyclerViewHelper.setOnItemClickListener((recyclerView, position, v) -> {
            Intent i = new Intent(getActivity(), BookDetailActivity.class);
            i.putExtras(BookDetailPresenter.getArguments(adapter.getItem(position)));
            startActivity(i);
        });
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void showData(List<Book> data) {
        adapter.setData(data);
    }

    protected abstract BooksAdapter.Type getAdapterType();

    protected abstract BooksPresenter.FeedType getFeedType();

    @Override
    public void showProgress(boolean show) {
        recyclerViewHelper.showProgress(show);
    }

    @Override
    public void showEmpty(boolean show) {
        recyclerViewHelper.showEmpty(show);
    }
}
