package com.codecamp.codecamp12.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codecamp.codecamp12.R;
import com.codecamp.codecamp12.mvp.presenter.FeedPresenter;
import com.codecamp.codecamp12.mvp.view.IFeedView;
import com.codecamp.codecamp12.ui.activity.AddBookActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;

/**
 * TODO: add a comment
 * Created by Georgiy Shur (georgiy.shur@ackee.cz) on 5/25/2016.
 */
@RequiresPresenter(FeedPresenter.class)
public class FeedFragment extends NucleusSupportFragment<FeedPresenter> implements IFeedView {
    public static final String TAG = FeedFragment.class.getName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        getActivity().setTitle(getString(R.string.nav_feed));
        initViewPager();
    }

    private void initViewPager() {
        viewPager.setAdapter(new FragmentAdapter(getActivity(), getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.fab)
    public void onFabClicked() {
        startActivity(new Intent(getActivity(), AddBookActivity.class));
    }

    public static class FragmentAdapter extends FragmentPagerAdapter {

        private Context context;

        public FragmentAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BooksListFragment();
                case 1:
                    return new CardBooksFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return context.getString(R.string.all);
                case 1:
                    return context.getString(R.string.featured);
            }
            return null;
        }
    }
}
